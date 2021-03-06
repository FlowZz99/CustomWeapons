package it.flowzz.customweapons.listeners;

import it.flowzz.customweapons.CustomWeaponsPlugin;
import it.flowzz.customweapons.weapons.impl.SlowBow;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    private CustomWeaponsPlugin plugin;

    public PlayerInteractListener(CustomWeaponsPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
        event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            plugin.getWeapons().stream()
                    .filter(weapon -> weapon.check(event.getPlayer()) && !(weapon instanceof SlowBow))
                    .forEach(weapon -> event.setCancelled(true));
        }
    }
}
