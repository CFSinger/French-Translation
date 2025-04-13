Extremely simple translation plugin for OSRS.

Currently only supports an incomplete (but growing!) catalog of French translations. New entries are added slowly as they are done manually with a CSV rather than using any kind of API or AI tool. This plugin attempts to translate Mouseover Text (and consequently Runelite's Mouse Tooltips), Menu Entries, Item Names, and NPC Names. Graphical or otherwise "built in" interface text is unaffected, and large-scale NPC dialogue localization is outside the scope of a novice-built direct text replacement plugin.

You will notice that there are no accents in any of the translated text. This is due to a limitation on Jagex's side that sometimes causes rendering issues when using non-standard characters. This is an inconsistent issue and accented characters do work sometimes, but to avoid random issues I've chosen to err on the side of consistency. This means some translations are technically imperfect, but they are good enough with context.

Additional languages may be added in the future, but my current priority is French. If you would like to contribute, please simply create an issue or pull request with grammatical or spelling corrections, or a pre-formatted list of entries to be reviewed and added. The CSV format is as follows (this is just an example, not a comprehensive list of current translations):

In-game string,Translated string<br>
Loot,Butin<br>
Chop down,Abattre<br>
View,Voir<br>
Trade,Commerce<br>
Search,Recherche<br>
Smelt,Eperlan

Known Issue [1]: When using Runelite's Mouse Tooltips plugin, you will see tooltips for trivial things like "Walk here" ("Marchez ici"). This is because Runelite filters these out by their English string. Changing those strings (by translating them) prevents Runelite from filtering potentially disruptive mouse tooltips. It is beyond the scope of this plugin to attempt to get around this. A suggestion has been placed on Runelite's git page for a custom filter that would allow us to get around this issue by blacklisting specific tooltips by their string.

As a bandaid solution, the following strings will simply not be translated by this plugin for the time being: Walk here, Continue, Cancel, Sliding piece (more may be added if found to be overly intrusive).

Known Issue [2]: When in a Shop menu, only the menu options for items in the Shop are translated. Menu actions (Value, Sell) for items in the user's inventory are not, but item names still are. I am currently unsure of how to fix this, sorry!