package com.github.manueligno78.cukehub;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class Config {
    private String gitProjectUrl;
    private String gitBranch;
    private String directoryPath;
    private String folderToExclude;
    private boolean isConfigurated;

    public Config() {
    }

    public Config(String gitProjectUrl, String gitBranch, String directoryPath, String folderToExclude,
            boolean isConfigurated) {
        this.gitProjectUrl = gitProjectUrl;
        this.gitBranch = gitBranch;
        this.directoryPath = directoryPath;
        this.folderToExclude = folderToExclude;
        this.isConfigurated = isConfigurated;
    }

    public String getGitProjectUrl() {
        return gitProjectUrl;
    }

    public void setGitProjectUrl(String gitProjectUrl) {
        this.gitProjectUrl = gitProjectUrl;
    }

    public String getGitBranch() {
        return gitBranch;
    }

    public void setGitBranch(String gitBranch) {
        this.gitBranch = gitBranch;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public String getFolderToExclude() {
        return folderToExclude;
    }

    public void setFolderToExclude(String folderToExclude) {
        this.folderToExclude = folderToExclude;
    }

    public boolean getIsConfigurated() {
        return isConfigurated;
    }

    public void setIsConfigurated(boolean b) {
        this.isConfigurated = b;
    }

    public Config loadFromFile(String fileName) {
        try {
            Gson gson = new Gson();
            Config tempConfig = gson.fromJson(new FileReader(fileName), Config.class);
            return tempConfig;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean writeToFile(String fileName) {
        Gson gson = new Gson();
        try {
            String jsonString = gson.toJson(this);
            Files.write(Paths.get(fileName), jsonString.getBytes());
            return true;
        } catch (IOException e) {
            System.out.println("Error writing config: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}