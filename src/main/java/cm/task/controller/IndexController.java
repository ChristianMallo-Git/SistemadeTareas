package cm.task.controller;

import cm.task.model.Task;
import cm.task.service.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class IndexController implements Initializable {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private TaskService taskService;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task, Integer> idTaskColumn;

    @FXML
    private TableColumn <Task, String> taskTaskColumn;

    @FXML
    private TableColumn <Task, String> managerTaskColumn;

    @FXML
    private TableColumn <Task, String> statusTaskColumn;

    private final ObservableList <Task> taskList =
            FXCollections.observableArrayList();
    //Al ser de tipo Observable cualquier cambio que hagamos sobre esta lista
    //va a afectar también a la información de la tabla.

    @FXML
    private TextField nameTaskText;

    @FXML
    private TextField nameManagerText;

    @FXML
    private TextField nameStatusText;

    private Integer idTask;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        taskTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        configureColumns();
        listTask();
    }

    private void configureColumns(){
        idTaskColumn.setCellValueFactory(new PropertyValueFactory<>("idTask")); //Tengo que poner el nombre de los atributos de la clase del paquete model
                                                                                   //si no no va a poder cargar la información.
        taskTaskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerTaskColumn.setCellValueFactory(new PropertyValueFactory<>("manager"));
        statusTaskColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void listTask(){
        LOGGER.info("Executing list of task");
        taskList.clear();
        taskList.addAll(taskService.listTask());
        taskTable.setItems(taskList);
    }

    public void addTask(){

        if(nameTaskText.getText().isEmpty()){
            showMessage("Validation error." , "Must display a message.");
            nameTaskText.requestFocus(); //Este método se utiliza para solicitar
                                         //que un componente obtenga el foco del teclado.
            return;
        }else{
            Task task = new Task();
            collectDataForm(task);
            task.setIdTask(null);
            taskService.saveTask(task);
            showMessage("Information.", "Task added.");
            clearForm();
            listTask();
        }
    }

    public void uploadTaskForm(){
       Task task = taskTable.getSelectionModel().getSelectedItem();
       if(task != null){
           idTask = task.getIdTask();
           nameTaskText.setText(task.getName());
           nameManagerText.setText(task.getManager());
           nameStatusText.setText(task.getStatus());
       }
    }

    public void modifyTask(){

        if(idTask == null){
            showMessage("Information.","You must selected a task.");
            return;

        }
        if(nameTaskText.getText().isEmpty()){
            showMessage("Validation error.", "You must provide a task.");
            nameTaskText.requestFocus();
            return;
        }

        Task task = new Task ();
        collectDataForm(task);
        taskService.saveTask(task);
        showMessage("Information.", "Modified task.");
        clearForm();
        listTask();
    }

    public void deleteTask(){

        Task task = taskTable.getSelectionModel().getSelectedItem();
        if(task != null){
            LOGGER.info("Record to delete: " + task.toString());
            taskService.deleteTask(task);
            showMessage("Information.", "Task delete: " + task.getIdTask());
            clearForm();
            listTask();
        }else{
            showMessage("Error.", "No tasks have been deleted.");
        }
    }

    public void clearForm(){
        idTask=null;
        nameTaskText.clear();
        nameManagerText.clear();
        nameStatusText.clear();
    }

    private void collectDataForm(Task task){
        if(idTask != null){
            task.setIdTask(idTask);
        }
        task.setName(nameTaskText.getText());
        task.setManager(nameManagerText.getText());
        task.setStatus(nameStatusText.getText());
    }

    private void showMessage(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

