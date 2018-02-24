/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import timerecorderdatamodel.Task;
import utility.io.parse.LongToReadableTime;


public class MainController extends TimerTask{
    
        long miliime;
    
        // Main GUI elements that need to be altered or used
        @FXML private Button  btnAddTask, btnEditTime,
                btnDelete, btnStart, btnClose;
        
        @FXML private MenuButton btnFileMenu;
        
        @FXML private BorderPane root;

        @FXML private VBox vBoxTaskList;
        @FXML private VBox vBoxRight;
        @FXML private HBox hBoxTop;
        @FXML private ListView taskList;
        
        // Timer Readout box the timer label goes in
        @FXML private VBox rootReadout;

        // The selected task from the vBoxTaskList's ListView
        // That is running 
        private int indexOfRunningTask;
        
        private boolean editingTask;



        
        // Controllers
        
        // Loads and Saves the data model
        private final StorageController storageController;
        
        // Gets and sets the data model
        private final DataController dataController;
        
        // Controls the prompting and inputing of a user-specified task
        private AddTaskController addTaskController;
        
        // Controls the view and communicates with the datamodel 
        // When a timer is running
        private TimerReadoutController readoutController;
        
        // 
        private EditTaskController editTaskController;
         
         
        public MainController(DataController dataController){
            storageController = new StorageController();
            this.dataController = dataController;
            this.addTaskController = new AddTaskController();
            this.readoutController = new TimerReadoutController();
            this.editTaskController = new EditTaskController();
            
            // Set states
            editingTask = false;
            
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(this, 0, 500);
            
            
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
                
                // Make sure there is data to save
                if(!this.dataController.getTaskRepo().getTasks().isEmpty()){
                    // Start save routine
                    storageController.saveData(this.dataController.getTaskRepo(), true);
                }

                
                
                
                
                
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
            
            if(taskList.getSelectionModel().getSelectedIndex() >= 0){
                
                
                editTaskController = new EditTaskController();
                clearRoot();
                buildEditingView();
                
                // Get selected task and pass it to the editor controller
                Task selectedTask = this.dataController.taskRepo.getTasks().
                        get(this.taskList.getSelectionModel().getSelectedIndex());
                
                editTaskController.startEditing(selectedTask);
            }

        }
        
        private void buildEditingView(){
                root.setCenter(editTaskController.getEditView());
                editTaskController.setEditing(true);
                editingTask = true;
        }

        
        
        
        // Start Selected Task
        @FXML
        protected void startTask(ActionEvent event){
            
            // Make sure a task is selected
            if(taskList.getSelectionModel().getSelectedIndex() >= 0){
                indexOfRunningTask = taskList.getSelectionModel().getSelectedIndex();
                // Pass control

                readoutController = new TimerReadoutController();
                this.readoutController.startTimerTask(root, rootReadout, 
                        indexOfRunningTask, dataController, this);
            }
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
                    restoreRoot();
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
            updateTaskList();
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


        
        

        
        
        // Cancels the current timer readout task(s)
        public void cancelTasks(){
            this.readoutController.cancelTimer();
            this.readoutController = new TimerReadoutController();
            this.cancel();
        }
        
        
        
        // Root Pane Controlls //
        //  //  //  //  //  //  //
        
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
        
        
        // Updates the main fxml view task list (the listview)
        private void updateTaskList(){
            if(dataController.getTaskRepo() != null)
            {
                ArrayList<String> list = new ArrayList<>();

                for(Task task : dataController.getTaskRepo().getTasks())
                    list.add(task.getName() + "\r\n" +
                            "Last Run: " + task.getLastRun() + "\r\n" +
                            "Total Time: " + LongToReadableTime.getReadableTime(task.getTotalTime()));

                
                ObservableList<String> items = FXCollections.observableArrayList();

                items.setAll(list);

                taskList.setItems(items);

                



                
            }

            
        }
        
        // Restores the root border pane to default
        protected void restoreRoot(){
            clearRoot();
            restoreRootPanes();
            updateTaskList(); 
            showControls();
        }
        
        // Clears the root border pane
        protected void clearRoot(){
            this.root.getChildren().clear();
 
        }
    
        protected void restoreRootPanes(){
            root.setTop(hBoxTop);
            root.setCenter(vBoxTaskList);
            root.setRight(vBoxRight);
        }
        
        protected void setRootCenter(VBox rootCenter){
            this.root.setCenter(rootCenter);
        }

    @Override
    public void run() {
        
        Platform.runLater(() -> {
            
            //When the controlcontroller is active and such hasn't been reflected by this.editingTask
            if(!this.editTaskController.getState() && this.editingTask == true) {
                editingTask = false;
                
                
                // Get the edited task and update the data model if the task controller
                // has the state of true for confirmed instead of false for a cancel action
                if(this.editTaskController.getConfirmedStatus()){
                    dataController.getTaskRepo().getTasks().get(
                        taskList.getSelectionModel().getSelectedIndex()).updateProperties(
                        this.editTaskController.getEditedTask());
                    
                    
                }
                
                
                this.editTaskController = new EditTaskController();
                restoreRoot();
            }
        });

    }

}
