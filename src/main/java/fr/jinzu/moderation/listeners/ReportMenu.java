package fr.jinzu.moderation.listeners;

import fr.jinzu.moderation.core.MainMod;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import sun.applet.Main;

public class ReportMenu implements Listener {

    private MainMod main;
    public ReportMenu(MainMod main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        ItemStack reason = e.getCurrentItem();
        String targetName = e.getInventory().getName().substring(12);

        if(reason != null) {
            String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
            switch(reason.getType()){
                case IRON_SWORD:
                    if(itemName.equals("§cCheat (Reach, AntiKB, Aimbot, AutoClick, etc...)")){

                        sendToMods(targetName, itemName.substring(2));

                        player.sendMessage("§2Le joueur a correctement été signalé !");

                        e.setCancelled(true);
                        player.closeInventory();
                    }
                    break;

                case DIAMOND_BLOCK:
                    if(itemName.equals("§cDuplication")){

                        sendToMods(targetName, itemName.substring(2));

                        player.sendMessage("§2Le joueur a correctement été signalé !");

                        System.out.println("coucou");
                        e.setCancelled(true);
                        player.closeInventory();
                    }
                    break;

                default:
                    break;
            }
        }
    }

    private void sendToMods(String targetName, String displayName) {
        ConfigurationSection section = main.getReports().getConfigurationSection("Reports");

        if(section == null) {
            section = main.getReports().createSection("Reports");
        }

        section.set(targetName, displayName);

        main.saveReports();

        for(Player players : Bukkit.getOnlinePlayers()){
            if(players.hasPermission("mod.receive")){
                players.sendMessage("§bLe joueur §a" + targetName + " §ba été signalé pour : " + displayName);
            }
        }
    }
}
