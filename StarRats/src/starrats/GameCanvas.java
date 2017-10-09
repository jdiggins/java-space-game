package starrats;

import javafx.scene.canvas.Canvas;


/**
 * A canvas to draw on containing our game loop.  All game graphics rendering
 * happens here
 * Created by John Diggins on 4/23/17.
 */
public class GameCanvas extends Canvas{


    public GameCanvas(int width, int height, Handler handler) {
        super(width, height);

        GameLoop gameloop = new GameLoop(handler, this, width, height);

    }



}
