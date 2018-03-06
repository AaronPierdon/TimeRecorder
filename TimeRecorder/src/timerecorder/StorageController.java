/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
    
    public void saveData(TaskRepository tasks,
            boolean overwrite){
        
       File file = timerecorder.TimeRecorder.saveFile();
       Serializer.serialize(file, tasks, overwrite);
       
       if(file != null && file.exists())
           this.lastSavedFile = file;
       else
           this.lastSavedFile = null;
    }
}
