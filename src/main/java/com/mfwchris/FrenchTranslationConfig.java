package com.mfwchris;

import com.mfwchris.config.lang;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("French Translation")
public interface FrenchTranslationConfig extends Config {

	@ConfigItem(
			keyName = "language",
			name = "Language",
			description = "Vestigial option. Only French is currently available."
	)
	default lang lang() {
		return lang.FRENCH;
	}}