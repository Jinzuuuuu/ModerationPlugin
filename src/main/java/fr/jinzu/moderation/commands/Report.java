package fr.jinzu.moderation.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Report implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("report")){
            if(commandSender instanceof Player) {
                Player player = (Player) commandSender;
                if(args.length == 1) {
                    String targetName = args[0];

                    Player target = Bukkit.getPlayer(targetName);

                    if(target != null){
                        Inventory inv = Bukkit.createInventory(null, 18, "§bReport: §c" + target.getName());

                        ItemStack cheatReason = new ItemStack(Material.IRON_SWORD);
                        ItemMeta cheatReasonMeta = cheatReason.getItemMeta();
                        cheatReasonMeta.setDisplayName("§cCheat (Reach, AntiKB, Aimbot, AutoClick, etc...)");
                        cheatReason.setItemMeta(cheatReasonMeta);

                        inv.addItem(cheatReason);

                        ItemStack dupliReason = new ItemStack(Material.DIAMOND_BLOCK);
                        ItemMeta dupliReasonMeta = dupliReason.getItemMeta();
                        dupliReasonMeta.setDisplayName("§cDuplication");
                        dupliReason.setItemMeta(dupliReasonMeta);

                        inv.addItem(dupliReason);

                        player.openInventory(inv);
                    } else {
                        player.sendMessage("§cCe joueur n'existe pas ou n'est pas connecté au serveur !");
                    }
                } else if(args.length == 0) {
                    player.sendMessage("§cVeuillez saisir le pseudo d'un joueur !");
                } else {
                    player.sendMessage("§cSyntaxe invalide !");
                }
            } else {
                commandSender.sendMessage("§cVous devez être un joueur pour utiliser cette commande !");
            }
        }

        return true;
    }
}
