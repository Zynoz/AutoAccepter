package sample;

import com.sun.glass.events.KeyEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;

import java.awt.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class Controller {

    @FXML
    private Label lb_interval;

    @FXML
    private javafx.scene.control.Button btn_stop;
    @FXML
    private javafx.scene.control.Button btn_start;

    private AtomicBoolean running = new AtomicBoolean(false);
    private Robot robot;
    private Thread thread;
    private int interval = 5000;

    @FXML
    private void start() {
        lb_interval.setText(String.valueOf(interval));
        btn_start.setDisable(true);
        btn_stop.setDisable(false);
        running.set(true);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        thread = new Thread(() -> {
            while (running.get()) {
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    @FXML
    private void stop() {
        btn_stop.setDisable(true);
        btn_start.setDisable(false);
        running.set(false);
        thread.interrupt();
        thread = null;
    }

    @FXML
    private void setInterval() {
        TextInputDialog dialog = new TextInputDialog("5");
        dialog.setTitle("Interval");
        dialog.setHeaderText("Enter interval time");
        dialog.setContentText("Please enter an interval time in seconds:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(test -> {
            interval = Integer.parseInt(test) * 1000;
        });
    }
}