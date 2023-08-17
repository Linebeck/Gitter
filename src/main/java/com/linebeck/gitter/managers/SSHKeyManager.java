package com.linebeck.gitter.managers;

import com.linebeck.gitter.entities.SSHKey;
import com.linebeck.gitter.internal.Main;
import com.linebeck.gitter.utilities.FileLocationUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class SSHKeyManager {

    public Set<SSHKey> sshKeys = new HashSet<>();

    public SSHKeyManager(FileConfiguration fileConfiguration) {
        ConfigurationSection sshKeysSection = fileConfiguration.getConfigurationSection("SSH-Keys");
        if(sshKeysSection != null) {
            for(String key : sshKeysSection.getKeys(false)) {
                try {
                    ConfigurationSection sshKey = sshKeysSection.getConfigurationSection(key);
                    var name = sshKey.getString("Name");

                    var sshKeyPath = new File(Main.getInstance().getDataFolder(), FileLocationUtil.correctFilePath(sshKey.getString("SSH-Key-Path")));
                    var sshKeyPassword = sshKey.getString("SSH-Key-Password");

                    sshKeys.add(new SSHKey(name, sshKeyPath, sshKeyPassword));
                    Main.getInstance().getLogger().info("Loaded SSH-Key: " + name);
                } catch (Exception exception) {
                    Main.getInstance().getLogger().info("Skipping SSH Key: " + key);
                }
            }
        }
    }

    public SSHKey getSSHKeyByName(String name) {
        for(var sshKey : sshKeys) {
            if(sshKey.name().equalsIgnoreCase(name)) {
                return sshKey;
            }
        }
        return null;
    }
}
