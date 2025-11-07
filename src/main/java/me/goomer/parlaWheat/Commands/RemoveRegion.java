package me.goomer.parlaWheat.Commands;

import me.goomer.parlaWheat.ParlaWheat;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveRegion implements CommandExecutor {
    private ParlaWheat plugin;

    public RemoveRegion(ParlaWheat plugin){
        this.plugin = plugin;
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
            return true;
        }
        String region = strings[0];
        if(plugin.getConfig().contains("regions." + region)){
            plugin.getConfig().set("regions." + region, null);
            plugin.saveConfig();
            if(commandSender instanceof Player p){
                p.sendMessage("Removed region");
            }
            else{
                System.out.println("Removed region");
            }
        }
        else{
            if(commandSender instanceof Player p){
                p.sendMessage("The region doesn't exist");
            }
            else{
                System.out.println("The region doesn't exist");
            }
        }
        return true;
    }
}
