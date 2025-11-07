package me.goomer.parlaWheat;

import me.goomer.parlaWheat.Region.Region;
import me.goomer.parlaWheat.Region.RegionHelper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class OnBreakEvent implements Listener {

    private final ParlaWheat plugin;
    private final RegionHelper regionHelper;
    private final int RANDOM_LIMIT = 300;
    private final int DELAY = 60; //Stone regenerate delay

    public OnBreakEvent(ParlaWheat plugin){
        this.plugin = plugin;
        this.regionHelper = new RegionHelper(plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if(regionHelper.isFarmMaterial(block.getType().name())){
            Region region = regionHelper.getByBlock(block);
            if(region!=null){
                Ageable ageable = (Ageable) block.getBlockData();
                if(ageable.getAge() >= 7){
                    if(plugin.getCountByKey(region.getKey())==0){
                        regenerateFarmBlocks(region);
                    }
                    plugin.addCountByKey(region.getKey());
                }
            }
        }
        else if(regionHelper.isMineMaterial(block.getType().name()) && regionHelper.getByPos(block.getLocation())!=null){
            event.setCancelled(true);
            // event.setDropItems(true); doesn't work
            if(block.getType()==Material.STONE){
                block.setType(Material.COBBLESTONE);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(block.getType()==Material.COBBLESTONE){
                            block.setType(Material.STONE);
                        }
                    }
                }.runTaskLater(plugin, DELAY);
            }
            else if (block.getType()==Material.COBBLESTONE) {
                block.setType(Material.BEDROCK);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        block.setType(Material.STONE);
                    }
                }.runTaskLater(plugin, DELAY);
            }
            else{
                Region region = regionHelper.getByBlock(block);
                if(region!=null){
                    if(plugin.getCountByKey(region.getKey())==0){
                        regenerateMineBlocks(region);
                    }
                    plugin.addCountByKey(region.getKey());
                    block.setType(Material.STONE);
                }
                else{
                    event.setCancelled(false); // hopefully will not need this
                }
            }
        }
    }

    public void regenerateMineBlocks(Region og){
        new BukkitRunnable(){

            @Override
            public void run() {
                // Region region = regionHelper.getByKey(og.getKey());
                if(plugin.getCountByKey(og.getKey())>0){
                    placeRandomMine(og);
                    plugin.removeCountByKey(og.getKey());
                    regenerateMineBlocks(og);
                }
            }
        }.runTaskLater(plugin, og.getDelay());
    }

    public void placeRandomMine(Region region){
        World world = region.getPos1().getWorld();

        int minX = (int) Math.min(region.getPos1().getX(), region.getPos2().getX());
        int maxX = (int) Math.max(region.getPos1().getX(), region.getPos2().getX());
        int minY = (int) Math.min(region.getPos1().getY(), region.getPos2().getY());
        int maxY = (int) Math.max(region.getPos1().getY(), region.getPos2().getY());
        int minZ = (int) Math.min(region.getPos1().getZ(), region.getPos2().getZ());
        int maxZ = (int) Math.max(region.getPos1().getZ(), region.getPos2().getZ());

        Random random = new Random();

        for(int i=0; i<RANDOM_LIMIT; i++) {
            int x = random.nextInt(minX, maxX + 1);
            int y = random.nextInt(minY, maxY + 1);
            int z = random.nextInt(minZ, maxZ + 1);

            Location pos = new Location(world, x, y, z);
            if(mineCheck(pos)){
                Block block = pos.getBlock();
                block.setType(Material.getMaterial(region.getMaterial()));
                return;
            }

        }

        plugin.addCountByKey(region.getKey()); // if didn't find any random block
    }

    public boolean mineCheck(Location pos){
        Block block = pos.getBlock();
        if(block.getType()==Material.STONE){
            int[][] d = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}, {-1, 0, 0}, {0, -1, 0}, {0, 0, -1}};
            for(int[] di : d){
                if(block.getRelative(di[0], di[1], di[2]).getType().isAir()){
                    return true;
                }
            }
        }
        return false;
    }

    public void regenerateFarmBlocks(Region og){
        new BukkitRunnable(){

            @Override
            public void run() {
                //Region region = regionHelper.getByKey(og.getKey());
                if(plugin.getCountByKey(og.getKey())>0){
                    placeRandomFarm(og, true);
                    plugin.removeCountByKey(og.getKey());
                    regenerateFarmBlocks(og);
                }
            }
        }.runTaskLater(plugin, og.getDelay());
    }

    public void placeRandomFarm(Region region, boolean flag){
        World world = region.getPos1().getWorld();

        int minX = (int) Math.min(region.getPos1().getX(), region.getPos2().getX());
        int maxX = (int) Math.max(region.getPos1().getX(), region.getPos2().getX());
        int minY = (int) Math.min(region.getPos1().getY(), region.getPos2().getY());
        int maxY = (int) Math.max(region.getPos1().getY(), region.getPos2().getY());
        int minZ = (int) Math.min(region.getPos1().getZ(), region.getPos2().getZ());
        int maxZ = (int) Math.max(region.getPos1().getZ(), region.getPos2().getZ());

        Random random = new Random();

        for(int i=0; i<RANDOM_LIMIT; i++) {
            int x = random.nextInt(minX, maxX + 1);
            int y = random.nextInt(minY, maxY + 1);
            int z = random.nextInt(minZ, maxZ + 1);

            Location pos = new Location(world, x, y, z);
            if(farmCheck(pos, region.getMaterial())){
                Block block = pos.getBlock();
                block.setType(Material.getMaterial(region.getMaterial()));
                Ageable ageable = (Ageable) block.getBlockData();
                ageable.setAge(7);
                block.setBlockData(ageable);
                if(region.getStar()!=null && flag)
                    region.drawParticles(plugin, pos);
                return;
            }

        }

        plugin.addCountByKey(region.getKey()); // if didn't find any random block
    }

    public boolean farmCheck(Location pos, String material){
        Block block = pos.getBlock();
        //Block under = pos.getWorld().getBlockAt(pos.getBlockX(), pos.getBlockY()-1, pos.getBlockZ());
        Block under = block.getRelative(0, -1, 0);
        if(under.getType()==Material.FARMLAND){
            if(block.getType().name().equalsIgnoreCase(material)){
                Ageable ageable = (Ageable) block.getBlockData();
                return ageable.getAge() < 7;
            }
            else{
                return block.getType().isAir();
            }
        }
        return false;
    }

}
