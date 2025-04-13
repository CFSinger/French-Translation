package com.mfwchris;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.RuneLiteConfig;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.util.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@PluginDescriptor(
		name = "French Translation",
		description = "French translations for mouseover text, menu entries, items, and NPC names. (WIP)",
		tags = {"french","localization","francais","translation"}
)
public class FrenchTranslationPlugin extends Plugin {

	private static final Set<MenuAction> NPC_MENU_ACTIONS = ImmutableSet.of(
			MenuAction.NPC_FIRST_OPTION, MenuAction.NPC_SECOND_OPTION,
			MenuAction.NPC_THIRD_OPTION, MenuAction.NPC_FOURTH_OPTION,
			MenuAction.NPC_FIFTH_OPTION, MenuAction.WIDGET_TARGET_ON_NPC,
			MenuAction.EXAMINE_NPC, MenuAction.EXAMINE_OBJECT);

	private static final Set<MenuAction> ITEM_MENU_ACTIONS = ImmutableSet.of(
			MenuAction.GROUND_ITEM_FIRST_OPTION, MenuAction.GROUND_ITEM_SECOND_OPTION,
			MenuAction.GROUND_ITEM_THIRD_OPTION, MenuAction.GROUND_ITEM_FOURTH_OPTION,
			MenuAction.GROUND_ITEM_FIFTH_OPTION, MenuAction.EXAMINE_ITEM_GROUND,
			// Inventory + Using Item on Players/NPCs/Objects
			MenuAction.CC_OP, MenuAction.CC_OP_LOW_PRIORITY, MenuAction.WIDGET_TARGET,
			MenuAction.WIDGET_TARGET_ON_PLAYER, MenuAction.WIDGET_TARGET_ON_NPC,
			MenuAction.WIDGET_TARGET_ON_GAME_OBJECT, MenuAction.WIDGET_TARGET_ON_GROUND_ITEM,
			MenuAction.WIDGET_TARGET_ON_WIDGET);

	private static final ImmutableMap<String, String> ItemNameRemap = ImmutableMap.<String, String>builder()
			.put("Bread", "Pain")
			.build();

	private static final ImmutableMap<String, String> NPCNameRemap = ImmutableMap.<String, String>builder()
			.put("Man", "Homme")
			.put("Woman", "Femme")
			.build();

	private static final ImmutableMap<String, Set<Integer>> NPCFilterList = ImmutableMap.<String, Set<Integer>>builder()
			.build();

	@Inject
	private FrenchTranslationConfig FrenchTranslationConfig;

	private HashMap<String,String> translation;

	private Client client;

	private ConfigManager configManager;

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		MenuEntry entry = event.getMenuEntry();
		if (NPC_MENU_ACTIONS.contains(entry.getType())) {
				RemapMenuEntryText(entry, NPCNameRemap);
		} else if (ITEM_MENU_ACTIONS.contains(entry.getType())) {
				RemapMenuEntryText(entry, ItemNameRemap);
		}
		
		try {
			String eng = event.getMenuEntry().getOption();
			if (translation.containsKey(eng)) {
				String tran = translation.get(eng);
				event.getMenuEntry().setOption(event.getOption().replace(eng, tran));
			}
		} catch (Exception e){
			log.error(e.getMessage());
		}
	}

	@Override
	protected void startUp() throws Exception
	{
		parseConfig();
		log.info("French Translation started!");
		loadTranslation(FrenchTranslationConfig.lang().getCode());

	}

	private void loadTranslation(String lang) throws Exception
	{
		translation = new HashMap<>();
		String fileName = "/" + lang + ".csv";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName))))

		{
			String line = null;
			while ((line = br.readLine()) != null) {
				String str[] = line.split(",");
				translation.put(str[0], str[1]);
			}


		} catch (Exception e){
			log.error(e.getMessage());
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) throws Exception {

		log.info("Loading translation... " + FrenchTranslationConfig.lang().getCode());

		loadTranslation(FrenchTranslationConfig.lang().getCode());

		if (!event.getGroup().equals(FrenchTranslationConfig.GROUP))
			return;

		parseConfig();
	}

	private void parseConfig() {
	}

	@Subscribe
	private void onBeforeRender(BeforeRender event) {
		if (client.getGameState() != GameState.LOGGED_IN)
			return;

		for (Widget widgetRoot : client.getWidgetRoots()) {
			remapWidget(widgetRoot);
		}
	}

	private void remapWidget(Widget widget) {
		final int groupId = WidgetInfo.TO_GROUP(widget.getId());
		final int CHAT_MESSAGE = 162, PRIVATE_MESSAGE = 163, FRIENDS_LIST = 429;

		if (groupId == CHAT_MESSAGE || groupId == PRIVATE_MESSAGE || groupId == FRIENDS_LIST)
			return;

		Widget[] children = widget.getDynamicChildren();
		if (children == null)
			return;

		Widget[] childComponents = widget.getDynamicChildren();
		if (childComponents != null)
			mapWidgetText(childComponents);

		childComponents = widget.getStaticChildren();
		if (childComponents != null)
			mapWidgetText(childComponents);

		childComponents = widget.getNestedChildren();
		if (childComponents != null)
			mapWidgetText(childComponents);
	}

	private void mapWidgetText(Widget[] childComponents) {
		for (Widget component : childComponents) {
			remapWidget(component);

			String text = component.getText();
			if (text.isEmpty())
				continue;

		}
	}

	private void RemapWidgetText(Widget component, String text, HashMap<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (text.equalsIgnoreCase(entry.getKey())) {
				component.setText(text.replace(entry.getKey(), entry.getValue()));
				return;
			}
		}
	}

	private void RemapWidgetText(Widget component, String text, ImmutableMap<String, String> map) {
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (text.equalsIgnoreCase(entry.getKey())) {
				component.setText(text.replace(entry.getKey(), entry.getValue()));
				return;
			}
		}
	}

	private void RemapMenuEntryText(MenuEntry menuEntry, Map<String, String> map) {
		String target = menuEntry.getTarget();
		NPC npc = menuEntry.getNpc();
		String cleanTarget = null;
		if (npc != null)
			cleanTarget = Text.removeTags(npc.getName());
		else
			cleanTarget = Text.removeTags(target);
		for (Map.Entry<String, String> entry : map.entrySet()) {
			if (cleanTarget.equals(entry.getKey())) {
				menuEntry.setTarget(target.replace(entry.getKey(), entry.getValue()));
			}
		}
	}

	private void RemapMenuEntryText(MenuEntry menuEntry, ImmutableMap<String, String> map) {
		RemapMenuEntryText(menuEntry, (Map<String, String>) map);
	}

	private boolean shouldRemapName(MenuEntry entry) {
		NPC npc = entry.getNpc();
		if (npc == null)
			return true;

		Set<Integer> ids = NPCFilterList.get(npc.getName());
		if (ids == null)
			return true;

		// Do *not* remap a name if there is an entry in the filter list
		// and the NPC does not have the correct ID
		return ids.contains(npc.getId());
	}

	@Override
	protected void shutDown()
	{
		log.info("French Translation stopped!");
	}

	@Provides
	FrenchTranslationConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(FrenchTranslationConfig.class);}
}