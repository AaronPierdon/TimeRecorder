/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorderdatamodel;

import java.io.Serializable;
import java.util.ArrayList;




public class TaskRepository implements Serializable{
    
    protected ArrayList<Task> tasks;

    
    public TaskRepository(){
        this.tasks = new ArrayList<>();
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
