package it.flowzz.customweapons.weapons.impl;

import it.flowzz.customweapons.CustomWeaponsPlugin;
import it.flowzz.customweapons.weapons.Weapon;
import lombok.Getter;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Samurai extends Weapon {

    public Samurai(String material, String displayName, List<String> lore, int cooldown) {
        super(material, displayName, lore, cooldown);
    }

    @Override
    protected void onClick(Player player) {
        player.setVelocity(player.getLocation().getDirection().normalize().setY(0.2).multiply(1.5));

        List<UUID> entityDamaged = new ArrayList<>();
        new BukkitRunnable(){
            int ticks = 20;
            @Override
            public void run() {
                if(ticks-- <= 0 || entityDamaged.size() >= 3) {
                    entityDamaged.clear();
                    this.cancel();
                }
                player.getNearbyEntities(1.0, 1.0, 1.0).forEach(nearbyEntity -> {
                    if (!entityDamaged.contains(nearbyEntity.getUniqueId())) {
                        if (nearbyEntity instanceof Damageable) {
                            Damageable entity = (Damageable) nearbyEntity;
                            entity.damage(8.0, player);
                            entityDamaged.add(entity.getUniqueId());
                        }
                    }
                });

            }
        }.runTaskTimer(CustomWeaponsPlugin.getInstance(),0,1);
    }
}
