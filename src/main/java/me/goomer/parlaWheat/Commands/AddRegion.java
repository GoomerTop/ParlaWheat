package me.goomer.parlaWheat.Commands;

import me.goomer.parlaWheat.ParlaWheat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddRegion implements CommandExecutor {
    private ParlaWheat plugin;

    public AddRegion(ParlaWheat plugin){
        this.plugin = plugin;
    }

    // /addregion <name> <x1> <y1> <z1> <x2> <y2> <z2> <world> <material> <delay> <xs> <ys> <zs>
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length<10){
            if(commandSender instanceof Player p){
                p.sendMessage("Not enough arguments");
            }
            else{
                System.out.println("Not enough arguments");
            }
            return true;
        }
        String name = strings[0];
        if(plugin.getConfig().contains(name)){
            if(commandSender instanceof Player p){
                p.sendMessage("Region already exists");
            }
            else{
                System.out.println("Region already exists");
            }
            return true;
        }
        try {
            int x1 = Integer.parseInt(strings[1]);
            int y1 = Integer.parseInt(strings[2]);
            int z1 = Integer.parseInt(strings[3]);
            int x2 = Integer.parseInt(strings[4]);
            int y2 = Integer.parseInt(strings[5]);
            int z2 = Integer.parseInt(strings[6]);
            int delay = Integer.parseInt(strings[9]);
            if(delay<=0){
                if(commandSender instanceof Player p){
                    p.sendMessage("Delay needs to be larger than 0");
                }
                else{
                    System.out.println("Delay needs to be larger than 0");
                }
                return true;
            }

            plugin.getConfig().set("regions." + name + ".pos1.x", x1);
            plugin.getConfig().set("regions." + name + ".pos1.y", y1);
            plugin.getConfig().set("regions." + name + ".pos1.z", z1);

            plugin.getConfig().set("regions." + name + ".pos2.x", x2);
            plugin.getConfig().set("regions." + name + ".pos2.y", y2);
            plugin.getConfig().set("regions." + name + ".pos2.z", z2);

            plugin.getConfig().set("regions." + name + ".delay", delay);

            plugin.getConfig().set("regions." + name + ".world", strings[7]);
            plugin.getConfig().set("regions." + name + ".material", strings[8]);

            if(strings.length>12){
                int xs = Integer.parseInt(strings[10]);
                int ys = Integer.parseInt(strings[11]);
                int zs = Integer.parseInt(strings[12]);
                plugin.getConfig().set("regions." + name + ".star.x", xs);
                plugin.getConfig().set("regions." + name + ".star.y", ys);
                plugin.getConfig().set("regions." + name + ".star.z", zs);
            }

            plugin.saveConfig();

            if(commandSender instanceof Player p){
                p.sendMessage("Added new region!");
            }
            else{
                System.out.println("Added new region!");
            }

        } catch (NumberFormatException e) {
            if(commandSender instanceof Player p){
                p.sendMessage("You didn't enter the arguments correctly");
            }
            else{
                System.out.println("You didn't enter the arguments correctly");
            }
        }

        return true;
    }
}
