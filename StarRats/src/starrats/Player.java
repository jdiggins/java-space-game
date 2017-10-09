package starrats;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.Serializable;

/**
 * User controlled GameObject Player. 
 * Created by John on 4/23/17.
 */
public class Player extends GameObject implements Serializable{

    private static final long serialVersionUID = 7601705907794504910L;


    private int count = 0;
    private double red = 0;
    private double blue = 0;
    private double green = 0;
    private double saveHealth = 0;
    private double saveShield = 0;
    private int saveWeaponSpeed = 0;

    public double shieldStrength = 1; // can be set 1 - 3
    // used for saving game score in binary file
    private int saveScore = 0;
    private boolean thrustY = false;
    private boolean thrustX = false;

    private double shieldRegenSpeed = .01;

    /**
     * health value
     */
    public transient DoubleProperty health = new SimpleDoubleProperty(100); // starting health

    /**
     * shield value
     */
    public transient DoubleProperty shield = new SimpleDoubleProperty(100); // our sheild

    /**
     * adjustable color
     */
    public transient Color playerColor;

    /**
     * player life status
     */
    public transient BooleanProperty alive = new SimpleBooleanProperty(true);
    private final double modelX = 25;
    private final double modelY = 25;

    /**
     *
     * @param x
     * @param y
     * @param id
     */
    public Player(double x, double y, ID id) {
        super(x, y, id);
    }

    /**
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @param id
     * @param maxX
     * @param maxY
     */
    public Player(double x, double y, double width, double height, ID id, double maxX, double maxY) {
        super(x, y, width, height, id, maxX, maxY);
        playerColor = new Color( numberGen.getRandDouble(),  numberGen.getRandDouble(), numberGen.getRandDouble(), 1);

    }

    /**
     * Update player state change (movement, health, shield)
     */
    @Override
    public void update() {
        // add movement
        x += speedX;
        y += speedY;
        checkBounds();
        /* This was for "reasistic" movement that I took out
        count++;
        if (count == 360) {
            count = 0;
        }
        if(thrustX == false) {
            if (count % 15 == 0){
                if(speedX < 0)
                    speedX += .25;
                else if(speedX > 0)
                    speedX -= .25;
            }
        }
        if(thrustY == false) {
            if (count % 15 == 0){
                if(speedY < 0)
                    speedY += .25;
                else if(speedY > 0)
                    speedY -= .25;
            }
        }*/
        // check if alive
        if(health.get() < 0 ) {
            alive.set(false);
        }

        // regenerate our peed based on a value
        if(shield.get() <= 100) {
            shield.set(shield.get() + shieldRegenSpeed);
            // if shield goes to 0, jumpstart generation
            if(shield.get() <= 0) {
                shield.set(0 + shieldRegenSpeed);
            }
        }
    }

    /**
     * Update drawing of player
     * @param gc
     */
    @Override
    public void render(GraphicsContext gc) {

        gc.setFill(playerColor);
        gc.setEffect(new Bloom());
        gc.fillOval(x, y, width, height);
        gc.setEffect(null);

        gc.setFill(Color.GRAY);
        gc.setStroke(playerColor.darker());

        gc.fillOval(x + 3, y + 3, width - 6, height - 6);


        gc.setFill(Color.BEIGE);
        gc.setFont(new Font(30));
        gc.fillText("^", (x + 8.5), (y + 18));
        gc.setFont(new Font(25));
        gc.fillText("◊", (x + 8.5), (y + 25));

    }

    /**
     * Draws our character for color change menu
     * @param gc
     */
    public void renderModel(GraphicsContext gc) {

        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,75,75);

        gc.setFill(playerColor);
        gc.setEffect(new Bloom());
        gc.fillOval(modelX, modelY, width, height);
        gc.setEffect(null);

        gc.setFill(Color.GRAY);
        gc.setStroke(playerColor.darker());

        gc.fillOval(modelX + 3, modelY + 3, width - 6, height - 6);


        gc.setFill(Color.BEIGE);
        gc.setFont(new Font(30));
        gc.fillText("^", (modelX + 8.5), (modelY + 18));
        gc.setFont(new Font(25));
        gc.fillText("◊", (modelX + 8.5), (modelY + 25));

    }

    public void setHealth(double health) {
        this.health.setValue(health);
    }

    public double getHealth() { return health.get(); }

    public void setShield(double shield) {
        this.shield.setValue(shield);
    }

    public double getShield() { return shield.get(); }

    public void setShieldStrength(double shieldStrength) {
        this.shieldStrength = shieldStrength;
    }

    public double getShieldStrength() { return shieldStrength; }

    public void setColor(Color color) {
        this.playerColor = color;
    }

    public Color getColor() { return playerColor; }

    /**
     *  Make sure player is inside bounds
     */
    public void checkBounds() {

        if (y < 0) {
            y = 0;
            speedY = 0;
        }
        if (x < 0) {
            x = 0;
            speedX = 0;
        }
        if (x > maxX - width) {
            x = maxX - width;
            speedX = 0;
        }

        if (y > maxY - height) {
            y = maxY - height;
            speedY = 0;
        }
    }


    public void thrustUpOn() {
        thrustY = true;
        if(speedY > -4)
            speedY -= 1;

    }

    public void thrustDownOn() {
        thrustY = true;
        if(speedY < 4)
            speedY += 1;

    }

    /**
     *
     */
    //@Override
    public void thrustLeftOn() {
        thrustX = true;
        if(speedX > -4)
            speedX -= 1;

    }

    public void thrustRightOn() {
        thrustX = true;
        if(speedX < 4)
            speedX += 1;

    }

    
    /**
     * turn color into double values for binary saving
     */
    public void saveColors() {
        red = playerColor.getRed();
        green = playerColor.getGreen();
        blue = playerColor.getBlue();

    }

    /**
     * turn shield value into save-able double
     */
    public void setSaveShield() {
        saveShield = shield.get();
    }

    /**
     * load player color from saved doubles
     */
    public void resetPlayerColor() {
        playerColor = new Color(red, green, blue, 1);

    }

    /**
     * set health into save-able double
     */
    public void setSaveHealth() {
        saveHealth = health.get();

    }

    /**
     * load health from save-able double, make sure player is alive
     */
    public void loadHealth() {
        health = new SimpleDoubleProperty(saveHealth);
        if(saveHealth > 0)
            alive = new SimpleBooleanProperty(true);
        else
            alive = new SimpleBooleanProperty(false);
    }

    /**
     * load shield from save-able double
     */
    public void loadShield() {
        shield = new SimpleDoubleProperty(saveShield);
    }

    /**
     * get score to save 
     */
    public void setSaveScore() {
        saveScore = Game.score.get();
    }

    /**
     * reset score from saved file
     */
    public void loadScore() {
        Game.score.set(saveScore);
    }

    public void thrustYOff() {
        thrustY = false;
    }

    public void thrustXOff() {
        thrustX = false;
    }

    public void setShieldRegenSpeed(double shieldRegenSpeed) {
        this.shieldRegenSpeed = shieldRegenSpeed;
    }

    public double getShieldRegenSpeed() { return shieldRegenSpeed; }

    public void setWeaponSpeed(int weaponSpeed) {
        this.saveWeaponSpeed = weaponSpeed;
    }
    
    public int loadWeaponSpeed() { return saveWeaponSpeed; }
}


