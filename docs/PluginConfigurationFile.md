The plugin.xml configuration file contains all the information about the plugin, which is displayed in the plugins'
settings dialog, and all registered extensions, actions, listeners, etc. The sections below describe all the elements in
detail.

The example plugin.xml files can be found in the IntelliJ SDK Docs Code Samples repository.

Additional Plugin Configuration Files﻿
A plugin can contain additional configuration files beside the main plugin.xml. They have the same format, and they are
included with the config-file attribute of <depends> elements specifying plugin dependencies. However, some elements and
attributes required in plugin.xml are ignored in additional configuration files. If the requirements differ, the
documentation below will state it explicitly. One use case for additional configuration files is when a plugin provides
optional features that are only available in some IDEs and require certain modules.

Useful Resources﻿
Please make sure to follow the guidelines from Best practices for listing your plugin for an optimal presentation of
your plugin on JetBrains Marketplace. The Busy Plugin Developers. Episode 2 discusses 5 tips for optimizing JetBrains
Marketplace plugin page in more detail.

See also Marketing about widgets and badges.

Configuration Structure Overview﻿
Private Configuration Elements
If an element or an attribute is not documented on this page, consider them as configuration items intended to be used
by JetBrains only. They must not be used by third-party plugins.

Deprecated elements are omitted in the list below.

Elements described on this page are available in quick documentation since IntelliJ IDEA 2025.1.

The Plugin DevKit plugin must be installed and enabled.

<idea-plugin>

<id>

<name>

<version>

<product-descriptor>

<idea-version>

<vendor>

<description>

<change-notes>

<depends>

<incompatible-with>

<extensions>

An Extension

<extensionPoints>

<extensionPoint>

<with>

<resource-bundle>

<actions>

<action>

<add-to-group>

<keyboard-shortcut>

<mouse-shortcut>

<override-text>

<synonym>

<abbreviation>

<group>

<action>

<add-to-group>

<keyboard-shortcut>

<mouse-shortcut>

<override-text>

<synonym>

<abbreviation>

<add-to-group>

<override-text>

<reference>

<add-to-group>

<separator>

<add-to-group>

<reference>

<add-to-group>

<applicationListeners>

<listener>

<projectListeners>

<listener>

<xi:include>

<xi:fallback>

idea-plugin﻿
The plugin.xml file root element.

Required
yes

Attributes
url (optional; ignored in an additional config file)
The link to the plugin homepage displayed on the plugin page in the JetBrains Marketplace.

require-restart (optional)

The boolean value determining whether the plugin installation, update, or uninstallation requires an IDE restart (see
Dynamic Plugins for details).
Default value: false.

Children
<actions>

<applicationListeners>

<change-notes>

<depends>

<description>

<extensionPoints>

<extensions>

<id>

<idea-version>

<incompatible-with>

<name>

<product-descriptor>

<projectListeners>

<resource-bundle>

<vendor>

<version>

<xi:include>

<application-components> Deprecated

<module-components> Deprecated

<project-components> Deprecated

id﻿
A unique identifier of the plugin. It should be a fully qualified name similar to Java packages and must not collide
with the ID of existing plugins. The ID is a technical value used to identify the plugin in the IDE and JetBrains
Marketplace. Please use characters, numbers, and '.'/'-'/'_' symbols only and keep it reasonably short.

Make sure to pick a stable ID, as the value cannot be changed later after public release.

Required
no; ignored in an additional config file
It is highly recommended to set in plugin.xml file.
The element can be skipped in the source plugin.xml file if the Gradle plugin patchPluginXml task (2.x, 1.x) is enabled
and configured.

Default value
Value of the <name> element.

Example
<id>com.example.framework</id>
name﻿
Reference: JetBrains Marketplace: Plugin Name

The user-visible plugin display name (Title Case).

Required
yes; ignored in an additional config file

Example
<name>My Framework Support</name>
version﻿
Reference: JetBrains Marketplace: Semantic Versioning

The plugin version displayed in the Plugins settings dialog and on the JetBrains Marketplace plugin page. Plugins
uploaded to the JetBrains Marketplace must follow semantic versioning.

