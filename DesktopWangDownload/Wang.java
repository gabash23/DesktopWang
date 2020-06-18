import javafx.util.Duration;
import java.util.Random;

import java.awt.*;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.scene.shape.Arc;

import java.io.File;

public class Wang extends Pane {

    private Group wang = new Group();
    private double limX, limY;
    private double x, y;
    private boolean shadowClone = false;
    private Wang wang1, wang2;
    private double WIDTH,HEIGHT;
    private Stage ps;
    


    public Wang(double wid, double hig) {

        Image img = new Image("wang.png");
        limX = img.getWidth();
        limY = img.getHeight();
        ImageView imgV = new ImageView(img);
        imgV.setFitWidth(wid);
        imgV.setFitHeight(hig);
        Line body = new Line(), armL = new Line(), armR = new Line(), legL = new Line(), legR = new Line();
        Line[] lines = {body, armL, armR, legL, legR};
                
        for (int i = 0; i < lines.length; i++) {

            Line limb = lines[i];
            limb.setStrokeWidth(5);
            limb.setStroke(Color.BLACK);

            limb.startXProperty().bind(imgV.fitWidthProperty().divide(2));

            if (i == 0)
                limb.endXProperty().bind(body.startXProperty());
            else if (i == 1 || i == 3)
                limb.endXProperty().bind(body.startXProperty().subtract(10));
            else if (i == 2 || i == 4)
                limb.endXProperty().bind(body.startXProperty().add(10));
            
            if (i == 0)
                limb.startYProperty().bind(imgV.fitHeightProperty().add(hig/64));
            else if (i == 1 || i == 2) 
                limb.startYProperty().bind((body.startYProperty().add(body.endYProperty()).divide(2.125)));
            else if (i == 3 || i == 4) 
                limb.startYProperty().bind(body.endYProperty());

            if (i == 0 || i == 1 || i == 2)
                limb.endYProperty().bind(body.startYProperty().add(25));
            else if (i == 3 || i == 4)
                limb.endYProperty().bind(body.endYProperty().add(25));

            wang.getChildren().add(limb);
        }

        wang.getChildren().add(imgV);
        getChildren().add(wang);   
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void wander() {
        Random rand = new Random();
        int randX;
        int randY;
        int randChoice = rand.nextInt(30);
        //System.out.println(randChoice);
        if (randChoice != 8 && randChoice != 9 && randChoice != 10 && randChoice != 11 && randChoice != 12) {
            if (randChoice < 4) {
                
                new Images(WIDTH,HEIGHT);
                ps.toFront();
            }

            else if (randChoice == 5) {
                
                new Videos(WIDTH,HEIGHT);
                ps.toFront();
            }

            else if (randChoice == 6 || randChoice == 7)  {
                sound();
            }
            
            randX = rand.nextInt((int) WIDTH);
            randY = rand.nextInt((int) HEIGHT);

            
            if (randX > WIDTH - limX)
                randX = (int) (WIDTH - limX);

            else if (randX < limX) 
                randX = (int) limX;
            
            else if (randY > HEIGHT - limY)
                randY = (int) (HEIGHT - limY);

            else if (randY < limY)
                randY = (int) limY;
                
            Line line = new Line(getX(),getY(),randX,randY);
            PathTransition pt = new PathTransition();
            pt.setDuration(Duration.millis(3000));
            pt.setPath(line);
            pt.setNode(wang);
            pt.setCycleCount(1);
            pt.play();

            if (shadowClone)
                shadowCloneMove(wang1, wang2, randX, randY);

            setX(line.getEndX());
            setY(line.getEndY());
             
            pt.setOnFinished(e -> wander());
        } 

        else if (randChoice == 8) {

            if (!shadowClone)
                shadowClone(WIDTH / 2.75 / 620 * 100, HEIGHT / 781 * 100);

            else {
                wander();
            }
        }

        else if (randChoice == 9 || randChoice == 10 || randChoice == 11) {
            
            if (shadowClone) 
                removeShadowClone();
            else 
                wander();
        }

        else if (randChoice == 12) 
            avatar();
        

    }
    
    public void follow(double x, double y) {
        Line line = new Line(this.x, this.y, x, y);
        line.setStroke(Color.CYAN);
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(2000));
        pt.setPath(line);
        pt.setNode(wang);
        pt.setCycleCount(1);
        pt.play();
        this.x = x;
        this.y = y;

    }

    public void captureMouse(double x, double y) throws AWTException {
        Robot robot = new Robot();
        robot.mouseMove(0, 0);
    }

    public void walk(double speed) {
        Arc arc = new Arc(0, 0, 10, 16, 250, 60);
        Arc arc2 = new Arc(0, 0, 10, 16, 300, -60);
        Line legL = (Line) wang.getChildren().get(3);
        Line legR = (Line) wang.getChildren().get(4);

        arc.centerXProperty().bind(legL.startXProperty());
        arc.centerYProperty().bind(legL.startYProperty());
        arc2.centerXProperty().bind(legR.startXProperty());
        arc2.centerYProperty().bind(legR.startYProperty());
        
        arc.setFill(Color.TRANSPARENT);
        arc.setStroke(Color.TRANSPARENT);
        getChildren().add(arc);
        arc2.setFill(Color.TRANSPARENT);
        arc2.setStroke(Color.TRANSPARENT);
        getChildren().add(arc2);

        PathTransition walking1 = new PathTransition();
        walking1.setDuration(Duration.millis(speed));
        walking1.setPath(arc);
        walking1.setNode(legL);
        walking1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        walking1.setCycleCount(Timeline.INDEFINITE);
        walking1.setAutoReverse(true);
        walking1.play();

        PathTransition walking2 = new PathTransition();
        walking2.setDuration(Duration.millis(speed));
        walking2.setPath(arc2);
        walking2.setNode(legR);
        walking2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        walking2.setCycleCount(Timeline.INDEFINITE);
        walking2.setAutoReverse(true);
        walking2.play();
    }

