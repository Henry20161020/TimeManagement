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
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author henry
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML Button read,write;
    Stage stage1=new Stage();
    
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        System.out.println("You clicked me!");
//        label.setText("Hello World!");
//    }
//    public void readFile() {
//        System.out.println("it's invoked");
//        FileChooser chooser = new FileChooser();
//        File file=chooser.showOpenDialog(stage1);
//        System.out.println(file.length());
//        if (file!=null) {
//            Scanner scan;
//            try {
//                scan=new Scanner(file);
//                while(scan.hasNext()) 
//                    System.out.println(scan.nextLine());
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        read.setOnAction(e->{
            FileChooser chooser = new FileChooser();
            File file=chooser.showOpenDialog(stage1);
            System.out.println(file.length());
            if (file!=null) {
                try {
                    Scanner scan=new Scanner(file);
                    while(scan.hasNext()) 
                        System.out.println(scan.nextLine());
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        write.setOnAction(e->{
            FileChooser chooser = new FileChooser();
            File file=chooser.showSaveDialog(stage1);
            if (file!=null) {
                PrintWriter pw;
                try {
                    pw=new PrintWriter(file);
                    pw.print("test");
                    pw.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }    
    
}
