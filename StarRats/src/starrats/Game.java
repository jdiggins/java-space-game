/*
* StarRats
* A Space shooter
* By John Diggins
* CIT 239 JAVA Final Project
*
*/
package starrats;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.application.Application;

/**
 * Main class with Start function. Handler and GameCanvas (which contains
 * GameLoop) are connected here. Menu Bar is handled here.
 * Created by John Diggins on 4/21/17.
 * @author John Diggins
 *
 */
public class Game extends Application {

    public static int weaponSpeed = 22;
    
    /**
     * Window Width
     */
    public static final int WIDTH = 600;

    /**
     * Height of HUD
     */
    public static final int HUD = 30;

    /**
     * Height of MENU
     */
    public static final int MENU = 30;

    /**
     * Height of Game (in between MENU and HUD)
     */
    public static final int HEIGHT = 600;

    /**
     * Game Score
     */
    public static IntegerProperty score = new SimpleIntegerProperty(0);
    
    private HUD hud;
    private Player player;

    Handler handler = new Handler(WIDTH, HEIGHT);

    @Override
    public void start(Stage primaryStage) {
        Scene root;

        hud = new HUD(handler);

        UserMenu userMenu = new UserMenu(WIDTH, MENU, handler);

        GameCanvas gameCanvas = new GameCanvas(WIDTH, HEIGHT, handler);
        GridPane pane = new GridPane();

        pane.add(userMenu, 1, 1);
        pane.add(gameCanvas, 1, 2);

        pane.add(hud, 1, 3);
        root = new Scene(pane, WIDTH, HEIGHT + HUD + MENU);

        // Key Input
        KeyInput keyInput = new KeyInput(handler, root);
        root.addEventFilter(KeyEvent.ANY, keyInput);

        primaryStage.setTitle(".:StarRats:.");
        primaryStage.setScene(root);
        primaryStage.show();

        root.getRoot().requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
