package com.d3lta.paladin.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import com.d3lta.paladin.Paladin;

public class PaladinBlockListener implements Listener {

	Paladin paladin;
	
	public PaladinBlockListener(Paladin plugin) {
		
		this.paladin = plugin;
		
		paladin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void onBlockDamage(BlockDamageEvent event) {
		
	}
	
	public void onBlockBreak(BlockBreakEvent event) {
		
	}
	
	public void onBlockPlace(BlockPlaceEvent event) {
		
	}
	
	public void onBlockPistonExtend(BlockPistonExtendEvent event) {
		
	}
	
	public void onBlockPistonRetract(BlockPistonRetractEvent event) {
		
	}
	
}
