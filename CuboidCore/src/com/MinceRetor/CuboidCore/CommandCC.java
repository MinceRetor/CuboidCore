package com.MinceRetor.CuboidCore;
import org.bukkit.command.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandCC implements CommandExecutor
{
	@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		BaseComponent message = null;
		
		if(args.length == 0)
		{
			message = new TextComponent("No arguments has been provided");
			message.setColor(ChatColor.RED);	
			return true;
		}
		
		
		String arg1 = args[0].toString();
		
		if(arg1.equalsIgnoreCase("create"))
		{
			if(args.length != 3)
			{
				message = new TextComponent("Wrong number of arguments has been provided");
				sender.spigot().sendMessage(message);
				return true;
			}
			if(!CuboidCore.getIsBlockSelected())
			{
				message = new TextComponent("No CoreBlock is selected");
				sender.spigot().sendMessage(message);
				return true;
			}
			
			String ownerGroupName = args[1];
			String regionName = args[2];
			
			CoreBlock block = new CoreBlock();
			block.x = CuboidCore.getSelectedBlockX();
			block.y = CuboidCore.getSelectedBlockY();
			block.z = CuboidCore.getSelectedBlockZ();
			block.ownerGroup = ownerGroupName;
			block.regionName = regionName;
			CuboidCore.addCoreBlock(block);
			
			message = new TextComponent("You created CoreBlock: owner group - " + block.ownerGroup + ", region name - " + block.regionName + ", position - " + block.x + ", " + block.y +  ", " + block.z);
			sender.spigot().sendMessage(message);
		}
		else if(arg1.equalsIgnoreCase("remove"))
		{
			CoreBlock block = CuboidCore.getCoreBlockOnPosition(CuboidCore.getSelectedBlockX(), CuboidCore.getSelectedBlockY(), CuboidCore.getSelectedBlockZ());
			if(!CuboidCore.getIsBlockSelected())
			{
				message = new TextComponent("No CoreBlock is selected");
				sender.spigot().sendMessage(message);
				return true;
			}
			message = new TextComponent("You removed CoreBlock: owner group - " + block.ownerGroup + ", region name - " + block.regionName + ", position - " + block.x + ", " + block.y +  ", " + block.z);
			sender.spigot().sendMessage(message);
			CuboidCore.removeCoreBlock(CuboidCore.getSelectedBlockX(), CuboidCore.getSelectedBlockY(), CuboidCore.getSelectedBlockZ());
		}
		else if(arg1.equalsIgnoreCase("select"))
		{
			CuboidCore.setNextSelect(true);
			CuboidCore.setIsBlockSelected(false);
			message = new TextComponent("You will select next block.");
			sender.spigot().sendMessage(message);
		}
		else if(arg1.equalsIgnoreCase("unselect"))
		{
			CuboidCore.setNextSelect(false);
			CuboidCore.setIsBlockSelected(false);
			message = new TextComponent("You unselected block.");
			sender.spigot().sendMessage(message);
		}
		else if(arg1.equalsIgnoreCase("info"))
		{
			if(!CuboidCore.getIsBlockSelected())
			{
				message = new TextComponent("No CoreBlock is selected");
				sender.spigot().sendMessage(message);
				return true;
			}
			CoreBlock block = CuboidCore.getCoreBlockOnPosition(CuboidCore.getSelectedBlockX(), CuboidCore.getSelectedBlockY(), CuboidCore.getSelectedBlockZ());
			message = new TextComponent("Block position: " + block.x + ", " + block.y + ", " + block.z);
			sender.spigot().sendMessage(message);
			message = new TextComponent("Block owner group: " + block.ownerGroup);
			sender.spigot().sendMessage(message);
			message = new TextComponent("Block region name: " + block.regionName);
			sender.spigot().sendMessage(message);
		}
		
        return true;
    }
}
