package me.goomer.parlaWheat.Region;

import me.goomer.parlaWheat.ParlaWheat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class RegionHelper {

    private final ParlaWheat plugin;

    public RegionHelper(ParlaWheat plugin){
        this.plugin = plugin;
    }

    public ArrayList<Region> getAll(){
        ArrayList<Region> regions = new ArrayList<>();
        for(String key : plugin.getConfig().getConfigurationSection("regions").getKeys(false)){
            regions.add(getByKey(key));
        }
        return regions;
    }

    public Region getByKey(String key){
        String worldName = plugin.getConfig().getString("regions."+key+".world");
        World world = Bukkit.getWorld(worldName);
        if (world == null){
            return null;
        }

        int x1 = plugin.getConfig().getInt("regions." + key + ".pos1.x");
        int y1 = plugin.getConfig().getInt("regions." + key + ".pos1.y");
        int z1 = plugin.getConfig().getInt("regions." + key + ".pos1.z");
        Location pos1 = new Location(world, x1, y1, z1);

        int x2 = plugin.getConfig().getInt("regions." + key + ".pos2.x");
        int y2 = plugin.getConfig().getInt("regions." + key + ".pos2.y");
        int z2 = plugin.getConfig().getInt("regions." + key + ".pos2.z");
        Location pos2 = new Location(world, x2, y2, z2);

        String material = plugin.getConfig().getString("regions."+key+".material");

        int count = plugin.getConfig().getInt("regions."+key+".count");

        int delay = plugin.getConfig().getInt("regions."+key+".delay");


        if(plugin.getConfig().contains("regions." + key + ".star")){
            int x = plugin.getConfig().getInt("regions." + key + ".star.x");
            int y = plugin.getConfig().getInt("regions." + key + ".star.y");
            int z = plugin.getConfig().getInt("regions." + key + ".star.z");
            Location star = new Location(world, x, y, z);
            return new Region(pos1, pos2, worldName, count, delay, material, key, star);
        }

        return new Region(pos1, pos2, worldName, count, delay, material, key);
    }

    public void updateByKey(Region region, String key){
        plugin.getConfig().set("regions."+key+".world", region.getWorld());

        plugin.getConfig().set("regions." + key + ".pos1.x", region.getPos1().getX());
        plugin.getConfig().set("regions." + key + ".pos1.y", region.getPos1().getY());
        plugin.getConfig().set("regions." + key + ".pos1.z", region.getPos1().getZ());

        plugin.getConfig().set("regions." + key + ".pos2.x", region.getPos2().getX());
        plugin.getConfig().set("regions." + key + ".pos2.y", region.getPos2().getY());
        plugin.getConfig().set("regions." + key + ".pos2.z", region.getPos2().getZ());

        plugin.getConfig().set("regions."+key+".material", region.getMaterial());

        plugin.getConfig().set("regions."+key+".count", region.getCount());

        plugin.getConfig().set("regions."+key+".delay", region.getDelay());

        if(region.getStar()!=null){
            plugin.getConfig().set("regions." + key + ".star.x", region.getStar().getX());
            plugin.getConfig().set("regions." + key + ".star.y", region.getStar().getY());
            plugin.getConfig().set("regions." + key + ".star.z", region.getStar().getZ());
        }

        plugin.saveConfig();
    }

    public void setCountByKey(int count, String key){
        plugin.getConfig().set("regions."+key+".count", count);
        plugin.saveConfig();
    }

    public int getCountByKey(String key){
        return plugin.getConfig().getInt("regions."+key+".count");
    }

    public boolean isFarmMaterial(String m){
        List<String> materials = plugin.getConfig().getStringList("material.farm");
        return materials.contains(m);
    }

    public boolean isMineMaterial(String m){
        List<String> materials = plugin.getConfig().getStringList("material.mine");
        return materials.contains(m);
    }

    public Region getByPos(Location pos){
        for(String key : plugin.getConfig().getConfigurationSection("regions").getKeys(false)){
            Region region = getByKey(key);
            if(region.contains(pos)){
                return region;
            }
        }
        return null;
    }

    public Region getByBlock(Block block){
        for(String key : plugin.getConfig().getConfigurationSection("regions").getKeys(false)){
            Region region = getByKey(key);
            if(region.contains(block.getLocation()) && region.getMaterial().equalsIgnoreCase(block.getType().name())){
                return region;
            }
        }
        return null;
    }
}
