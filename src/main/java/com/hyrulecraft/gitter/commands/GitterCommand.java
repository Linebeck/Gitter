package com.hyrulecraft.gitter.commands;

import com.hyrulecraft.gitter.handlers.TextHandler;
import com.hyrulecraft.gitter.internal.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GitterCommand implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("Gitter.Admin")) { return false; }
        boolean success = Main.getInstance().getGitHandler().cloneRepo();

        if(success) {
            sender.sendMessage(TextHandler.setText("Successfully cloned!", "#d9442d"));
        } else {
            sender.sendMessage(TextHandler.setText("There was an error cloning the repository!", "#d9442d"));
        }
        return false;
    }
}
