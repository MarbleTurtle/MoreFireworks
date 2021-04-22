package com.morefireworks;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.events.PlayerLootReceived;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.Random;
import java.util.Set;

@Slf4j
@PluginDescriptor(
	name = "More Fireworks"
)
public class MoreFireworksPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	ItemManager itemManager;

	@Inject
	private MoreFireworksConfig config;
	Integer[] fireWorks = {199,1388,1389};
	private static final Set<Integer> LAST_MAN_STANDING_REGIONS = ImmutableSet.of(13658, 13659, 13914, 13915, 13916);

	@Override
	protected void startUp() throws Exception
	{
		log.info("Fireworks lit!");
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.info("Fireworks were duds!");
	}

	@Subscribe(priority = 4)
	public void onOverheadTextChanged(OverheadTextChanged event)
	{
		if(event.getOverheadText().toLowerCase().contains("gz")||event.getOverheadText().toLowerCase().contains("@@@")||event.getOverheadText().toLowerCase().contains(config.custom().toLowerCase())){
			event.getActor().setGraphic(fireWorks[new Random().nextInt(fireWorks.length)]);
			event.getActor().setSpotAnimFrame(0);
		}
		/*
		To be revisited at later date, probably different plugin
			event.getActor().setGraphic(59);
			event.getActor().setGraphic(68);
			event.getActor().setGraphic(78);
			event.getActor().setGraphic(110);
			event.getActor().setGraphic(113);
		 */
	}

	@Subscribe
	public void onNpcLootReceived(final NpcLootReceived npcLootReceived)
	{
		for (ItemStack items:npcLootReceived.getItems()) {
			if(itemManager.getItemPrice(items.getId())>=config.loot()&&config.loot()!=0){
				client.getLocalPlayer().setGraphic(fireWorks[new Random().nextInt(fireWorks.length)]);
				client.getLocalPlayer().setSpotAnimFrame(0);
			}
		}
	}

	@Subscribe
	public void onPlayerLootReceived(final PlayerLootReceived playerLootReceived)
	{
		if (isPlayerWithinMapRegion(LAST_MAN_STANDING_REGIONS))
		{
			return;
		}
		for (ItemStack items:playerLootReceived.getItems()) {
			if(itemManager.getItemPrice(items.getId())>=config.loot()&&config.loot()!=0){
				client.getLocalPlayer().setGraphic(fireWorks[new Random().nextInt(fireWorks.length)]);
				client.getLocalPlayer().setSpotAnimFrame(0);
			}
		}
	}

	private boolean isPlayerWithinMapRegion(Set<Integer> definedMapRegions)
	{
		final int[] mapRegions = client.getMapRegions();

		for (int region : mapRegions)
		{
			if (definedMapRegions.contains(region))
			{
				return true;
			}
		}

		return false;
	}

	@Provides
	MoreFireworksConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(MoreFireworksConfig.class);
	}

}
