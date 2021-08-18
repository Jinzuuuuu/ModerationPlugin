package fr.jinzu.moderation.core;

import fr.jinzu.moderation.commands.Kick;
import fr.jinzu.moderation.commands.ModerationMode;
import fr.jinzu.moderation.commands.Report;
import fr.jinzu.moderation.listeners.ReportMenu;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class MainMod extends JavaPlugin {

    private FileConfiguration sanctions;
    private FileConfiguration reports;

    private final ArrayList<UUID> moderateurs = new ArrayList<>();

    @Override
    public void onEnable() {
        reloadReports();
        reloadSanctions();

        getCommand("mod").setExecutor(new ModerationMode(this));
        getCommand("report").setExecutor(new Report());
        getCommand("kick").setExecutor(new Kick(this));

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new ReportMenu(this), this);
    }

    public ArrayList<UUID> getModerateurs() {
        return moderateurs;
    }

    public FileConfiguration getSanctions() {
        return sanctions;
    }
    public FileConfiguration getReports() { return  reports; }

    public void reloadSanctions() {
        File sanctionsFile = new File(getDataFolder(), "sanctions.yml");
        if (!sanctionsFile.exists()) {
            sanctionsFile.getParentFile().mkdirs();
            saveResource("sanctions.yml", false);
        }

        sanctions = new YamlConfiguration();
        try {
            sanctions.load(sanctionsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveSanctions() {
        File sanctionsFile = new File(getDataFolder(), "sanctions.yml");
        try {
            getSanctions().save(sanctionsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadReports() {
        File reportsFile = new File(getDataFolder(), "reports.yml");
        if (!reportsFile.exists()) {
            reportsFile.getParentFile().mkdirs();
            saveResource("reports.yml", false);
        }

        reports = new YamlConfiguration();
        try {
            reports.load(reportsFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveReports() {
        File reportsFile = new File(getDataFolder(), "reports.yml");
        try {
            getReports().save(reportsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
