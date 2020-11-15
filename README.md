## Setup
Clone this repository.

Find your steam library (Steam -> Settings -> Downloads -> Content Library), henceforth called $Steam

Copy
* $Steam\steamapps\common\SlayTheSpire\desktop-1.0.jar -> ./lib/slaythespire.jar
* $Steam\steamapps\workshop\content\646570\1605060445\ModTheSpire.jar -> ./lib/ModTheSpire.jar
* $Steam\steamapps\workshop\content\646570\1605833019\BaseMod.jar -> ./lib/BaseMod.jar
* $Steam\steamapps\workshop\content\646570\1609158507\StSLib.jar -> ./lib/StSLib.jar

If you don't have the workshop files, subscribe to the respective mods in the Steam Workshop.

Run `mvn clean`. This will make the libraries available to this project.

Run `mvn package` to build the mod.