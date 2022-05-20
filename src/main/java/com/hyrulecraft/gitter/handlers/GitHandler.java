package com.hyrulecraft.gitter.handlers;

import com.hyrulecraft.gitter.internal.Main;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

public class GitHandler {

    private final String repo;

    private File file;

    public GitHandler(String repo, String directory) {
        this.repo = repo;
        this.file = new File(Main.getInstance().getDataFolder().getParent() + directory);
    }

    public boolean cloneRepo() {
        try {
            File gitFile = new File(this.file + File.separator + ".git");
            if(gitFile.exists()) {
                Git git = Git.open(gitFile);
                if(git != null) {
                    git.pull().call();
                    git.close();
                }
            } else {
                Git.cloneRepository()
                        .setURI(this.repo)
                        .setDirectory(this.file)
                        .call()
                        .close();
            }
        } catch (GitAPIException | IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
