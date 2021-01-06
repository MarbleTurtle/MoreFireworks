package com.morefireworks;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface MoreFireworksConfig extends Config
{
	@ConfigItem(
		keyName = "customTrigger",
		name = "Custom Trigger",
		description = "Will spawn fireworks if this is said."
	)
	default String custom()
	{
		return "Kaboom";
	}

	@ConfigItem(
			keyName = "itemTrigger",
			name = "Item Value trigger",
			description = "Get a drop over this (GE) value to spawn fireworks, 0 to disable."
	)
	default int loot()
	{
		return 0;
	}

	@ConfigItem(
			keyName = "easterEggs",
			name = "Easter Eggs",
			description = "Secret ways to get fireworks, or other things."
	)
	default boolean easter()
	{
		return true;
	}
}
