package starrats;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Blinking stars for our background.  These objects are "inactive" and stored
 * in our inactive GameObject list in the handler.  Uses NumberGenerator class
 * to vary size and blinking rate of stars
 * Created by John on 4/23/17.
 */
public final class Stars extends GameObject{

    private static final long serialVersionUID = 5200314300683021820L;

    int count = 0;
    double rand = numberGen.getRandDouble(.02, 1.);
    //double rand = Math.random() * .5 + .5;
    int randCount = numberGen.getRandInt(15, 45);

    public Stars(double x, double y, double width, double height, ID id) {
        super(x, y, width, height, id);
    }

    public Stars(double x, double y, ID id, double maxX, double maxY) {
        super(x, y, id, maxX, maxY);
    }

    @Override
    public void update() {


    }

    @Override
    public void render(GraphicsContext gc) {
        if(count == randCount) {
            rand = Math.random() * .5 + .5;
            randCount = (int)(Math.random() * 30 + 15);
            count = 0;
        }

        gc.setFill(Color.color(1, 1, 1, rand));
        gc.fillOval(x, y, width, height);
        count++;
    }
}
