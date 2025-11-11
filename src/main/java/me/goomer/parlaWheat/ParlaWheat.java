package me.goomer.parlaWheat;

import me.goomer.parlaWheat.Commands.AddRegion;
import me.goomer.parlaWheat.Commands.EditRegion;
import me.goomer.parlaWheat.Commands.Regenerate;
import me.goomer.parlaWheat.Commands.RemoveRegion;
import me.goomer.parlaWheat.Region.Region;
import me.goomer.parlaWheat.Region.RegionHelper;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ParlaWheat extends JavaPlugin {

    private HashMap<String, Integer> counter;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        counter = new HashMap<>();
        getServer().getPluginManager().registerEvents(new OnBreakEvent(this), this);
        getServer().getPluginManager().registerEvents(new OnBlockPhysics(this), this);
        getCommand("regenerate").setExecutor(new Regenerate(this));
        getCommand("editregion").setExecutor(new EditRegion(this));
        getCommand("addregion").setExecutor(new AddRegion(this));
        getCommand("removeregion").setExecutor(new RemoveRegion(this));

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        regenerateAll();
    }

    public void regenerateAll(){
        for(String key : counter.keySet()){
            regenerateByKey(key);
        }
    }

    public void regenerateByKey(String key){
        RegionHelper regionHelper = new RegionHelper(this);
        OnBreakEvent place = new OnBreakEvent(this);
        Region region = regionHelper.getByKey(key);
        int count = getCountByKey(key);
        if(regionHelper.isMineMaterial(region.getMaterial())){
            regenerateStone(region);
        }
        while (count>0){
            removeCountByKey(key);
            if(regionHelper.isFarmMaterial(region.getMaterial())){
                place.placeRandomFarm(region, false);
            }
            else{
                place.placeRandomMine(region);
            }
            count = counter.get(key);
        }

    }

    public void regenerateStone(Region region){
        System.out.println("enter");
        int minX = (int) Math.min(region.getPos1().getX(), region.getPos2().getX());
        int maxX = (int) Math.max(region.getPos1().getX(), region.getPos2().getX());
        int minY = (int) Math.min(region.getPos1().getY(), region.getPos2().getY());
        int maxY = (int) Math.max(region.getPos1().getY(), region.getPos2().getY());
        int minZ = (int) Math.min(region.getPos1().getZ(), region.getPos2().getZ());
        int maxZ = (int) Math.max(region.getPos1().getZ(), region.getPos2().getZ());

        World world = region.getPos1().getWorld();

        for(int x = minX; x<=maxX; x++){
            for(int y = minY; y<=maxY; y++){
                for(int z = minZ; z<=maxZ; z++){
                    Block block = world.getBlockAt(x, y, z);
                    if(block.getType()== Material.COBBLESTONE || block.getType()==Material.BEDROCK){
                        System.out.println("works");
                        block.setType(Material.STONE);
                    }
                }
            }
        }
    }

    public int getCountByKey(String key){
        if(counter.containsKey(key)){
            return counter.get(key);
        }
        return 0;
    }

    public void setCountByKey(String key, int count){
        counter.put(key, count);
    }

    public void addCountByKey(String key){
        int count = getCountByKey(key);
        counter.put(key, count+1);
    }

    public void removeCountByKey(String key){
        int count = getCountByKey(key);
        counter.put(key, count-1);
    }
}
