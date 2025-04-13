Extremely simple translation plugin for OSRS.

Currently only supports an incomplete (but growing!) catalog of French translations. New entries are added slowly as they are done manually rather than using any kind of API or AI tool. This plugin attempts to translate Mouseover Text (and consequently Runelite's Mouse Tooltips), Menu Entries, Item Names, and NPC Names. Graphical or otherwise "built in" interface text is unaffected, and large-scale NPC dialogue localization is outside the scope of a novice-built direct text replacement plugin.

You will notice that there are no accents in any of the translated text. This is due to a limitation on Jagex's side that sometimes causes rendering issues when using non-standard characters. This is an inconsistent issue and accented characters do work sometimes, but to avoid random issues I've chosen to err on the side of consistency. This means some translations are technically imperfect, but they are good enough with context.

Additional languages may be added in the future, but my current priority is French. If you would like to contribute, please simply create an issue or pull request with grammatical or spelling corrections, or a pre-formatted list of entries to be reviewed and added. The entry format is as follows (this is just an example, not a comprehensive list of current translations):

In-game string,Translated string<br>
Loot,Butin<br>
Chop down,Abattre<br>
View,Voir<br>
Trade,Commerce<br>
Search,Recherche<br>
Smelt,Eperlan

Known Issue [1]: Translating trivial text like "Walk here" ("Marchez ici") breaks Runelite's mouse tooltip filter, meaning you would almost always see a tooltip for moving your character. This is because Runelite filters out trivial tooltip strings by their English string. Changing those strings prevents Runelite from filtering them. It is beyond the scope of this plugin to attempt to get around this. A suggestion has been placed on Runelite's git page for a custom filter that would allow us to get around this issue by blacklisting specific tooltips by their string.

As a bandaid solution, the following strings will simply not be translated by this plugin for the time being: Walk here, Continue, Cancel, Sliding piece (more may be added if found to be overly intrusive).

Known Issue [2]: When viewing shop menus using the old interface (most of them), only the menu entries for items in the shop are translated. Menu options (Value, Sell) for items in the user's inventory are not translated, but item names still are. Extremely low priority to fix as [1] it's a negligble issue and [2] I've already tried and failed a few times! Someone more experienced than me is welcome to make a fork and fix it if they've got the time.
