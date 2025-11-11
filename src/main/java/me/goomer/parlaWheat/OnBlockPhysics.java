package me.goomer.parlaWheat;

import me.goomer.parlaWheat.Region.RegionHelper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class OnBlockPhysics implements Listener {

    private ParlaWheat plugin;
    private RegionHelper regionHelper;

    public OnBlockPhysics(ParlaWheat plugin){
        this.plugin = plugin;
        regionHelper = new RegionHelper(plugin);
    }


    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event){
        if(regionHelper.isFarmMaterial(event.getBlock().getType().name())){
            event.setCancelled(true);
        }
    }
}
