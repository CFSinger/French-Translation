Extremely simple translation plugin for OSRS.

Currently only supports an incomplete catalog of French translations. New entries are added slowly as they are done manually with a CSV rather than using any kind of API or AI tool, but this removes the need for any additional work on the user's part--just turn it on and play. You will notice that there are no accents in any of the translated text--this is due to a limitation on Jagex's side that causes rendering issues when using non-standard characters.

Additional languages may be added in the future, but my current priority is French. If you would like to contribute, please simply create an issue or pull request with a pre-formatted list of entries to be reviewed and added. Please do not include accented characters when seeking to contribute (see above). The CSV format is as follows (this is just an example, not a comprehensive list of current translations):

In-game string,Translated string<br>
Loot,Butin<br>
Chop down,Abattre<br>
View,Voir<br>
Trade,Commerce<br>
Claim,Reclamation<br>
Search,Recherche<br>
Smelt,Eperlan<br>
Steal-From,Voler de<br>
Mine,Exploiter

If you'd like to work on another language indepedently, feel free to make a copy of this repository in your development environment, add your language of choice to the master.csv, and work from there. It really is quite simple, just a lot of manual work.

Known Issue: When using Runelite's Mouse Tooltips plugin, you will see tooltips for trivial things like "Walk here" ("Marchez ici"). This is because Runelite filters these out by their English string. Changing those strings (by translating them) prevents Runelite from filtering potentially disruptive mouse tooltips. It is beyond the scope of this plugin to attempt to get around this. A suggestion has been placed on Runelite's git page for a custom filter that would allow us to get around this issue by blacklisting specific tooltips by their string.