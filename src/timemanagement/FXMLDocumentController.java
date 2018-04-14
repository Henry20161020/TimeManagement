/**
 * This file is to control FXMLDocument.fxml
 * @author henry
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
import javafx.scene.control.ScrollPane;
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
import javax.swing.JOptionPane;
import static timemanagement.FileController.readFile;
import static timemanagement.FileController.writeFile;

public class FXMLDocumentController implements Initializable {
    /*the three grid pane, display_area at right, grid_input at top left, search_box at bottom left*/
    @FXML GridPane display_area, search_box, grid_input;
    /*buttons at the top left area*/
    @FXML Button button_new,button_exit,button_save,button_delete,button_pomodoro,button_clear;
    /*listview for choosing filtering criteria*/
    @FXML ListView category_list;
    /*the scroll pane at right area and the parent of the grid pane display_area*/
    @FXML private ScrollPane scrollpane;
    /*all tasks are stored in task.txt*/
    private File file=new File("task.txt");
    /*The data will read from file to the ArrayList below*/
    private ArrayList<Task> tasks=new ArrayList<>();
    /**
     *The global variable in this controller for the index of current task, -1 means
     *that no task is selected
     */
    private int currentTask=-1;
    /*This String is used in the header of the display area*/
    private String[] header={"#","Description","Category","Due Date","Coworker", "Situation","Comments"};
    /*val instance is used to validate the input data*/
    private Validator val=new Validator();

    /*This method is to display the header*/
    private void displayHeader(String[] h) {
        for (int i = 0; i < 7; i++) {
            Label label=new Label(h[i]);
            label.setFont(Font.font("Arial",FontWeight.BOLD,15));
            display_area.add(label,i,0);
            display_area.setHalignment(label, HPos.CENTER);
        }
    }
    
    /**
     * This method is to display one line for each task
     * Each line consists of one control button and 6 labels
     */
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
    
    /** 
     * This method is to get the text content from the input grid pane
     * at the specified row and column number
     */
    private String getContent(GridPane grid, int row, int col) {
        for(Node node: grid.getChildren()) 
            if (node instanceof TextField && grid.getColumnIndex(node)==col && grid.getRowIndex(node)==row )
                return ((TextField)node).getText();
        return "N/A";
    }
    
    /**
     * This method is to show the details of current task at input grid pane
     * for further actions such as update or delete
     */
    
    private void show(GridPane grid) {
        showTextField(grid,0,1,tasks.get(currentTask).getTaskDescription());
        showTextField(grid,1,1,tasks.get(currentTask).getTaskCategory());
        showTextField(grid,2,1,tasks.get(currentTask).getTaskDueDate());
        showTextField(grid,3,1,tasks.get(currentTask).getTaskCoworker());
        showTextField(grid,4,1,tasks.get(currentTask).getTaskSituation());
        showTextField(grid,5,1,tasks.get(currentTask).getComments());
        
    }
    
    /**
     * This method is to display text at the specific text field in the grid pane
     * with the specified row and column number
    */
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
//            Pane root = (Pane)FXMLLoader.load(getClass().getResource(xml));
            FXMLLoader loader=new FXMLLoader(getClass().getResource(xml));
            Pane root = (Pane)loader.load();
            PomodoroController pomodoro=loader.getController();
            System.out.println();
            System.out.println(task);
            pomodoro.setTask(task);

            scene = new Scene(root,600,400);
            Stage secondStage = new Stage();
            secondStage.setTitle("Pomodoro Timer");
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
    
    private boolean validate(GridPane grid, char delimiter) {
        for (int i = 0; i < 6; i++) 
            if (val.containDelimiter(getContent(grid_input,i,1), delimiter)) return false;
        return true;    
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        scrollpane.setContent(display_area);
        scrollpane.setFitToHeight(true);
        displayHeader(header);

        try {
                tasks=readFile(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < tasks.size(); i++)
            displayLine(i);
//        ArrayList<String> list=new ArrayList<>();
//        for (Node node: search_box.getChildren()) {
//            if(search_box.getColumnIndex(node)==1) updateList((ListView)node);
//        }
        
        button_new.setOnAction(e->{
            if (validate(grid_input,'|')) {
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
            } else
                JOptionPane.showMessageDialog(null,  "Character '|' is not allowed","Error", JOptionPane.ERROR_MESSAGE);
        });
        
        button_save.setOnAction(e->{
            if (currentTask>=0 && currentTask<tasks.size()) {
                if (validate(grid_input,'|')){
                    updateTasks(tasks);
                    refresh();
                } else 
                    JOptionPane.showMessageDialog(null, "Character '|' is not allowed", "Error", JOptionPane.ERROR_MESSAGE);
            } else
                JOptionPane.showMessageDialog(null, "You have to choose a task first","Error",  JOptionPane.ERROR_MESSAGE);
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
            if (currentTask>=0 && currentTask<tasks.size()) {
                tasks.remove(currentTask);
                refresh();
            } else
                JOptionPane.showMessageDialog(null, "You have to choose a task first","Error",  JOptionPane.ERROR_MESSAGE);
        });
        
        button_pomodoro.setOnAction(e->{
            
            if (currentTask>=0 && currentTask<tasks.size()) 
                try {
                    load("Pomodoro.fxml",tasks.get(currentTask));
                } catch (IOException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            else
                JOptionPane.showMessageDialog(null, "You have to choose a task first","Error",  JOptionPane.ERROR_MESSAGE);
        });
        
        button_clear.setOnAction(e->{
            for (int i = 0; i < 6; i++) 
                showTextField(grid_input,i,1,"");
        });
        
    }    
    
}
