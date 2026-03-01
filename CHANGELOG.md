<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# agents-skills-finder Changelog

## [Unreleased]

## [0.0.2] - 2026-03-01
### Added
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)
- Agent Skills Finder functionality to discover and index skills from `.agents/skills` directory
- Tool window for searching and browsing skills
- Clipboard integration for copying skill paths
- Plugin icons (pluginIcon.svg and pluginIcon_dark.svg) following JetBrains requirements

### Changed
- Improved plugin.xml configuration following JetBrains best practices
- Updated platform compatibility to IntelliJ 2024.2+ (branch 242) for broader compatibility
- Enhanced plugin metadata with proper description and vendor information
- Optimized Plugin Verifier configuration
- Replaced oversized cetro-solid.svg with compliant 40x40px plugin icons

### Removed
- Non-compliant cetro-solid.svg icon (15KB, 512x512px)
