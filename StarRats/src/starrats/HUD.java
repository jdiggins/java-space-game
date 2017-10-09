package starrats;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 * Panel to display player information: Score, health, shield, and update
 * screen button.
 * Created by John on 4/28/17.
 */
public final class HUD extends GridPane {
    private Player player;

    Handler handler;

    public HUD(Handler handler) {

        this.handler = handler;

        setPadding(new Insets(0,10,0,10)); // top, right, bottom, left
        setHgap(5);

        
        setMinHeight(Game.HUD);
        setMinWidth(Game.WIDTH);
        setMaxHeight(Game.HUD);
        setMaxWidth(Game.WIDTH);
        setStyle("-fx-background-color: #3b3b3a");

        // Wait for game start to add Score and Health bar
        Handler.gameStarted.addListener(ob-> {
            if(Handler.gameStarted.get() == true) {
                player = handler.getPlayer();

                /*********************
                 *       SCORE       *
                 *********************/
                Text scoreText = new Text();
                scoreText.setFill(Color.WHITE);
                scoreText.textProperty().bind(Bindings.createStringBinding(()
                        -> "Score: " + Game.score.get(), Game.score));




                /*********************
                 *    HEALTH BAR     *
                 *********************/
                Rectangle whiteBox = new Rectangle( 100, 10); // width, height
                whiteBox.setFill(Color.WHITE);
                whiteBox.setStroke(Color.WHITE);
                Rectangle redBox = new Rectangle ( player.getHealth(), 10); // width, height
                redBox.setStroke(Color.WHITE);
                redBox.setFill(Color.RED);
                redBox.widthProperty().bind(player.health);
                Text healthLabel = new Text("Health: ");
                healthLabel.setFill(Color.WHITE);

                /*********************
                 *    SHIELD BAR     *
                 *********************/
                Rectangle whiteBox2 = new Rectangle( 100, 10); // width, height
                whiteBox2.setFill(Color.WHITE);
                whiteBox2.setStroke(Color.WHITE);
                Rectangle blueBox = new Rectangle ( player.getShield(), 10); // width, height
                blueBox.setStroke(Color.WHITE);
                blueBox.setFill(Color.BLUE);
                blueBox.widthProperty().bind(player.shield);
                Text shieldLabel = new Text("Shield: ");
                shieldLabel.setFill(Color.WHITE);

                StackPane healthBar = new StackPane();
                healthBar.getChildren().addAll(whiteBox, redBox);

                StackPane shieldBar = new StackPane();
                shieldBar.getChildren().addAll(whiteBox2, blueBox);
                GridPane playerStats = new GridPane();
                playerStats.add(healthLabel,0, 0);
                playerStats.add(whiteBox,1, 0);
                playerStats.add(redBox,1, 0);
                playerStats.add(shieldLabel,2, 0);
                playerStats.add(whiteBox2,3, 0);
                playerStats.add(blueBox,3, 0);
                playerStats.add(scoreText, 4, 0);
                playerStats.setAlignment(Pos.CENTER_RIGHT);
                playerStats.setHgap(10);

                this.add(playerStats, 1, 0); // column, row

            }
        });



        /*************************
         *  Player Option Window *
         *************************/

        Button btn = new Button();
        btn.setText("Upgrades");
        btn.setTextFill(Color.WHITE);
        btn.setFocusTraversable(false);
        btn.setStyle( "    -fx-background-color: rgba(98,98,98,0.7);\n" +
                "    -fx-background-radius: 15 15 15 15;\n" +
                "    -fx-background-insets: 2 2 2 2 ;");
        btn.setOnMouseEntered(e->btn.setStyle(" -fx-background-color: rgba(119,119,119,0.7);" +
                "    -fx-background-radius: 15 15 15 15;\n" +
                "    -fx-background-insets: 2 2 2 2 ;"));
        btn.setOnMouseExited(e->btn.setStyle( "-fx-background-color: rgba(98,98,98,0.7);" +
                "    -fx-background-radius: 15 15 15 15;\n" +
                "    -fx-background-insets: 2 2 2 2 ;"));

        btn.setOnAction(a-> {
                        final Stage upgradeWindow = new Stage();
                        upgradeWindow.initModality(Modality.APPLICATION_MODAL);
                        //dialog.initOwner();
                        UpgradePane upgradePane = new UpgradePane(handler);
                        Scene dialogScene = new Scene(upgradePane, 500, 300);

                        upgradeWindow.setScene(dialogScene);
                        upgradeWindow.show();
                        handler.gamePause();
                        upgradeWindow.setOnCloseRequest(e-> {if(handler.intro == false) handler.gamePlay();});

                });



        btn.setAlignment(Pos.CENTER_RIGHT);

        this.add(btn, 0, 0);






    }




}