Required
yes; ignored in an additional config file
The element can be skipped in the source plugin.xml file if the Gradle plugin patchPluginXml task (2.x, 1.x) is enabled
and configured.

Example
<version>1.3.18</version>
product-descriptor﻿
Reference: JetBrains Marketplace: How to add required parameters for paid plugins

Paid or Freemium plugin descriptor.

Required
only for paid or freemium plugins; ignored in an additional config file
Do not add <product-descriptor> element in a free plugin.

Attributes
code (required)
The plugin product code used in the JetBrains Sales System. The code must be agreed with JetBrains in advance and follow
the requirements.

release-date (required)
Date of the major version release in the YYYYMMDD format.

release-version (required)
A major version in a specific number format, for example, 20242 for the 2024.2 major release.
See release-version constraints for more details.

optional (optional)
The boolean value determining whether the plugin is a Freemium plugin.
Default value: false.

eap (optional)

Specifies the boolean value determining whether the plugin is an EAP release.
Default value: false.

idea-version﻿
Reference: Build Number Ranges

The plugin's range of compatible IntelliJ-based IDE versions.

Required
yes; ignored in an additional config file
The element can be skipped in the source plugin.xml file if the Gradle plugin patchPluginXml task (2.x, 1.x) is enabled
and configured.

Attributes
since-build (required)
The lowest IDE version compatible with the plugin.

until-build (optional)
The highest version of the IDE the plugin is compatible with. It's highly recommended not to set this attribute, so the
plugin will be compatible with all IDE versions since the version specified by the since-build.
If it becomes necessary to specify the highest compatible IDE version later, it'll be possible to do that via JetBrains
Marketplace.
Only if the publishing process for the plugin is configured to upload a new version for each major IDE version, it makes
sense to limit the highest compatible IDE version from the beginning. In that case, use strict-until-build instead.

strict-until-build (optional; available since 2025.3)

The highest version of the IDE the plugin is compatible with.
Use this attribute only if the publishing process for the plugin is configured to upload a new version for each major
IDE version. Otherwise, skip this attribute.
If it becomes necessary to specify the highest compatible IDE version later, it'll be possible to do that via JetBrains
Marketplace.

Examples
Compatibility with a specific build number (2021.3.3) and higher versions:

<idea-version since-build="213.7172.25"/>
Compatibility with versions from any of 213 branches to any of 221 branches:

<idea-version
since-build="213" until-build="221.*"/>
vendor﻿
Reference: JetBrains Marketplace: Contacts and Resources

The vendor name or organization ID (if created) in the Plugins settings dialog and on the JetBrains Marketplace plugin
page.

Required
yes; ignored in an additional config file

Attributes
url (optional)
The URL to the vendor's homepage. Supports https:// and http:// scheme links.

email (optional)

The vendor's email address.

Examples
Personal vendor with an email address provided:

<vendor email="joe@example.com">Joe Doe</vendor>
Organizational vendor with a website URL and email address provided:

<vendor
url="https://mycompany.example.com"
email="contact@example.com">
My Company
</vendor>
description﻿
Reference: JetBrains Marketplace: Plugin Description

The plugin description displayed on the JetBrains Marketplace plugin page and in the Plugins settings dialog.

Simple HTML elements, like text formatting, paragraphs, lists, etc., are allowed and must be wrapped
into <![CDATA[... ]]> section.

Required
yes; ignored in an additional config file
The element can be skipped in the source plugin.xml file if the Gradle plugin patchPluginXml task (2.x, 1.x) is enabled
and configured.

Example
<description><![CDATA[
Provides support for My Framework.
The support includes:
<ul>
  <li>code completion</li>
  <li>references</li>
</ul>
For more information visit the
<a href="https://example.com">project site</a>.
]]></description>
change-notes﻿
Reference: JetBrains Marketplace: Change Notes

A short summary of new features, bugfixes, and changes provided with the latest plugin version. Change notes are
displayed on the JetBrains Marketplace plugin page and in the Plugins settings dialog.

Simple HTML elements, like text formatting, paragraphs, lists, etc., are allowed and must be wrapped
into <![CDATA[... ]]> section.

