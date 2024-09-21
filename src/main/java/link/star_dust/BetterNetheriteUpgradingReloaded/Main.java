package link.star_dust.BetterNetheriteUpgrading;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ForgeListener(this), this);
        getLogger().info("BetterNetheriteUpgrading Reloaded Version now enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BetterNetheriteUpgrading Reloaded Version now disabled!");
    }

    public FileConfiguration getPluginConfig() {
        return this.getConfig();
    }
}
