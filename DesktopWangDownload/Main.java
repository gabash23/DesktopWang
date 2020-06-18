import java.awt.AWTException;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Screen;

public class Main extends Application {

    @Override
    public void start(Stage ps) {

        Pane pane = new Pane();
        
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        final double WIDTH = screenBounds.getWidth();
        final double HEIGHT = screenBounds.getHeight();
        final double wid = WIDTH / 2.75 / 620 * 100;
        final double hig = HEIGHT / 781 * 100;

        Wang wang = new Wang(wid, hig);
        
        wang.start(WIDTH,HEIGHT, ps);
        pane.getChildren().add(wang);

        wang.walk(700);
        
        pane.setOnMouseDragged(e -> {

            wang.follow(e.getX(), e.getY());

        });

        pane.setOnMouseClicked(e -> {
            try {
                wang.captureMouse(e.getX(), e.getY());
            } catch (AWTException e1) {
                e1.printStackTrace();
            }
        });

        pane.setOnKeyPressed(e -> {
            
            if ((e.getCode() == KeyCode.E) || (e.getCode() == KeyCode.ESCAPE)) {
                System.exit(0);
            } 

        });
        
        ps.initStyle(StageStyle.TRANSPARENT);
        ps.setAlwaysOnTop(true);
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        ps.setTitle("DesktopWang");
        ps.setScene(scene);
        ps.show();
        wang.requestFocus();
    }
}