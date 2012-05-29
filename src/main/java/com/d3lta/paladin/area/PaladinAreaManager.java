package com.d3lta.paladin.area;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import com.d3lta.paladin.Paladin;

public class PaladinAreaManager {

	private Paladin paladin;
	
	private HashSet<PaladinArea> protectedAreas;
	private HashMap<String,PaladinArea> cacheProtectedChunks;		//Store protected chunks as world,x,z
	
	//Area File
	private File areasFile;
	private FileConfiguration areasConfig;
	
	public PaladinAreaManager(Paladin paladin) {
		
		this.paladin = paladin;
		
		this.protectedAreas = new HashSet<PaladinArea>();
		this.cacheProtectedChunks = new HashMap<String,PaladinArea>();
		
		areasFile = new File(paladin.getDataFolder(), "areas.yml");
		try {
			initalizeAreaFile();
			
			areasConfig = new YamlConfiguration();
			areasConfig.load(areasFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		loadAreas();
	}
	
	private void initalizeAreaFile() throws Exception {
		if (!areasFile.exists()) {
			areasFile.getParentFile().mkdirs();
			copy(paladin.getResource("areas.yml"), areasFile);
		}
	}
	
	private void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			 while ((len=in.read(buf))>0) {
				 out.write(buf,0,len);
			 }
			 out.close();
			 in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loadAreas() {
		Set<String> areaList = areasConfig.getKeys(false);
		System.out.println(areaList.toString());
		
		for (Iterator<String> Iareas = areaList.iterator(); Iareas.hasNext();) {
			String areaName = Iareas.next();
			System.out.println(areaName);
			
			String configPath = areaName;
			PaladinArea area = new PaladinArea(paladin, areasConfig.getConfigurationSection(configPath));
			
			this.protectedAreas.add(area);
			
			for (Iterator<String> Ichunk = area.getChunks().iterator(); Ichunk.hasNext();) {
				String chunk = Ichunk.next();
				
				this.cacheProtectedChunks.put(chunk,  area);
			}
		}
	}
	
	public void saveAreas() {
		
		for (Iterator<PaladinArea> Iareas = protectedAreas.iterator(); Iareas.hasNext();) {
			PaladinArea area = Iareas.next();
			String areaPath = area.getOwnerName();
			
			areasConfig.set(areaPath, area.saveArea());
		}
		
		try {
			areasConfig.save(areasFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isChunkProtected(Chunk chunk) {
		
		String chunkString = PaladinAreaManager.getChunkString(chunk);
		
		if (this.cacheProtectedChunks.containsKey(chunkString))
			return true;
		
		return false;
		
	}
	
	public PaladinArea getPaladinArea(Chunk chunk) {
		
		String chunkString = PaladinAreaManager.getChunkString(chunk);
		
		if (this.cacheProtectedChunks.containsKey(chunkString)) {
			return this.cacheProtectedChunks.get(chunkString);
		}
		
		return null;
		
	}
	
	public boolean getPaladinAreaBuildPermission(Player builder, Chunk chunk) {
		
		if(isChunkProtected(chunk)) {
			PaladinArea area = getPaladinArea(chunk);
			return area.isBuilder(builder);
		}
		
		return true;
		
	}
	
	public PaladinArea getPlayerPaladinArea(Player owner) {
		
		for (Iterator<PaladinArea> Iareas = this.protectedAreas.iterator(); Iareas.hasNext();) {
			PaladinArea area = Iareas.next();
			if (area.getOwnerName().equalsIgnoreCase(owner.getName())) {
				return area;
			}
		}
		
		PaladinArea area = new PaladinArea(paladin);
		area.setOwner(owner);
		this.protectedAreas.add(area);
		
		return area;
	}
	
	public boolean addPaladinChunk(Player owner, Chunk chunk) {
		
		String chunkString = PaladinAreaManager.getChunkString(chunk);
		
		if (this.cacheProtectedChunks.containsKey(chunkString)) {
			return false;
		}
		
		PaladinArea area = getPlayerPaladinArea(owner);
		area.addChunk(chunk);
		this.cacheProtectedChunks.put(chunkString, area);
		
		return true;
	}
	
	public boolean canBuild(Player builder, Chunk chunk) {
		
		System.out.println("Checking for protection");
		if (isChunkProtected(chunk)) {
			System.out.println("Chunk Protected");
			PaladinArea area = getPaladinArea(chunk);
			return area.isBuilder(builder);
		}
		System.out.println("Chunk Not Protected");
		return true;
	}
	
	
	public static String getChunkString(Chunk chunk) {
		
		return chunk.getWorld().getName() + "," + chunk.getX() + "," + chunk.getZ();
		
	}
	
}
