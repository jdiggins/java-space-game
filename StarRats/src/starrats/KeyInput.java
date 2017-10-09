package starrats;
import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static starrats.GameLoop.weaponCount;


/**
 * KeyInput handles all of our key press actions.  
 * Created by John Diggins on 4/23/17.
 */
public class KeyInput implements EventHandler<KeyEvent> {
    private final Handler handler;
    private final Scene scene;
    boolean weaponFired = false;

    public KeyInput(Handler handler, Scene scene) {
        this.handler = handler;
        this.scene = scene;
    }

    @Override
    public void handle(KeyEvent event) {

        scene.setOnKeyPressed((KeyEvent ke) -> {
            // U - launch upgrade window
            if(ke.getCode() == KeyCode.U) {
                final Stage upgradeWindow = new Stage();
                upgradeWindow.initModality(Modality.APPLICATION_MODAL);

                UpgradePane upgradePane = new UpgradePane(handler);
                Scene dialogScene = new Scene(upgradePane, 500, 300);

                upgradeWindow.setScene(dialogScene);
                upgradeWindow.show();
                handler.gamePause();
                upgradeWindow.setOnCloseRequest(e -> {
                    if (Handler.intro == false) handler.gamePlay();
                });
            }
            // P - Play / Pause
            if(ke.getCode() == KeyCode.P) {
                if(Handler.paused == true) {
                    handler.gamePlay();
                }
                else {
                    handler.gamePause();
                }
            }
            // basic movement controls
            if (Handler.play == true) {
                if (ke.getCode() == KeyCode.SPACE && weaponCount > Game.weaponSpeed) {
                    handler.fireWeapon();
                    weaponFired = true;
                    weaponCount = 0;
                }
                for (int i = 0; i < handler.gameObjects.size(); i++) {
                    GameObject temp = handler.gameObjects.get(i);
                    if (temp instanceof Player) {
                        if (ke.getCode() == KeyCode.W) temp.setSpeedY(-3);
                        if (ke.getCode() == KeyCode.S) temp.setSpeedY(3);
                        if (ke.getCode() == KeyCode.D) temp.setSpeedX(3);
                        if (ke.getCode() == KeyCode.A) temp.setSpeedX(-3);
                    }
                }
            }
            // Intro Screen: move choice pointer and enter selects
            // Pressing up and down shifts the images to a "position"
            // This position is saved and used to calculate user choice
            if(Handler.intro == true) {
                if(Handler.pointerPosition < 3 && (ke.getCode() == KeyCode.S || ke.getCode() == KeyCode.DOWN))
                    handler.pointerDown();
                if(Handler.pointerPosition > 1 && (ke.getCode() == KeyCode.W || ke.getCode() == KeyCode.UP)) 
                    handler.pointerUp();
                if(ke.getCode() == KeyCode.ENTER || ke.getCode() == KeyCode.SPACE) {
                    if(Handler.pointerPosition == 1) {
                        handler.gamePlay();
                        Handler.gameStarted.set(true); // boolean for game started to load HUD items
                    }
                    if(Handler.pointerPosition == 2) {
                        try {
                            handler.loadGame();
                            handler.gamePlay();
                            Handler.gameStarted.set(true); // boolean for game started to load HUD items

                        } catch (IOException | ClassNotFoundException e) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error Loading Game");
                            alert.setHeaderText("Load Error");
                            alert.setContentText("We had an issue loading your game. \nPlease start a new game.");

                            alert.showAndWait();

                            System.out.println(e);
                        } 
                    }
                    if(Handler.pointerPosition == 3)
                        System.exit(0);

                }

            }



                }
        );

        // stop moving player when move keys released
        scene.setOnKeyReleased(ke -> {
            if (Handler.play == true) {
                if (ke.getCode() == KeyCode.SPACE) {
                    weaponFired = false;
                }
                for (int i = 0; i < handler.gameObjects.size(); i++) {
                    GameObject temp = handler.gameObjects.get(i);
                    if (temp.getID() == ID.Player) {
                        if (ke.getCode() == KeyCode.W) temp.setSpeedY(0);
                        if (ke.getCode() == KeyCode.S) temp.setSpeedY(0);
                        if (ke.getCode() == KeyCode.D) temp.setSpeedX(0);
                        if (ke.getCode() == KeyCode.A) temp.setSpeedX(0);
                    }
                }
            }



    }


        );

/*   My attemps at a "space" feel with key input.  Interferes with space button being pressed too much
        scene.setOnKeyPressed(ke -> {
                    for (int i = 0; i < handler.gameObjects.size(); i++) {
                        handler.gameObjects.get(i);
                        if (ke.getCode() == KeyCode.SPACE && weaponFired == false) {
                            fireWeapon();
                            weaponFired = true;
                        }
                        if (handler.gameObjects.get(i) instanceof Player) {
                            if (ke.getCode() == KeyCode.W) handler.gameObjects.get(i).thrustUpOn();
                            if (ke.getCode() == KeyCode.S) handler.gameObjects.get(i).thrustDownOn();
                            if (ke.getCode() == KeyCode.D) handler.gameObjects.get(i).thrustRightOn();
                            if (ke.getCode() == KeyCode.A) handler.gameObjects.get(i).thrustLeftOn();
                        }
                    }
                }
        );

        scene.setOnKeyReleased(ke -> {
                    if(ke.getCode() == KeyCode.SPACE){
                        weaponFired = false;
                    }
                    for (int i = 0; i < handler.gameObjects.size(); i++) {
                        GameObject temp = handler.gameObjects.get(i);
                        if (temp.getID() == ID.Player) {
                            if (ke.getCode() == KeyCode.W) temp.thrustYOff();
                            if (ke.getCode() == KeyCode.S) temp.thrustYOff();
                            if (ke.getCode()== KeyCode.D) temp.thrustXOff();
                            if (ke.getCode() == KeyCode.A) temp.thrustXOff();
                        }
                    }
                }
        );


        scene.setOnKeyReleased(ke -> {
                    for (int i = 0; i < handler.gameObjects.size(); i++) {
                        GameObject temp = handler.gameObjects.get(i);
                        if (temp.getID() == ID.Player) {
                            if (ke.getCode() == KeyCode.W) temp.setSpeedY(0);
                            if (ke.getCode() == KeyCode.S) temp.setSpeedY(0);
                            if (ke.getCode() == KeyCode.D) temp.setSpeedX(0);
                            if (ke.getCode() == KeyCode.A) temp.setSpeedX(0);
                        }
                    }
                }
        ); */
    }


}
