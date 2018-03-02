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
import javafx.scene.layout.VBox;
import timerecorderdatamodel.Task;

public class AddTaskController{
            
            private MainController controller;
            
            @FXML TextField txtTaskInput;
            
            public void startAddTask(MainController controller){
                this.controller = controller;
                
                // Set up GUI
                controller.clearRoot();
                controller.setRootCenter(getEntryView());
                
            }
            
            
            private VBox getEntryView(){
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
            
            

            
            @FXML
            protected void addTaskInput(ActionEvent event){

                // Create task and add it to the model
                Task task = new Task(txtTaskInput.getText());


                controller.addNewTask(task);
                controller.endAddNewTask();

            }
            
            @FXML
            protected void cancelAddTask(ActionEvent event){
                controller.endAddNewTask();
            }
        }
        
