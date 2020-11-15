package it.flowzz.customweapons;

import it.flowzz.customweapons.listeners.PlayerInteractListener;
import it.flowzz.customweapons.weapons.Weapon;
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
        new PlayerInteractListener(this);

    }

    @Override
    public void onDisable() {
        instance = null;
        weapons.clear();
    }

    private void loadWeapons() {
        //Samurai
        weapons.add(new Samurai(
                        getConfig().getString("Weapons.Samurai.material"),
                getConfig().getString("Weapons.Samurai.display-name"),
                getConfig().getStringList("Weapons.Samurai.lore"),
                getConfig().getInt("Weapons.Samurai.cooldown")

        ));

    }
}
