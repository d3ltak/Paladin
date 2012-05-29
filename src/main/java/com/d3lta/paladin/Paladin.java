package com.d3lta.paladin;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


import com.d3lta.paladin.area.PaladinAreaManager;
import com.d3lta.paladin.command.PaladinCommand;
import com.d3lta.paladin.listener.PaladinPlayerListener;

public class Paladin extends JavaPlugin {
	
	//TODO: Split Config Handling into its own class
	
	private String version = "0.0.1";
	
	private PaladinAreaManager areaManager;
	
	private PaladinPlayerListener playerListener;
	
	@Override
	public void onEnable() {
		
		this.areaManager = new PaladinAreaManager(this);
		
		playerListener = new PaladinPlayerListener(this);
		
		PluginManager manager = this.getServer().getPluginManager();
		manager.registerEvents(playerListener, this);
		
		getCommand("paladin").setExecutor(new PaladinCommand(this));
		
		
		
		System.out.println("[Paladin] " + version + " chunk protection enabled!");
		
	}
	
	@Override
	public void onDisable() {
		
		areaManager.saveAreas();
		
		System.out.println("[Paladin] " + version + " chunk protection disabled.");
		
	}
	
	public PaladinAreaManager getPaladinAreaManager() {
		return this.areaManager;
		
	}
}
