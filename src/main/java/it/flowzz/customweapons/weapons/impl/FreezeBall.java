package it.flowzz.customweapons.weapons.impl;

import it.flowzz.customweapons.CustomWeaponsPlugin;
import it.flowzz.customweapons.weapons.Weapon;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class FreezeBall extends Weapon {

    public FreezeBall(String material, String displayName, List<String> lore, int cooldown) {
        super(material, displayName, lore, cooldown);
    }

    @Override
    protected void onClick(Player player) {
        player.launchProjectile(Snowball.class).setMetadata("Freeze", new FixedMetadataValue(CustomWeaponsPlugin.getInstance(),true));
        if (player.getItemInHand().getAmount() > 1) player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
        else  player.setItemInHand(null);

    }
}
