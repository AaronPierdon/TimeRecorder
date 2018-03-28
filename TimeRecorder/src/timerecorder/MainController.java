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
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.fxml.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import timerecorderdatamodel.Task;
import timerecorderdatamodel.TaskRepository;
import utility.io.parse.LongToReadableTime;


public class MainController extends TimerTask{
    
        long miliime;
    
        // Main GUI elements that need to be altered or used
        @FXML private Button  btnAddTask, btnEditTime, btnViewSessions, btnViewChart,
                btnDelete, btnStart, btnClose;
        
        @FXML private MenuBar btnFileMenu;
       
        
        @FXML private BorderPane root;
        
        private double rootWidth;
        private double rootHeight;

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
        
        private ChartController chartController;

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
            this.chartController = new ChartController();
            
            this.rootWidth = 360;
            this.rootHeight = 420;
            
            this.isViewUpdating = false;
            
            // Set states
            editingTask = false;
            isAddingTask = false;
            
            Timer timer = new Timer();
            timer.scheduleAtFixedRate(this, 0, 80);
            


            
        }
        
        public void iniTaskList(){
            TaskRepository temp = storageController.attemptToLoadFile();
            
            if(temp != null && !temp.getTasks().isEmpty()){
                dataController.setTaskRepo(temp);
                updateTaskList();
            }
            
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
        protected void viewCharts(ActionEvent event){
            this.chartController.showCharts(dataController.getTaskRepo().getTasks());
            
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

                 this.rootWidth = root.getWidth();
                 this.rootHeight = root.getHeight();
                 displayReadoutScene(this.root);
           
        }
    }
        
        
        
        // Delete Selected Task
        @FXML
        protected void deleteTask(ActionEvent event){
            
            
            if(taskList.getSelectionModel().getSelectedIndex() >= 0){
                
                hideAllExceptClose();

                    VBox confirmPane = new VBox();
                    confirmPane.setAlignment(Pos.BOTTOM_CENTER);
                    
                    VBox confirmPaneHolder = new VBox();
                    confirmPaneHolder.setAlignment(Pos.CENTER);
                    
                    
                    Button btnConfirm = new Button("Confirm");
                    
                    btnConfirm.addEventHandler(MouseEvent.MOUSE_CLICKED, new 
                        EventHandler<MouseEvent>(){
                            @Override
                            public void handle(MouseEvent e){
                                confirmedDelete();
                                restoreRoot();
                            }
                        });
                    
                    
                    Button btnCancel = new Button("Cancel");
                    btnCancel.addEventHandler(MouseEvent.MOUSE_CLICKED, 
                            new EventHandler<MouseEvent>(){
                                @Override
                                public void handle(MouseEvent e){
                                    cancelDelete();
                                }
                            });
                    
                    HBox btnBox = new HBox();
                    btnBox.setPadding(new Insets(5, 5, 5, 5));
                    btnBox.setSpacing(5);
                    btnBox.setAlignment(Pos.BOTTOM_RIGHT);
                    btnBox.getChildren().addAll(btnConfirm, btnCancel);
                    btnBox.setStyle("-fx-background-color: transparent;");
                    
                    
                    
                    Label lblConfirm = new Label("Are you sure?");
                    lblConfirm.setStyle("-fx-font-size: 24;");
                    VBox lblBox = new VBox();
                    lblBox.setAlignment(Pos.TOP_CENTER);
                    lblBox.getChildren().add(lblConfirm);
                    
                    confirmPaneHolder.setFillWidth(true);
                    confirmPaneHolder.setPadding(new Insets(0, 0, 5, 5));
                    confirmPaneHolder.setStyle("-fx-background-color: transparent;");
                    
                    
                    confirmPane.getChildren().add(btnBox);
                    confirmPane.setPrefHeight(rootHeight);
                    confirmPane.setPrefWidth(rootWidth);
                    
                    confirmPaneHolder.setPrefWidth(176);
                    confirmPaneHolder.setPrefHeight(337);
                    confirmPaneHolder.getChildren().addAll(lblBox, confirmPane);
                    
                    this.root.setCenter(confirmPaneHolder);
                
            
            }
        }
    
                
        //  //  //  //
        // Confirm Delete  Handlers //
        //  //  //  //
        

        private void confirmedDelete(){
            dataController.removeTask(taskList.getSelectionModel().getSelectedIndex());
            
            vBoxTaskList.getChildren().clear();
            vBoxTaskList.setAlignment(Pos.TOP_LEFT);
            vBoxTaskList.getChildren().add(taskList);
            updateTaskList();
            showControls();
        }

        private void cancelDelete(){
            // Revert to task list view
            restoreRoot();
            updateTaskList();
            showControls();
        }
        
        @FXML 
        protected void alwaysOnTop(){
            
            
            this.primaryStage.setAlwaysOnTop(!this.primaryStage.isAlwaysOnTop());
            
        }
        
        // Timer Readout Functions
        protected void updateTimerReadout(){
            
            isViewUpdating = true;
            this.readoutController.updateReadoutView();   
            isViewUpdating = false;
        }
        
        @FXML 
        protected void viewSessions(ActionEvent event){
            // Make sure a task is selected
            if(taskList.getSelectionModel().getSelectedIndex() >= 0){
                Task tempTask = this.dataController.getTaskRepo().getTasks().get(
                   taskList.getSelectionModel().getSelectedIndex());

            
                
                BubbleChartController bubbleController = new BubbleChartController();
                bubbleController.displayChart(this.dataController.getTaskRepo().getTasks().get(
                    taskList.getSelectionModel().getSelectedIndex()));
            }
            
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
            btnViewSessions.setVisible(true);
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
            btnViewSessions.setVisible(false);
            btnViewChart.setVisible(false);
            btnStart.setVisible(false);
            btnClose.setVisible(false);
        }
        
        private void hideAllExceptClose(){
            btnFileMenu.setVisible(false);
            btnAddTask.setVisible(false);
            btnDelete.setVisible(false);
            btnEditTime.setVisible(false);
            btnViewSessions.setVisible(false);
            btnViewChart.setVisible(false);
            btnStart.setVisible(false);
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

            Scene scene  = new Scene(root, this.rootWidth, this.rootHeight);
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
       
        }
        
        protected void displayReadoutScene(BorderPane root){
            
         FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TimerReadout.fxml"));
         loader.setController(this.readoutController);

            try{
                root.setCenter(loader.load());

            }catch(IOException e){}
       
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
            root.setTop(btnFileMenu);
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
                    // Make sure input was confirmed
                    if(this.addTaskController.isConfirmedInput())
                        dataController.getTaskRepo().addTask(this.addTaskController.getNewTask());
                    this.endAddNewTask();
                }


            }

        });

    }

}
