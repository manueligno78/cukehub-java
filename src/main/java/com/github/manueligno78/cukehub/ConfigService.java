package com.github.manueligno78.cukehub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private final Config config;

    @Autowired
    public ConfigService(Config config) {
        this.config = config;
    }

    public Config loadConfig(String filePath) {
        return config.loadFromFile(filePath);
    }

    public boolean saveConfig(Config newConfig, String filePath) {
        return newConfig.writeToFile(filePath);
    }

}
