package com.d3lta.paladin.listener;

import org.bukkit.event.Listener;

import com.d3lta.paladin.Paladin;

public class PaladinBlockListener implements Listener {

	
	public PaladinBlockListener(Paladin plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
}
