package fr.jinzu.moderation.commands;

import fr.jinzu.moderation.core.MainMod;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Kick implements CommandExecutor {

    private final MainMod main;

    public Kick(MainMod main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("kick")) {
            if(commandSender.hasPermission("mod.kick")){
                if(args.length == 1) {
                    String targetName = args[0];

                    Player target = Bukkit.getPlayer(targetName);

                    if(target != null) {
                        ConfigurationSection section = main.getSanctions().getConfigurationSection("kick");

                        if(section == null) {
                            section = main.getSanctions().createSection("kick");
                        }

                        int playerInt = section.getInt(targetName);
                        if(playerInt != 0) {
                            section.set(targetName, playerInt+1);
                        } else {
                            section.set(targetName, 1);
                        }

                        main.saveSanctions();

                        target.kickPlayer(ChatColor.RED + "Vous avez été kick par un membre du staff !");
                    } else {
                        commandSender.sendMessage("§cCe joueur n'existe pas ou n'est pas connecté au serveur !");
                    }
                } else if(args.length == 0) {
                    commandSender.sendMessage("§cVeuillez saisir le pseudo d'un joueur !");
                } else {
                    commandSender.sendMessage("§cSyntaxe invalide !");
                }
            } else {
                commandSender.sendMessage("§4Vous n'avez pas la permission d'éxecuter cette commande !");
            }
        }
        return true;
    }
}
