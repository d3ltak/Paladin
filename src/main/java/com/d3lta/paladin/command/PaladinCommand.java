package com.d3lta.paladin.command;

import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.d3lta.paladin.Paladin;
import com.d3lta.paladin.area.PaladinArea;
import com.d3lta.paladin.area.PaladinAreaManager;

public class PaladinCommand implements CommandExecutor {

	private Paladin paladin;
	private PaladinAreaManager areaManager;
	
	private String usageMessage = "/paladin [chunk/area]";
	
	private String chunkUsageMessage = "/paladin chunk [info/add]";
	
	private String chunkExistingOwnerMessage = "Chunk is already owned by somebody else";
	private String chunkProtectedMessage = "Chunk is now protected";
	private String chunkFailedMessage = "We failed to protect this chunk";
	
	private String chunkOwnedMessage = "This chunk is owned by ";
	private String chunkNotOwnedMessage = "Nobody owns this chunk";
	
	private String areaUsageMessage = "/paladin area [info/builders]";
	private String areaBuildersUsageMessage = "/paladin area builders [add/remove/list]";
	private String areaBuildersAddRemoveUsageMessage = "/paladin area builders [add/remove] [name]";
	private String areaBuildersNoPlayerMessage = "Unable to find that player";
	private String areaInfoMessage = "Your area covers chunks ";
	private String areaBuildersListMessage = "Builders in your area: ";
	private String areaBuildersAddMessage = " has been added as a builder";
	private String areaBuildersRemoveMessage = " has been removed as a builder";
	
	
	public PaladinCommand(Paladin paladin) {
		
		this.paladin = paladin;
		this.areaManager = paladin.getPaladinAreaManager();
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Console commands not supported at this time, sorry :(");
			return true;
		}
		
		if (args.length < 1) {
			sender.sendMessage(usageMessage);
			return false;
		}
		
		if (args[0].equalsIgnoreCase("chunk")) {
			return PaladinChunkCommand(sender, args);
		}
		
		if (args[0].equalsIgnoreCase("area")) {
			return PaladinAreaCommand(sender, args);
		}
		
		sender.sendMessage(usageMessage);
		return false;
	}
	
	private boolean PaladinAreaCommand(CommandSender sender, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player)sender;
		
		if (args.length < 2) {
			sender.sendMessage(areaUsageMessage);
			return false;
		}
		
		PaladinArea area = areaManager.getPlayerPaladinArea(player);
		
		if (args[1].equalsIgnoreCase("info")) {
			sender.sendMessage(areaInfoMessage);
			
			for(Iterator<String> Ichunks = area.getChunks().iterator(); Ichunks.hasNext();) {
				sender.sendMessage(Ichunks.next());
			}
			return true;
		}

		if (args[1].equalsIgnoreCase("builders")) {
			
			if (args.length < 3) {
				sender.sendMessage(areaBuildersUsageMessage);
				return false;
			}

			
			if (args[2].equalsIgnoreCase("list")) {
				HashSet<String> builders = area.getBuilders();
				String builderList = "";
				if (builders.size() > 0) {
					for (Iterator<String> Ibuilders = builders.iterator(); Ibuilders.hasNext();) {
						builderList += Ibuilders.next();
						if (Ibuilders.hasNext()) {
							builderList += " ,";
						}
					}
				}
				sender.sendMessage(areaBuildersListMessage + builderList);
				return true;
			}
			
			if (args.length < 4) {
				sender.sendMessage(areaBuildersAddRemoveUsageMessage);
				return false;
			}
			
			Player builder = paladin.getServer().getPlayer(args[3]);
			
			if (builder == null) {
				sender.sendMessage(areaBuildersNoPlayerMessage);
				return false;
			}
			
			if (args[2].equalsIgnoreCase("add")) {
				area.addBuilder(builder);
				sender.sendMessage(areaBuildersAddMessage);
				return true;
			}
			
			if (args[2].equalsIgnoreCase("remove")) {
				area.removebuilder(builder);
				sender.sendMessage(areaBuildersRemoveMessage);
				return true;
			}
		}
		return false;
	}
	
	private boolean PaladinChunkCommand(CommandSender sender, String[] args) {
		
		if (!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player)sender;
		Chunk chunk = player.getLocation().getChunk();
		
		if (args.length < 2) {
			sender.sendMessage(chunkUsageMessage);
			return false;
		}
		
		if (args[1].equalsIgnoreCase("add")) {
			if (areaManager.isChunkProtected(chunk)) {
				sender.sendMessage(chunkExistingOwnerMessage);
				return true;
			}
			
			if (areaManager.addPaladinChunk(player, chunk)) {
				sender.sendMessage(chunkProtectedMessage);
				return true;
			}
			
			sender.sendMessage(chunkFailedMessage);
			return true;
		}
		
		if (args[1].equalsIgnoreCase("info")) {
			System.out.println(chunk.toString());
			if (areaManager.isChunkProtected(chunk)) {
				sender.sendMessage(chunkOwnedMessage + areaManager.getPaladinArea(chunk).getOwnerName());
				return true;
			} else {
				sender.sendMessage(chunkNotOwnedMessage);
				return true;
			}
		}
		
		return false;
		
	}

}
