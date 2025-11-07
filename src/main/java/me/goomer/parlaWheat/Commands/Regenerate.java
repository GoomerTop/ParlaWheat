package me.goomer.parlaWheat.Commands;

import me.goomer.parlaWheat.ParlaWheat;
import me.goomer.parlaWheat.Region.RegionHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Regenerate implements CommandExecutor {

    private ParlaWheat plugin;
    private RegionHelper regionHelper;

    public Regenerate(ParlaWheat plugin){
        this.plugin = plugin;
        this.regionHelper = new RegionHelper(plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length==0){
            if(commandSender instanceof Player p){
                p.sendMessage("You must enter a region!");
            }
            else{
                System.out.println("You must enter a region!");
            }
        }
        else{
            String key = strings[0];
            if(regionHelper.containKey(key)){
                plugin.regenerateByKey(key);
                if(commandSender instanceof Player p){
                    p.sendMessage("Regenerated " + key + "!");
                }
                else{
                    System.out.println("Regenerated " + key + "!");
                }
            }
            else if(key.equalsIgnoreCase("*")){
                plugin.regenerateAll();
                if(commandSender instanceof Player p){
                    p.sendMessage("Regenerated all!");
                }
                else{
                    System.out.println("Regenerated all!");
                }
            }
            else{
                if(commandSender instanceof Player p){
                    p.sendMessage("Region not found!");
                }
                else{
                    System.out.println("Region not found!");
                }
            }
        }
        return true;
    }
}
