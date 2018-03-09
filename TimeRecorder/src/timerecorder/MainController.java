/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timerecorder;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static timerecorder.TimeRecorder.theStage;
import timerecorderdatamodel.Task;
import timerecorderdatamodel.TaskRepository;
import utility.io.parse.LongToReadableTime;


public class MainController extends TimerTask{
    
        long miliime;
    
        // Main GUI elements that need to be altered or used
        @FXML private Button  btnAddTask, btnEditTime, btnViewChart,
                btnDelete, btnStart, btnClose;
        
        @FXML private MenuButton btnFileMenu;
        
        @FXML private BorderPane root;

        @FXML private VBox vBoxTaskList;
        @FXML private VBox vBoxRight;
        @FXML private HBox hBoxTop;
        @FXML private ListView taskList;
        
        // Timer Readout box the timer label goes in


        // The selected task from the vBoxTaskList's ListView
        // That is running 
        private int indexOfRunningTask;
        
        private boolean editingTask;
        
        private boolean isAddingTask;
        
        private boolean isViewUpdating;
        private boolean showingChart;



        
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
        
        private PieChartController pieChartController;

        // 
        private EditTaskController editTaskController;
        private boolean isTimerRunning;
        
        private Stage primaryStage;
         
         
        public MainController(DataController dataController, Stage primaryStage){
            
            this.primaryStage = primaryStage;
            storageController = new StorageController();
            this.dataController = dataController;
            this.addTaskController = new AddTaskController();
            this.readoutController = new TimerReadoutController();
            this.editTaskController = new EditTaskController();
            this.pieChartController = new PieChartController();
            
            this.isViewUpdating = false;
            
            // Set states
            editingTask = false;
            isAddingTask = false;
            showingChart = false;
            
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(this, 0, 80);
            
            
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
            addTaskController = new AddTaskController();
            addTaskController.startAddTask();
            clearRoot();
            root.setCenter(addTaskController.getEntryView());
            this.isAddingTask = true;
        }
        
