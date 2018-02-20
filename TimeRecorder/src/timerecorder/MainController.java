/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;


import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import timerecorderdatamodel.Task;
import utility.io.parse.LongToReadableTime;


public class MainController{
    
        ColorChanger colorChanger;
    
        // Main GUI elements that need to be altered or used
        @FXML private Button  btnAddTask, btnEditTime,
                btnDelete, btnStart, btnClose;
        
        @FXML private MenuButton btnFileMenu;
        
        @FXML private BorderPane root;

        @FXML private VBox vBoxTaskList;
        @FXML private VBox vBoxRight;
        @FXML private HBox hBoxTop;
        
        
        @FXML private ListView taskList;
        
        // Timer Readout
        @FXML private VBox rootReadout;
        @FXML private Label lblTimerReadout;

        private int indexOfRunningTask;
        
        // The Task Timer Object
        protected TimeCounter timer;

        protected int rColor;
        protected int gColor;
        protected int bColor;

        
        // Controllers
        
        // Loads and Saves the data model
        private final StorageController storageController;
        
        // Gets and sets the data model
        private final DataController dataController;
        
        // Controls the prompting and inputing of a user-specified task
        private AddTaskController addTaskController;
        
  
         
         
        public MainController(DataController dataController){
            storageController = new StorageController();
            this.dataController = dataController;
            this.timer = new TimeCounter();
            this.colorChanger = new ColorChanger();
            this.addTaskController = new AddTaskController();
        }

        
        // Action Handlers //
        //                 //
    
        // File Management //

        // Open File
        @FXML
        protected void openFile(ActionEvent event){
                
                dataController.setTaskRepo(storageController.attemptToLoadData());
                
                updateTaskList();
             
                
        }

        // Save File
        @FXML
        protected void saveFile(ActionEvent event){
                
                
                System.out.println(this.dataController.getTaskRepo().getTasks().
                        get(0));
                
                // Start save routine
                storageController.saveData(this.dataController.getTaskRepo(), true);
                
                
                
                
                
        }
        
        
        
        
        // Task Management //
    
        
        // Add Task //
        //  //  //  //
        @FXML 
        protected void addTask(ActionEvent event){
            // Alter GUI for input 
            // Control is now passed to user for task name input
            addTaskController.startTask(this);
  
        }
        
        // Used when control needs to be passed from AddTaskController object
        // To this
        protected void endAddNewTask(){
            
            addTaskController.cancel();
            addTaskController = new AddTaskController();
            
            // Revert to task list view
            restoreRoot();
        }
        
        protected void addNewTask(Task task){
                
            dataController.addNewTask(task);
            
        }
        
        

        // Edit Task //
        //  //  //  //
        
        // Edit a Selected Task
        @FXML
        protected void editTask(ActionEvent event){
            System.out.println(dataController.getTaskRepo().getTasks().get(0).getName());
        }

        
        
        
        // Start Selected Task
        @FXML
        protected void startTask(ActionEvent event){
            indexOfRunningTask = taskList.getSelectionModel().getSelectedIndex(); 
            
            timer.startTimer(this);
            
            
            
        }
        
        // Delete Selected Task
        @FXML
        protected void deleteTask(ActionEvent event){
            if(taskList.getSelectionModel().getSelectedIndex() >= 0){
            
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DeleteConfirmPane.fxml"));
                
                loader.setController(this);
                
                try{
                    StackPane confirmPane = loader.load();
                    vBoxTaskList.getChildren().add(confirmPane);
                    
                }catch(IOException e){
                
                    System.out.println(e.toString());

                    // Revert to task list view
                    revertFromTimerGUI();
                }
                
            
            }
        }
    
                
        //  //  //  //
        // Confirm Delete FXML Handlers //
        //  //  //  //
        
        @FXML
        protected void confirmedDelete(ActionEvent event){
            dataController.removeTask(taskList.getSelectionModel().getSelectedIndex());
            
            // Revert to task list view
            revertFromTimerGUI();
        }
        
        @FXML
        protected void cancelDelete(ActionEvent event){
            // Revert to task list view
            vBoxTaskList.getChildren().clear();
            vBoxTaskList.setAlignment(Pos.TOP_LEFT);
            vBoxTaskList.getChildren().add(taskList);
            updateTaskList();
        }
        
        
        
        
        // Program Management
    
