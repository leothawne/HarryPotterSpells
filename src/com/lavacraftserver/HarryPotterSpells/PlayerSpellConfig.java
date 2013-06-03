package com.lavacraftserver.HarryPotterSpells;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * A class that manages the {@code PlayerSpellConfig.yml} file. <br>
 * This file is used to store all the spells that players know.
 */
public class PlayerSpellConfig {
	private FileConfiguration PSC = null;
	private File PSCFile = null;
	
	/**
	 * Reloads the PlayerSpellConfig
	 */
	public void reloadPSC() {
	    if (PSCFile == null)
	    	PSCFile = new File(HPS.Plugin.getDataFolder(), "PlayerSpellConfig.yml");
	    PSC = YamlConfiguration.loadConfiguration(PSCFile);
	    InputStream defConfigStream = HPS.Plugin.getResource("PlayerSpellConfig.yml");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        PSC.setDefaults(defConfig);
	    }
	    savePSC();
	}
	
	/**
	 * Gets the PlayerSpellConfig file
	 * @return the file as a {@link FileConfiguration}
	 */
	public FileConfiguration getPSC() {
	    if (PSC == null)
	        reloadPSC();
	    return PSC;
	}
	
	/**
	 * Saves change made to the PlayerSpellConfig to disk
	 */
	public void savePSC() {
	    if (PSC == null || PSCFile == null) 
	        return;
	    
	    try {
	        getPSC().save(PSCFile);
	    } catch (IOException ex) {
	    	HPS.PM.log(Level.SEVERE, "Could not save config to " + PSCFile + ".");
	    	HPS.PM.debug(ex);
	    }
	}
	
	/**
	 * A utility function that is meant to be used instead of {@link FileConfiguration#getStringList(String)}. <br>
	 * This is because the Bukkit version returns {@code null} when no String list is found.
	 * @param string the path to the String list
	 * @return the string list at that location or an empty {@link ArrayList}
	 */
	public List<String> getStringListOrEmpty(String string) {
	    return getPSC().getStringList(string) == null ? new ArrayList<String>() : getPSC().getStringList(string);
	}

}
