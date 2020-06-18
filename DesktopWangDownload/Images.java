import java.io.File;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

class Images {

    Images(double x,double y) {
        Random rand = new Random();
        int upperBound = new File("theStash").listFiles().length;
        Stage subStage = new Stage();
        subStage.setTitle("The Stash");
        

        Pane root = new Pane();
        
        Image img = new Image("theStash/" + (rand.nextInt(upperBound) + 1) + ".png");
        double wid = img.getWidth();
        double hig = img.getHeight();

        ImageView imgV = new ImageView(img);
        

        if (wid < 1000) {
            imgV.setFitHeight(hig/1.5);
            imgV.setFitWidth(wid/1.5);
            hig /= 1.5;
            wid /= 1.5;
        }
        else {
            imgV.setFitHeight(hig/4);
            imgV.setFitWidth(hig/4);
            hig /= 4;
            wid /= 4;
        }

        root.getChildren().add(imgV);
        
        subStage.setX(rand.nextInt((int) (x)));
        subStage.setX(rand.nextInt((int) (y)));
        Scene scene = new Scene(root);
        subStage.setAlwaysOnTop(true);
        subStage.setScene(scene);
        subStage.show();
    }
}