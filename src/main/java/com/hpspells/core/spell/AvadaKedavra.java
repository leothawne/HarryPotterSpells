package com.hpspells.core.spell;

import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.Particle.DustOptions;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.hpspells.core.HPS;
import com.hpspells.core.SpellTargeter.SpellHitEvent;
import com.hpspells.core.spell.Spell.SpellInfo;
import com.hpspells.core.util.HPSParticle;

@SpellInfo(
        name = "Avada Kedavra",
        description = "descAvadaKedavra",
        range = 50,
        goThroughWalls = false,
        cooldown = 300
)
public class AvadaKedavra extends Spell {

    public AvadaKedavra(HPS instance) {
        super(instance);
    }

    public boolean cast(final Player p) {
        HPS.SpellTargeter.register(p, new SpellHitEvent() {
            @Override
            public void hitEntity(LivingEntity entity) {
            	p.getWorld().playSound(p, "mdc_avada_kedavra", SoundCategory.AMBIENT, 1.0f, 1.0f);
            	entity.getWorld().playSound(entity, "mdc_avada_kedavra", SoundCategory.AMBIENT, 1.0f, 1.0f);
            	entity.damage(entity.getHealth() * 2);
                //entity.setHealth(0);
            }

            @Override
            public void hitBlock(Block block) {
                HPS.PM.warn(p, HPS.Localisation.getTranslation("spellLivingEntityOnly"));

            }
        }, 1, 0.5, 2, new HPSParticle(Particle.REDSTONE, new DustOptions(Color.GREEN, 1)));
        return true;
    }

}
