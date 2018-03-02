/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorderdatamodel;

import java.awt.Desktop.Action;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import utility.io.console.PositionedConsoleOutput;
import utility.io.getAnswer.GetMenuSelection;
import utility.io.parse.LongToReadableTime;
import utility.io.parse.TimeConverter;

/**
 *
 * @author Aaron Pierdon
 */
public class Task implements Serializable{
    
    // New Array list of start times and array list of length... maybe map keyvalue with start time in milli and a milli for length
    
    // Should be impossible for a task to have same start time with same length until we add concurrent tasks
    
    private String taskName;
    private Date creationDate;
    private long totalTime;
    
    // Keeps track of the Time Performed Task Started as milliseconds since epoch and
    // Length of time in milliseconds it was performed.
    private HashMap<Long, Long> sessions;
    
   
    //
    // Constructors
    //

    public Task(){
        this.taskName = "Unkown Task";
        this.creationDate = new Date();
        this.sessions = new HashMap<>();
        this.totalTime = 0;
    }
    public Task(String name){
        this.taskName = name;
        this.creationDate = new Date();
        this.sessions = new HashMap<>();
        this.totalTime = 0;
    }
    
   
    
    // Clears the member variables except the name
    public void clear(){
        this.creationDate = new Date();
        this.totalTime = 0;
        this.sessions = new HashMap<>();
    }
    
    public void setTask(Task task){
        this.taskName = task.taskName;
        this.totalTime = task.totalTime;
        this.creationDate = task.creationDate;
        this.sessions = task.sessions;
    }
    
    private void updaterProperties(){
        
    }
    
    // Main AddTime Method, accepts a long
    public void addSession(long time){
        // Increment total time by
        this.totalTime += time;
        
        // Record the date and time of now
        long date = System.currentTimeMillis();
        
        // Record the date and time of now along with the length of the task that was 
        // Added now
        this.sessions.put(date, time);
  
    }
    
    public void addTime(long time){
        this.totalTime += time;
        
        // Get the value for the key 0
        long key = 0;
        long currentUnorganizedTime = 0;
        
        if(this.sessions.get(key) != null && !this.sessions.isEmpty())
            currentUnorganizedTime = this.sessions.get(key);
        
        // Add the added time to the currentUnorganizedTime and put in the
        // key 0
        this.sessions.put(key, (time + currentUnorganizedTime));
        
    }
    
    // Set this.sessions to one that is sorted by key, the time of occurrence
    public  void sortByRecent(){
         Map.Entry newEntry;
         Map<Long, Long> map = new TreeMap<Long, Long>(this.sessions);
         Set set2 = map.entrySet();
         Iterator iterator2 = set2.iterator();
         this.sessions.clear();
         while(iterator2.hasNext()) {
              newEntry = (Map.Entry)iterator2.next();
              this.sessions.put((long) newEntry.getKey(), (long) newEntry.getValue());

         }

    }

    

    
    public String getName(){
        return this.taskName;
    }
    
    public void setName(String name){
        this.taskName = name;
    }
    
    public long getTotalTime(){
        return this.totalTime;
    }
    

    
    public void setTotalTime(long time){
        
        this.totalTime = time;
    }
    
    public long getLastRunDate(){
        Long latestSession = new Long(0);
        
        for(Long sessionDate : this.sessions.keySet()){
            if(latestSession == 0)
                latestSession = sessionDate;
            // SessionDate is later in time than latest, so update
            if(latestSession < sessionDate)
                latestSession = sessionDate;
        }
        
        return latestSession;
    }
    
   
    
    
}
