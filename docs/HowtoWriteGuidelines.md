This page describes basic rules of writing articles for IntelliJ Platform UI Guidelines.

If you want to add new articles or edit existing ones, learn more about the contribution process in Contributing to the
IntelliJ Platform SDK.

Article structure
The article structure can vary depending on whether a component or a principle is described. Most of the articles should
include the structural elements listed below.

Useful links
Implementation: JButton

Specification: Button

Add a block with useful links at the beginning of each article. If an article is about a component, add links to the
component implementation and design specification in the <tldr></tldr> element. You can add other useful links to this
block if needed.

Introduction paragraph
In the first paragraph, describe a component or a principle and provide an illustration.

Anatomy
Add this section if a component consists of several controls. Provide an image that shows all parts of the component.
Use callouts to label the component parts:

Callouts
If needed, describe all the parts in details below the image.

When to use
Describe common use cases for the component and provide an image for each use case.

When not to use
If the component is often misused, describe these cases. If possible, write which components should be used instead and
provide images.

How to use
Provide guidelines on how to use a component, for example:

Behavior details for a single component and for a group of such components if applicable.

Rules for writing labels, hints, empty state texts.

How to use this component with other components.

Any other recommendations specific to this component.

Each rule in a separate chapter
Try to put each rule or a group of rules into a separate subchapter and add a short and descriptive title. This will
allow the reader to scan the information on the page faster and copy the link to the rule if needed.

Sizes, placement, and style details (obsolete)
Do not describe the component appearance in a separate section. All the details are provided in the Int UI Kit library
in Figma. Make sure that an actual link to Figma is added in the useful links.

### Images

#### Create an image

Get the Int UI kit and enable it in Figma.

Open the Images Figma file and copy the Image background and Callout components to your Figma file.

Create an image by adding Int UI components to the image background. Create the same image for the Dark theme.

Export all images into the corresponding folder of the documentation project (../images/ui/<component name>) in PNG
format and x2 scale. For images in Dark theme, add the _dark postfix to the filename.

### Background

Use a gray rounded background for all images. Find details in the Figma file.

### Width

All images must be either 706 px or 378 px wide.

### 706 px

Use 706 px width for images with wide content or for single images that are surrounded by text:

Cancel and Save buttons
If an image description appears above the image, end it with a colon.

### 378 px

Use 378 px width for images in borderless tables. Use the border="false" attribute for creating borderless tables.

Put images into borderless tables when listing a set of smaller images with descriptions on the right or when adding
correct/incorrect/acceptable examples.

Sets of smaller images:

Using split buttons instead of buttons
Use a split button instead if there are more than two related actions.

Using built-in buttons instead of buttons
Use a built-in button instead if it's related to an input field, combo box, search field.

Correct/incorrect/acceptable examples:

Correct

Incorrect

A correct button with a clear 'Save' label
An incorrect button with a generic 'OK' label
Correct

Acceptable

A button with the 'Edit Action Icon...' label
A button with 'Edit' label

### Callouts

Add callouts to describe the anatomy of complex components.

All texts on images should be horizontally oriented. Refer to the Figma file for details.

Callouts example
Text﻿
The guidelines should be short, clear, and consistent. Find the common rules below.

### Grammar

Prefer present tense.

A progress bar informs a user about the progress of a lengthy operation.

Prefer the active voice.

Progress bar is shown.
Progress bar appears.

Avoid unnecessary modal verbs.

Label should use sentence-style capitalization.
Use sentence capitalization in labels.

Use imperatives.

The cursor changes to the pointing hand.
Change the cursor to the pointing hand.

When describing user behavior, write 'a user + verb'.

A user looks forward to what will appear after completion.

Avoid bracketed text. If information is important, put it out of the brackets or add a new sentence. Otherwise, remove
it.

Provide a header (bold) for each progress.
Provide a bold header for each progress.

### Content

Omit common introductory phrases.

Write one idea per sentence.

Split the text to subsections and short paragraphs.

Use bulleted lists when the order of points does not matter, and numbered list when it does.

When giving a recommendation, explain why it is useful:

Incorrect

Correct

If a process is started by a user, provide a notification when the process finishes.

If a process is started by a user, provide a notification. This way the user will know they can return and see the
results.

Add descriptive links to other sections or websites:

Incorrect

Correct

Find more details here.

Find more details in the Useful links

section.

### Tips and notes

Use tips <tip></tip> to add links to additional materials, sources, useful facts, and examples:

Find more examples in Writerside documentation.

Use notes <note></note> to highlight important information like exceptions, limitations, or known issues:

Do not apply this rule to the New Project wizard.

Correct/incorrect/acceptable labels﻿
Use the following labels in tables and paragraphs:

Incorrect

<format color="E55765" style="bold">Incorrect</format>
Correct

<format color="369650" style="bold">Correct</format>
Acceptable

<format color="DarkOrange" style="bold">Acceptable</format>

### Word-level recommendations

Incorrect

Correct

Avoid using would.

Displaying indicator would be distracting.
Displaying indicator is distracting.

Omit then if possible.

If a process is started by the user, then provide notification.

Replace he or she with they.

If a process lasts less than one second, the user won’t be able to read the process name and showing it would distract
them.

Format names of UI elements with <control></control>.

Apply the same rule for the OK and Cancel buttons.

Format quotes with <code></code>.

Do not use now in button labels.

Format shortcuts with <shortcut></shortcut>.

Use a title case for key names and the + sign to connect several keys.

If the shortcuts are different on Windows/Linux and macOS, list all possible variants.

Press
Cdm+Space
on macOS or
Ctrl+Space
on Windows/Linux.

### Code snippets

Provide code snippets along the article to help developers implement the described look and behavior.

If a code snippet is too big, put it at the end of the article and provide a link.

To insert a snippet, use <code-block></code-block>. Read more in Writerside documentation.

Fuente: https://plugins.jetbrains.com/docs/intellij/how-to-write-guidelines.html