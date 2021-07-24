package com.MinceRetor.CuboidCore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class CuboidCodeListener implements Listener
{
	@EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockDestroy(BlockBreakEvent event) 
	{
		RegionManager regions = CuboidCore.getRegionContainer().get(WorldGuardPlugin.inst().wrapPlayer(event.getPlayer()).getWorld());
		
		
		if(CuboidCore.getNextSelect())
		{
			event.setCancelled(true);
			CuboidCore.setNextSelect(false);
			return;
		}
		CoreBlock coreBlock = CuboidCore.getCoreBlockOnPosition(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
		
		if(coreBlock != null)
		{
			String groupPrefix = CuboidCore.getChat().getGroupPrefix(event.getPlayer().getWorld(), coreBlock.ownerGroup);
			groupPrefix = ChatColor.translateAlternateColorCodes('&', groupPrefix);
			groupPrefix = ChatColor.stripColor(groupPrefix);
			event.setCancelled(false);
			regions.removeRegion(coreBlock.regionName);
			CuboidCore.removeCoreBlock(event.getBlock().getX(), event.getBlock().getY(), event.getBlock().getZ());
			
			TextComponent message = new TextComponent("Player " + event.getPlayer().getName() + " destroyed core of " + groupPrefix);
			message.setColor(ChatColor.GREEN);
			for (Player player : Bukkit.getOnlinePlayers()) 
			{
			    player.spigot().sendMessage(message);
			}
		}
    }
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) 
	{
		if(!CuboidCore.getNextSelect())
		{
			return;
		}
		
		Action action = event.getAction();
		if(action.equals(Action.LEFT_CLICK_BLOCK))
		{
			Location blockLocation = event.getClickedBlock().getLocation();
			CuboidCore.setSelectedBlock(blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ());
			CuboidCore.setIsBlockSelected(true);
			
			BaseComponent message = new TextComponent("You have selected block: " + event.getClickedBlock().getType() + ", " + blockLocation.getBlockX() + ", " + blockLocation.getBlockY() + ", " + blockLocation.getBlockZ());
			event.getPlayer().spigot().sendMessage(message);
		}
	}
}
