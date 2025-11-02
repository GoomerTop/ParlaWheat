package me.goomer.parlaWheat;

import org.bukkit.plugin.java.JavaPlugin;

public final class ParlaWheat extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new OnBreakEvent(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
