package com.linebeck.gitter.commands;

import com.linebeck.gitter.handlers.TextHandler;
import com.linebeck.gitter.internal.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GitterCommand implements CommandExecutor, TabCompleter {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("Gitter.Admin")) { return false; }

        if(args.length == 0) {
            sender.sendMessage(TextHandler.setText("Usage: /gitter <repo name>"));
            return false;
        }

        String name = args[0];

        var session = Main.getInstance().getGitSessionManager().getSessionByName(name);

        if(session == null) {
            sender.sendMessage(TextHandler.setText("Could not find a session with that name."));
            return false;
        }

        session.cloneRepo(sender);

        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, Command command, String label, String[] args) {
        List<String> arguments = Main.getInstance().getGitSessionManager().gitSessions.stream().map(session -> session.name()).collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        if(args.length == 1) {
            for(String argument : arguments) {
                if(argument.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(argument);
                }
            }
            return result;
        }
        return null;
    }
}
