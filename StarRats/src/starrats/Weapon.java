package starrats;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * "Bullet" that comes out of player
 * Created by John on 4/28/17.
 */
public class Weapon extends GameObject implements Serializable{

    public Weapon(double x, double y, double width, double height, ID id) {
        super(x, y, width, height, id);
    }

    public Weapon(double x, double y, ID id, double maxX, double maxY) {
        super(x, y, id, maxX, maxY);
    }

    @Override
    public void update() {
        checkBounds();
        y -= 4;
    }

    @Override
    public void render(GraphicsContext gc) {

        gc.setFill(Color.RED);
        gc.fillOval(x, y, width, height);

    }

    public void checkBounds() {

        if (y < 0) {
            deleteObject = true;
        }
    }


}
