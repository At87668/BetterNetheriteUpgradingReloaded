package link.star_dust.BetterNetheriteUpgrading;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.SmithingInventory;
import org.bukkit.plugin.java.JavaPlugin;

public class ForgeListener implements Listener {

    private final Main plugin;

    public ForgeListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPrepareSmithing(PrepareSmithingEvent event) {
        SmithingInventory inventory = event.getInventory();
        ItemStack templateItem = inventory.getItem(0); // Get the smithing template
        ItemStack baseItem = inventory.getItem(1); // Get the base item
        ItemStack additionItem = inventory.getItem(2); // Get the additional item (Netherite Ingot)

        // Check if the correct smithing template and base item are used
        if (templateItem == null || templateItem.getType() != Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE) {
            return; // Not a Netherite upgrade, exit
        }

        if (baseItem == null || additionItem == null || additionItem.getType() != Material.NETHERITE_INGOT) {
            return; // Not a Netherite upgrade, exit
        }

        // Get the number of Netherite Ingots required from the config file
        String itemType = baseItem.getType().name();
        int requiredIngots = plugin.getPluginConfig().getInt("Items." + itemType, 1);

        // If the amount of ingots provided by the player is less than required, do not produce a result
        if (additionItem.getAmount() < requiredIngots) {
            event.setResult(null);  // Does not meet the condition, no result generated
            return;
        }

        // Create the new Netherite item, keeping all original properties
        ItemStack resultItem = new ItemStack(Material.valueOf("NETHERITE_" + itemType.split("_")[1]));

        // Retain the original ItemMeta properties
        resultItem.setItemMeta(baseItem.getItemMeta());

        // Set the result of the smithing
        event.setResult(resultItem);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory() instanceof SmithingInventory)) {
            return;
        }

        SmithingInventory inventory = (SmithingInventory) event.getInventory();
        ItemStack templateItem = inventory.getItem(0); // Get the smithing template
        ItemStack baseItem = inventory.getItem(1); // Get the base item
        ItemStack additionItem = inventory.getItem(2); // Get the Netherite Ingot
        ItemStack resultItem = event.getCurrentItem(); // Get the item in the result slot

        // Ensure the clicked slot is the result slot and the items are not null
        if (event.getRawSlot() == 3 && resultItem != null && baseItem != null && templateItem != null && additionItem != null) {
            // Get the number of Netherite Ingots required from the config file
            String itemType = baseItem.getType().name();
            int requiredIngots = plugin.getPluginConfig().getInt("Items." + itemType, 1);

            // Check if the number of ingots is greater than or equal to the required amount
            if (additionItem.getAmount() >= requiredIngots) {
                // Only consume the amount of ingots specified in the config and 1 template
                int takeIngots = requiredIngots - 1;
                additionItem.setAmount(additionItem.getAmount() - takeIngots);
                templateItem.setAmount(templateItem.getAmount() - 0); // Corrected to consume 1 template
            }
        }
    }
}
