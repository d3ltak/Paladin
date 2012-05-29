package com.d3lta.paladin;

import org.bukkit.plugin.java.JavaPlugin;


import com.d3lta.paladin.area.PaladinAreaManager;
import com.d3lta.paladin.command.PaladinCommand;
import com.d3lta.paladin.listener.PaladinBlockListener;
import com.d3lta.paladin.listener.PaladinPlayerListener;

public class Paladin extends JavaPlugin {
	
	//TODO: Split Config Handling into its own class
	
	private String version = this.getDescription().getVersion();
	
	private PaladinAreaManager areaManager;
	
	@SuppressWarnings("unused")
	private PaladinPlayerListener playerListener;
	@SuppressWarnings("unused")
	private PaladinBlockListener blockListener;
	
	@Override
	public void onEnable() {
		
		// Initalize Listeners
		playerListener = new PaladinPlayerListener(this);
		blockListener = new PaladinBlockListener(this);
		
		// Startup the area manager
		this.areaManager = new PaladinAreaManager(this);
		
		// Register Commands
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
