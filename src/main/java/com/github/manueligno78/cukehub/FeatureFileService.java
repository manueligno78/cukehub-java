package com.github.manueligno78.cukehub;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FeatureFileService {

    public List<FeatureFile> getFiles(Config config) {
        List<FeatureFile> featureFiles = new ArrayList<>();
        Path startPath = Paths.get(config.getDirectoryPath());
        String pattern = "glob:**/*.feature";

        try {
            Files.walk(startPath)
                .filter(path -> Files.isRegularFile(path) && !path.toString().matches(config.getFolderToExclude()))
                .forEach(path -> {
                    if (path.getFileSystem().getPathMatcher(pattern).matches(path)) {
                        String name = path.getFileName().toString();
                        String absolutePath = path.toAbsolutePath().toString();
                        String relativePath = startPath.relativize(path).toString();
                        String content = "";
                        try {
                            content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        FeatureFile featureFile = new FeatureFile(name, absolutePath, relativePath, content, null);
                        featureFiles.add(featureFile);
                    }
                });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return featureFiles;
    }

    public void updateFeatureFilesCopy(List<FeatureFile> featureFiles) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFeatureFilesCopy'");
    }

    public List<FeatureFile> updateFeatureFiles(Config config) {
        List<FeatureFile> featureFiles = getFiles(config);
        for (FeatureFile featureFile : featureFiles) {
            System.out.println("Feature file: " + featureFile.getName());
            System.out.println("Relative path: " + featureFile.getRelativePath());
            System.out.println("Tags: " + featureFile.getTags().toString());
        }
        return featureFiles;
    // featureFilesModule.updateFeatureFilesCopy(featureFiles);
    // notifyClients(JSON.stringify({ action: 'featureFilesUpdated' }));
    }

}


// const fs=require('fs');const path=require('path');const Gherkin=require('@cucumber/gherkin');const Messages=require('@cucumber/messages');const gherkinDocumentToString=require('./gherkinUtils.js');const{setNestedProperty,ensureDirectoryExistence}=require('./utils.js');

// let config = JSON.parse(fs.readFileSync(path.join(__dirname, '../../config.json'), 'utf8'));

// let featureFilesCopy = [];

// function updateFeatureFilesCopy(newData) {
//     featureFilesCopy = newData;
// }

// function getFeatureFilesCopy() {
//     return featureFilesCopy;
// }

// function getFiles(dirPath, arrayOfFiles = []) {
//     const files = fs.readdirSync(dirPath);
//     const foldersToExclude = config.folderToExclude;
//     let excludePatterns = [];
//     if (foldersToExclude) {
//         excludePatterns = foldersToExclude.split(',').map(folder =>
//             new RegExp('^' + folder.trim().replace(/\*/g, '.*') + '$')
//         );
//     }
//     files.forEach(file => {
//         const fullPath = path.join(dirPath, "/", file);
//         const isExcluded = excludePatterns.some(pattern => pattern.test(fullPath));
//         if (isExcluded) {
//             return;
//         }
//         if (fs.statSync(fullPath).isDirectory()) {
//             arrayOfFiles = getFiles(fullPath, arrayOfFiles);
//         } else if (file.endsWith('.feature')) {
//             const scenarioData = getScenarios(fullPath);
//             if (scenarioData) {
//                 arrayOfFiles.push({
//                     name: file,
//                     path: fullPath,
//                     relativePath: fullPath.replace(config.directoryPath, ''),
//                     ...scenarioData
//                 });
//             }
//         }
//     });
//     return arrayOfFiles;
// }

// function getScenarios(filePath) {
//     const fileContent = fs.readFileSync(filePath, 'utf-8');
//     return parseGherkinContent(fileContent);
// }

// function parseGherkinContent(content) {
//     try {
//         const uuidFn = Messages.IdGenerator.uuid();
//         const builder = new Gherkin.AstBuilder(uuidFn);
//         const matcher = new Gherkin.GherkinClassicTokenMatcher();
//         const parser = new Gherkin.Parser(builder, matcher);
//         const gherkinDocument = parser.parse(content);
//         const featureId = uuidFn();
//         const tags = [];
//         // Handle feature tags
//         if (gherkinDocument.feature.tags) {
//             gherkinDocument.feature.tags.forEach(tag => {
//                 tags.push({
//                     tag: tag.name,
//                     featureId: featureId
//                 });
//             });
//         }
//         gherkinDocument.feature.children.forEach(child => {
//             if (child.scenario) {
//                 child.scenario.tags.forEach(tag => {
//                     tags.push({
//                         tag: tag.name,
//                         scenario: child.scenario.name,
//                         featureId: featureId
//                     });
//                 });
//                 child.scenario.isOutline = child.scenario.keyword.includes('Outline');
//                 if (child.scenario.isOutline && child.scenario.examples) {
//                     child.scenario.numberOfExamples = child.scenario.examples.reduce((count, example) => {
//                         return count + (example.tableBody ? example.tableBody.length : 0);
//                     }, 0);
//                 }
//                 // Count the steps in the scenario
//                 child.scenario.numberOfSteps = child.scenario.steps ? child.scenario.steps.length : 0;
//             }
//         });
//         return {
//             featureId: featureId,
//             tags: tags,
//             ...gherkinDocument
//         };
//     } catch (error) {
//         console.error('Error parsing gherkin content:' + error);
//         return null;
//     }
// }

// function reset() {
//     config=JSON.parse(fs.readFileSync(path.join(__dirname,'../../config.json'),'utf8'));const directoryPath=config.directoryPath;if(!directoryPath){console.error('directoryPath non è definito nel file config.json');return null;}let featureFiles=getFiles(directoryPath);updateFeatureFilesCopy(JSON.parse(JSON.stringify(featureFiles)));return true;
// }

// function updateFeatureFile(featureId, field, newValue) {
//     let featureFile = getFeatureFilesCopy().find(file => file.featureId === featureId);
//     if (featureFile) {
//         // If the field path contains a tag validate the tag (start with @ and no spaces)
//         if (field.includes('tags')) {
//             if (!newValue.startsWith('@') || newValue.includes(' ')) {
//                 return null;
//             }
//         }
//         setNestedProperty(featureFile, field, newValue);
//         return featureFile;
//     }
//     return null;
// }

// function removeTag(featureId, scenarioId, tag) {
//     let featureFile = getFeatureFilesCopy().find(file => file.featureId === featureId);
//     let result = null;
//     if (featureFile) {
//         // Remove tag from feature tags
//         if (featureFile.feature.tags && featureFile.feature.tags.length > 0 && !scenarioId) {
//             let tagIndex = featureFile.feature.tags.findIndex(t => t.name === tag);
//             if (tagIndex > -1) {
//                 featureFile.feature.tags.splice(tagIndex, 1);
//                 let featureIndex = getFeatureFilesCopy().findIndex(file => file.featureId === featureId);
//                 getFeatureFilesCopy()[featureIndex] = featureFile;
//                 result = true;
//             }
//         }
//         // Remove tag from scenario tags
//         if (featureFile.feature.children && featureFile.feature.children.length > 0 && scenarioId) {
//             let scenario = featureFile.feature.children.find(child => child.scenario && child.scenario.id === scenarioId);
//             if (scenario) {
//                 let tagIndex = scenario.scenario.tags.findIndex(t => t.name === tag);
//                 if (tagIndex > -1) {
//                     scenario.scenario.tags.splice(tagIndex, 1);
//                     let scenarioIndex = featureFile.feature.children.findIndex(child => child.scenario && child.scenario.id === scenarioId);
//                     featureFile.feature.children[scenarioIndex] = scenario;
//                     let featureIndex = getFeatureFilesCopy().findIndex(file => file.featureId === featureId);
//                     getFeatureFilesCopy()[featureIndex] = featureFile;
//                     result = true;
//                 }
//             }
//         }
//     }
//     return result;
// }

// function addTag(featureId, scenarioId, tag) {
//     let result = null;
//     // validate tag (start with @ and no spaces)
//     if (!tag.startsWith('@') || tag.includes(' ')) {
//         result = null;
//         return result;
//     }
//     let featureFilesCopy = getFeatureFilesCopy();
//     let featureFile = featureFilesCopy.find(file => file.featureId === featureId);
//     if (featureFile) {
//         // Add tag to feature tags
//         if (!featureFile.feature.tags) {
//             featureFile.feature.tags = [];
//         }
//         let featureTagExists = featureFile.feature.tags.some(existingTag => existingTag.name === tag);
//         if (!featureTagExists && scenarioId === null) {
//             featureFile.feature.tags.push({ name: tag });
//             let featureIndex = featureFilesCopy.findIndex(file => file.featureId === featureId);
//             featureFilesCopy[featureIndex] = featureFile;
//             updateFeatureFilesCopy(featureFilesCopy);
//             result = true;
//         }
//         // Add tag to scenario tags
//         let scenario = featureFile.feature.children.find(child => child.scenario && child.scenario.id === scenarioId);
//         if (scenario) {
//             let tagExists = scenario.scenario.tags.some(existingTag => existingTag.name === tag);
//             if (!tagExists && scenarioId !== null) {
//                 scenario.scenario.tags.push({ name: tag });
//                 let scenarioIndex = featureFile.feature.children.findIndex(child => child.scenario && child.scenario.id === scenarioId);
//                 featureFile.feature.children[scenarioIndex] = scenario;
//                 let featureIndex = featureFilesCopy.findIndex(file => file.featureId === featureId);
//                 featureFilesCopy[featureIndex] = featureFile;
//                 updateFeatureFilesCopy(featureFilesCopy);
//                 result = true;
//             }
//         }
//     }
//     return result;
// }

// // Following function needs to be tested
// function saveOnDisk() {
//     try{const directoryPath=config.directoryPath;getFeatureFilesCopy().forEach(featureFile=>{const gherkinText=gherkinDocumentToString(featureFile);fs.writeFileSync(featureFile.path,gherkinText);});return true;}catch(error){console.error(`Error saving file:${error}`);return null;}
// }

// function deleteAllOccurencyOfTag(tag) {
//     try{let tagFound=false;let featureFilesCopy=getFeatureFilesCopy();featureFilesCopy.forEach(featureFile=>{
//     // Remove tag from feature tags
//     if(featureFile.feature.tags){let tagIndex=featureFile.feature.tags.findIndex(t=>t.name===tag);if(tagIndex>-1){featureFile.feature.tags.splice(tagIndex,1);tagFound=true;}}
//     // Remove tag from scenario tags
//     featureFile.feature.children.forEach(child=>{if(child.scenario){let tagIndex=child.scenario.tags.findIndex(t=>t.name===tag);if(tagIndex>-1){child.scenario.tags.splice(tagIndex,1);tagFound=true;}}});});
//     // Update the original feature files array
//     updateFeatureFilesCopy(featureFilesCopy);return tagFound?true:null;}catch(error){console.error(`Error deleting tag:${error}`);return null;}
// }

// // Bug needs to be investigated, seems not to be invoked
// function updateAllOccurencyOfTag(tag,newTag){
// // validate tag (start with @ and no spaces)
// if(!newTag.startsWith('@')||newTag.includes(' ')){return null;}let tagExists=false;let newTagExists=false;let featureFilesCopy=getFeatureFilesCopy();featureFilesCopy.forEach(featureFile=>{
// // Update tag in feature tags
// if(featureFile.feature.tags){let tagIndex=featureFile.feature.tags.findIndex(t=>t.name===tag);if(tagIndex>-1){tagExists=true;let newTagIndex=featureFile.feature.tags.findIndex(t=>t.name===newTag);if(newTagIndex>-1){newTagExists=true;}else{featureFile.feature.tags[tagIndex].name=newTag;}}}
// // Update tag in scenario tags
// featureFile.feature.children.forEach(child=>{if(child.scenario){let tagIndex=child.scenario.tags.findIndex(t=>t.name===tag);if(tagIndex>-1){tagExists=true;let newTagIndex=child.scenario.tags.findIndex(t=>t.name===newTag);if(newTagIndex>-1){newTagExists=true;}else{child.scenario.tags[tagIndex].name=newTag;}}}});});
// // Update the original feature files array
// updateFeatureFilesCopy(featureFilesCopy);return tagExists&&!newTagExists?true:null;}

// module.exports={updateFeatureFilesCopy,getFeatureFilesCopy,getFiles,getScenarios,reset,parseGherkinContent,gherkinDocumentToString,updateFeatureFile,removeTag,addTag,saveOnDisk,deleteAllOccurencyOfTag,updateAllOccurencyOfTag
// }
// ;


/// from gherkinUtils.js

// function gherkinDocumentToString(gherkinDocument) {
//     //console.log('gherkinDocument:', JSON.stringify(gherkinDocument, null, 2));
//     // Check if the document contains a feature otherwise return an error
//     if (!gherkinDocument.feature.name) {
//         return 'Error: The document is empty';
//     }
//     let gherkinText = '';
//     // Add the feature tags, if any
//     if (gherkinDocument.feature.tags && gherkinDocument.feature.tags.length > 0) {
//         const tags = gherkinDocument.feature.tags.map(tag => tag.name).join(' ');
//         gherkinText += `${tags}\n`;
//     }
//     // Add the feature title
//     gherkinText += `Feature: ${gherkinDocument.feature.name}\n`;
//     // Add the feature description, if any
//     if (gherkinDocument.feature.description) {
//         gherkinText += `${gherkinDocument.feature.description}\n`;
//     }
//     // Add each scenario or background
//     gherkinDocument.feature.children.forEach((child, index, array) => {
//         gherkinText += '\n';
//         if (child.background) { // TODO: bug: title is not displayed
//             gherkinText += `\tBackground: ${child.background.name}\n`;
//             // Add each step of the background
//             child.background.steps.forEach(step => {
//                 gherkinText += `\t\t${step.keyword} ${step.text}\n`;
//             });
//             // Add a blank line after the background
//             gherkinText += '\n';
//         } else if (child.scenario) {
//             // Add the scenario tags, if any
//             if (child.scenario.tags && child.scenario.tags.length > 0) {
//                 const tags = child.scenario.tags.map(tag => tag.name).join(' ');
//                 gherkinText += `\t${tags}\n`;
//             }
//             // Check if the scenario is an outline
//             if (child.scenario.examples && child.scenario.examples.length > 0) {
//                 gherkinText += `\tScenario Outline: ${child.scenario.name}\n`;
//             } else {
//                 gherkinText += `\tScenario: ${child.scenario.name}\n`;
//             }
//             // Add the scenario description, if any
//             if (child.scenario.description) {
//                 gherkinText += `${child.scenario.description}\n`;
//             }
//             // Add each step of the scenario
//             child.scenario.steps.forEach((step, stepIndex) => {
//                 gherkinText += `\t\t${step.keyword}${step.text}`;
//                 // Check if the step has a datatable
//                 if (step.dataTable) {
//                     step.dataTable.rows.forEach(row => {
//                         gherkinText += '\n\t\t\t| ' + row.cells.map(cell => cell.value.replace(/\|/g, '\\\\|')).join(' | ') + ' |';
//                     });
//                 }
//                 if (stepIndex < child.scenario.steps.length) {
//                     gherkinText += '\n';
//                 }
//             });
//             // Add the Examples, if any
//             if (child.scenario.examples && child.scenario.examples.length > 0) {
//                 child.scenario.examples.forEach(example => {
//                     gherkinText += `\n\t\tExamples:\n`;
//                     // Add the table header
//                     const header = example.tableHeader.cells.map(cell => cell.value).join(' | ');
//                     gherkinText += `\t\t\t| ${header} |\n`;
//                     // Add the table rows
//                     example.tableBody.forEach((row, rowIndex) => {
//                         // fix bug: escape pipe character
//                         const rowText = row.cells.map(cell => cell.value.replace(/\|/g, '\\\\|')).join(' | ');
//                         gherkinText += `\t\t\t| ${rowText} |`;
//                         if (rowIndex < example.tableBody.length) {
//                             gherkinText += '\n';
//                         }
//                     });
//                 });
//             }
//             // // Add a blank line after each scenario, except the last one
//             // if (index < array.length - 1) {
//             //     gherkinText += '\n';
//             // }
//         }
//     });
//     return gherkinText;
// }

// module.exports = gherkinDocumentToString;