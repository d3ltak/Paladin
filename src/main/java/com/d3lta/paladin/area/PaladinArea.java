package com.d3lta.paladin.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.d3lta.paladin.Paladin;

public class PaladinArea {

	//private Paladin paladin;

	private String areaOwner = "";
	private HashSet<String> areaBuilders = new HashSet<String>();
	private HashSet<String> protectedChunks = new HashSet<String>(); 

	public PaladinArea(Paladin paladin) {
		//this.paladin = paladin;
	}

	public PaladinArea(Paladin paladin, ConfigurationSection config) {
		this.areaOwner = config.getString("owner");
		this.protectedChunks = new HashSet<String>(config.getStringList("chunks"));
		this.areaBuilders = new HashSet<String>(config.getStringList("builders"));
	}
	
	public Map<String, Object> saveArea() {

		Map<String, Object> config = new HashMap<String, Object>();
		config.put("owner", areaOwner);
		config.put("builders", new ArrayList<String>(areaBuilders));
		config.put("chunks", new ArrayList<String>(protectedChunks));

		return config;
	}
	
	public void addChunk(Chunk chunk) {
		this.protectedChunks.add(PaladinAreaManager.getChunkString(chunk));
	}

	public boolean hasChunk(Chunk chunk) {
		return this.protectedChunks.contains(PaladinAreaManager.getChunkString(chunk));
	}

	public void setOwner(Player owner) {
		this.areaOwner = owner.getName();
	}

	public String getOwnerName() {
		return this.areaOwner;
	}

	public void addBuilder(Player builder) {
		this.areaBuilders.add(builder.getName());
	}

	public void removebuilder(Player builder) {
		this.areaBuilders.remove(builder.getName());
	}

	public boolean isBuilder(Player builder) {

		if (this.areaOwner.equals(builder.getName())|| this.areaBuilders.contains(builder.getName())) {
			return true;
		}
		return false;
	}
	
	public HashSet<String> getBuilders() {
		return this.areaBuilders;
	}
	
	public HashSet<String> getChunks() {
		return this.protectedChunks;
	}
}
