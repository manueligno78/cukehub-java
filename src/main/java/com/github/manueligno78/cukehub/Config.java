package com.github.manueligno78.cukehub;

public class Config {
    private String gitProjectUrl;
    private String gitBranch;
    private String directoryPath;
    private String folderToExclude;
    private String isConfigurated;

    public Config() {
    }

    public Config(String gitProjectUrl, String gitBranch, String directoryPath, String folderToExclude,
            String isConfigurated) {
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

    public String getIsConfigurated() {
        return isConfigurated;
    }

    public void setIsConfigurated(String isConfigurated) {
        this.isConfigurated = isConfigurated;
    }
}