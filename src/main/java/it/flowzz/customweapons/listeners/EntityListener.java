package it.flowzz.customweapons.listeners;

import it.flowzz.customweapons.CustomWeaponsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityListener implements Listener {

    private CustomWeaponsPlugin plugin;

    public EntityListener(CustomWeaponsPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Projectile)) return;
        if(!(event.getEntity() instanceof Player)) return;
        Player victim = ((Player) event.getEntity());

        if(event.getDamager().hasMetadata("Freeze")){
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,3*20,4));
            victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,3*20,0));
        }
    }

}
