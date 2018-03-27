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
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static timemanagement.FileController.readFile;

/**
 *
 * @author henry
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML GridPane display_area, search_box;
    @FXML Button display;
    @FXML ListView category_list;

    private File file=new File("test.txt");
    private ArrayList<Task> tasks=new ArrayList<>();

    private void displayHeader(String[] h) {
        for (int i = 0; i < 6; i++) {
            display_area.add(new Label(h[i]),i,0);
        }
    }
//    private void updateList(ListView selectionList) {
//        ArrayList<String> list = new ArrayList<>();
//        for (Task task : tasks)
//            for (String item: list)
//                if (item.equals(task.))
//        selectionList.getItems().addAll(list);
//    }
    
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
            for (int i = 0; i < tasks.size(); i++) {
                display_area.add(new Label(tasks.get(i).getTaskDescription()),0,i+1);
                display_area.add(new Label(tasks.get(i).getTaskCategory()),1,i+1);
                display_area.add(new Label(tasks.get(i).getTaskDueDate()),2,i+1);
                display_area.add(new Label(tasks.get(i).getTaskCoworker()),3,i+1);
                display_area.add(new Label(tasks.get(i).getTaskSituation()),4,i+1);
                display_area.add(new Label(tasks.get(i).getComments()),5,i+1);
            } 
        });
    }    
    
}
