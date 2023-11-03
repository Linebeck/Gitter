package com.linebeck.gitter.entities;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.linebeck.gitter.enums.RepositoryType;
import com.linebeck.gitter.handlers.TextHandler;
import com.linebeck.gitter.internal.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.SshTransport;
import org.eclipse.jgit.transport.ssh.jsch.JschConfigSessionFactory;
import org.eclipse.jgit.transport.ssh.jsch.OpenSshConfig;
import org.eclipse.jgit.util.FS;

import java.io.File;
import java.io.IOException;

public record GitSession(String name, String repository, File directoryLocation, RepositoryType repositoryType, String sshKeyName) {

    public void cloneRepo(CommandSender sender) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            boolean success;
            if(sshKeyName != null && !sshKeyName.isEmpty()) {
                Main.getInstance().getLogger().info("Cloning " + name + " via SSH.");
                success = cloneWithSSH();
            } else {
                Main.getInstance().getLogger().info("Cloning " + name + " via public.");
                success = cloneWithoutSSH();
            }

            if(success) {
                sender.sendMessage(
                        TextHandler.setText("Successfully cloned/pulled: ", "#d9442d")
                                .append(TextHandler.setText(name, "#00A36C"))
                );
            } else {
                sender.sendMessage(
                        TextHandler.setText("There was an error cloning/pulling: ", "#880808")
                                .append(TextHandler.setText(name, "#00A36C"))
                );
            }
        });
    }

    private boolean cloneWithoutSSH() {
        if(isPublicRepositoryLink()) {
            Main.getInstance().getLogger().info("You must use the HTTPS URL to clone a public repository.");
            return false;
        }

        try {
            if(this.directoryLocation.exists() && new File(this.directoryLocation, ".git").exists()) {
                try (Git git = Git.open(this.directoryLocation)) {
                    git.pull().call();
                }
            } else {
                Git.cloneRepository()
                        .setURI(this.repository)
                        .setDirectory(this.directoryLocation)
                        .call()
                        .close();
            }
            return true;
        } catch (GitAPIException | IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }


    private boolean cloneWithSSH() {
        try {
            SshSessionFactory sshSessionFactory = new JschConfigSessionFactory() {

                @Override
                protected void configure(OpenSshConfig.Host hc, Session session) {
                    session.setConfig("StrictHostKeyChecking", "no");
                }

                @Override
                protected JSch createDefaultJSch(FS fs) throws JSchException {
                    var sshKey = Main.getInstance().getSSHKeyManager().getSSHKeyByName(sshKeyName);

                    Main.getInstance().getLogger().info("Using SSH Key: " + sshKey.location().getPath());

                    JSch defaultJSch = super.createDefaultJSch(fs);
                    defaultJSch.addIdentity(sshKey.location().getPath(), sshKey.password());
                    return defaultJSch;
                }
            };

            File gitFile = new File(this.directoryLocation + File.separator + ".git");
            if (gitFile.exists()) {
                Git git = Git.open(this.directoryLocation); // Use the directory location itself
                git.pull().setTransportConfigCallback(transport -> {
                    SshTransport sshTransport = (SshTransport) transport;
                    sshTransport.setSshSessionFactory(sshSessionFactory);
                }).call();
                git.close();
            } else {
                CloneCommand cloneCommand = Git.cloneRepository();
                cloneCommand.setURI(this.repository);
                cloneCommand.setDirectory(this.directoryLocation);
                cloneCommand.setTransportConfigCallback(transport -> {
                    SshTransport sshTransport = (SshTransport) transport;
                    sshTransport.setSshSessionFactory(sshSessionFactory);
                });
                cloneCommand.call().close();
            }

            Main.getInstance().getLogger().info("Cloned Report Successfully!");
            return true;
        } catch (GitAPIException | RuntimeException | IOException exception) {
            Main.getInstance().getLogger().info("Error cloning repository: " + exception.getMessage());
            exception.printStackTrace();
            return false;
        }
    }

    private boolean isPublicRepositoryLink() {
        if(!this.repository.startsWith("https://github.com/")) {
            return false;
        }
        return true;
    }
}
