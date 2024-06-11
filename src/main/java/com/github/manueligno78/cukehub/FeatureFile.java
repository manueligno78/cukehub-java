package com.github.manueligno78.cukehub;

import java.util.List;
import java.util.stream.Collectors;

public class FeatureFile {
    // name of the feature file
    // path of the feature file
    // relative path of the feature file
    // content of the feature file
    // tags of the feature file

    private String name;
    private String path;
    private String relativePath;
    private String content;
    private String tags;
    private String featureId;

    public FeatureFile(String name, String path, String relativePath, String content, String tags) {
        this.name = name;
        this.path = path;
        this.relativePath = relativePath;
        this.content = content;
        this.tags = tags;
        this.featureId = name+randomString(5);
    }

    private String randomString(int length) {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public String getFeatureId() {
        return featureId;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        // search for tags in the content
        //TODO: this method is buggy
        return content.lines().filter(line -> line.startsWith("@")).collect(Collectors.toList());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
