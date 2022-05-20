package com.hyrulecraft.gitter.internal;

import com.hyrulecraft.gitter.commands.GitterCommand;
import com.hyrulecraft.gitter.handlers.GitHandler;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    private static Main main;
    public static Main getInstance() { return main; }

    private GitHandler gitHandler;
    public GitHandler getGitHandler() { return gitHandler; }

    @Override
    public void onEnable() {
        this.getLogger().info(ChatColor.GREEN + "##############################");
        this.getLogger().info(ChatColor.GREEN + getDescription().getName());
        this.getLogger().info(ChatColor.GREEN + "Version: " + getDescription().getVersion());
        this.getLogger().info(ChatColor.GREEN + "Author: " + getDescription().getAuthors());
        this.getLogger().info(ChatColor.GREEN + "##############################");

        main = this;

        loadConfiguration();

        String repo = getConfig().getString("Repository");
        String directory = getConfig().getString("Directory");

        if(repo == null || directory == null) {
            return;
        }

        this.gitHandler = new GitHandler(repo, directory);
        this.getCommand("gitter").setExecutor(new GitterCommand());
    }

    private void loadConfiguration() {
        try {
            File file = new File(getDataFolder(), "config.yml");
            if(file.exists()) { return; }
            saveDefaultConfig();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onDisable() {}
}
