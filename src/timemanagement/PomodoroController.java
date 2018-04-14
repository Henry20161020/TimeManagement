/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timemanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author henry
 */
public class PomodoroController implements Initializable {
    @FXML Label label_countdown;
    @FXML private TextArea textarea_comment;
    @FXML private Button button_write;
    private int currentTime;
    private Task task=new Task();
    private Calendar cal = Calendar.getInstance();
    private Date startTime,endTime;
    private File file=new File("journal.txt");
    private Validator val=new Validator();

    
    public void setTask(Task task) {
        this.task=task;
    }
    
    private void timer(int period) {
        currentTime=period;
        Timeline timeline=new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame=new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                label_countdown.setText(convert(currentTime));
                currentTime--;
                if (currentTime==0){
                    timeline.stop();
                    endTime=cal.getTime();

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
    
    private void dangdang() {
        String musicFile = "finish.mp3";     

        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        startTime=cal.getTime();
        timer(1500);
        button_write.setOnAction(e->{
            String text=textarea_comment.getText();
            if (!val.containDelimiter(text, '|')) {
                dangdang(); 
                if (val.containDelimiter(text, '\n')) 
                    JOptionPane.showMessageDialog(null, "New line character is coverted to ' '", "Information", JOptionPane.INFORMATION_MESSAGE);
                task.setComments(text.replace("\n", " "));
                if (endTime==null) endTime=cal.getTime();
                try {
                    FileController.writeJournal(file,task,startTime,endTime);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(PomodoroController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else
                JOptionPane.showMessageDialog(null,"Character '|' is not allowed","Error", JOptionPane.ERROR_MESSAGE);
        });
        
    }    
    
}
