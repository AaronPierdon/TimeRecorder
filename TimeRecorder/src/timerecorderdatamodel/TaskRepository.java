/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorderdatamodel;

import java.io.Serializable;
import java.util.ArrayList;
import utility.io.getAnswer.GetMenuSelection;
import utility.io.parse.LongToReadableTime;



public class TaskRepository implements Serializable{
    
    protected ArrayList<Task> tasks;

    
    public TaskRepository(){
        this.tasks = new ArrayList<>();
    }
    
    public void printTasks(){
        int counter = 1;
        for(Task task : tasks){
            
            System.out.println("*****************Task*****************");
            System.out.println("Task #: " + counter++);
            System.out.println("Name: " + task.taskName);
            System.out.println("Total Time: " + LongToReadableTime.
                    getReadableTime(task.totalTime));
            System.out.println("Creation: " + task.creationDate);
            System.out.println("Last Ran: " + task.lastRunDate);
            System.out.println("**************************************");
            
        }
    }
    
    // To be removed.. used via console
    public void addTimeViaConsole(){
        System.out.println("For which task?");
        this.printTasks();
        this.tasks.get(GetMenuSelection.getMenuSelection(0, this.tasks.size())-1)
                .addTimeViaConsole();
                
    }
    
    public ArrayList<Task> getTasks(){
        return tasks;
    }
    
    public void addTask(Task task){
        this.tasks.add(task);
    }
    
    public void removeTask(int index){
        tasks.remove(index);
    }
    
    public void setTasks(ArrayList<Task> tasks){
        this.tasks = tasks;
    }

}
