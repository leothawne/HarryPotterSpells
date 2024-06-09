package com.hpspells.core.disabledspell;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.hpspells.core.HPS;
import com.hpspells.core.spell.Spell;

/*@SpellInfo(
        name = "Enderchest",
        description = "descEnderChest",
        range = 10,
        goThroughWalls = false,
        cooldown = 0,
        icon = Material.ENDER_CHEST
)*/
public class Enderchest extends Spell {

    public Enderchest(HPS instance) {
        super(instance);
    }

    @Override
    public boolean cast(Player p) {
        Inventory i = p.getEnderChest();
        p.openInventory(i);
        return true;
    }

}
