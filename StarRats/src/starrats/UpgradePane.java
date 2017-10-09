package starrats;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

/**
 * Upgrade pane that loads in new window to update color and buy upgrades.
 * Created by John Diggins on 5/8/17.
 */
public class UpgradePane extends BorderPane {

    Handler handler;
    int pointsNeeded;
    int holdColor;

    public UpgradePane(Handler handler) {
        this.handler = handler;
        Player player = handler.getPlayer();

        /**
         * ***************************
         * Player Color Control
         */
        Color newColor;
        // Create rectangle for displaying all RGB
        Rectangle colorBox = new Rectangle(100, 100);
        colorBox.setStroke(Color.BLACK);
        colorBox.setFill(Color.rgb(127, 127, 127));

        // Create rectangle for displaying RED slider color
        Rectangle redBox = new Rectangle(150, 10);
        redBox.setStroke(Color.BLACK);
        holdColor = (int) (player.playerColor.getRed() * 255);
        redBox.setFill(Color.rgb(holdColor, 0, 0));

        // Slider for red color
        Slider redSlider = new Slider(0, 255, holdColor);
        redSlider.setMajorTickUnit(255);
        redSlider.setShowTickLabels(true);

        // Create rectangle for displaying GREEN slider color
        Rectangle greenBox = new Rectangle(150, 10);
        greenBox.setStroke(Color.BLACK);
        holdColor = (int) (player.playerColor.getGreen() * 255);
        greenBox.setFill(Color.rgb(0, holdColor, 0));

        // Slider for green color
        Slider greenSlider = new Slider(0, 255, holdColor);
        greenSlider.setMajorTickUnit(255);
        greenSlider.setShowTickLabels(true);

        // Create rectangle for displaying BLUE slider color
        Rectangle blueBox = new Rectangle(150, 10);
        blueBox.setStroke(Color.BLACK);
        holdColor = (int) (player.playerColor.getBlue() * 255);
        blueBox.setFill(Color.rgb(0, 0, holdColor));

        // Slider for blue color
        Slider blueSlider = new Slider(0, 255, holdColor);
        blueSlider.setMajorTickUnit(255);
        blueSlider.setShowTickLabels(true);

        // Update Player Color.
        // Listen for red slider movement
        redSlider.valueProperty().addListener(ov -> {
            redBox.setFill(Color.rgb((int) redSlider.getValue(), 0, 0));
            colorBox.setFill(Color.rgb((int) redSlider.getValue(),
                    (int) greenSlider.getValue(), (int) blueSlider.getValue()));
            player.setColor(Color.rgb((int) redSlider.getValue(), (int) greenSlider.getValue(), (int) blueSlider.getValue()));

        });

        // Listen for green slider movement
        greenSlider.valueProperty().addListener(ov -> {
            greenBox.setFill(Color.rgb(0, (int) greenSlider.getValue(), 0));
            colorBox.setFill(Color.rgb((int) redSlider.getValue(),
                    (int) greenSlider.getValue(), (int) blueSlider.getValue()));
            player.setColor(Color.rgb((int) redSlider.getValue(), (int) greenSlider.getValue(), (int) blueSlider.getValue()));

        });

        // Listen for blue slider movement
        blueSlider.valueProperty().addListener(ov -> {
            blueBox.setFill(Color.rgb(0, 0, (int) blueSlider.getValue()));
            colorBox.setFill(Color.rgb((int) redSlider.getValue(),
                    (int) greenSlider.getValue(), (int) blueSlider.getValue()));
            player.setColor(Color.rgb((int) redSlider.getValue(), (int) greenSlider.getValue(), (int) blueSlider.getValue()));

        });

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        pane.setHgap(5);
        pane.setVgap(5);

        Label title = new Label("Edit Player Color:");
        title.setTextFill(Color.WHITE);

        Label red = new Label("Green");
        red.setTextFill(Color.WHITE);
        redSlider.setStyle("fx-text-fill: white;");
        Label green = new Label("Green");
        green.setTextFill(Color.WHITE);
        Label blue = new Label("Blue");
        blue.setTextFill(Color.WHITE);
        pane.add(title, 0, 0);
        pane.add(red, 0, 1);
        pane.add(redBox, 1, 1);
        pane.add(redSlider, 1, 2);
        pane.add(green, 0, 3);
        pane.add(greenBox, 1, 3);
        pane.add(greenSlider, 1, 4);
        pane.add(blue, 0, 5);
        pane.add(blueBox, 1, 5);
        pane.add(blueSlider, 1, 6);

        StackPane pane2 = new StackPane();

        Canvas canvas = new Canvas(75, 75);
        canvas.setStyle("-fx-border-color: black");

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                player.renderModel(canvas.getGraphicsContext2D());

            }
        }.start();

        pane2.getChildren().add(canvas);
        HBox editPlayerBox = new HBox();
        editPlayerBox.getChildren().add(pane);
        editPlayerBox.getChildren().add(pane2);
        editPlayerBox.setAlignment(Pos.CENTER);

        /**
         * ***************************
         * Top Banner
         */
        // button press actions;
        /* TOP BANNER */
        HBox banner = new HBox();

        // player option button
        Button playerOption = new Button();

        playerOption.setText("Edit Player");
        playerOption.setTextFill(Color.WHITE);
        playerOption.setFocusTraversable(false);
        playerOption.setStyle("    -fx-background-color: rgba(98,98,98,0.7);\n"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;");
        playerOption.setOnMouseEntered(e -> playerOption.setStyle(" -fx-background-color: rgba(119,119,119,0.7);"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;"));
        playerOption.setOnMouseExited(e -> playerOption.setStyle("-fx-background-color: rgba(98,98,98,0.7);"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;"));

        /**
         * ***************************
         * Upgrade Player Options
         */
        /**
         * ***************************
         * Weapon Upgrade
         */
        Button upgradeWeaponSpeed;

        switch (Game.weaponSpeed) {
            case 22:
                upgradeWeaponSpeed = new Button("Upgrade Weapon\n    500 Points");
                break;
            case 19:
                upgradeWeaponSpeed = new Button("Upgrade Weapon\n    1000 Points");
                break;
            case 16:
                upgradeWeaponSpeed = new Button("Upgrade Weapon\n    1500 Points");
                break;
            case 13:
                upgradeWeaponSpeed = new Button("Upgrade Weapon\n    2000 Points");
                break;
            case 10:
                upgradeWeaponSpeed = new Button("Upgrade Weapon\n    2500 Points");
                break;
            default:
                upgradeWeaponSpeed = new Button("Upgrade Weapon\n    All Purchased");
                break;
        }

        upgradeWeaponSpeed.setTextAlignment(TextAlignment.CENTER);
        upgradeWeaponSpeed.setMinWidth(175);
        upgradeWeaponSpeed.setTextFill(Color.WHITE);
        upgradeWeaponSpeed.setFocusTraversable(false);
        upgradeWeaponSpeed.setStyle("    -fx-background-color: rgba(98,98,98,0.7);\n"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;");
        upgradeWeaponSpeed.setOnMouseEntered(e -> {

            switch (Game.weaponSpeed) {
                case 22:
                    pointsNeeded = 500;
                    break;
                case 19:
                    pointsNeeded = 1000;
                    break;
                case 16:
                    pointsNeeded = 1500;
                    break;
                case 13:
                    pointsNeeded = 2000;
                    break;
                case 10:
                    pointsNeeded = 2500;
                    break;
                default:
                    pointsNeeded = -1;
                    break;
            }
            if (Game.score.get() >= pointsNeeded && pointsNeeded > 0) {
                upgradeWeaponSpeed.setStyle(" -fx-background-color: rgba(119,119,119,0.7);"
                        + "    -fx-background-radius: 15 15 15 15;\n"
                        + "    -fx-background-insets: 2 2 2 2 ;");
            }
        });

        upgradeWeaponSpeed.setOnMouseExited(e -> upgradeWeaponSpeed.setStyle("-fx-background-color: rgba(98,98,98,0.7);"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;"));
        upgradeWeaponSpeed.setOnMousePressed(e -> {
            switch (Game.weaponSpeed) {
                case 22:
                    pointsNeeded = 500;
                    break;
                case 19:
                    pointsNeeded = 1000;
                    break;
                case 16:
                    pointsNeeded = 1500;
                    break;
                case 13:
                    pointsNeeded = 2000;
                    break;
                case 10:
                    pointsNeeded = 2500;
                    break;
                default:
                    pointsNeeded = -1;
                    break;
            }
            if (Game.score.get() >= pointsNeeded && pointsNeeded > 0) {
                Game.score.set(Game.score.get() - pointsNeeded);
                if (pointsNeeded == 2500) {
                    upgradeWeaponSpeed.setText("Upgrade Weapon\n     Purchased   ");
                } else {
                    pointsNeeded += 500;
                    upgradeWeaponSpeed.setText("Upgrade Weapon\n    " + pointsNeeded + " Points");
                }

                Game.weaponSpeed -= 3;
            }
        });

        /**
         * ***************************
         * Shield Upgrade
         */
        Button upgradeShield;
        if (player.getShieldStrength() != 1) {
            upgradeShield = new Button("Shield Strength\n   Max Purchase  ");

        } else {
            upgradeShield = new Button("Shield Strength\n  500 points  ");
        }
        upgradeShield.setTextAlignment(TextAlignment.CENTER);
        upgradeShield.setMinWidth(175);
        upgradeShield.setAlignment(Pos.CENTER);

        upgradeShield.setTextFill(Color.WHITE);
        upgradeShield.setFocusTraversable(false);
        // Set buttons style
        upgradeShield.setStyle("    -fx-background-color: rgba(98,98,98,0.7);\n"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;");
        // Change style for mouse over
        upgradeShield.setOnMouseEntered(e -> {
            // If user has enough points
            if (Game.score.get() > 2 && player.getShieldStrength() == 1) {
                // Make button look useable
                upgradeShield.setStyle(
                        " -fx-background-color: rgba(119,119,119,0.7);"
                        + "-fx-background-radius: 15 15 15 15;\n"
                        + "-fx-background-insets: 2 2 2 2 ;");

            }
        });
        // Change style back for mouse exit
        upgradeShield.setOnMouseExited(e -> upgradeShield.setStyle(
                "-fx-background-color: rgba(98,98,98,0.7);"
                + "-fx-background-radius: 15 15 15 15;\n"
                + "-fx-background-insets: 2 2 2 2 ;"));
        // Set mouse press action
        upgradeShield.setOnMousePressed(e -> {
            // if user has enough points
            if (Game.score.get() > 500 && player.getShieldStrength() == 1) {

                upgradeShield.setText("Shield Strength\n   Purchased   ");
                player.setShieldStrength(player.getShieldStrength() + 1);
                Game.score.set(Game.score.get() - 500);

            }

        });

        /**
         * ***************************
         * Shield Regeneration Upgrade
         */
        Button shieldRegen;
        if (player.getShieldRegenSpeed() == .01) {
            shieldRegen = new Button("Shield Regeneration\n    500 points");
        } else if (player.getShieldRegenSpeed() == .02) {
            shieldRegen = new Button("Shield Regeneration\n    1000 points");
        } else if (player.getShieldRegenSpeed() == .03) {
            shieldRegen = new Button("Shield Regeneration\n    1500 points");
        } else if (player.getShieldRegenSpeed() == .04) {
            shieldRegen = new Button("Shield Regeneration\n    2000 points");
        } else if (player.getShieldRegenSpeed() == .05) {
            shieldRegen = new Button("Shield Regeneration\n    2500 points");
        } else if (player.getShieldRegenSpeed() == .06) {
            shieldRegen = new Button("Shield Regeneration\n    3000 points");
        } else if (player.getShieldRegenSpeed() == .07) {
            shieldRegen = new Button("Shield Regeneration\n    3500 points");
        } else if (player.getShieldRegenSpeed() == .08) {
            shieldRegen = new Button("Shield Regeneration\n    4000 points");
        } else if (player.getShieldRegenSpeed() == .09) {
            shieldRegen = new Button("Shield Regeneration\n    4500 points");
        } else if (player.getShieldRegenSpeed() == .1) {
            shieldRegen = new Button("Shield Regeneration\n    5000 points");
        } else {
            shieldRegen = new Button("Shield Regeneration\n    All Purchased");
        }
        shieldRegen.setMinWidth(175);
        shieldRegen.setTextAlignment(TextAlignment.CENTER);
        shieldRegen.setTextFill(Color.WHITE);
        shieldRegen.setFocusTraversable(false);

        shieldRegen.setStyle("    -fx-background-color: rgba(98,98,98,0.7);\n"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;");
        shieldRegen.setOnMouseEntered(e -> {
            if (player.getShieldRegenSpeed() == .01) {
                pointsNeeded = 500;
            } else if (player.getShieldRegenSpeed() == .02) {
                pointsNeeded = 1000;
            } else if (player.getShieldRegenSpeed() == .03) {
                pointsNeeded = 1500;
            } else if (player.getShieldRegenSpeed() == .04) {
                pointsNeeded = 2000;
            } else if (player.getShieldRegenSpeed() == .05) {
                pointsNeeded = 2500;
            } else if (player.getShieldRegenSpeed() == .06) {
                pointsNeeded = 3000;
            } else if (player.getShieldRegenSpeed() == .07) {
                pointsNeeded = 3500;
            } else if (player.getShieldRegenSpeed() == .08) {
                pointsNeeded = 4000;
            } else if (player.getShieldRegenSpeed() == .09) {
                pointsNeeded = 4500;
            } else if (player.getShieldRegenSpeed() == .1) {
                pointsNeeded = 5000;
            } else {
                pointsNeeded = -1;
            }
            if (Game.score.get() >= pointsNeeded && pointsNeeded > 0) {
                shieldRegen.setStyle(" -fx-background-color: rgba(119,119,119,0.7);"
                        + "    -fx-background-radius: 15 15 15 15;\n"
                        + "    -fx-background-insets: 2 2 2 2 ;");
            }

        });
        shieldRegen.setOnMouseExited(e -> shieldRegen.setStyle("-fx-background-color: rgba(98,98,98,0.7);"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;"));
        // Set mouse press action
        shieldRegen.setOnMousePressed(e -> {
            // if user has enough points

            if (player.getShieldRegenSpeed() == .01) {
                pointsNeeded = 500;
            } else if (player.getShieldRegenSpeed() == .02) {
                pointsNeeded = 1000;
            } else if (player.getShieldRegenSpeed() == .03) {
                pointsNeeded = 1500;
            } else if (player.getShieldRegenSpeed() == .04) {
                pointsNeeded = 2000;
            } else if (player.getShieldRegenSpeed() == .05) {
                pointsNeeded = 2500;
            } else if (player.getShieldRegenSpeed() == .06) {
                pointsNeeded = 3000;
            } else if (player.getShieldRegenSpeed() == .07) {
                pointsNeeded = 3500;
            } else if (player.getShieldRegenSpeed() == .08) {
                pointsNeeded = 4000;
            } else if (player.getShieldRegenSpeed() == .09) {
                pointsNeeded = 4500;
            } else if (player.getShieldRegenSpeed() == .1) {
                pointsNeeded = 5000;
            } else {
                pointsNeeded = -1;
            }
            if (Game.score.get() >= pointsNeeded && pointsNeeded > 0) {
                Game.score.set(Game.score.get() - pointsNeeded);
                if (pointsNeeded == 2500) {
                    shieldRegen.setText("Shield Regeneration\n    All Purchased");
                } else {
                    pointsNeeded += 500;
                    shieldRegen.setText("Shield Regeneration\n    " + (pointsNeeded) + " points");
                }
                player.setShieldRegenSpeed(player.getShieldRegenSpeed() + .01);

            }

        });

        /**
         * ***************************
         * Refill Health
         */
        Button refillHealth;
        if(player.getHealth() < 100)
            refillHealth = new Button("Refill Health\n350 points");
        else
            refillHealth = new Button("Refill Health\nHealth Full");
        refillHealth.setTextAlignment(TextAlignment.CENTER);
        refillHealth.setMinWidth(175);
        refillHealth.setTextFill(Color.WHITE);
        refillHealth.setFocusTraversable(false);

        refillHealth.setStyle("    -fx-background-color: rgba(98,98,98,0.7);\n"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;");
        refillHealth.setOnMouseEntered(e -> {
            if (Game.score.get() >= 350 && player.getHealth() < 100) {
                refillHealth.setStyle(" -fx-background-color: rgba(119,119,119,0.7);"
                        + "    -fx-background-radius: 15 15 15 15;\n"
                        + "    -fx-background-insets: 2 2 2 2 ;");
            }

        });
        refillHealth.setOnMouseExited(e -> refillHealth.setStyle("-fx-background-color: rgba(98,98,98,0.7);"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;"));
        // Set mouse press action
        refillHealth.setOnMousePressed(e -> {
            // if user has enough points
            if (Game.score.get() >= 350 && player.getHealth() < 100) {
                refillHealth.setText("Refill Health\nHealth Full");
                player.setHealth(100);
                Game.score.set(Game.score.get() - 350);

            }

        });

        GridPane upgradeGrid = new GridPane();
        upgradeGrid.setHgap(30);
        upgradeGrid.setVgap(30);
        upgradeGrid.add(upgradeWeaponSpeed, 0, 0);
        upgradeGrid.add(upgradeShield, 1, 0);
        upgradeGrid.add(shieldRegen, 0, 1);
        upgradeGrid.add(refillHealth, 1, 1);
        upgradeGrid.setAlignment(Pos.CENTER);

        // Our Banner
        Button upgradeOption = new Button();
        upgradeOption.setText("Buy Upgrades");
        upgradeOption.setTextFill(Color.WHITE);
        upgradeOption.setFocusTraversable(false);
        upgradeOption.setStyle("    -fx-background-color: rgba(98,98,98,0.7);\n"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;");
        upgradeOption.setOnMouseEntered(e -> upgradeOption.setStyle(" -fx-background-color: rgba(119,119,119,0.7);"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;"));
        upgradeOption.setOnMouseExited(e -> upgradeOption.setStyle("-fx-background-color: rgba(98,98,98,0.7);"
                + "    -fx-background-radius: 15 15 15 15;\n"
                + "    -fx-background-insets: 2 2 2 2 ;"));

        banner.getChildren().addAll(playerOption, upgradeOption);
        // set screen to edit player
        playerOption.setOnMouseClicked(e -> {
            setCenter(editPlayerBox);
        });
        // set screen to upgrade player
        upgradeOption.setOnMouseClicked(e -> {
            setCenter(upgradeGrid);
        });

        setCenter(editPlayerBox);
        setTop(banner);
        setStyle("-fx-background-color: Black;" + "-fx-text-fill: white;");
    }

}
