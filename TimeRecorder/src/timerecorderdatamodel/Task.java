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
    private Date lastRun;

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
        this.lastRun = new Date();
        this.sessions = new HashMap<>();
        this.totalTime = 0;
    }
    public Task(String name){
        this.taskName = name;
        this.creationDate = new Date();
        this.lastRun = new Date();
        this.sessions = new HashMap<>();
        this.totalTime = 0;
    }
    
   
    
    // Clears the member variables except the name
    public void clear(){
        this.creationDate = new Date();
        this.lastRun = new Date();
        this.totalTime = 0;
        this.sessions = new HashMap<>();
    }
    
    public void setTask(Task task){
        this.taskName = task.taskName;
        this.totalTime = task.totalTime;
        this.creationDate = task.creationDate;
        this.lastRun = task.lastRun;
        this.sessions = task.sessions;
    }
    

    
    // Main AddTime Method, accepts a long and sets the session date to now
    public void addSession(long time){
        
        // No negatives
        
        if(time >= 0){
            // Increment total time by
            this.totalTime += time;

            // Record the date and time of now
            long date = System.currentTimeMillis();

            // Record the date and time of now along with the length of the task that was 
            // Added now
            this.sessions.put(date, time);
            this.lastRun = new Date(date);
        }

  
    }
    
    // Main AddTime Method, accepts a long and sets the session date to the input Date
    public void addSession(long time, Date sessionDate){
        // No negatives
        
        if(time >= 0)
        {
            // Increment total time by
            this.totalTime += time;

            

            // Record the date and time of now along with the length of the task that was 
            // Added now
            this.sessions.put(sessionDate.getTime(), time);
        
            
            
        }
    }
    
    public HashMap<Long, Long> getSessions(){
        return this.sessions;
    }
    
    
    public void addTime(long time){
        // No negatives
        if(time >= 0)
            this.totalTime += time;
        
 
        
    }
    
    // Set this.sessions to one that is sorted by key, the time of occurrence
    public void sortSessionsByRecent(){
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

    

    public int getSessionCount(){
        return this.sessions.size();
    }
    
    public String getName(){
        return this.taskName;
    }
    
    public void setName(String name){
        this.taskName = name;
    }
    
    
    public void setLastRun(Date lastRun) {
        this.lastRun = lastRun;
    }

    public Date getLastRun() {
        return lastRun;
    }
    
    public long getTotalTime(){
        return this.totalTime;
    }
    

    
    public void setTotalTime(long time){
        
        this.totalTime = time;
    }

    
    
}
