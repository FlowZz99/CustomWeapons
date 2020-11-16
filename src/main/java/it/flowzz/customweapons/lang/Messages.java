package it.flowzz.customweapons.lang;

import it.flowzz.customweapons.CustomWeaponsPlugin;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;

@AllArgsConstructor
public enum Messages {


    COMMAND_SUCCESS("command-success"),
    COMMAND_SYNTAX("command-syntax"),
    COOLDOWN("cooldown"),
    INVENTORY_FULL("inventory-full"),
    PLAYER_OFFLINE("player-offline"),
    RECEIVED_WEAPON("received-weapon");

    private String configNode;

    public String getTranslation(){
        return ChatColor.translateAlternateColorCodes('&', CustomWeaponsPlugin.getInstance().getConfig().getString("Messages." + configNode));
    }
}
