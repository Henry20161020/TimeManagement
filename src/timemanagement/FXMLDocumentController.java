/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static timemanagement.FileController.readFile;
import static timemanagement.FileController.writeFile;

/**
 *
 * @author henry
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML GridPane display_area, search_box, grid_input;
    @FXML Button display,button_new,button_exit;
    @FXML ListView category_list;

    private File file=new File("test.txt");
    private ArrayList<Task> tasks=new ArrayList<>();
    private ArrayList<Integer> indexes=new ArrayList<>();

    private void displayHeader(String[] h) {
        for (int i = 0; i < 6; i++) {
            display_area.add(new Label(h[i]),i,0);
        }
    }
    
    private void displayLine(int taskNumber) {
        display_area.add(new Label(tasks.get(taskNumber).getTaskDescription()),0,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskCategory()),1,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskDueDate()),2,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskCoworker()),3,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getTaskSituation()),4,taskNumber+1);
        display_area.add(new Label(tasks.get(taskNumber).getComments()),5,taskNumber+1);
    }
//    private void updateList(ListView selectionList) {
//        ArrayList<String> list = new ArrayList<>();
//        for (Task task : tasks)
//            for (String item: list)
//                if (item.equals(task.))
//        selectionList.getItems().addAll(list);
//    }
    
    private String getContent(GridPane grid, int row, int col) {
        System.out.println(grid.getChildren());
        for(Node node: grid.getChildren()) {
            System.out.println(node);
            System.out.println(grid.getColumnIndex(node));
            System.out.println(grid.getRowIndex(node));
            if (node instanceof TextField && grid.getColumnIndex(node)==col && grid.getRowIndex(node)==row )
                return ((TextField)node).getText();}
        return "N/A";
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String[] header={"Description","Category","Due Date","Coworker", "Situation","Comments"};
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
        
        button_exit.setOnAction(e->{
            try {
                writeFile(file,tasks);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        });
        
    }    
    
}