    public void sound() {
        Random rand = new Random();
        int upperBound = new File("asmr").listFiles().length;

        AudioClip sound = new AudioClip(this.getClass().getResource("asmr/" + (rand.nextInt(upperBound) + 1) + ".m4a").toString());
        sound.play();
        //activate();
    }

    public void start(double width, double height, Stage ps) {
        
        Line line = new Line(width/2, height + 50, width/2, height);
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(2000));
        pt.setPath(line);
        pt.setNode(wang);
        pt.setAutoReverse(true);
        pt.setCycleCount(1);
        
        Line line2 = new Line(width/2, height, width/2, height / 2);
        PathTransition pt2 = new PathTransition();
        pt2.setDuration(Duration.millis(1000));
        pt2.setPath(line2);
        pt2.setNode(wang);
        pt2.setCycleCount(1);

        SequentialTransition seqT = new SequentialTransition(pt,pt2);
        seqT.play();

        x = width/2;
        y = height/2;

        WIDTH = width;
        HEIGHT = height;
        this.ps = ps;
        seqT.setOnFinished(e -> wander());
    
    }

    public void shadowClone(double x, double y) {
        wang1 = new Wang(x,y);
        wang2 = new Wang(x,y);
        
        wang1.walk(700);
        wang2.walk(700);

        getChildren().add(wang1);
        getChildren().add(wang2);

        shadowClone = true;

        Line l1 = new Line(getX(), getY(), getX() + 100, getY());
        Line l2 = new Line(getX(), getY(), getX() - 100, getY());
        
        PathTransition p1 = new PathTransition();
        p1.setDuration(Duration.millis(1500));
        p1.setPath(l1);
        p1.setNode(wang1);
        p1.setCycleCount(1);
        p1.play();

        FadeTransition f1 = new FadeTransition(Duration.millis(1500),wang1);
        f1.setFromValue(0);
        f1.setToValue(1);
        f1.setCycleCount(1);
        f1.play();

        PathTransition p2 = new PathTransition();
        p2.setDuration(Duration.millis(1500));
        p2.setPath(l2);
        p2.setNode(wang2);
        p2.setCycleCount(1);
        p2.play();

        FadeTransition f2 = new FadeTransition(Duration.millis(1500),wang2);
        f2.setFromValue(0);
        f2.setToValue(1);
        f2.setCycleCount(1);
        f2.play();

        f2.setOnFinished(e -> {
            wander();
        });
    }

    public void shadowCloneMove(Wang wang1, Wang wang2, double randX, double randY) {
        Line line1 = new Line(getX() + 100 , getY(), randX + 100, randY);
        PathTransition pt1 = new PathTransition();
        pt1.setDuration(Duration.millis(3000));
        pt1.setPath(line1);
        pt1.setNode(wang1);
        pt1.setCycleCount(1);
        pt1.play();

        Line line2 = new Line(getX() - 100, getY(), randX - 100, randY);
        PathTransition pt2 = new PathTransition();
        pt2.setDuration(Duration.millis(3000));
        pt2.setPath(line2);
        pt2.setNode(wang2);
        pt2.setCycleCount(1);
        pt2.play();
    }

    public void removeShadowClone() {
        Line l1 = new Line(getX() + 100, getY(), getX(), getY());
        Line l2 = new Line(getX() - 100, getY(), getX(), getY());
        
        PathTransition p1 = new PathTransition();
        p1.setDuration(Duration.millis(1500));
        p1.setPath(l1);
        p1.setNode(wang1);
        p1.setCycleCount(1);
        p1.play();

        FadeTransition f1 = new FadeTransition(Duration.millis(1500),wang1);
        f1.setFromValue(1);
        f1.setToValue(0);
        f1.setCycleCount(1);
        f1.play();

        PathTransition p2 = new PathTransition();
        p2.setDuration(Duration.millis(1500));
        p2.setPath(l2);
        p2.setNode(wang2);
        p2.setCycleCount(1);
        p2.play();

        FadeTransition f2 = new FadeTransition(Duration.millis(1500),wang2);
        f2.setFromValue(1);
        f2.setToValue(0);
        f2.setCycleCount(1);
        f2.play();
        
        f2.setOnFinished(e -> {
            getChildren().remove(wang1);
            getChildren().remove(wang2);
            wander();

        });

        shadowClone = false;
    }

    public void avatar() {
        Random rand = new Random();
        int upperBound = new File("avatar").listFiles().length;
        Image img = new Image("avatar/" + (rand.nextInt(upperBound) + 1) + ".png");
        ImageView ryann = new ImageView(img);
        ryann.setFitHeight(200);
        ryann.setFitWidth(200);     
        
        getChildren().add(ryann);
            
        Line path;
        
        if (Math.abs(x - WIDTH) < WIDTH / 2) {
            path = new Line(x,y,WIDTH,y);          
        }

        else {
            path = new Line(x,y,0,y);
        }

        PathTransition ryry = new PathTransition();
        ryry.setPath(path);
        ryry.setNode(ryann);
        ryry.setDuration(Duration.millis(2000));
        ryry.setCycleCount(1);
        ryry.play();

        ryry.setOnFinished(e -> {
            getChildren().remove(ryann);
            wander();
        });     
    }
}