Required
no; ignored in an additional config file
The element can be skipped in the source plugin.xml file if the Gradle plugin patchPluginXml task (2.x, 1.x) is enabled
and configured.

Example
<change-notes><![CDATA[
<h2>New Features</h2>
<ul>
  <li>Feature 1</li>
  <li>Feature 2</li>
</ul>
<h2>Bug Fixes</h2>
<ul>
  <li>Fixed issue 1</li>
  <li>Fixed issue 2</li>
</ul>
]]></change-notes>
depends﻿
Reference: Plugin Dependencies, Modules Specific to Functionality

Specifies a dependency on another plugin or a module of an IntelliJ Platform-based product. A single <idea-plugin>
element can contain multiple <depends> elements.

Required
no; in most cases dependency on the platform module is needed

Attributes
optional (optional)
Boolean value defining whether the dependency is optional to load the plugin in the IDE. If the dependency plugin is not
installed in the current IDE, and optional is:

true - the plugin will be loaded

false (default) - the plugin will not be loaded

config-file (required when optional is true)

Relative path to an additional configuration file, loaded only if the dependency plugin is installed in the current IDE.

Examples
Required plugin dependency:

<depends>com.example.dependencypluginid</depends>
Required dependency on the IntelliJ IDEA Java Module:

<depends>com.intellij.modules.java</depends>
Required module dependency with additional configuration:

<depends
config-file="myPluginId-withJava.xml">
com.intellij.modules.java
</depends>
Optional module dependency with additional configuration:

<depends
optional="true"
config-file="myPluginId-withKotlin.xml">
org.jetbrains.kotlin
</depends>
2020.2+
incompatible-with﻿
Reference: Declaring Incompatibility with Plugin

The ID or alias of the plugin the current plugin is incompatible with. The plugin is not loaded if the incompatible
plugin is installed in the current IDE.

Required
no; ignored in an additional config file

Examples
Incompatibility with the Java plugin:

<incompatible-with>
  com.intellij.java
</incompatible-with>
Incompatibility with the AppCode plugin referenced via its alias:

<incompatible-with>
  com.intellij.modules.appcode.ide
</incompatible-with>
extensions﻿
Reference: Extensions

Defines the plugin extensions.

Required
no

Attributes
defaultExtensionNs (optional)

Default extensions namespace. It allows skipping the common prefix in fully qualified extension point names. Usually,
the com.intellij namespace is used when the plugin implements IntelliJ Platform extensions.

Children
The children elements are registrations of instances of extension points provided by the IntelliJ Platform or plugins.
An extension element name is defined by its extension point via name or qualifiedName attributes.
An extension element attributes depend on the extension point implementation, but all extensions support basic
attributes: id, order, and os.

Examples
Extensions' declaration with a default namespace:

<extensions defaultExtensionNs="com.intellij">
  <applicationService
      serviceImplementation="com.example.Service"/>
</extensions>
Extensions' declaration using the fully qualified extension name:

<extensions>
  <com.example.vcs.myExtension
      implementation="com.example.MyExtension"/>
</extensions>
An Extension﻿
An extension instance registered under <extensions>.

Listed attributes are basic attributes available for all extensions. The list of actual attributes can be longer
depending on the extension point implementation.

Attributes
id (optional)
Unique extension identifier. It allows for referencing an extension in other attributes, for example, in order.
To not clash with other plugins defining extensions with the same identifier, consider prepending the identifier with a
prefix related to the plugin <id> or <name>, for example, id="com.example.myplugin.myExtension".

order (optional)
Allows for ordering the extension relative to other instances of the same extension point. Supported values:

first - orders the extension as first. It is not guaranteed that the extension will be the first if multiple extensions
are defined as first.

last - orders the extension as last. It is not guaranteed that the extension will be the last if multiple extensions are
defined as last.

before extension_id - orders the extension before an extension with the given id

after extension_id - orders the extension after an extension with the given id
Values can be combined, for example, order="after extensionY, before extensionX".

os (optional)

Allows restricting an extension to a given OS. Supported values:

freebsd

linux

mac

unix

windows

For example, os="windows" registers the extension on Windows only.

extensionPoints﻿
Reference: Extension Points

Extension points defined by the plugin.

Required
no

