package com.d3lta.paladin.listener;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.d3lta.paladin.Paladin;
import com.d3lta.paladin.area.PaladinAreaManager;

public class PaladinPlayerListener implements Listener {

	private Paladin paladin;
	private PaladinAreaManager areaManager;
	
	public PaladinPlayerListener(Paladin paladin) {
		this.paladin = paladin;
		this.areaManager = paladin.getPaladinAreaManager();
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(final PlayerInteractEvent event) {
		
		//Lets make sure the dude is actually doing something...
		if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
			return;
		
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		Chunk chunk = block.getLocation().getChunk();
		
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) && (!areaManager.canBuild(player, chunk))) {
			player.sendMessage("You are not able to destroy here!");
			event.setCancelled(true);
			return;
		}
	}
}
