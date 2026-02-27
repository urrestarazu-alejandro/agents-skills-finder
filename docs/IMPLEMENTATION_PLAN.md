# Plan de Implementación: Plugin IntelliJ Agent Skills Finder

## Resumen
Crear un plugin de IntelliJ IDEA que busca skills en `.agents/skills/`, filtra por descripción y permite copiar el path relativo para usar en workflows agenticos.

## Arquitectura

### Componentes Principales
1. **SkillParser** - Parsea YAML front matter de SKILL.md
2. **SkillsFileScanner** - Escanea directorio en background thread
3. **SkillsService** - Service de proyecto con cache de skills
4. **SkillsToolWindowContent** - UI principal con tabla y búsqueda

### Stack Técnico
- **YAML Parser**: SnakeYAML 2.3 (ligero, maduro, usado por IntelliJ)
- **UI**: JBTable + TableRowSorter para filtrado client-side
- **Threading**: Background task con ProgressIndicator
- **Cache**: CopyOnWriteArrayList en SkillsService

## Decisiones de Diseño (basadas en respuestas del usuario)
- ✅ Tool Window (panel lateral derecho)
- ✅ Mostrar: Name, Description, Path
- ✅ Botón "Copy Path" explícito
- ✅ Menú contextual con opciones adicionales

## Archivos Críticos a Modificar/Crear

### 1. Dependencias
**Archivo**: `build.gradle.kts`
```kotlin
dependencies {
    implementation("org.yaml:snakeyaml:2.3")
    // ... existing dependencies
}
```

### 2. Modelo de Datos
**Archivo**: `src/main/kotlin/.../model/Skill.kt`
```kotlin
data class Skill(
    val name: String,
    val description: String,
    val relativePath: String, // .agents/skills/skill-name
    val source: String? = null,
    val risk: String? = null,
    val filePath: String
)
```

**Archivo**: `src/main/kotlin/.../model/ParseResult.kt`
```kotlin
sealed class ParseResult {
    data class Success(val skill: Skill) : ParseResult()
    data class Error(val filePath: String, val reason: String) : ParseResult()
}
```

### 3. Parser YAML
**Archivo**: `src/main/kotlin/.../parser/SkillParser.kt`
- Extrae front matter entre delimitadores `---`
- Parsea YAML con SnakeYAML
- Extrae campos: name, description, source, risk
- Calcula path relativo desde project base
- Maneja errores gracefully (retorna ParseResult.Error)

### 4. Scanner de Archivos
**Archivo**: `src/main/kotlin/.../scanner/SkillsFileScanner.kt`
- Escanea `.agents/skills/` en background thread
- Usa `Task.Backgroundable` con `ProgressIndicator`
- Busca `SKILL.md` en cada subdirectorio
- Llama a SkillParser para cada archivo
- Callback con lista de skills parseados

### 5. Service Layer
**Archivo**: `src/main/kotlin/.../services/SkillsService.kt`
- `@Service(Service.Level.PROJECT)`
- Cache in-memory con `CopyOnWriteArrayList<Skill>`
- `loadSkillsAsync()` - carga inicial en background
- `getSkills()` - retorna cache
- `reload()` - recarga skills
- Listeners para notificar cuando skills están cargados

### 6. UI Components

**Archivo**: `src/main/kotlin/.../toolWindow/SkillsTableModel.kt`
- Extiende `AbstractTableModel`
- 3 columnas: Name, Description, Path
- Métodos: `setSkills()`, `getSkillAt(rowIndex)`

**Archivo**: `src/main/kotlin/.../toolWindow/SkillRowFilter.kt`
- Extiende `RowFilter<SkillsTableModel, Int>`
- Filtra por description (case-insensitive)
- Retorna true si description contiene search text

**Archivo**: `src/main/kotlin/.../toolWindow/SkillsToolWindowContent.kt`
- Layout: BorderLayout
  - NORTH: JBTextField para búsqueda
  - CENTER: JBTable con JBScrollPane
  - SOUTH: JBLabel para status
- TableRowSorter con SkillRowFilter
- Debounce de 300ms en search field
- Doble-click o menú contextual para copiar path
- Usa `CopyPasteManager` para clipboard
- Muestra notificaciones con `Notifications.Bus`

**Archivo**: `src/main/kotlin/.../toolWindow/MyToolWindowFactory.kt` (MODIFICAR)
- Reemplazar contenido dummy con `SkillsToolWindowContent`
- `shouldBeAvailable()` verifica que `.agents/skills/` existe

### 7. Listeners
**Archivo**: `src/main/kotlin/.../listeners/ProjectOpenListener.kt`
- Implementa `ProjectManagerListener`
- En `projectOpened()`: inicia carga de skills en background

### 8. Configuración Plugin
**Archivo**: `src/main/resources/META-INF/plugin.xml`
```xml
<extensions defaultExtensionNs="com.intellij">
    <toolWindow
        factoryClass="...MyToolWindowFactory"
        id="Agent Skills"
        anchor="right"/>
    <projectService serviceImplementation="...SkillsService"/>
</extensions>

<projectListeners>
    <listener
        class="...ProjectOpenListener"
        topic="com.intellij.openapi.project.ProjectManagerListener"/>
</projectListeners>
```

**Archivo**: `src/main/resources/messages/MyBundle.properties`
```properties
skills.status.loading=Loading skills...
skills.status.total={0} skills
skills.notification.copied=Copied: {0}
```

## Secuencia de Implementación

