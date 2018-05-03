/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import timerecorderdatamodel.Task;

public class AddTaskController{
            
            @FXML TextField txtTaskInput;
            @FXML VBox rootAddView;
            
            private Task newTask;
            private boolean isAdding;

    public void setIsAdding(boolean isAdding) {
        this.isAdding = isAdding;
    }

    public void setConfirmedInput(boolean confirmedInput) {
        this.confirmedInput = confirmedInput;
    }

    public boolean isIsAdding() {
        return isAdding;
    }

    public boolean isConfirmedInput() {
        return confirmedInput;
    }
            
            // Did user confirm or cancel the input 
            private boolean confirmedInput;
            
            public AddTaskController(){
                confirmedInput = false;
                isAdding = false;
            }
            public void startAddTask(){
                isAdding = true;

                
            }
            
            
            protected VBox getEntryView(){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddTask.fxml"));
                loader.setController(this);
                
                try{

                    // Create and set up the input vbox
                    VBox taskInputBox = loader.load();

                    VBox parentBox = new VBox();
                    parentBox.setAlignment(Pos.CENTER);
                    
                    parentBox.getChildren().add(taskInputBox);
                    
                    return parentBox;

                }catch(IOException e){
                    System.out.println(e.toString());

                    return null;
                }
            }
            
            protected Task getNewTask(){
                return this.newTask;
            }

            @FXML
            protected void enterTaskName(KeyEvent event){
                if(event.getCode() == KeyCode.ENTER){
                    addTaskInput(null);
                }
            }
            
            @FXML
            protected void addTaskInput(ActionEvent event){
                    
                // Create task and add it to the model
                this.newTask = new Task(txtTaskInput.getText());
                this.confirmedInput = true;
                this.isAdding = false;
                

            }
            
            @FXML
            protected void cancelAddTask(ActionEvent event){
                this.confirmedInput = false;
                this.isAdding = false;
            }
        }
        
