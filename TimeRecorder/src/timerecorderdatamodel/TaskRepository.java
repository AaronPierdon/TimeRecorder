/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorderdatamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;




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
    
    public void sortByRecent(){

        
       for(int i = 0; i < this.tasks.size(); i++){
           for(int j = 0; j < this.tasks.size() - i - 1; j++){
               if(this.tasks.get(j).getLastRun().getTime() < this.tasks.get(j + 1).getLastRun().getTime()){
                   Task temp = new Task();
                   temp.setTask(this.tasks.get(j));
                   this.tasks.get(j).setTask(this.tasks.get(j + 1));
                   this.tasks.get(j+1).setTask(temp);
               }
           }
       }

        
    }

}
