package com.linebeck.gitter.managers;

import com.linebeck.gitter.entities.GitSession;
import com.linebeck.gitter.enums.RepositoryType;
import com.linebeck.gitter.internal.Main;
import com.linebeck.gitter.utilities.FileLocationUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class GitSessionManager {

    public Set<GitSession> gitSessions = new HashSet<>();

    public GitSessionManager(FileConfiguration fileConfiguration) {
        ConfigurationSection sessions = fileConfiguration.getConfigurationSection("Sessions");
        if(sessions == null) return;

        for(String key : sessions.getKeys(false)) {
            try {
                ConfigurationSection session = sessions.getConfigurationSection(key);
                var name = session.getString("Name");
                var repository = session.getString("Repository");

                var directoryLocation = new File(Main.getInstance().getDataFolder().getParent(), FileLocationUtil.correctFilePath(session.getString("Directory-Location")));

                var repositoryType = RepositoryType.getRepositoryTypeByName(session.getString("Repository-Type"));

                var sshKeyName = session.getString("SSH-Key-Name");

                gitSessions.add(new GitSession(name, repository, directoryLocation, repositoryType, sshKeyName));
                Main.getInstance().getLogger().info("Loaded session: " + name);
            } catch (Exception exception) {
                Main.getInstance().getLogger().info("Skipping session: " + key);
            }
        }
    }

    public GitSession getSessionByName(String name) {
        for(var session : gitSessions) {
            if(session.name().equals(name)) return session;
        }
        return null;
    }
}