Children
<extensionPoint>

extensionPoint﻿
Reference: Declaring Extension Points

A single extension point entry of the <extensionPoints> defined by the plugin. A single <extensionPoints> element can
contain multiple <extensionPoint> elements.

Required
no

Attributes
name (name or qualifiedName is required)
The extension point name that should be unique in the scope of the plugin, e.g., myExtension. The fully qualified name
of the extension point is built at runtime by prepending the value of the name attribute with the plugin <id> + .
prefix.
Example: when the name is myExtension and plugin ID is com.example.myplugin, the fully qualified name of the EP will be
com.example.myplugin.myExtension.
Only one of the name and qualifiedName attributes can be specified.

qualifiedName (name or qualifiedName is required)
The fully qualified name of the extension point. It should be unique between different plugins, and it is recommended to
include a plugin ID to guarantee uniqueness, e.g., com.example.myplugin.myExtension.
Only one of the name and qualifiedName attributes can be specified.

interface (interface or beanClass is required)
The fully qualified name of the interface to be implemented for extending the plugin's functionality.
Only one of the interface and beanClass attributes can be specified.

beanClass (interface or beanClass is required)
The fully qualified name of the extension point bean class providing additional information to the plugin.
The bean class specifies one or several properties annotated with the @Attribute annotation. Note that bean classes do
not follow the JavaBean standard. Implement PluginAware to obtain information about the plugin providing the actual
extension (see Error Handling).
Only one of the interface and beanClass attributes can be specified.

dynamic (optional)
Boolean value defining whether the extension point meets the requirements to be dynamic, which is a prerequisite for
dynamic plugins.
Default value: false.

area (optional)

The scope in which the extension is instantiated.

Allowed values:

IDEA_APPLICATION (default)

IDEA_PROJECT

IDEA_MODULE (deprecated)

It is strongly recommended not to introduce new project- and module-level extension points. If an extension point needs
to operate on a Project or Module instance, declare an application-level extension point and pass the instance as a
method parameter.

Children
<with>

with﻿
Specifies the required parent type for class names provided in extension point tags or attributes. A
single <extensionPoint> element can contain multiple <with> elements.

Required
no

Attributes
tag (tag or attribute is required)
The name of the tag holding the fully qualified name of the class which parent type will be limited by the type provided
in the implements attribute.
Only one of the tag and attribute attributes can be specified.

attribute (tag or attribute is required)
The name of the attribute holding the fully qualified name of the class which parent type will be limited by the type
provided in the implements attribute.
Only one of the tag and attribute attributes can be specified.

implements (required)

The fully qualified name of the parent type limiting the type provided in the place specified by tag or attribute.

Example
An extension point which restricts the type provided in a myClass attribute to be an instance of com.example.ParentType,
and the type provided in a someClass element to be an instance of java.lang.Comparable:

<extensionPoint
name="myExtension"
beanClass="com.example.MyExtension">
<with
attribute="myClass"
implements="com.example.ParentType"/>
<with
tag="someClass"
implements="java.lang.Comparable"/>
</extensionPoint>
When using the above extension point, an implementation could be registered as follows:

<myExtension ...
myClass="com.example.MyCustomType">
<someClass>com.example.MyComparable</someClass>
</myExtension>
where:

com.example.MyCustomType must be a subtype of com.example.ParentType

com.example.MyComparable must be a subtype of java.lang.Comparable

resource-bundle﻿
A resource bundle to be used with message key attributes in extension declarations and for action and group
localization. A single <idea-plugin> element can contain multiple <resource-bundle> elements.

Required
no

Example
To load the content of messages/Bundle.properties bundle, declare:

<resource-bundle>messages.Bundle</resource-bundle>
actions﻿
Reference: Actions

Defines the plugin actions.

Required
no

Attributes
resource-bundle (optional; available since 2020.1)

Defines the dedicated actions resource bundle. See Localizing Actions and Groups for more details.

Children
<action>

<group>

<reference>

Example
<actions resource-bundle="messages.ActionsBundle">
  <!--
  Actions/Groups defined here will use keys
  from the ActionsBundle.properties bundle.
  -->
</actions>
action﻿
Reference: Registering Actions in plugin.xml

