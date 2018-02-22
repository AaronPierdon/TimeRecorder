/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorderdatamodel;

import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;
import utility.io.console.PositionedConsoleOutput;
import utility.io.getAnswer.GetMenuSelection;
import utility.io.parse.TimeConverter;

/**
 *
 * @author Aaron Pierdon
 */
public class Task implements Serializable{
    
    
    protected String taskName;
    protected Date creationDate;
    protected Date lastRunDate;
    protected long totalTime;
    
    //
    // Constructors
    //

    public Task(){
        this.taskName = "Unkown Task";
        this.creationDate = new Date();
        this.lastRunDate = null;
        this.totalTime = 0;
    }
    public Task(String name){
        this.taskName = name;
        this.creationDate = new Date();
        this.lastRunDate = null;
        this.totalTime = 0;
    }
    
   
    
    // Clears the member variables except the name
    public void clear(){
        this.creationDate = new Date();
        this.lastRunDate = null;
        this.totalTime = 0;
    }
    
    public void updateProperties(Task task){
        this.lastRunDate = task.getLastRun();
        this.taskName = task.getName();
        this.totalTime = task.getTotalTime();
        this.creationDate = task.creationDate;
    }
    
    // Main AddTime Method, accepts a long
    public void addTime(long time){
        this.totalTime += time;
    }
    
    // Can be called via console.. to be depcreciated
    public void addTimeViaConsole(){
        
        System.out.println("What time of increment would you like to add?");
        int choice = addTimeTypeQuery();
        switch(choice){
            case 1: this.totalTime += (this.addTimeQuery() * 1000);
                break;
            case 2: this.totalTime += (new TimeConverter().minutesToSeconds(
                        this.addTimeQuery()) * 1000);
                break;
            case 3: this.totalTime += (new TimeConverter().hoursToSeconds(
                        this.addTimeQuery()) * 1000);
                break;
            default: System.out.println("Shouldn't be here...");
            
        }
    }
    
    // Called from addTimeViaConsole()
    private int addTimeTypeQuery(){
        PositionedConsoleOutput conOut = new PositionedConsoleOutput();
        conOut.clearDisplay();
        System.out.println("1| Seconds");
        System.out.println("2| Minutes");
        System.out.println("3| Hours");
        System.out.println("4| Cancel");
        
        return GetMenuSelection.getMenuSelection(1, 4);
        
    }
    // Called from addTimeViaConsole()
    private int addTimeQuery(){
        PositionedConsoleOutput conOut = new PositionedConsoleOutput();
        conOut.clearDisplay();
        System.out.println("How much time to add?");
        
        return new Scanner(System.in).nextInt();
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
    
    public Date getLastRun(){
        return this.lastRunDate;
    }
    
    public void setLastRun(Date date){
        this.lastRunDate = date;
    }
    
    public void setTotalTime(long time){
        this.totalTime = time;
    }
    
    
    
   
    
    
}
