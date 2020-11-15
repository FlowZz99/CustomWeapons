package it.flowzz.customweapons.weapons;

import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


@Getter
public abstract class Weapon {

    protected int cooldown;
    protected HashMap<UUID, Long> lastUse;
    protected Material material;
    protected String displayName;
    protected List<String> lore;

    public Weapon(String material, String displayName, List<String> lore, int cooldown) {
        this.material = Material.valueOf(material);
        this.displayName = ChatColor.translateAlternateColorCodes('&',displayName);
        this.lore = new ArrayList<>();
        for (String line : lore) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        this.cooldown = cooldown;
        this.lastUse = new HashMap<>();
    }

    public void check(Player player) {
        if(player.getItemInHand() != null && player.getItemInHand().hasItemMeta()){
            ItemMeta weaponMeta = player.getItemInHand().getItemMeta();
            //Check if it's a CustomWeapon
            if(player.getItemInHand().getType().equals(material) &&
                    weaponMeta.getDisplayName().equalsIgnoreCase(displayName) &&
                    weaponMeta.getLore().equals(lore)){
                //Check for cooldown
                if(lastUse.containsKey(player.getUniqueId())){
                    if(lastUse.get(player.getUniqueId()) + (cooldown * 1000) < System.currentTimeMillis()){
                        onClick(player);
                        lastUse.put(player.getUniqueId(),System.currentTimeMillis());
                    }
                }else {
                    onClick(player);
                    lastUse.put(player.getUniqueId(),System.currentTimeMillis());
                }
            }
        }
    }

    protected abstract void onClick(Player player);

}
