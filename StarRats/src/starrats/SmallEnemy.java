package starrats;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * Small enemies meant to spawn in mass numbers.  Low health and low damage
 * only yield the player low points.
 * Created by John on 4/29/17.
 */
public final class SmallEnemy extends Enemy implements Serializable {

    private static final long serialVersionUID = 8350310727683155761L;

    private final double size;


    public SmallEnemy(ID id, double minSize, double maxSize, double maxX, double maxY){
        super(id, maxX, maxY);
        size = numberGen.getRandDouble(minSize, maxSize);
        width = size;
        height = size;
        spawnTop();
        this.count = 0;
        speedX = numberGen.getRandDouble(-.75, .75);
        speedY = numberGen.getRandDouble(.5, 2);


        health = 1;
        damage = 2;
        pointValue = 5;
    }


    @Override
    public void render(GraphicsContext gc) {

        gc.setFill(Color.grayRgb( 95));

        gc.fillOval(x, y, width, height);


    }
}
