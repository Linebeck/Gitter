package com.linebeck.gitter.internal;

import com.linebeck.gitter.commands.GitterCommand;
import com.linebeck.gitter.managers.GitSessionManager;
import com.linebeck.gitter.managers.SSHKeyManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {

    private static Main main;
    public static Main getInstance() { return main; }

    private GitSessionManager gitSessionManager;
    public GitSessionManager getGitSessionManager() { return gitSessionManager; }

    private SSHKeyManager sshKeyManager;
    public SSHKeyManager getSSHKeyManager() { return sshKeyManager; }

    @Override
    public void onEnable() {
        this.getLogger().info(ChatColor.GREEN + "##############################");
        this.getLogger().info(ChatColor.GREEN + getDescription().getName());
        this.getLogger().info(ChatColor.GREEN + "Version: " + getDescription().getVersion());
        this.getLogger().info(ChatColor.GREEN + "Author: " + getDescription().getAuthors());
        this.getLogger().info(ChatColor.GREEN + "##############################");

        main = this;

        loadConfiguration();

        this.gitSessionManager = new GitSessionManager(getConfig());

        if(gitSessionManager.gitSessions.size() == 0) { return; }

        this.sshKeyManager = new SSHKeyManager(getConfig());

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
}
