package it.flowzz.customweapons.weapons;

import it.flowzz.customweapons.lang.Messages;
import it.flowzz.customweapons.weapons.impl.SlowBow;
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

    public boolean check(Player player) {
        if(player.getItemInHand() != null && player.getItemInHand().hasItemMeta()){
            ItemMeta weaponMeta = player.getItemInHand().getItemMeta();
            //Check if it's a CustomWeapon
            if(player.getItemInHand().getType().equals(material) &&
                    weaponMeta.getDisplayName().equalsIgnoreCase(displayName) &&
                    weaponMeta.getLore().equals(lore)){
                //Check for cooldown
                if(lastUse.containsKey(player.getUniqueId()) && !(this instanceof SlowBow)){
                    long remainingTime = lastUse.get(player.getUniqueId()) + (cooldown * 1000) - System.currentTimeMillis();
                    if(remainingTime < 0){
                        onClick(player);
                        lastUse.put(player.getUniqueId(),System.currentTimeMillis());
                    }else player.sendMessage(Messages.COOLDOWN.getTranslation().replace("%time%", String.valueOf(remainingTime/1000)));
                }else {
                    onClick(player);
                    if(!(this instanceof SlowBow))
                    lastUse.put(player.getUniqueId(),System.currentTimeMillis());
                }
                return true;
            }
        }
        return false;
    }

    protected abstract void onClick(Player player);

}