A single action entry of the <actions> implemented by the plugin. A single <actions> element can contain
multiple <action> elements.

Required
no

Attributes
id (optional; defaults to the action class short name if not specified)
A unique action identifier. It is recommended to specify the id attribute explicitly.
The action identifier must be unique across different plugins. To ensure uniqueness, consider prepending it with the
value of the plugin's <id>.

class (required)
The fully qualified name of the action implementation class.

text (required if the action is not localized)
The default long-version text to be displayed for the action (tooltip for toolbar button or text for menu item).

description (optional)
The text which is displayed in the status bar when the action is focused.

icon (optional)

The icon that is displayed on the toolbar button or next to the action menu item. See Working with Icons for more
information about defining and using icons.

use-shortcut-of (optional)

The ID of the action whose keyboard shortcut this action will use.

Children
<abbreviation>

<add-to-group>

<keyboard-shortcut>

<mouse-shortcut>

<override-text>

<synonym>

Examples
Action declaring explicit text:

<action
id="com.example.myframeworksupport.MyAction"
class="com.example.impl.MyAction"
text="Do Action"
description="Do something with the code"
icon="AllIcons.Actions.GC">
  <!-- action children elements -->
</action>
Action without the text attribute must use the texts from the resource bundle declared with the <resource-bundle> element, or the resource-bundle attribute of the <actions> element:

<action
id="com.example.myframeworksupport.MyAction"
class="com.example.impl.MyAction"
icon="AllIcons.Actions.GC"/>
add-to-group﻿
Specifies that the action should be added to an existing <group>. A single action can be added to multiple groups.

Required
no

Attributes
group-id (required)
Specifies the ID of the <group> to which the action is added. The group must be an implementation of the
DefaultActionGroup class.

anchor (optional)
Specifies the position of the action relative to other actions. Allowed values:

first - the action is placed as the first in the group

last (default) - the action is placed as the last in the group

before - the action is placed before the action specified by the relative-to-action attribute

after - the action is placed after the action specified by the relative-to-action attribute

relative-to-action (required if anchor is before/after)

The action before or after which the current action is inserted.

Example
<add-to-group
group-id="ToolsMenu"
anchor="after"
relative-to-action="GenerateJavadoc"/>
keyboard-shortcut﻿
Specifies the keyboard shortcut for the action. A single action can have several keyboard shortcuts.

Required
no

Attributes
keymap (required)
Specifies the keymap for which the action shortcut is active. IDs of the standard keymaps are defined as constants in
the KeymapManager class.

first-keystroke (required)
Specifies the first keystroke of the action shortcut. The keystrokes are specified according to the regular Swing rules.

second-keystroke (optional)
Specifies the second keystroke of the action shortcut.

remove (optional)
Removes a shortcut from the specified action.

replace-all (optional)

Removes all keyboard and mouse shortcuts from the specified action before adding the specified shortcut.

Examples
Add the first and second keystrokes to all keymaps:

<keyboard-shortcut
keymap="$default"
first-keystroke="control alt G"
second-keystroke="C"/>
Remove the given shortcut from the Mac OS X keymap:

<keyboard-shortcut
keymap="Mac OS X"
first-keystroke="control alt G"
second-keystroke="C"
remove="true"/>
Remove all existing keyboard and mouse shortcuts and register one for the Mac OS X 10.5+ keymap only:

<keyboard-shortcut
keymap="Mac OS X 10.5+"
first-keystroke="control alt G"
second-keystroke="C"
replace-all="true"/>
mouse-shortcut﻿
Specifies the mouse shortcut for the action. A single action can have several mouse shortcuts.

Required
no

Attributes
keymap (required)
Specifies the keymap for which the action shortcut is active. IDs of the standard keymaps are defined as constants in
the KeymapManager class.

keystroke (required)
Specifies the clicks and modifiers for the action. It is defined as a sequence of words separated by spaces:

modifier keys: shift, control, meta, alt, altGraph

mouse buttons: button1, button2, button3

button double-click: doubleClick

remove (optional)
Removes a shortcut from the specified action.

replace-all (optional)

Removes all keyboard and mouse shortcuts from the specified action before adding the specified shortcut.

