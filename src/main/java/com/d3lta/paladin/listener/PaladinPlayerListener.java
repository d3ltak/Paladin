package com.d3lta.paladin.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.d3lta.paladin.Paladin;

public class PaladinPlayerListener implements Listener {

	private Paladin paladin;
	
	public PaladinPlayerListener(Paladin plugin) {
		this.paladin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(final PlayerInteractEvent event) {
		
		if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) {
			return;
		}
		
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Material blockType = event.getMaterial();
		Material blockHeld = player.getItemInHand().getType();

		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (blockType == Material.NOTE_BLOCK
					|| blockType == Material.JUKEBOX
					|| blockType == Material.DRAGON_EGG) {
				if (!paladin.getPaladinAreaManager().canBuild(player, block.getChunk())) {
					player.sendMessage(ChatColor.DARK_RED + "You can not do that here");
					event.setCancelled(true);
					return;
				}
			}
		}
		
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (blockType == Material.DISPENSER
					|| blockType == Material.NOTE_BLOCK
					|| blockType == Material.BED_BLOCK
					|| blockType == Material.CHEST
					|| blockType == Material.FURNACE
					|| blockType == Material.BURNING_FURNACE
					|| blockType == Material.JUKEBOX
					|| blockType == Material.DIODE_BLOCK_ON
					|| blockType == Material.DIODE_BLOCK_OFF
					|| blockType == Material.LOCKED_CHEST
					|| blockType == Material.BREWING_STAND
					|| blockType == Material.CAULDRON) {
				if (!paladin.getPaladinAreaManager().canBuild(player, block.getChunk())) {
					player.sendMessage(ChatColor.DARK_RED + "You can not do that here");
					event.setCancelled(true);
					return;
				}
			}
			if (blockHeld == Material.WATER_BUCKET || blockHeld == Material.LAVA_BUCKET) {
				if (!paladin.getPaladinAreaManager().canBuild(player, block.getChunk())) {
					player.sendMessage(ChatColor.DARK_RED + "You can not do that here");
					event.setCancelled(true);
					return;
				}
			}
		}
	}
}
