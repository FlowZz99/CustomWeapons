package it.flowzz.customweapons.commands;

import it.flowzz.customweapons.CustomWeaponsPlugin;
import it.flowzz.customweapons.lang.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GiveCommand implements CommandExecutor {

    private CustomWeaponsPlugin plugin;

    public GiveCommand(CustomWeaponsPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("cweapons").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("customweapons.command.give")) return false;
        //cweapons give %player% <type>
        if(args.length != 3){
            sender.sendMessage(Messages.COMMAND_SYNTAX.getTranslation());
            return false;
        }
        if(args[0].equalsIgnoreCase("give")){
            Player player = Bukkit.getPlayer(args[1]);
            //Check if player is online
            if(player == null){
                sender.sendMessage(Messages.PLAYER_OFFLINE.getTranslation());
                return false;
            }
            //Check if inventory isn't full
            if(player.getInventory().firstEmpty() == -1){
                sender.sendMessage(Messages.INVENTORY_FULL.getTranslation());
                return false;
            }

            player.getInventory().addItem(buildWeapon(args[2]));
            player.sendMessage(Messages.RECEIVED_WEAPON.getTranslation());
            sender.sendMessage(Messages.COMMAND_SUCCESS.getTranslation());

        }


        return false;
    }


    private ItemStack buildWeapon(String type){
        ItemStack cweapon = new ItemStack(Material.valueOf(plugin.getConfig().getString("Weapons."+ type.toLowerCase() +".material")));
        ItemMeta cweaponMeta = cweapon.getItemMeta();

        cweaponMeta.spigot().setUnbreakable(true);
        cweaponMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        cweaponMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        cweaponMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("Weapons."+ type.toLowerCase() +".display-name")));
        List<String> lore = new ArrayList<>();
        for (String line : plugin.getConfig().getStringList("Weapons." + type.toLowerCase() + ".lore")) {
            lore.add(ChatColor.translateAlternateColorCodes('&',line));
        }
        cweaponMeta.setLore(lore);
        cweapon.setItemMeta(cweaponMeta);
        return cweapon;
    }
}
