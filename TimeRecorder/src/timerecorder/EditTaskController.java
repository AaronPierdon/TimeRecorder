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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import timerecorderdatamodel.Task;

/**
 * FXML Controller class
 *
 * @author Aaron Pierdon
 */
public class EditTaskController {


    @FXML private TextField txtNameInput, txtHours, txtMinutes, txtSeconds;
    @FXML private Label lblHours, lblMinutes, lblSeconds, lblHrError, lblMinError, lblSecError;
    @FXML private GridPane timeControlsGrid;
    
    @FXML CheckBox cboxAddSession, cboxAddTime;
    
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
            showTimeAdder(false);
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
    protected void keyPressed(KeyEvent e){
        if(e.getCode() == KeyCode.ENTER)
            confirmEdit(null);
    }
    
    @FXML
    protected void confirmEdit(ActionEvent event){
        // Get Integer Values for the string input
        int sec = 0;
        int min = 0; 
        int hr = 0;
        
        boolean inputError = false;
           
        // Make sure edit doesn't progress without valid integer input

            // Make sure input values are valid integers
            try{
                sec = Integer.valueOf(txtSeconds.getText());
            }catch(NumberFormatException numberException){
                lblSecError.setText("(Please input a valid integer between 0-10,000)");
                lblSecError.setStyle("-fx-text-fill: derive(#d11313, 30%);");

                inputError = true;
            }
                

            try{
                min = Integer.valueOf(txtMinutes.getText());

            }catch(NumberFormatException numberException){
                lblMinError.setText("(Please input a valid integer between 0-10,000)");
                lblMinError.setStyle("-fx-text-fill: derive(#d11313, 30%);");
                inputError = true;
            }

            try{
                hr = Integer.valueOf(txtHours.getText());

            }catch(NumberFormatException numberException){
                lblHrError.setText("(Please input a valid integer between 0-10,000)");
                lblHrError.setStyle("-fx-text-fill: derive(#d11313, 30%);");
                inputError = true;
            }

            
            // Make sure input values are within 0 - 10,000
            if((inputError == false) && !(withinRange(sec))){
                inputError = true;
                lblSecError.setText("(Please input a valid integer between 0-10,000)");
                lblSecError.setStyle("-fx-text-fill: derive(#d11313, 30%);");
            }
            
            if((inputError == false) && !(withinRange(min))){
                inputError = true;
                lblMinError.setText("(Please input a valid integer between 0-10,000)");
                lblMinError.setStyle("-fx-text-fill: derive(#d11313, 30%);");
            }
            
            if((inputError == false) && !(withinRange(min))){
                inputError = true;
                lblHrError.setText("(Please input a valid integer between 0-10,000)");
                lblHrError.setStyle("-fx-text-fill: derive(#d11313, 30%);");
            }
                        
        
        if(inputError == false){
        
            // Make sure time was added before using these values
            // and adding time to the task record
            if(cboxAddTime.isSelected()){

                try{
                    long taskTimeInMilli = (long) 1000 * ((Integer.valueOf(txtSeconds.getText())) + 
                        (Integer.valueOf(txtMinutes.getText()) * 60) +
                        (Integer.valueOf(txtHours.getText()) * 60 * 60));

                    if(cboxAddSession.isSelected()){
                        Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

                        editedTask.addSession(taskTimeInMilli, date);
                    }else{
                        editedTask.addTime(taskTimeInMilli);

                    }


                }catch(NumberFormatException numberException){
                    // Reset the fields because  input was could not be parsed into  type int
                    // To later be casted to long
                    loadTaskValues();



                }

            }

                editedTask.setName(txtNameInput.getText());

                // Ends editing
                confirmedStatus = true;
                editingTask = false;

        }
    }
    
    //Checks whether an int is between 0-10000
    public boolean withinRange(int testValue){
        if(testValue < 0 || testValue > 10000)
            return false;
        else
            return true;
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
    
    @FXML
    protected void toggleTimeAdder(MouseEvent event){
        showTimeAdder(cboxAddTime.isSelected());
    }
    
    private void showTimeAdder(boolean show){
        this.lblHours.setVisible(show);
        this.lblMinutes.setVisible(show);
        this.lblSeconds.setVisible(show);
        this.txtHours.setVisible(show);
        this.txtMinutes.setVisible(show);
        this.txtSeconds.setVisible(show);
        cboxAddSession.setVisible(show);
    }


    
 
        
    
}
