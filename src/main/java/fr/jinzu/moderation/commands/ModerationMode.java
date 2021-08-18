package fr.jinzu.moderation.commands;

import fr.jinzu.moderation.core.MainMod;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModerationMode implements CommandExecutor {

    private final MainMod main;

    public ModerationMode(MainMod main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;

            if(command.getName().equalsIgnoreCase("mod")){
                if(player.hasPermission("mod.mod")){
                    if(!main.getModerateurs().contains(player.getUniqueId())){
                        main.getModerateurs().add(player.getUniqueId());

                        player.sendMessage("§2Vous êtes à présent dans le mode modération");
                        //TODO SAVE INV
                    } else {
                        main.getModerateurs().remove(player.getUniqueId());
                        player.getInventory().clear();

                        player.sendMessage("§eVous n'êtes plus en mode modération !");
                        //TODO RENDRE INV
                    }
                } else {
                    player.sendMessage("§4Vous n'avez pas la permission d'éxecuter cette commande !");
                }
            }
        } else {
            sender.sendMessage("§cVous devez être un joueur pour utiliser cette commande !");
        }

        return true;
    }
}
