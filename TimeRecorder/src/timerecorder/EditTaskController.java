/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import timerecorderdatamodel.Task;
import utility.io.parse.LongToReadableTime;
import utility.io.parse.TimeConverter;

/**
 * FXML Controller class
 *
 * @author Aaron Pierdon
 */
public class EditTaskController {


    @FXML private TextField txtNameInput, txtHours, txtMinutes, txtSeconds;
    

    
    private Task editedTask;

    
    private boolean editingTask;
    private boolean confirmedStatus;
    
    public EditTaskController(){
        editingTask = false;
        confirmedStatus = false;
        editedTask = new Task();

    }

    
    public VBox getEditView(){
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditTask.fxml"));
        loader.setController(this);
        
        try{
      
            VBox editor = loader.load();
            
            // Now that the fxml controls are loaded, set the factories to the 
            // Spinners
            
            
            return editor;
        }catch(IOException e){
        
        }
        
        return null;
        
    }
    
    protected void startEditing(Task selectedTask){
        // Set the editedtask so it persists
        this.editedTask = selectedTask;
        loadTaskValues();
        
    }
    
    protected void setEditing(boolean editing){
        
        
        this.editingTask = editing;
    }
    

    public void loadTaskValues(){
        
        txtSeconds.setText(Integer.toString((int) (editedTask.getTotalTime() / 1000) % 60));
        txtMinutes.setText(Integer.toString((int) (editedTask.getTotalTime() / (1000 * 60)) % 60));
        txtHours.setText(Integer.toString((int) (editedTask.getTotalTime() / (1000 * 60 * 60))));
        txtNameInput.setText(editedTask.getName());
        
        
    }
    
    public void stopediting(){
        editingTask = false;
    }
    
    public boolean getState(){
        return this.editingTask;
    }
    
    public Task getEditedTask(){
        return this.editedTask;
    }
    
    protected boolean getConfirmedStatus(){
        return confirmedStatus;
    }
    
    
    @FXML
    protected void confirmEdit(ActionEvent event){
        
        
        try{
            long taskTimeInMilli = (long) 1000 * ((Integer.valueOf(txtSeconds.getText())) + 
                (Integer.valueOf(txtMinutes.getText()) * 60) +
                (Integer.valueOf(txtHours.getText()) * 60 * 60));
            System.out.println(taskTimeInMilli);
            editedTask.setTotalTime(taskTimeInMilli);

            // Ends editing
            confirmedStatus = true;
            editingTask = false;
            
        }catch(NumberFormatException numberException){
            // Reset the fields because  input was could not be parsed into  type int
            // To later be casted to long
            loadTaskValues();
            
            
            
        }

        
    }
    
    @FXML
    protected void cancelEdit(ActionEvent event){
        confirmedStatus = false;
        editingTask = false;
    }
    
    @FXML
    protected void test(ActionEvent event){
        
        System.out.println("test");
    }

    
 
        
    
}
