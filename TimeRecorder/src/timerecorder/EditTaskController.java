/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import timerecorderdatamodel.Task;

/**
 * FXML Controller class
 *
 * @author Aaron Pierdon
 */
public class EditTaskController {


    @FXML private TextField txtNameInput, txtHours, txtMinutes, txtSeconds;
    @FXML CheckBox cboxAddSession;
    @FXML DatePicker datePicker;

    
    private Task editedTask;

    // Lets caller know when this controller is done managing the view
    private boolean editingTask;
    
    // Let's caller know that the input was confirmed.
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
            datePicker.setVisible(false);
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
        txtNameInput.setText(editedTask.getName());
        
        txtHours.setText(String.valueOf(0));
        txtMinutes.setText(String.valueOf(0));
        txtSeconds.setText(String.valueOf(0));
        

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
            
            if(cboxAddSession.isSelected()){
                Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                
                editedTask.addSession(taskTimeInMilli, date);
            }else
                editedTask.addTime(taskTimeInMilli);
            
            editedTask.setName(txtNameInput.getText());

            
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
    
    // Selects the text field input for the given source of event if any apply
    @FXML
    protected void textClicked(MouseEvent event){
        if(event.getSource().toString().contains("Hours")){
            txtHours.selectAll();
        } else if(event.getSource().toString().contains("Minutes")){
            txtMinutes.selectAll();
        } else if(event.getSource().toString().contains("Seconds")){
            txtSeconds.selectAll();
        } else if(event.getSource().toString().contains("Name")){
            txtNameInput.selectAll();
        }
        
    }
    
    @FXML
    protected void toggleDatePicker(MouseEvent event){
        if(cboxAddSession.isSelected())
            datePicker.setVisible(true);
        if(!cboxAddSession.isSelected())
            datePicker.setVisible(false);
    }


    
 
        
    
}
