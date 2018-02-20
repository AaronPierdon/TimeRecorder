/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import timerecorderdatamodel.Task;
import timerecorderdatamodel.TaskRepository;

/**
 *
 * @author Aaron
 */
public class DataController {
    
    // The Data model
    protected TaskRepository taskRepo;
    
    
    public DataController(TaskRepository tasks){
        this.taskRepo = tasks;
    }

    
    
    // Getters and Setters
    public void setTaskRepo(TaskRepository tasks) {
        this.taskRepo = tasks;
    }

    public TaskRepository getTaskRepo() {
        return taskRepo;
    }
    
    
    // Attempts to add a new task
    public void addNewTask(Task task){
        this.taskRepo.addTask(task);
    }
    
    // Attempts to remove a task
    public void removeTask(int index){
        this.taskRepo.removeTask(index);
    }
}
