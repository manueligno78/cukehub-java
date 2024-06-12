
package com.github.manueligno78.cukehub;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    private final ConfigService configService;
    private final GitService gitService;
    private final FeatureFileModule featureFilesModule;

    @Autowired
    public AppController(ConfigService configService, GitService gitService, FeatureFileModule featureFilesModule) {
        this.configService = configService;
        this.gitService = gitService;
        this.featureFilesModule = featureFilesModule;
    }

    @GetMapping("/")
    public String getRoot(Model model) {
        Config loadedConfig = configService.loadConfig("config.json");
        if (!loadedConfig.getIsConfigurated()) {
            System.out.println("Config check failed, redirecting to /settings");
            return "redirect:/settings";
        }
        System.out.println("Git Project URL: " + loadedConfig.getGitProjectUrl());
        System.out.println("Config check true, rendering pages/index");
        // if (featureFilesModule.getFeatureFilesCopy().length == 0) {
        updateFeatureFiles(loadedConfig);
        // }

        model.addAttribute("configuration", loadedConfig);
        model.addAttribute("featureFiles", featureFilesModule.getFiles(loadedConfig.getDirectoryPath(), loadedConfig.getFolderToExclude()));
        return "index";
    }

    @GetMapping("/settings")
    public String getSettings(Model model) {
        Config loadedConfig = configService.loadConfig("config.json");
        model.addAttribute("gitProjectUrl", loadedConfig.getGitProjectUrl());
        model.addAttribute("gitBranch", loadedConfig.getGitBranch());
        model.addAttribute("directoryPath", loadedConfig.getDirectoryPath());
        model.addAttribute("folderToExclude", loadedConfig.getFolderToExclude());
        return "settings";
    }

    @PostMapping("/save-settings")
    public String saveSettings(String gitProjectUrl, String gitBranch, String directoryPath, String folderToExclude) {
        Config newConfig = new Config(gitProjectUrl, gitBranch, directoryPath, folderToExclude, true);
        if (configService.saveConfig(newConfig, "config.json")) {
            System.out.println("Config saved.");
            System.out.println("Create new directory if not exists");
            boolean folderCheckSuccessful = createFolderIfNotExists(directoryPath);
            boolean cloneSuccessful = gitService.cloneGitRepository(gitProjectUrl, gitBranch, directoryPath);
            System.out.println("folderCheckSuccessful: " + folderCheckSuccessful);
            System.out.println("cloneSuccessful: " + cloneSuccessful);
            if (cloneSuccessful && folderCheckSuccessful) {
                System.out.println("Clone and folder success, redirecting to /");
                return "redirect:/";
            } else {
                System.out.println("Clone or folder fail, redirecting to /settings");
                return "redirect:/settings";
            }
        } else {
            return "Error: Config not saved";
        }
    }

    private void updateFeatureFiles(Config config) {
        List<FeatureFile> featureFiles = featureFilesModule.getFiles(config.getDirectoryPath(),
                config.getFolderToExclude());
        for (FeatureFile featureFile : featureFiles) {
            System.out.println("Feature file: " + featureFile.getName());
            System.out.println("Relative path: " + featureFile.getRelativePath());
            System.out.println("Tags: " + featureFile.getTags().toString());
        }
        // featureFilesModule.updateFeatureFilesCopy(featureFiles);
        // notifyClients(JSON.stringify({ action: 'featureFilesUpdated' }));
    }

    private boolean createFolderIfNotExists(String folderPath) {
        try {
            System.out.println("Try creating folder at " + folderPath);
            if (!Files.exists(Paths.get(folderPath))) {
                System.out.println("Creating folder at " + folderPath);
                Files.createDirectory(Paths.get(folderPath));
            }
            return true;
        } catch (IOException e) {
            System.out.println("Error creating folder at " + folderPath + ": " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}

// Controller.java is a porting of the following JavaScript code:
// const express=require('express');const bodyParser=require('body-parser');const fs=require('fs');const path=require('path');const http=require('http');const featureFilesModule=require('./js/modules/featureFilesModule.js');const gitModule=require('./js/modules/gitModule.js'); // new
//                                                                                                                                                                                                                                                                                    // module
//                                                                                                                                                                                                                                                                                    // for
//                                                                                                                                                                                                                                                                                    // git
//                                                                                                                                                                                                                                                                                    // operations
// const app=express();const server=http.createServer(app);const rateLimit=require("express-rate-limit");const{initializeWebSocket,handleReset,notifyClients}=require('./js/modules/websocket.js');const{create}=require('domain');const wss=initializeWebSocket(server);

// let config = loadConfig(); // load config using a function

// const limiter=

// rateLimit({
//   windowMs: 1 * 60 * 1000, // 1 minutes
//   max: 100
// });

// app.use(limiter);app.set('view engine','ejs');app.set('views',path.join(__dirname,'views'));app.use(bodyParser.urlencoded({ extended: false }));app.use(express.static(path.join(__dirname, 'public')));

// module.exports.server = server;

// app.get('/', async (req, res) => {
//   config = loadConfig(); // reload config
//   if (!config.isConfigurated) {
//     console.log('Config check failed, redirecting to /settings');
//     res.redirect('/settings');
//     return;
//   }
//   console.log('Config check true, rendering pages/index');
//   if (featureFilesModule.getFeatureFilesCopy().length === 0){

// await updateFeatureFiles();

// }res.render('pages/index',{configuration:config,featureFiles:featureFilesModule.getFeatureFilesCopy()});});

// app.get('/settings',(req,res)=>{config=loadConfig();res.render('pages/settings',{gitProjectUrl:config.gitProjectUrl,gitBranch:config.gitBranch,directoryPath:config.directoryPath,folderToExclude:config.folderToExclude});});

// app.post('/save-settings',async(req,res)=>{const newGitProjectUrl=req.body.gitProjectUrl;const newGitBranch=req.body.gitBranch;const newDirectoryPath=req.body.directoryPath;const newFolderToExclude=req.body.folderToExclude;const newConfig={gitProjectUrl:newGitProjectUrl,gitBranch:newGitBranch,directoryPath:newDirectoryPath,folderToExclude:newFolderToExclude,isConfigurated:true};fs.writeFileSync(path.join(__dirname,'config.json'),JSON.stringify(newConfig,null,2),'utf-8');config=newConfig;console.log('Config saved.');console.log('Create new directory if not exists');const folderCheckSuccessful=

// await createFolderIfNotExists(newDirectoryPath);
//   const cloneSuccessful = await gitModule.cloneGitRepository(newGitProjectUrl, newGitBranch, newDirectoryPath, wss);
//   console.log("folederCheckSuccessful: " + folderCheckSuccessful);
//   console.log("cloneSuccessful: " + cloneSuccessful);
//   if (cloneSuccessful && folderCheckSuccessful) {
//     console.log("Clone and folder success, redirecting to /");
//     updateFeatureFiles();
//     res.redirect('/');
//   } else {
//     console.log("Clone or folder fail, redirecting to /settings");
//     res.redirect('/settings');
//   }
// });

// async function

// createFolderIfNotExists(folderPath) {
//   try {
//     console.log(`Try creating folder at ${folderPath}`);
//     if (!fs.existsSync(folderPath)) {
//       console.log(`Creating folder at ${folderPath}`);
//       fs.mkdirSync(folderPath);
//     }
//     return true;
//   } catch (error) {
//     console.error(`Error creating folder at ${folderPath}: ${error}`);
//     return false;
//   }
// }

// function loadConfig() {
//     return JSON.parse(fs.readFileSync(path.join(__dirname,'config.json'),'utf8'));
// }

// async function

// updateFeatureFiles() {
//   const featureFiles = featureFilesModule.getFiles(config.directoryPath);
//   featureFilesModule.updateFeatureFilesCopy(JSON.parse(JSON.stringify(featureFiles)));
//   notifyClients(JSON.stringify({ action: 'featureFilesUpdated' }));
// }

// server.listen(3000,()=>{console.log('C

// ukeHub listening
// on http:// localhost:3000');
// });
