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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Aaron Pierdon
 */
public class EditTaskController {

    @FXML private Spinner sliderHours;

    
    private boolean editingTask;
    
    public EditTaskController(){
        editingTask = false;
    }
    
    public VBox getEditView(){
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditTask.fxml"));
        loader.setController(this);
        
        try{
      
            VBox editor = loader.load();
            
            return editor;
        }catch(IOException e){
        
        }
        
        return null;
        
    }
    
    public void startEditing(){
        editingTask = true;
    }

    public void stopediting(){
        editingTask = false;
    }
    
    public boolean getState(){
        return this.editingTask;
    }
    
    
    @FXML
    protected void confirmEdit(ActionEvent event){
        editingTask = false;
    }
    
    @FXML
    protected void cancelEdit(ActionEvent event){
        editingTask = false;
    }
    

    protected void testDrag(ActionEvent event){
        
        System.out.println("test");
    }
    
 
        
    
}
