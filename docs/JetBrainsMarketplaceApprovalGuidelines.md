JetBrains Marketplace Approval Guidelines
Version 1.2, effective as of August 22, 2023

The capitalized terms used in this document have the same meaning as those defined in the JetBrains Marketplace
Developer Agreement.

Approval Process
Every new Plugin and all new Plugin versions (also known as updates), are subject to a verification and approval process
performed by and at the sole discretion of the JetBrains Marketplace team.

All new Plugins and their subsequent updates are reviewed according to JetBrains' availability at the time the Plugin or
its update is uploaded, but JetBrains doesn't guarantee the time frame for the review as it can take longer due to
various factors.

Along with automated checks, we manually review each Plugin and Plugin update one-by-one, before it becomes publicly
available on JetBrains Marketplace. The Plugin Developer will receive a notification as soon as the status of the review
changes, or if there are any questions regarding the Plugin or Plugin update.

If you haven't heard from us within the next 3–4 working days after the Plugin upload, please reach out to us at
marketplace@jetbrains.com.

JetBrains reserves the right to repeat the review of Plugins and Plugin updates from time to time and withdraw approval
for a Plugin or Plugin update if new information on their conformity with the approval criteria comes to JetBrains'
attention after such a review.

Plugin Approval Criteria
All Plugins for JetBrains Marketplace must meet these general approval criteria:

Plugin content:
The Plugin logo:
Must be different from the default logo for the IntelliJ Platform Plugin Template.
Must not resemble any JetBrains product logos.
Must be provided in one size: 40 px by 40 px.
Must be in SVG format.
The Plugin name:
Must be an original and unique name.
Must include keywords or concepts that accurately describe the Plugin.
Must not use famous or trademarked names of third parties without their authorization.
Must not contain pricing information, unless approved by JetBrains.
Must be limited to 30 characters.
Must not abuse the letter "A" or the period as the initial character with the intent of securing a better listing
position or search results.
Must be in Latin characters/symbols, including numbers, and general or special symbols if needed (e.g., period, hyphen,
parentheses).
Must not contain the word “Plugin” or “IntelliJ”, or include “JetBrains” or the name of any JetBrains product.
The Vendor's website and email address are provided, valid, and functional.
The Vendor's name can be light-hearted but must remain somewhat professional.
A Plugin description has been provided and the first line of the description contains a short summary in English. The
following must be in English: name, first sentence/paragraph of the Plugin description, first sentence/paragraph of the
change notes.
Change notes must not have “Add change notes here” or “most HTML tags may be used”.
Change notes should contain only relevant information.
Color scheme and Theme Plugins must have screenshots to represent the visual style they provide:
The screenshots should be of high quality. The recommended screenshot size is 1280 x 800 px (16:10 aspect ratio).
The screenshots shouldn't contain device images (e.g. a photo of your screen) or small text that will be illegible.
None of the Plugin screenshots should contain advertising or content unrelated to the Plugin functionality.
The screenshots shouldn't misrepresent your Plugin.
All of the external links on your Plugin page are valid, reachable from the internet, and relate to the Plugin or Plugin
author.
Plugin marketing assets do not infringe on JetBrains Brand Assets (see the JetBrains Brand Assets Guidelines for more
information).<
The Plugin and/or marketing assets do not infringe on any copyright or third-party trademarks.
The Plugin does not contain, share, or distribute data or content that you do not own or have permission to use.
The Plugin, description, documentation, or any other material associated with the Plugin does not incite, engage in, or
encourage, abuse, violence, hatred, or discrimination, or use language that is deemed by JetBrains to be malicious.
The Vendor must not misrepresent themselves or their right to use the Plugin, or act or use the Plugin or its content in
a fraudulent or deceptive manner. This includes misrepresenting their rights (e.g. impersonating an “admin” user) or
impersonating another person.
Plugin functionality:
The Plugin is compatible with at least one JetBrains product.
The Plugin can be installed on top of defined compatible products and compatible versions of the products.
During each upload of a Plugin (or new version of such Plugin), compatibility must be verified by using the Plugin
Verifier to ensure binary compatibility.
The Plugin does not violate JetBrains' internal API usage.
The Plugin does not introduce any security vulnerabilities or privacy issues to JetBrains products or services.
The Plugin does not negatively affect the performance of JetBrains products in a significant way (e.g. slowing the
product down).
In case of processing (e.g. collection, transfer, etc.) of any personal, statistical, or telemetric data, the Plugin
asks for explicit permission from the user.
The Plugin does not transfer statistics and/or telemetric data when the Plugin is not being used by the user.
Plugin metadata and marketing assets do not include any content for additional promotion that would give it an unfair
advantage or cause JetBrains Marketplace to malfunction (such as trying to manipulate search results, adding irrelevant
keywords, breaking HTML formatting of the page, etc.).
Legal Agreements and Privacy:
The Plugin Developer must accept the JetBrains Marketplace Developer Agreement before submitting the Plugin.
The Plugin Developer must provide their own end user license agreement (known as a Developer EULA) with any Plugin.
Plugin Developers need to provide a link to the source code if the Plugins are licensed under an open-source license.
The Plugin Developer must provide Privacy Notice agreements if they collect any personal data.
Plugin Developer must declare if their account is considered a trader or non-trader with respect to European Economic
Area (EEA) consumer protection laws.
JetBrains may establish additional criteria on a case-by-case basis. JetBrains reserves the right to remove any Plugin
from JetBrains Marketplace at any time and at its sole discretion.

Approval Criteria for Features Implemented by Plugins
JetBrains Marketplace uses a Feature Extractor (aka intellij-feature-extractor) to determine additional features
supported by the Plugin. Based on the features determined, the Plugin is advertised in JetBrains products and services:

The features are specified in the Plugin classes using the IntelliJ Platform API. Features may belong to the following
categories:

Configuration types
Facet types
File extensions
Artifact types
Module type
Features implemented by the Plugin are subject to review and approval by JetBrains, and must conform to the following
criteria:

The Plugin does not implement features which are not related to its major functionality.
The Plugin does not implement any malicious features or features for additional promotion that would give it an unfair
advantage.
JetBrains may establish additional criteria on a case-by-case basis.

If you have any questions about the approval process, please email us at plugins-admin@jetbrains.com.