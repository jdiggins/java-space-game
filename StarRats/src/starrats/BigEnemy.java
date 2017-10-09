package starrats;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * 
 * Large astroid enemy worth more points and deals more damage to player.  
 * Uses NumberGenerator class to randomly generate size and speed within
 * limits.
 * 
 * Created by John Diggins on 4/29/17.
 * 
 * 
 */
public final class BigEnemy extends Enemy implements Serializable {

    private static final long serialVersionUID = 3645487102859475264L;

    double[] randNums = new double[10];
    double [] randPosition = new double [10];
    double size;

    public BigEnemy(ID id, double minSize, double maxSize, double maxX, double maxY){
        super(id, maxX, maxY);
        spawnTop();

        // set size of enemy (circle)
        size = numberGen.getRandDouble(minSize, maxSize);
        width = size;
        height = size;

        this.count = 0;
        speedX = numberGen.getRandDouble(-.1, .1);
        speedY = numberGen.getRandDouble(.1, .6);

        // random numbers for drawing "moon" detail
        for(int i = 0; i < 10; i++){
            randNums[i] = Math.random() * (width / 4);
            randPosition[i] = Math.random() * (width / 1.75);
        }
        health = numberGen.getRandInt(10, 30);
        damage = 25;
        pointValue = 600;
    }

    @Override
    public void render(GraphicsContext gc) {

        gc.setFill(Color.grayRgb( 80));

        gc.fillOval(x, y, width, height);
        for(int i = 0; i < 10; i++){
            gc.setFill(Color.grayRgb((int)randNums[i] + 40));
            gc.setStroke(Color.grayRgb(60));
            gc.fillOval(x + 5 + randPosition[i], y + 5 + randPosition[9-i], randNums[i], randNums[i]);
        }

    }
}
