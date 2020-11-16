package it.flowzz.customweapons;

import it.flowzz.customweapons.commands.GiveCommand;
import it.flowzz.customweapons.listeners.EntityListener;
import it.flowzz.customweapons.listeners.PlayerInteractListener;
import it.flowzz.customweapons.weapons.Weapon;
import it.flowzz.customweapons.weapons.impl.FreezeBall;
import it.flowzz.customweapons.weapons.impl.Samurai;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CustomWeaponsPlugin extends JavaPlugin {


    @Getter private List<Weapon> weapons = new ArrayList<>();
    @Getter private static CustomWeaponsPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        loadWeapons();
        new GiveCommand(this);
        new PlayerInteractListener(this);
        new EntityListener(this);
    }

    @Override
    public void onDisable() {
        instance = null;
        weapons.clear();
    }

    private void loadWeapons() {
        //Samurai
        weapons.add(new Samurai(
                getConfig().getString("Weapons.samurai.material"),
                getConfig().getString("Weapons.samurai.display-name"),
                getConfig().getStringList("Weapons.samurai.lore"),
                getConfig().getInt("Weapons.samurai.cooldown"),
                getConfig().getDouble("Weapons.samurai.velocity")
        ));
        //FreezeBall
        weapons.add(new FreezeBall(
                getConfig().getString("Weapons.freezeball.material"),
                getConfig().getString("Weapons.freezeball.display-name"),
                getConfig().getStringList("Weapons.freezeball.lore"),
                getConfig().getInt("Weapons.freezeball.cooldown")

        ));

    }
}
