package me.goomer.parlaWheat.Commands;

import me.goomer.parlaWheat.ParlaWheat;
import me.goomer.parlaWheat.Region.RegionHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class EditRegion implements CommandExecutor {
    private RegionHelper regionHelper;
    private ParlaWheat plugin;



    public EditRegion(ParlaWheat plugin){
        this.plugin = plugin;
        regionHelper = new RegionHelper(plugin);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length==0){
            if(commandSender instanceof Player p){
                p.sendMessage("You need to enter a region");
            }
            else{
                System.out.println("You need to enter a region");
            }
        }
        else if(strings.length==1){
            if(commandSender instanceof Player p){
                p.sendMessage("You need to enter a key");
            }
            else{
                System.out.println("You need to enter a key");
            }
        }
        else if(strings.length==2){
            if(commandSender instanceof Player p){
                p.sendMessage("You need to enter a value");
            }
            else{
                System.out.println("You need to enter a value");
            }
        }
        else{
            String region = strings[0];
            if(!regionHelper.containKey(region)){
                if(commandSender instanceof Player p){
                    p.sendMessage("Region doesn't exist");
                }
                else{
                    System.out.println("Region doesn't exist");
                }
                return true;
            }
            String key = strings[1];
            if(key.equals("world") || key.equals("material")){
                plugin.getConfig().set("regions." + region + "." + key, strings[2]);
                plugin.saveConfig();
                if(commandSender instanceof Player p){
                    p.sendMessage("Done.");
                }
                else{
                    System.out.println("Done.");
                }
            }
            else if(key.equals("delay")){
                try {
                    int value = Integer.parseInt(strings[2]);
                    if(value > 0){
                        plugin.getConfig().set("regions." + region + "." + key, value);
                        plugin.saveConfig();
                        if(commandSender instanceof Player p){
                            p.sendMessage("Done.");
                        }
                        else{
                            System.out.println("Done.");
                        }
                    }
                    else{
                        if(commandSender instanceof Player p){
                            p.sendMessage("Delay needs to be a number larger than 0");
                        }
                        else{
                            System.out.println("Delay needs to be a number larger than 0");
                        }
                    }
                } catch (NumberFormatException e) {
                    if(commandSender instanceof Player p){
                        p.sendMessage("Delay needs to be a number larger than 0");
                    }
                    else{
                        System.out.println("Delay needs to be a number larger than 0");
                    }
                }
            }
            else if(key.equals("pos1") || key.equals("pos2") || key.equals("star")){
                if(strings.length<5){
                    if(commandSender instanceof Player p){
                        p.sendMessage("You need to enter x, y, z coordinates");
                    }
                    else{
                        System.out.println("You need to enter x, y, z coordinates");
                    }
                    return true;
                }
                try{
                    int x = Integer.parseInt(strings[2]);
                    int y = Integer.parseInt(strings[3]);
                    int z = Integer.parseInt(strings[4]);
                    plugin.getConfig().set("regions." + region + "." + key + ".x", x);
                    plugin.getConfig().set("regions." + region + "." + key + ".y", y);
                    plugin.getConfig().set("regions." + region + "." + key + ".z", z);
                    plugin.saveConfig();
                    if(commandSender instanceof Player p){
                        p.sendMessage("Done.");
                    }
                    else{
                        System.out.println("Done.");
                    }
                } catch (NumberFormatException e) {
                    if(commandSender instanceof Player p){
                        p.sendMessage("You need to enter x, y, z coordinates");
                    }
                    else{
                        System.out.println("You need to enter x, y, z coordinates");
                    }
                }
            }
            else{
                if(commandSender instanceof Player p){
                    p.sendMessage("Key doesn't exist");
                }
                else{
                    System.out.println("Key doesn't exist");
                }
            }
        }

        return true;
    }
}
