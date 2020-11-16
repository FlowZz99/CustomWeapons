package it.flowzz.customweapons.weapons.impl;

import it.flowzz.customweapons.lang.Messages;
import it.flowzz.customweapons.weapons.Weapon;
import org.bukkit.entity.Player;

import java.util.List;

public class SlowBow extends Weapon {

    public SlowBow(String material, String displayName, List<String> lore, int cooldown) {
        super(material, displayName, lore, cooldown);
    }

    @Override
    protected void onClick(Player player) {
    }

    public boolean handleCooldown(Player player){
        if(lastUse.containsKey(player.getUniqueId())){
            long remainingTime = lastUse.get(player.getUniqueId()) + (cooldown * 1000) - System.currentTimeMillis();
            if(remainingTime < 0){
                lastUse.put(player.getUniqueId(),System.currentTimeMillis());
                return false;
            }else {
                player.sendMessage(Messages.COOLDOWN.getTranslation().replace("%time%", String.valueOf(remainingTime/1000)));
                return true;
            }
        }else {
            lastUse.put(player.getUniqueId(),System.currentTimeMillis());
            return false;
        }
    }


}
