import java.io.File;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


class Videos {

    Videos(double x, double y) {
        Random rand = new Random();
        int upperBound = new File("vids").listFiles().length;

        Stage subStage = new Stage();
        subStage.setTitle("New Stage");

        Media media = new Media((this.getClass().getResource("vids/" + (rand.nextInt(upperBound) + 1) + ".mp4").toExternalForm()));
        MediaPlayer mp = new MediaPlayer(media);
        mp.setAutoPlay(true);
        MediaView mv = new MediaView(mp);

        Group root = new Group();  
        root.getChildren().add(mv);

        subStage.setX(rand.nextInt((int) (x)));
        subStage.setX(rand.nextInt((int) (y)));

        Scene scene = new Scene(root,600,360);  
        subStage.setScene(scene);  
        subStage.setAlwaysOnTop(true);
        subStage.setTitle("Im sorry if this is a Rick Roll");  
        subStage.show();  
    }  
}