        // Close Program
        @FXML
        protected void closeProgram(ActionEvent event){
            System.exit(0);
        }

        
        

        

        

        
        
        //  //  //
        // TimerReadout //
        //  //  //  //
        
        public void startTimerGUI(){
            hideControls();
            
            root.setStyle("-fx-background-color: #000000");
            
            root.getChildren().clear();
            
            
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TimerReadout.fxml"));
            
            loader.setController(this);
            
            try{
                rootReadout = loader.load();
                
                VBox vBox = rootReadout;
                
                
                
                lblTimerReadout.setText(LongToReadableTime.getReadableTime(timer.getTotalTime()));
                
                root.setCenter(vBox);
                
                colorChanger.startTimer(this);
                
            }catch(IOException e){
                
            }
        }
        
        public void updateTimerReadout(){
            
          lblTimerReadout.setText(LongToReadableTime.getReadableTime(timer.getTotalTime()));
            
        }
        
        public void endTimerReadout(){

            // Stop and reset the color changer
            colorChanger.cancelTimer();
            colorChanger = new ColorChanger();
            
            // Stop timer
            timer.cancelTimer();
            
            restoreRootPanes();
            
            

            

            dataController.getTaskRepo().getTasks().get(indexOfRunningTask).
                    addTime(timer.getTotalTime());
            
            dataController.getTaskRepo().getTasks().get(indexOfRunningTask).setLastRun(
                timer.startDate);
            
            indexOfRunningTask = 0;
            
            // Reset the timer
            timer = new TimeCounter();
            
            revertFromTimerGUI();
                        
            
        }
        
        public void setReadoutBackground(String colorSequence){
            this.rootReadout.setStyle(colorSequence);
        }
        
        @FXML
        protected void stopTimer(ActionEvent event){
            endTimerReadout();
            
            
        }

        public void revertFromTimerGUI(){
            rColor = 0;
            gColor = 0;
            bColor = 0;

            restoreRoot();
            

        }
        
        private void showControls(){
            btnFileMenu.setVisible(true);
            btnAddTask.setVisible(true);
            btnEditTime.setVisible(true);
            btnStart.setVisible(true);
            btnDelete.setVisible(true);
            btnClose.setVisible(true);


        }
        
        private void hideControls(){
            
                        
            btnFileMenu.setVisible(false);
            btnAddTask.setVisible(false);
            btnDelete.setVisible(false);
            btnEditTime.setVisible(false);
            btnStart.setVisible(false);
            btnClose.setVisible(false);
  

        }
        
        

        
        private void updateTaskList(){
            if(dataController.getTaskRepo() != null)
            {
                ArrayList<String> list = new ArrayList<>();

                for(Task task : dataController.getTaskRepo().getTasks())
                    list.add(task.getName() + "\r\n" +
                            "Last Run: " + task.getLastRun() + "\r\n" +
                            "Total Time: " + LongToReadableTime.getReadableTime(task.getTime()));

                
                ObservableList<String> items = FXCollections.observableArrayList();

                items.setAll(list);

                taskList.setItems(items);

                taskList.setPrefWidth(100);
                taskList.setPrefHeight(70);
                

                this.vBoxTaskList.getChildren().clear();
                this.vBoxTaskList.getChildren().add(taskList);
            }

            
        }
        
        
        // Cancels the current timer task and the color changer task
        public void cancelTasks(){
            this.colorChanger.cancelTimer();
            this.timer.cancelTimer();         
            
            
            timer = new TimeCounter();
            colorChanger = new ColorChanger();
        }
        
        
        
        // Root Pane Controlls //
        //  //  //  //  //  //  //
        
        // Clears the root border pane
        protected void clearRoot(){
            this.root.getChildren().clear();
            
            
        }
        
        // Restores the root border pane to default
        protected void restoreRoot(){
            root.setStyle("-fx-background-color: #ffffff");
            clearRoot();
            restoreRootPanes();
            updateTaskList();
            showControls();
        }
        
        protected void restoreRootPanes(){
            root.setTop(hBoxTop);
            root.setCenter(vBoxTaskList);
            root.setRight(vBoxRight);
        }
        
        protected void setRootCenter(VBox rootCenter){
            this.root.setCenter(rootCenter);
        }
        
        

        
        
        
        
}