Examples
Add the shortcut to all keymaps:

<mouse-shortcut
keymap="$default"
keystroke="control button3 doubleClick"/>
Remove the given shortcut from the Mac OS X keymap:

<mouse-shortcut
keymap="Mac OS X"
keystroke="control button3 doubleClick"
remove="true"/>
Remove all existing keyboard and mouse shortcuts and register one for the Mac OS X 10.5+ keymap only:

<mouse-shortcut
keymap="Mac OS X 10.5+"
keystroke="control button3 doubleClick"
replace-all="true"/>
2020.1+
override-text﻿
Defines an alternate menu action or group text depending on context: menu location, toolbar, and other.

Supported
2020.1+ for actions
2020.3+ for groups

Required
no

Attributes
place (required)
Declares where the alternate text should be used.

text (text or use-text-of-place is required)
Defines the text to be displayed for the action.

use-text-of-place (text or use-text-of-place is required)

Defines a location whose text should be displayed for this action.

Examples
Explicitly overridden text:

<!--
Default action text:
"Garbage Collector: Collect _Garbage"
-->
<action
class="com.example.CollectGarbage"
text="Garbage Collector: Collect _Garbage"
...>
  <!--
  Alternate text displayed anywhere in the main menu:
  "Collect _Garbage"
  -->
<override-text
place="MainMenu"
text="Collect _Garbage"/>
</action>
Overridden text reused from the MainMenu place:

<override-text
place="EditorPopup"
use-text-of-place="MainMenu"/>
2020.3+
synonym﻿
Defines an alternative text for searching the action in Help | Find Action... or Navigate | Search Everywhere popups. A
single action can have multiple synonyms.

Required
no

Attributes
key (key or text is required)
The key of the synonym text provided in a message bundle.

text (key or text is required)

The synonym text.

Example
<!-- Default action text: Delete Element -->
<synonym key="my.action.text.remove.element"/>
<synonym text="Remove Element"/>
abbreviation﻿
Defines an abbreviation for searching the action in Help | Find Action... or Navigate | Search Everywhere popups. A single action can have multiple abbreviations.

Required
no

Attributes
value (required)

The abbreviation value.

Example
<!-- Default action text: UI Inspector -->
<abbreviation value="uii"/>
group﻿
Reference: Grouping Actions

Defines an action group. The <action>, <group> and <separator> elements defined inside the group are automatically
included in it. The <group> elements can be nested.

Required
no

Attributes
id (required)
A unique group identifier. The group identifier must be unique between different plugins. Thus, it is recommended to
prepend it with the value of the plugin <id>.

class (optional)
The fully qualified name of the group implementation class. If not specified, DefaultActionGroup is used.

text (required if the popup is true and the group is not localized)
The default long-version text to be displayed for the group (text for the menu item showing the submenu).

description (optional)
The text which is displayed in the status bar when the group is focused.

icon (optional)
The icon that is displayed next to the group menu item. See Working with Icons for more information about defining and
using icons.

popup (optional)
Boolean flag defining whether the group items are presented in the submenu popup.

true - group actions are placed in a submenu

false (default) - actions are displayed as a section of the same menu delimited by separators

compact (optional)
Boolean flag defining whether disabled actions within this group are hidden. If the value is:

true - disabled actions are hidden

false (default) - disabled actions are visible

use-shortcut-of (optional)
The ID of the action whose keyboard shortcut this group will use.

searchable (optional; available since 2020.3)

Boolean flag defining whether the group is displayed in Help | Find Action... or Navigate | Search Everywhere popups.
Default value: true.

Children
<action>

<add-to-group>

<group>

<override-text>

<reference>

<separator>

Examples
Group declaring explicit text:

<group
id="com.example.myframeworksupport.MyGroup"
popup="true"
text="My Tools">
  <!-- group children elements -->
</group>
A popup group without the text attribute must use the texts from the resource bundle declared with the <resource-bundle> element, or the resource-bundle attribute of the <actions> element:

<group
id="com.example.myframeworksupport.MyGroup"
popup="true"/>
A group with custom implementation and icon:

