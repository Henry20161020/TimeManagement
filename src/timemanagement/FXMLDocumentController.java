/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static timemanagement.FileController.readFile;
import static timemanagement.FileController.writeFile;

/**
 *
 * @author henry
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML GridPane display_area, search_box, grid_input;
    @FXML Button display,button_new,button_exit,button_save,button_delete,button_pomodoro;
    @FXML ListView category_list;

    private File file=new File("test.txt");
    private ArrayList<Task> tasks=new ArrayList<>();
    private ArrayList<Integer> indexes=new ArrayList<>();
    private int currentTask;
    private String[] header={"#","Description","Category","Due Date","Coworker", "Situation","Comments"};


    private void displayHeader(String[] h) {
        for (int i = 0; i < 7; i++) {
            Label label=new Label(h[i]);
            label.setFont(Font.font("Arial",FontWeight.BOLD,15));
            display_area.add(label,i,0);
            display_area.setHalignment(label, HPos.CENTER);
        }
    }
    
    private void displayLine(int taskNumber) {
        Button button=new Button();
        button.setOnAction(e->{
            Node source = (Node)e.getSource();
            currentTask=display_area.getRowIndex(source)-1;
            show(grid_input);
        });
        display_area.add(button, 0, taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskDescription()),1,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskCategory()),2,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskDueDate()),3,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskCoworker()),4,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskSituation()),5,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getComments()),6,taskNumber+1);
    }
//    private void updateList(ListView selectionList) {
//        ArrayList<String> list = new ArrayList<>();
//        for (Task task : tasks)
//            for (String item: list)
//                if (item.equals(task.))
//        selectionList.getItems().addAll(list);
//    }
    
    private String getContent(GridPane grid, int row, int col) {
        for(Node node: grid.getChildren()) 
            if (node instanceof TextField && grid.getColumnIndex(node)==col && grid.getRowIndex(node)==row )
                return ((TextField)node).getText();
        return "N/A";
    }
    
    private void show(GridPane grid) {
        showTextField(grid,0,1,tasks.get(currentTask).getTaskDescription());
        showTextField(grid,1,1,tasks.get(currentTask).getTaskCategory());
        showTextField(grid,2,1,tasks.get(currentTask).getTaskDueDate());
        showTextField(grid,3,1,tasks.get(currentTask).getTaskCoworker());
        showTextField(grid,4,1,tasks.get(currentTask).getTaskSituation());
        showTextField(grid,5,1,tasks.get(currentTask).getComments());
        
    }
    
    private void showTextField(GridPane grid, int row, int col, String content) {
        for(Node node: grid.getChildren()) 
            if (node instanceof TextField && grid.getColumnIndex(node)==col && grid.getRowIndex(node)==row )
                ((TextField)node).setText(content);
    }
    
    private void showLabel(GridPane grid, int row, int col, String content) {
        for(Node node: grid.getChildren()) 
            if (node instanceof Label && grid.getColumnIndex(node)==col && grid.getRowIndex(node)==row )
                ((Label)node).setText(content);
    }
    
    private void updateTasks(ArrayList<Task> tasks){
        Task task=tasks.get(currentTask);
        task.setTaskDescription(getContent(grid_input,0,1));
        task.setTaskCategory(getContent(grid_input,1,1));
        task.setTaskDueDate(getContent(grid_input,2,1));
        task.setTaskCoworker(getContent(grid_input,3,1));
        task.setTaskSituation(getContent(grid_input,4,1));
        task.setComments(getContent(grid_input,5,1));
    }
    
    private void load(String xml, Task task) throws IOException {
        try{
            Scene scene;
            FXMLLoader loader=new FXMLLoader();
            Pane root = (Pane)loader.load(getClass().getResource(xml));
            PomodoroController pomodoro=loader.getController();
            pomodoro.setTask(task);
            scene = new Scene(root,600,400);
            Stage secondStage = new Stage();
            secondStage.setScene(scene);
            secondStage.initModality(Modality.APPLICATION_MODAL);  // Use this to make the 2nd window modal (must close 2nd window to return to main window)
            secondStage.showAndWait();
        }
        catch(Exception e) {
            e.printStackTrace();    
        }
    }
    
    private void refresh() {
        Node node=display_area.getChildren().get(0);
        display_area.getChildren().clear();
        display_area.getChildren().add(node);
        displayHeader(header);
        for (int i = 0; i < tasks.size(); i++)
            displayLine(i); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        displayHeader(header);

        try {
                tasks=readFile(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        ArrayList<String> list=new ArrayList<>();
//        for (Node node: search_box.getChildren()) {
//            if(search_box.getColumnIndex(node)==1) updateList((ListView)node);
//        }
        display.setOnAction(e->{
            for (int i = 0; i < tasks.size(); i++)
                displayLine(i);
        });
        
        button_new.setOnAction(e->{
            tasks.add(new Task(
                    tasks.size(),
                    getContent(grid_input,0,1),
                    getContent(grid_input,1,1),
                    getContent(grid_input,2,1),
                    getContent(grid_input,3,1),
                    getContent(grid_input,4,1),
                    "",
                    getContent(grid_input,5,1)
            ));
            displayLine(tasks.size()-1);
        });
        
        button_save.setOnAction(e->{
            updateTasks(tasks);
            refresh();
        });
        
        button_exit.setOnAction(e->{
            try {
                writeFile(file,tasks);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        });
        
        button_delete.setOnAction(e->{
            tasks.remove(currentTask);
            refresh();
//            display_area.setGridLinesVisible(true);
        });
        
        button_pomodoro.setOnAction(e->{
            try {
                load("Pomodoro.fxml",tasks.get(currentTask));
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }    
    
}
