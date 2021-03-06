App Description: Application that allows your to create a web comic based on pre-defined models. It provides a set of tools to defify the models, add text bubbles, narration, title and credits. The app provides the functionality to save and load the comic as an XML file, and also save and present the comic as an HTML file.

Execution:
	-Command to execute jar: java -jar StayAtHomies.jar

	-Required to execute: JDK version 13 or above

	-Instructions: It is recommended to select the default directory for the models(images folder in this repo contains models). For further 	instructions access the help page in the app.

Team Name: StayAtHomies
Team Members : Gabriel Gherasim, Daiana Morjolic , Niall McG

=== Story 1 ===

The code submitted displays our wireframe for the project and future features.

The GUI will have the buttons for the tools on the left hand side, the main story editor in the center and the storyboard at the bottom.
We used a BorderPane to structure the elements of the GUI, this allows us to select where we want to display our scenes.

The buttons use a "VBox" which keeps the buttons going vertically down the page.
The storyboard uses a "HBox" whiich keeps the storyboard horizontal.
The main.app_run.Main story editor as of right now is just an empty ListView.

If there isn't enough space for the buttons on screen, a scroll wheel appears. If you minimize the application by about half the size, a scroll wheel appears.


=== Story 2 & 3 ====

How to execute:
-Command to execute : java -jar StayAtHomies.jar
-Required to execute: JDK version 13 or above

Story2:
Second button on the left bar opens up a window for you to search and select the image model.main.model.Character that is to be placed on the left side of the panel.
The third button does the same thing, it lets you import a model.main.model.Character for the right side of the panel.

Story 3:
The forth button flips the model.main.model.Character image horizontally. Image has to be selected in order for the operation to work.

=== Story 4 , 5 , 6 ===

How to execute :
-Command to execute : java -jar StayAtHomies.jar
-Required to execute: JDK version 13 or above

Story 4 :
The 5th button from the left hand side bar switches the gender of characters . model.main.model.Character has to be selected in order for the operation to work .

Story 5 :
In order to change skin colour , first use the first button on the left hand side bar which will open the color palette . Once the preferred color
is selected , press the 6th button down which will change the skin color . model.main.model.Character has to be selected in order for the operation to work .

Story 6 :
To change hair color again select a color from the color palette (first button on left hand side bar) . Then use the 7th button down to change
the hair color to the selected color . model.main.model.Character has to be selected in order for the operation to work .

Additional :
To change lip colour select a colour from the colour palette (first button on left hand side bar) . Then use the 8th button down to change
the lips colour . model.main.model.Character has to be selected in order for the operation to work .

=== Story 7 & 8 ===

How to execute :
-Command to execute : java -jar StayAtHomies.jar
-Required to execute: JDK version 13 or above

Story 7 :
First insert a character by pressing the second button on the left hand side bar , then selecting the image of the desired character . Once
thats done , select the speech button which is 9th button down on the left hand side bar . A window will pop up where you can introduce the
desired text , and press ok . A speech bubble will appear above the character with the desired text . Make sure the character is selected before
inserting the speech bubble .

Story 8 :
Follow the same steps as described above in "Story 7" in order to insert a thought bubble by using the 10th button down from the left hand side bar .
Make sure character is selected before inserting the thought bubble .

Extra :
If you wish to delete a speech bubble or a thought bubble , use the 11th button down from the left hand side bar . In order for the operation to work ,
make sure you select the character of which you want to delete the speech bubble .
Niall McGurk: 33%
Gabriel: 34%
Daiana: 33%
Ryan: 0%

=== Story 9 & 10 ===

How to execute :
-Command to execute : java -jar StayAtHomies.jar
-Required to execute: JDK version 13 or above

Story 9 :
Once a character is selected , use the 12th button down on the left hand side bar which will bring up a popup . Insert the text you wish to add
at the TOP of the panel .

Story 10 :
Once a character is selected , use the 13th button down from the left hand side bar which will bring up a popup . Insert the text you wish to add
at the BOTTOM of the panel.

=== Story 11, 12 & 13 ===

How to execute :
-Command to execute : java -jar StayAtHomies.jar
-Required to execute: JDK version 13 or above

Story 11 :
In order to save panels to a list , use the top bar "Panel" menu . Make sure you have 2 characters into the current panel , then use the "save" option
on the model.Panel menu . This will save the panels to the bottom comic strip section .

Story 12 :
To go back and work on a previous panel , simply click on the panel you desire to open again .

Story 13 :
To delete a panel you no longer need , click on the panel from the comic strip then use the "Panel" menu in the top bar and press on "Delete" .
A popup will show on screen to make sure you want to delete the panel . By clicking yes the panel will be deleted .

=== Story 15 & 16 ===
-Command to execute : java -jar StayAtHomies.jar
-Required to execute: JDK version 13 or above

Story 15:
	Saves the comic strip in the form of an xml file.

Story 16:
	Loads xml comic strip files into the app and populated the app with the panels found in the xml file.
	
Gabriel Gherasim: Coded the save and load xml features.
Daiana Morjolic : Coded the Help , about and Getting Started pages for user guidance.

=== Story 17 ===
-Command to execute : java -jar StayAtHomies.jar
-Required to execute: JDK version 13 or above

Story 17:
	Saves the comic strip in the form of an HTML file.

Niall McGurk: Coded the conversion from comic strip to HTML (main features)
Gabriel Gherasim: Worked on implementing the feature to change panel position in the strip, designed the html structure for the html file generation,
		added feature to set the comic title and fixed a few bugs.
Daiana Morjolic : Worked on implementing sprint 6 feedback and added new information to help page .

=== FINAL SUBMISSION ===
-Command to execute : java -jar StayAtHomies.jar
-Required to execute: JDK version 13 or above

Story 14 was implemented last week and improved this week. To change the position of a panel you can either use the arrow buttons at the top of the strip
or right click a panel and select change position.

Story 18 was implemented in the beginning of the of the project, which allows the user to import a character image with the help of a pop up window that lets
you browse the. Clicking on File -> Characters Directory lets you choose a default directory for the characters.

Niall McGurk:
Gabriel Gherasim: For this sprint I worked on and improved the 'change panel position' feature; testing, debugging and fixing bugs. Improved logger formatting and logfile readability. Improved loading and creating xml features. Helped create the 'add closing panel' feature. Created button icons and worked with my team to improve the ui design. Refactored parts of code. Worked on narrative text and bubble text auto font resizing feature.
Daiana Morjolic: implemented Comic Credits button, title and credits prompts from sprint 7 feedback, new information for help page added.
