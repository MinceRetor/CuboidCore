package com.MinceRetor.CuboidCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class CoreBlocksDataFile implements Serializable
{
	private static final long serialVersionUID = 3793437008504423824L;
	public LinkedList<CoreBlock> coreBlocksList = new LinkedList<CoreBlock>();
	
	public boolean saveData(String filePath) 
	{
		File configFile = new File(filePath);
    	if(!configFile.exists())
    	{ 
    		try 
    		{
				configFile.createNewFile();
			}
    		catch (IOException e) 
    		{
				e.printStackTrace();
				return false;
			}
    	}
		
        try 
        {
        	BukkitObjectOutputStream out = new BukkitObjectOutputStream(new FileOutputStream(configFile));
            out.writeObject(this);
            out.close();
            return true;
        }
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
	
	public boolean loadData(String filePath) 
	{
		File configFile = new File(filePath);
    	if(!configFile.exists() || configFile.length() <= 0)
    	{ 
    		return false;
    	}
    	
        try 
        {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new FileInputStream(configFile));
            CoreBlocksDataFile data = (CoreBlocksDataFile) in.readObject();
            in.close();
            coreBlocksList = data.coreBlocksList;
            return true;
        }
        catch (ClassNotFoundException | IOException e) 
        {
            e.printStackTrace();
            return false;
        }
    }
}