        // Used when control needs to be passed from AddTaskController object
        // To this
        protected void endAddNewTask(){
            addTaskController = new AddTaskController();
            this.isAddingTask = false;
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
                Task selectedTask = this.dataController.getTaskRepo().getTasks().
                        get(this.taskList.getSelectionModel().getSelectedIndex());
                
                editTaskController.startEditing(selectedTask);
            }

        }
        
        private void buildEditingView(){
                root.setCenter(editTaskController.getEditView());
                editTaskController.setEditing(true);
                editingTask = true;
        }

        @FXML
        protected void viewChart(ActionEvent event){
            this.pieChartController = new PieChartController();
            this.showingChart = true;
            this.pieChartController.showPieChart(primaryStage, 
                    dataController.getTaskRepo().getTasks());
            
            
        }
        
        
        
        // Start Selected Task
        @FXML
        protected void startTask(ActionEvent event){
            
         
            
            // Make sure a task is selected
            if(taskList.getSelectionModel().getSelectedIndex() >= 0){
                this.isViewUpdating = true;
                indexOfRunningTask = taskList.getSelectionModel().getSelectedIndex();
                // Pass control

                readoutController = new TimerReadoutController();
                readoutController.startTimers();
    
                 this.isTimerRunning = true;
                 this.isViewUpdating = false;

                 displayReadoutScene();
           
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
        
        
        // Timer Readout Functions
        protected void updateTimerReadout(){
            
            isViewUpdating = true;
            this.readoutController.updateReadoutView();   
            isViewUpdating = false;
        }
        
        protected void endTimerReadout(){
            
            
            this.readoutController.getTimerDuration();
            this.readoutController.setRunning(false);
            
            // Get timer duratin and add as a session
            this.dataController.getTaskRepo().getTasks().
                    get(indexOfRunningTask).
                    addSession(this.readoutController.getTimerDuration());
            
            this.readoutController = new TimerReadoutController();
            this.isTimerRunning = false;
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
            btnViewChart.setVisible(true);
            btnStart.setVisible(true);
            btnDelete.setVisible(true);
            btnClose.setVisible(true);


        }
        
        private void hideControls(){   
            btnFileMenu.setVisible(false);
            btnAddTask.setVisible(false);
            btnDelete.setVisible(false);
            btnEditTime.setVisible(false);
            btnViewChart.setVisible(false);
            btnStart.setVisible(false);
            btnClose.setVisible(false);
        }
        
        
        // Updates the main fxml view task list (the listview)
        private void updateTaskList(){
            if(dataController.getTaskRepo() != null)
            {
                
                // Sort the list
                dataController.getTaskRepo().sortByRecent();
                ArrayList<String> list = new ArrayList<>();
                for(Task task : dataController.getTaskRepo().getTasks())
                    
                    if(task.getSessionCount() > 0){
                        list.add(task.getName() + "\r\n" +
                        "Last Run: " + new Date(task.getLastRun().getTime()) + "\r\n" +
                        "Total Time: " + LongToReadableTime.getReadableTime(task.getTotalTime()));
                    } else {
                        list.add(task.getName() + "\r\n" +
                                "No Sessions Recorded" + "\r\n" +
                                "Total Time: " + LongToReadableTime.getReadableTime(task.getTotalTime()));
                    }


                
                ObservableList<String> items = FXCollections.observableArrayList();

                items.setAll(list);

                taskList.setItems(items);



                
            }

            
        }
        
        protected void displayMainScene(){
                    
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TimeRecorderMainFXML.fxml"));
            BorderPane newRoot = new BorderPane();

            newRoot.getStylesheets().add(getClass().getResource("stylesheet.css").toString());

            fxmlLoader.setController(this);

            try{
                newRoot = fxmlLoader.load();

            }catch(IOException e){

            }

            Scene scene  = new Scene(root);
            this.primaryStage.setScene(scene);
            
            updateTaskList();
        }
        
        protected void displayReadoutScene(){
            
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TimerReadout.fxml"));
         loader.setController(this.readoutController);

            try{
                VBox newRoot = loader.load();
                Scene scene = new Scene(newRoot);
                this.primaryStage.setScene(scene);

            }catch(IOException e){}
            
            
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/test.fxml"));
            loader.setController(this);
            
            try{
                SplitPane bor = loader.load();
                Scene scene = new Scene(bor);
                this.primaryStage.setScene(scene);
            }catch(IOException e){}
            */
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
       

    @Override
    public void run() {
        
        Platform.runLater(() -> {
            
            
            if(this.isViewUpdating != true){
                //When the controlcontroller is active and such hasn't been reflected by this.editingTask
                if(!this.editTaskController.getState() && this.editingTask == true) {
                    editingTask = false;


                    // Get the edited task and update the data model if the task controller
                    // has the state of true for confirmed instead of false for a cancel action
                    if(this.editTaskController.getConfirmedStatus()){
                        dataController.getTaskRepo().getTasks().get(
                            taskList.getSelectionModel().getSelectedIndex()).setTask(
                            this.editTaskController.getEditedTask());


                    }


                    this.editTaskController = new EditTaskController();
                    restoreRoot();
                }




                // If a timer has stopped and this.isTimerRunning reflects that it is 
                // still running
                if(this.readoutController.isRunning() && this.isTimerRunning == true){
                    if(this.readoutController.getReadoutView() != null)
                        updateTimerReadout();
                }else if(!this.readoutController.isRunning() && this.isTimerRunning == true){
                    this.isTimerRunning = false;
                    endTimerReadout();
                    displayMainScene();
              
                } 
                
                // If the add task is complete but main has not recorded this state of
                // the add task controller
                if(!this.addTaskController.isIsAdding() && this.isAddingTask == true){
                    dataController.getTaskRepo().addTask(this.addTaskController.getNewTask());
                    this.endAddNewTask();
                }
                
                
                if(this.pieChartController.isDone() && this.showingChart){
                    this.pieChartController = new PieChartController();
                    displayMainScene();
                    updateTaskList();
                    this.showingChart = false;
                }
            }

        });

    }

}
