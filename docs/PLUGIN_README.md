# Agent Skills Finder - IntelliJ Plugin

Plugin de IntelliJ IDEA para buscar y filtrar skills del directorio `.agents/skills/`.

## Características

- **Tool Window**: Panel lateral derecho con tabla de skills
- **Búsqueda flexible**: 
  - Búsqueda por tokens (separar términos con espacios)
  - Fuzzy matching con algoritmo Levenshtein (>80% similitud)
  - Busca en nombre y descripción
- **Copiar path**: Click derecho o doble-click para copiar el path relativo
- **Carga en background**: Escaneo async sin bloquear la UI
- **Notificaciones**: Feedback visual al copiar paths

## Arquitectura

```
src/main/kotlin/.../
├── model/
│   ├── Skill.kt          # Modelo de datos del skill
│   └── ParseResult.kt    # Resultado del parsing
├── parser/
│   └── SkillParser.kt    # Parser YAML front matter
├── scanner/
│   └── SkillsFileScanner.kt  # Escaneo en background
├── services/
│   └── SkillsService.kt  # Service con cache in-memory
├── toolWindow/
│   ├── SkillsTableModel.kt    # Table model
│   ├── SkillRowFilter.kt      # Filtro de búsqueda
│   ├── SkillsToolWindowContent.kt  # UI principal
│   └── MyToolWindowFactory.kt # Factory del tool window
└── listeners/
    └── ProjectOpenListener.kt # Listener para cargar skills
```

## Dependencias

- **SnakeYAML 2.3**: Parser YAML para front matter
- **Apache Commons Text 1.11.0**: Algoritmos de búsqueda fuzzy

## Formato de SKILL.md

```yaml
---
name: skill-name
description: "Descripción del skill"
source: community
risk: safe
---

# Contenido del skill
```

## Búsqueda Avanzada

El sistema de búsqueda soporta:

1. **Búsqueda exacta**: Escribe el texto exacto a buscar
2. **Búsqueda por tokens**: "java spring" encuentra skills con "java" O "spring"
3. **Fuzzy matching**: "java-pro" encuentra "java-pro" aunque tenga errores tipográficos
4. **Búsqueda en ambos campos**: Busca en nombre y descripción

## Uso

1. Abre un proyecto que contenga `.agents/skills/`
2. Busca el tool window "Agent Skills" en el panel derecho
3. Escribe en el campo de búsqueda para filtrar
4. Click derecho o doble-click en una fila para copiar el path

## Build

```bash
./gradlew buildPlugin    # Construir plugin
./gradlew runIde         # Probar en IDE
```

## Path Copiado

El path copiado tiene el formato: `.agents/skills/nombre-skill`

Listo para usar en workflows agenticos.

---

**Versión**: 1.0
**Fecha**: 2026-02-26
