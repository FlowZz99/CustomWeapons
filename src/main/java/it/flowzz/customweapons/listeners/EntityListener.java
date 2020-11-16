package it.flowzz.customweapons.listeners;

import it.flowzz.customweapons.CustomWeaponsPlugin;
import it.flowzz.customweapons.lang.Messages;
import it.flowzz.customweapons.weapons.Weapon;
import it.flowzz.customweapons.weapons.impl.SlowBow;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

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
            return;
        }
        if(event.getDamager().hasMetadata("Slow")){
            if(victim.getHealth() - event.getFinalDamage() <= 0){
                ProjectileSource shooter = ((Projectile) event.getDamager()).getShooter();
                if(shooter instanceof Player){
                    ((Player) shooter).getInventory().addItem(new ItemStack(Material.ARROW,3));
                    return;
                }
            }
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,3*20,0));
        }
    }

    @EventHandler
    public void onBowUse(EntityShootBowEvent event){
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        for (Weapon weapon : plugin.getWeapons()) {
            if (weapon.check(player) && weapon instanceof SlowBow) {
                SlowBow slowBow = (SlowBow) weapon;
                if(slowBow.handleCooldown(player)){
                    event.setCancelled(true);
                    return;
                }
                event.getProjectile().setMetadata("Slow", new FixedMetadataValue(plugin,true));
            }
        }
    }

}
