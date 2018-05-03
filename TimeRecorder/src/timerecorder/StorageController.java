/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import utility.fileUtility.Serializer;
import timerecorderdatamodel.TaskRepository;

/**
 *
 * @author Aaron Pierdon
 */
public class StorageController {
    
    File lastSavedFile;
    
    
    public TaskRepository attemptToLoadData(){
        
        
        
        // This just makes sure the file exists
        File file = timerecorder.TimeRecorder.openFile();
        if(file != null && file.exists()){
            
            // File exists, try to deserialize into a TaskRepository object
            TaskRepository temp = (TaskRepository) Serializer.deserialize(file);
            if(temp != null){
                 this.lastSavedFile = file;
                return temp;
            } else
                return null;
               
        }else
            // File not found
            
            
            return null;
    }
    
    public TaskRepository attemptToLoadDefaultFile(){
        File iniFile = new File(TimeRecorder.DEFAULTDIR + "\\TimeRecorder.ini");
        if(iniFile.exists()){
            // File exists, try to deserialize into a TaskRepository object
            
           
            File defaultFile = getDefaultFile(iniFile);
            if(defaultFile.exists()){
                TaskRepository temp = (TaskRepository) Serializer.deserialize(defaultFile);
                if(temp != null){
                     this.lastSavedFile = defaultFile;
                    return temp;
                } else{
                    return null;
                }
                
            } else{
                return null;
            }
               
        }else{
            // File not found
                try{
                    iniFile.createNewFile();
                }catch(IOException e){}
                
            return null;
        
        }
    }
    
    private File getDefaultFile(File iniFile){
        try{
            FileReader fr = new FileReader(iniFile);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while((line = br.readLine()) != null){
                lines.add(line);
            }
            
            String absolutePath = "";
            for(String iniLine : lines){
                if(iniLine.contains("#LastSaved#"))
                    absolutePath = iniLine.substring(11);
            }
            
            File file = new File(absolutePath);
            return file;
            
        }catch(IOException e){
            return null;
        }
    }
    
    public void saveData(TaskRepository tasks,
            boolean overwrite){
        
       File file = timerecorder.TimeRecorder.saveFile();
       Serializer.serialize(file, tasks, overwrite);
       
       updateIni(file);
       
       if(file != null && file.exists())
           this.lastSavedFile = file;
       else
           this.lastSavedFile = null;
    }
    
    private void updateIni(File file){
        String path =  file.getAbsolutePath();
        File iniFile = new File(TimeRecorder.DEFAULTDIR + "\\TimeRecorder.ini");
            if(iniFile.exists()){
            // File exists, try to deserialize into a TaskRepository object
            
                try{
                    FileReader fr = new FileReader(iniFile);
                    BufferedReader br = new BufferedReader(fr);
                    ArrayList<String> lines = new ArrayList<>();
                    
                    String line;
                    while((line = br.readLine()) != null){
                        lines.add(line);
                    }
                    
                    boolean found = false;
                    for(String aLine : lines){
                        if(aLine.contains("#LastSaved#")){
                            aLine = "#LastSaved#" + path;  
                            found = true;
                        }
                    }
                    
                    if(!found){
                        lines.add("#LastSaved#" + path);
                    }
                    File newIniFile = new File(iniFile.getAbsolutePath());
                    iniFile.delete();
                    newIniFile.createNewFile();
                    FileWriter fw = new FileWriter(newIniFile);
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    for(String aLine : lines){
                        bw.write(aLine);
                    }
                    
                    bw.flush();
                    bw.close();
                }catch(IOException e){}
                
            } else {
                try{
                    iniFile.createNewFile();
                }catch(IOException e){}
            }
    }
}
