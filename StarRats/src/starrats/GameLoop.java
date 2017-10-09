package starrats;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

/**
 * Created by John on 4/23/17.
 */
public class GameLoop{
    Handler handler;


    public static long count;

    public static int weaponCount;

    private final GraphicsContext gc;

    public GameLoop(Handler handler, Canvas canvas, int width, int height){
        this.handler = handler;

        this.gc = canvas.getGraphicsContext2D();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(1000/60), e -> {

                    ++count;
                    ++weaponCount;

                    update();
                    render();
                }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void update(){
        handler.update();
    }

    public void render() {
        handler.render(gc);
    }
}
