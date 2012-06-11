package com.d3lta.paladin.listener;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.d3lta.paladin.Paladin;

public class PaladinBlockListener implements Listener {

	private Paladin paladin;
	
	public PaladinBlockListener(Paladin plugin) {
		
		this.paladin = plugin;
		
		paladin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/*
	 * Called when block is damaged
	 */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockDamage(BlockDamageEvent event) {
		Player player = event.getPlayer();
		Block blockDamaged = event.getBlock();
		
		// Cake is damaged on use, not broken
		if (blockDamaged.getType() == Material.CAKE_BLOCK) {
			if (!paladin.getPaladinAreaManager().canBuild(player, blockDamaged.getChunk())) {
				player.sendMessage(ChatColor.DARK_RED + "Sorry, no cake for you");
				event.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block blockBroken = event.getBlock();
		
		if (!paladin.getPaladinAreaManager().canBuild(player, blockBroken.getChunk())) {
			player.sendMessage(ChatColor.DARK_RED + "You can not destroy here");
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block blockPlaced = event.getBlock();
		
		if (!paladin.getPaladinAreaManager().canBuild(player, blockPlaced.getChunk())) {
			player.sendMessage(ChatColor.DARK_RED + "You can not build here");
			event.setCancelled(true);
			return;
		}
	}
	
	// TODO: Add piston protection
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {

	}
	
	public void onBlockPistonRetract(BlockPistonRetractEvent event) {
		
	}
	
}