<group
id="com.example.myframeworksupport.MyGroup"
class="com.example.impl.MyGroup"
icon="AllIcons.Actions.GC"/>
reference﻿
Allows adding an existing action to the group. The element can be used directly under the <actions> element, or in
the <group> element.

Required
no

Attributes
ref (required)
The ID of the action to add to a group.

id (optional)
Deprecated: Use ref instead.

The ID of the action to add to a group.

Children
<add-to-group>

Examples
An action reference in a group:

<group ...>
<reference ref="EditorCopy"/>
</group>
An action reference registered directly in the <actions> element:

<actions>
  <reference ref="com.example.MyAction">
    <add-to-group group-id="ToolsMenu"/>
  </reference>
</group>
separator﻿
Defines a separator between actions in a group. The element can be used directly under the <actions> element with the child <add-to-group> element defining the target group, or in the <group> element.

Required
no

Attributes
text (optional)
Text displayed on the separator. Separator text is displayed only in specific contexts such as popup menus, toolbars,
etc.

key (optional)

The message key for the separator text. The message bundle for use should be registered via the resource-bundle
attribute of the <actions> element. The attribute is ignored if the text attribute is specified.

Children
<add-to-group>

Examples
A separator dividing two actions in a group:

<group ...>
<action .../>
<separator/>
<action .../>
</group>
A separator registered directly in the <actions> element:

<actions>
  <separator>
    <add-to-group
        group-id="com.example.MyGroup"
        anchor="first"/>
  </separator>
</group>
A separator with a defined text:

<separator text="Group By"/>
A separator with a text defined by a message key:

<separator key="message.key"/>
2019.3
applicationListeners﻿
Reference: Defining Application-Level Listeners

Defines the application-level listeners.

Required
no

Children
<listener>

2019.3
listener﻿
Reference: Listeners

Defines a single application or project-level listener. A single <applicationListeners> or <projectListeners> can
contain multiple <listener> elements.

Required
no

Attributes
topic (required)
The fully qualified name of the listener interface corresponding to the type of received events.

class (required)
The fully qualified name of the class implementing the listener interface that receives and handles the events.

os (optional; available since 2020.1)
Restricts listener instantiation to a specific operating system. Allowed values:

freebsd

mac

linux

unix

windows

activeInTestMode (optional)
Boolean flag defining whether the listener should be instantiated in test mode.
Default value: true.

activeInHeadlessMode (optional)

Boolean flag defining whether the listener should be instantiated in headless mode.
Default value: true.

Example
<listener
topic="com.intellij.ide.AppLifecycleListener"
class="com.example.MyListener"
os="mac"
activeInTestMode="false"/>
2019.3
projectListeners﻿
Reference: Defining Project-Level Listeners

Defines the project-level listeners.

Required
no

Children
<listener>

xi:include﻿
Allows including content of another plugin descriptor in this descriptor with XInclude standard.

Namespace
xi="http://www.w3.org/2001/XInclude"

Required
no

Attributes
href (optional)
Path of the plugin descriptor file to include.

xpointer (optional)
Deprecated since 2021.2: The xpointer attribute must be xpointer(/idea-plugin/*) or not defined.

Elements pointer to include.
Default value: xpointer(/idea-plugin/*).

Children
<xi:fallback>

Example
Given a plugin descriptor:

<idea-plugin xmlns:xi="http://www.w3.org/2001/XInclude">
  <id>com.example.myplugin</id>
  <name>Example</name>
  <xi:include href="/META-INF/another-plugin.xml"/>
  ...
</idea-plugin>
and /META-INF/another-plugin.xml:

<idea-plugin>
  <extensions>...</extensions>
  <actions>...</actions>
</idea-plugin>
The effective plugin descriptor loaded to memory will contain the following elements:

<idea-plugin xmlns:xi="http://www.w3.org/2001/XInclude">
  <id>com.example.myplugin</id>
  <name>Example</name>
  <extensions>...</extensions>
  <actions>...</actions>
  ...
</idea-plugin>
xi:fallback﻿
Indicates that including the specified file is optional.

If the file referenced in href is not found and the xi:fallback element is missing, the plugin will fail to load.

Namespace
xi="http://www.w3.org/2001/XInclude"

Required
no