### Paso 1: Setup Dependencias
1. Agregar SnakeYAML a `build.gradle.kts`
2. Sync Gradle

### Paso 2: Modelo y Parser (sin dependencias)
1. Crear `model/Skill.kt`
2. Crear `model/ParseResult.kt`
3. Crear `parser/SkillParser.kt` con lógica de extracción YAML
4. Tests: `parser/SkillParserTest.kt`

### Paso 3: Scanner y Service (depende de Paso 2)
1. Crear `scanner/SkillsFileScanner.kt` con background task
2. Crear `services/SkillsService.kt` con cache y async loading
3. Tests: `scanner/SkillsFileScannerTest.kt`, `services/SkillsServiceTest.kt`

### Paso 4: UI (depende de Pasos 2 y 3)
1. Crear `toolWindow/SkillsTableModel.kt`
2. Crear `toolWindow/SkillRowFilter.kt`
3. Crear `toolWindow/SkillsToolWindowContent.kt` con layout completo
4. Modificar `toolWindow/MyToolWindowFactory.kt`

### Paso 5: Integración
1. Crear `listeners/ProjectOpenListener.kt`
2. Actualizar `plugin.xml` con services, listeners, tool window config
3. Actualizar `MyBundle.properties` con i18n strings

### Paso 6: Testing Manual
1. Build plugin: `./gradlew buildPlugin`
2. Run IDE: `./gradlew runIde`
3. Verificar tool window aparece
4. Verificar carga de ~900 skills
5. Probar búsqueda
6. Probar copy path

## Manejo de Errores

### Parser
- YAML malformado → Log warning, skip skill
- Campos faltantes → Log warning con path
- Archivos ilegibles → Log error, skip

### Scanner
- Directorio no existe → Mostrar tabla vacía
- Permisos insuficientes → Log error + notificación
- Cancelación → Respetar `indicator.isCanceled`

### UI
- No hay skills → Mostrar "No skills found"
- Filter error → Mostrar todos (degradación graceful)
- Clipboard error → Notificación con mensaje

## Performance

### Objetivos
- **Load time**: < 2 segundos para 900 skills
- **Search**: < 50ms para filtrar
- **Memory**: ~1MB (1KB por skill promedio)

### Estrategias
- Background thread para scanning
- TableRowSorter (filtrado client-side, muy rápido)
- Debounce de 300ms en search field
- Cache in-memory single por proyecto
- JBTable virtualiza rows (solo visibles renderizados)

## Verificación End-to-End

### Checklist de Testing
1. ✅ Abrir proyecto con `.agents/skills/`
2. ✅ Tool Window "Agent Skills" aparece en sidebar derecho
3. ✅ Status muestra "Loading skills..." → "900 skills"
4. ✅ Tabla muestra 3 columnas: Name, Description, Path
5. ✅ Search field filtra por description (case-insensitive)
6. ✅ Status actualiza: "X of 900 skills" durante filtrado
7. ✅ Click derecho → menú contextual con "Copy Path"
8. ✅ Click en "Copy Path" copia `.agents/skills/skill-name` al clipboard
9. ✅ Notificación muestra "Copied: .agents/skills/..."
10. ✅ Doble-click en row también copia path
11. ✅ UI permanece responsive durante carga

### Testing de Edge Cases
- Proyecto sin `.agents/skills/` → Tool Window oculto
- SKILL.md sin front matter → Skill ignorado, warning logged
- SKILL.md con YAML inválido → Skill ignorado, warning logged
- Búsqueda con texto especial (regex chars) → Funciona correctamente
- Cancelar carga → Scanner se detiene gracefully

### Validación de Paths
Ejemplo de path copiado:
```
.agents/skills/3d-web-experience
```

Este path debe ser:
- Relativo al content root
- Sin slash inicial
- Listo para usar en workflows agenticos

## Archivos Críticos (Resumen)

### Nuevos
- `src/main/kotlin/.../model/Skill.kt`
- `src/main/kotlin/.../model/ParseResult.kt`
- `src/main/kotlin/.../parser/SkillParser.kt`
- `src/main/kotlin/.../scanner/SkillsFileScanner.kt`
- `src/main/kotlin/.../services/SkillsService.kt`
- `src/main/kotlin/.../toolWindow/SkillsTableModel.kt`
- `src/main/kotlin/.../toolWindow/SkillRowFilter.kt`
- `src/main/kotlin/.../toolWindow/SkillsToolWindowContent.kt`
- `src/main/kotlin/.../listeners/ProjectOpenListener.kt`

### Modificados
- `build.gradle.kts` (agregar SnakeYAML)
- `src/main/kotlin/.../toolWindow/MyToolWindowFactory.kt` (usar nuevo content)
- `src/main/resources/META-INF/plugin.xml` (registrar service + listener)
- `src/main/resources/messages/MyBundle.properties` (agregar i18n)

## Notas de Implementación

### YAML Front Matter Format
```yaml
---
name: skill-name
description: "Description here"
source: community
risk: unknown
---
```

### Ejemplo de Uso Final
1. Usuario abre proyecto con skills
2. Abre tool window "Agent Skills"
3. Ve tabla con 900+ skills
4. Escribe "3D" en search → filtra a skills con "3D" en description
5. Click derecho en "3d-web-experience" → Copy Path
6. Path `.agents/skills/3d-web-experience` copiado al clipboard
7. Usuario pega en workflow agentico

---

**Fecha de creación**: 2026-02-26
**Versión**: 1.0
**Estado**: Aprobado para implementación
