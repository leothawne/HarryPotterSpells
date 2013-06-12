package com.lavacraftserver.HarryPotterSpells.Spells;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

import com.lavacraftserver.HarryPotterSpells.HPS;
import com.lavacraftserver.HarryPotterSpells.Spells.Spell.spell;
import com.lavacraftserver.HarryPotterSpells.Utils.Targeter;

@spell (
		name="Alarte Ascendare",
		description="descAlarteAscendare",
		range=30,
		goThroughWalls=false,
		cooldown=45
)
public class AlarteAscendare extends Spell implements Listener {
    
	public AlarteAscendare(HPS plugin) {
        super(plugin);
        HPS.getServer().getPluginManager().registerEvents(this, HPS);
    }

    @Override
	public boolean cast(Player p) {
		if(Targeter.getTarget(p, this.getRange(), this.canBeCastThroughWalls()) instanceof LivingEntity) {
			LivingEntity le = (LivingEntity) Targeter.getTarget(p, this.getRange(), this.canBeCastThroughWalls());
			le.setVelocity(new Vector(0,1,0));
		}
		return true;
	}
}
