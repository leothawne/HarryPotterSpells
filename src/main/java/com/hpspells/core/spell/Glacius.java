package com.hpspells.core.spell;

import com.hpspells.core.HPS;
import com.hpspells.core.SpellTargeter.SpellHitEvent;
import com.hpspells.core.spell.Spell.SpellInfo;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;

@SpellInfo(
        name = "Glacius",
        description = "descGlacius",
        range = 50,
        goThroughWalls = false,
        cooldown = 120
)
public class Glacius extends Spell {
    HashMap<Location, Material> blocks = new HashMap<Location, Material>();
    private final BlockFace[] surroundings = new BlockFace[]{
            BlockFace.NORTH,
            BlockFace.NORTH_EAST,
            BlockFace.EAST,
            BlockFace.SOUTH_EAST,
            BlockFace.SOUTH,
            BlockFace.SOUTH_WEST,
            BlockFace.WEST,
            BlockFace.NORTH_WEST
    };

    public Glacius(HPS instance) {
        super(instance);
    }

    @Override
    public boolean cast(final Player p) {
        HPS.SpellTargeter.register(p, new SpellHitEvent() {

            @Override
            public void hitBlock(Block block) {
                HPS.PM.warn(p, HPS.Localisation.getTranslation("spellPlayerOnly"));
            }

            @Override
            public void hitEntity(LivingEntity entity) {
                if (entity instanceof Player) {
                	Player tarPlayer = (Player) entity;

                	p.getWorld().playSound(p, "mdc_let_it_go", SoundCategory.AMBIENT, 1.0f, 1.0f);
                	tarPlayer.getWorld().playSound(tarPlayer, "mdc_let_it_go", SoundCategory.AMBIENT, 1.0f, 1.0f);
                	
                    World world = tarPlayer.getWorld();
                    double playerX = Math.round(tarPlayer.getLocation().getBlockX() - .5) + .5;
                    double playerY = tarPlayer.getLocation().getBlockY();
                    double playerZ = Math.round(tarPlayer.getLocation().getBlockZ() - .5) + .5;
                    float pitch = tarPlayer.getLocation().getPitch();
                    float yaw = tarPlayer.getLocation().getYaw();

                    Location loc = new Location(world, playerX, playerY, playerZ, pitch, yaw);
                    tarPlayer.teleport(loc);

                    final Location[] locations = new Location[]{
                            tarPlayer.getLocation(),
                            tarPlayer.getLocation().add(0, 1, 0),
                            tarPlayer.getLocation().add(0, 2, 0)
                    };

                    final Location top = tarPlayer.getLocation().add(0, 2, 0);
                    blocks.put(top, top.getBlock().getType());
                    top.getBlock().setType(Material.ICE);

                    for (Location locs : locations) {
                        for (BlockFace bf : surroundings) {

                            final Block relative = locs.getBlock().getRelative(bf, 1);

                            blocks.put(relative.getLocation(), relative.getType());
                            relative.setType(Material.ICE);

                        }

                    }

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HPS, new Runnable() {
                        public void run() {

                            for (Location locs : locations) {
                                for (BlockFace bf : surroundings) {
                                    final Block relative = locs.getBlock().getRelative(bf, 1);

                                    relative.setType(blocks.get(relative.getLocation()));
                                    blocks.remove(relative.getLocation());

                                }

                            }

                            top.getBlock().setType(blocks.get(top));
                            blocks.remove(top);

                        }

                    }, HPS.getConfig().getLong("spells.glacius.ice-duration", 40L));
                }
            }

        }, 1.0, Effect.SMOKE);
        return true;
    }
}
