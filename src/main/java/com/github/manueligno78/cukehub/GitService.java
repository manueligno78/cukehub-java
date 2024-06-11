package com.github.manueligno78.cukehub;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.stereotype.Service;

@Service
public class GitService {

    public boolean cloneGitRepository(String gitProjectUrl, String gitBranch, String directoryPath) {
        try {
            File directory = new File(directoryPath);
            if (directory.exists() && directory.isDirectory()) {
                File gitDir = new File(directoryPath + "/.git");
                if (gitDir.exists()) {
                    // Existing git repository
                    Repository existingRepo = new FileRepositoryBuilder().setGitDir(gitDir).build();
                    String currentRemoteUrl = existingRepo.getConfig().getString("remote", "origin", "url");
                    if (currentRemoteUrl == null || !currentRemoteUrl.equals(gitProjectUrl)) {
                        throw new Exception("Existing git project in directory does not match the provided URL");
                    }
                    Git git = new Git(existingRepo);
                    String currentBranch = git.getRepository().getBranch();
                    if (!currentBranch.equals(gitBranch)) {
                        git.checkout().setName(gitBranch).call();
                    }
                } else if (directory.list().length > 0) {
                    throw new Exception("Directory is not empty and does not contain a git project");
                } else {
                    // Clone repository
                    Git.cloneRepository()
                            .setURI(gitProjectUrl)
                            .setBranch(gitBranch)
                            .setDirectory(directory)
                            .call();
                }
            } else {
                throw new Exception("Directory does not exist");
            }
            // notify clients via wss
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
