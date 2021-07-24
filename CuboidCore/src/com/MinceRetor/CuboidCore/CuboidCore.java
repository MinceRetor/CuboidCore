package com.MinceRetor.CuboidCore;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import java.io.File;
import java.util.LinkedList;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;

public class CuboidCore  extends JavaPlugin
{
	private static Permission perms = null;
	private static Chat chat = null;
	private static RegionContainer container = null;
	private static String coreBlocksDataFileName = "coreblocks.data";

	private static LinkedList<CoreBlock> coreBlocksList = new LinkedList<CoreBlock>();
	private static int selectedBlockX = 0;
	private static int selectedBlockY = 0;
	private static int selectedBlockZ = 0;
	private static boolean selectNextBlock = false;
	private static boolean isBlockSelected = false;
	
	@Override
    public void onEnable() 
    {
    	this.getCommand("cc").setExecutor(new CommandCC());
    	getServer().getPluginManager().registerEvents(new CuboidCodeListener(), this);
    	setupPermissions();
    	
    	File directory = this.getDataFolder();
	    if (!directory.exists())
	    {
	        directory.mkdir();
	    }
    	
	    CoreBlocksDataFile blocksData = new CoreBlocksDataFile();
    	if(blocksData.loadData(this.getDataFolder().getPath() + "/" + coreBlocksDataFileName))
    	{
    		coreBlocksList = blocksData.coreBlocksList;
    	}
    	
    	container = WorldGuard.getInstance().getPlatform().getRegionContainer();
    }
    
    @Override
    public void onDisable() 
    {
    	File directory = this.getDataFolder();
	    if (!directory.exists())
	    {
	        directory.mkdir();
	    }
    	
    	CoreBlocksDataFile blocksData = new CoreBlocksDataFile();
    	blocksData.coreBlocksList = coreBlocksList;
    	blocksData.saveData(this.getDataFolder().getPath() + "/" + coreBlocksDataFileName);
    }
    
    private boolean setupPermissions() 
    {
        RegisteredServiceProvider<Permission> rspperms = getServer().getServicesManager().getRegistration(Permission.class);
        RegisteredServiceProvider<Chat> rspchat = getServer().getServicesManager().getRegistration(Chat.class);
        perms = rspperms.getProvider();
        chat = rspchat.getProvider();
        return perms != null;
    }
    
    public static Permission getPerms()
    {
    	return perms;
    }
    
    public static Chat getChat()
    {
    	return chat;
    }
    
    public static RegionContainer getRegionContainer()
    {
    	return container;
    }
    
    public static CoreBlock getCoreBlockOnPosition(int x, int y, int z)
    {
    	for(int i = 0; i < coreBlocksList.size(); i++)
    	{
    		CoreBlock block = coreBlocksList.get(i);
    		if(block.x == x && block.y == y && block.z == z)
    		{
    			return block;
    		}
    	}
    	
    	return null;
    }
    
    public static void addCoreBlock(CoreBlock block)
    {
    	if(getCoreBlockOnPosition(block.x, block.y, block.z) == null)
    	{
    		coreBlocksList.add(block);
    	}
    }
    
    public static void removeCoreBlock(int x, int y, int z)
    {
    	for(int i = 0; i < coreBlocksList.size(); i++)
    	{
    		CoreBlock block = coreBlocksList.get(i);
    		if(block.x == x && block.y == y && block.z == z)
    		{
    			coreBlocksList.remove(i);
    		}
    	}
    }
    
    public static void setSelectedBlock(int x, int y, int z)
    {
    	selectedBlockX = x;
    	selectedBlockY = y;
    	selectedBlockZ = z;
    }
    
    public static int getSelectedBlockX()
    {
    	return selectedBlockX;
    }
    
    public static int getSelectedBlockY()
    {
    	return selectedBlockY;
    }
    
    public static int getSelectedBlockZ()
    {
    	return selectedBlockZ;
    }
    
    public static void setNextSelect(boolean state)
    {
    	selectNextBlock = state;
    }
    
    public static boolean getNextSelect()
    {
    	return selectNextBlock;
    }
    
    public static void setIsBlockSelected(boolean state)
    {
    	isBlockSelected = state;
    }
    
    public static boolean getIsBlockSelected()
    {
    	return isBlockSelected;
    }
}
