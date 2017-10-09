package starrats;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Top Menu Bar includes About, Save, Load, Quit
 * @author John Diggins
 */
public class UserMenu extends MenuBar{
    Handler handler;
    Player player;

    public UserMenu(int width, int height, Handler handler) {
        
   
        
        setMinWidth(width);
        setMinHeight(height);
        this.handler = handler;
        Menu menuFile = new Menu("File");
        MenuItem about = new MenuItem("About");
        about.setOnAction(a -> {
            final Stage aboutWindow = new Stage();
            aboutWindow.initModality(Modality.WINDOW_MODAL);
            VBox textBox = new VBox(20);
            textBox.getChildren().add(new Text(
                    "2074: Scientists open a black hole on Earth.\n"
                    + "Your job is to help clear a path for passenger ships\n"
                    + "as space debris flies towards Earth.\n"
                    + "Focus your efforts on the large astroids to gain points.\n\n"
                    + "Points: \n"
                    + "\tSmall Astroids: +5 destroyed, -1 per lost astroid\n"
                    + "\tLarge Astroids: +600 destroyed\n\n"
                    + "Controls:\n"
                    + "\tW: up | S: down | A: left | D: right\n"
                    + "\tSPACE: shoot | P: Pause | U: Upgrades\n\n")
            );

            Scene textScene = new Scene(textBox, 500, 300);

            aboutWindow.setScene(textScene);
            aboutWindow.show();
            handler.gamePause();
            aboutWindow.setOnCloseRequest(e -> {
                if (Handler.intro == false) {
                    handler.gamePlay();
                }
            });

        });
        MenuItem loadGame = new MenuItem("Load Game");
        loadGame.setOnAction((ActionEvent a) -> {
            try {
                Handler.gameStarted.set(false);
                handler.loadGame();
                Handler.gameStarted.set(true);
                Handler.endGame = false;
                handler.gamePlay();

            } catch (IOException | ClassNotFoundException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Loading Game");
                alert.setHeaderText("Load Error");
                alert.setContentText("We had an issue loading your game.");

                alert.showAndWait();
            }
            finally {
                if(Handler.intro == false)
                    Handler.gameStarted.set(true);
            }
        });
        MenuItem quit = new MenuItem("Quit");
        quit.setOnAction(e -> System.exit(0));
        MenuItem save = new MenuItem("Save");
        save.setOnAction(a -> {
            try {
                handler.saveGame();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Saving Game");
                alert.setHeaderText("Save Error");
                alert.setContentText("We had an issue saving your game.");

                alert.showAndWait();

                System.out.println(e);
            }
        });
        menuFile.getItems().addAll(about, loadGame, save, quit);
        getMenus().add(menuFile);

        Handler.gameStarted.addListener(ov -> {
            if (Handler.gameStarted.get() == true) {
                player = handler.getPlayer();
                // make sure player is still alive
                player.alive.addListener(ov2 -> {
                    if (player.alive.get() == false) {
                        handler.endGame();
                    }
                });
            }

        });
    }
}
