package link.star_dust.BetterNetheriteUpgrading;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public class Main extends JavaPlugin implements CommandExecutor {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new ForgeListener(this), this);
        getCommand("bnu").setExecutor(this);
        getLogger().info("BetterNetheriteUpgrading Reloaded Version now enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("BetterNetheriteUpgrading Reloaded Version now disabled!");
    }

    public FileConfiguration getPluginConfig() {
        return this.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("betternetheriteupgrading") && args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("betternetheriteupgrading.reload")) {
                    player.sendMessage("§cYou do not have permission to use this command.");
                    return true;
                }
            }
            reloadConfig();
            sender.sendMessage("§aBetterNetheriteUpgrading Reloaded Version config reloaded.");
            return true;
        }
        return false;
    }
}
