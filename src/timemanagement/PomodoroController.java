/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author henry
 */
public class PomodoroController implements Initializable {
    @FXML Label label_countdown;
    private int currentTime;
    @FXML private TextField textarea_comment;
    private Task task;
//    PomodoroController(String pomodorofxml) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    
    public void setTask(Task task) {
        this.task=task;
    }
    
    private void timer(int startTime) {
        currentTime=startTime;
        Timeline timeline=new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame=new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                label_countdown.setText(convert(currentTime));
                currentTime--;
                if (currentTime==0){
                    timeline.stop();
                }
            }
        });
        timeline.getKeyFrames().add(frame);
        timeline.playFromStart();
    }
    
    private String convert(int time) {
        int seconds=time%60;
        return String.valueOf(time/60)+":"+(seconds<10? "0":"")+String.valueOf(seconds);
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        timer(1500);
        
    }    
    
}
