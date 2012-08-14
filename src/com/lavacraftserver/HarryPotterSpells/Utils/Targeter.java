package com.lavacraftserver.HarryPotterSpells.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

public class Targeter extends JavaPlugin {

	public static LivingEntity getTarget(Player p, int range) {
			List<Entity> nearbyE = p.getNearbyEntities(range, range, range);
	        ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();
	        for (Entity e : nearbyE) {
	            if (e instanceof LivingEntity) {
	            	livingE.add((LivingEntity) e);
	            }
	        }

	        LivingEntity target = null;
	        BlockIterator bItr;
	        try {
	        	bItr = new BlockIterator(p, range);
	        }catch (IllegalStateException e) {
	        	p.sendMessage(ChatColor.RED+"Not a Selected Entity!");
				return null;
	        }
	        Block block;
	        Location loc;
	        int bx, by, bz;
	        double ex, ey, ez;
	        while (bItr.hasNext()) {
	                block = bItr.next();
	                bx = block.getX();
	                by = block.getY();
	                bz = block.getZ();
	                for (LivingEntity e : livingE) {
	                    loc = e.getLocation();
	                    ex = loc.getX();
	                    ey = loc.getY();
	                    ez = loc.getZ();

	                    float cY = getYMod(e);
	                    float cV = 1.2f;

	                    if ((bx-cV <= ex && ex <= bx+cV) && (bz-cV <= ez && ez <= bz+cV) && (by-cY <= ey && ey <= by+0.1)) {
	                    	target = e;
	                        return target;
	                    }
	                }
	        }
	        p.sendMessage(ChatColor.RED+"Not a Selected Entity!");
	        return null;
	}

	private static float getYMod (Entity e) {
		float y;
        if(e instanceof Enderman) {
        	y = 2.1f;
        }else if(e instanceof Cow || e instanceof Pig || e instanceof Sheep || e instanceof MushroomCow || e instanceof MagmaCube || e instanceof Slime) {
        	y = 1.1f;
        }else if(e instanceof Ocelot || e instanceof Wolf || e instanceof Chicken || e instanceof CaveSpider || e instanceof Spider ||  e instanceof Silverfish || e instanceof Squid){
        	y = 0.1f;
        }else if(e instanceof Player || e instanceof Skeleton || e instanceof Creeper || e instanceof Zombie || e instanceof PigZombie ||e instanceof Snowman ||  e instanceof Villager || e instanceof Blaze) {
        	y = 1.1f;
        }else{
        	y = 1.0f;
        }
        return y;
	}
}