package starrats;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Handler contains two linked lists to hold our game objects (active list and
 * inactive list). The handler allows other classes to easily have access to all
 * the game pieces. It also detects collision, updates game object states and
 * updates the rendering. Game state changes (intro, pause, play) are handled
 * here as well
 * Created by John Diggins on 4/22/17.
 * @author John Diggins
 *
 */
public final class Handler {

    // main menu screen
    Image introImage = new Image("file:src/images/StarRatsMenu.gif");
    // main menu selector image
    Image pointer = new Image("file:src/images/point.png");

    // placement for main menu selector image ^^
    private int pointerX = 190;
    private int pointerY = 295;
    private boolean drawPauseBackground = false;
    private final File file = new File("gamesave.dat");

    protected int sceneWidth;
    protected int sceneHeight;
    public static int pointerPosition = 1;
    public static boolean play;
    public static boolean paused;
    public static boolean intro = true;
    public static boolean endGame = false;
    public static BooleanProperty gameStarted = new SimpleBooleanProperty(false);
    public LinkedList<GameObject> gameObjects = new LinkedList<>();
    public LinkedList<GameObject> scenery = new LinkedList<>();
    
    public Handler() {
    }


    public Handler(int sceneWidth, int sceneHeight) {
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        // add scenery first
        for (int i = 0; i < 200; i++) {
            double tempWidth = Math.random() * 4;
            addScenery(new Stars((int) (Math.random() * sceneWidth), (int) (Math.random() * sceneHeight), tempWidth, tempWidth, ID.Scenery));
        }

        // add our game pieces
        addObject(new Player(600 / 2 - 32, 600 / 2 - 32, 32, 32, ID.Player, sceneWidth, sceneHeight));
    }

    // Update our game objects

    public void update() {
        if (intro) {

        }
        if (play) {
            // ADD OUR ENEMIES

            // ADD BIG ENEMIES SLOWLY
            if (GameLoop.count % 2000 == 0) {
                addObject(new BigEnemy(ID.Enemy, 45, 65, sceneWidth, sceneHeight));
            }

            // ADD LOTS OF SMALL EMEMIES
            if (GameLoop.count % 4 == 0) {
                addObject(new SmallEnemy(ID.Enemy, 12, 24, sceneWidth, sceneHeight));

            }
            for (int i = 0; i < scenery.size(); i++) {
                GameObject temp = scenery.get(i);
                temp.update();
            }
            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject temp = gameObjects.get(i);
                // if gameObjects is scheduled to be deleted, then delete it
                if (temp.deleteObject == true) {
                    removeObject(temp);
                } // else update our item
                else {
                    temp.update();
                }
            }

            detectWeaponCollision();

            detectPlayerCollision();
        }
        if (paused) {

        }

    }

    /**
     *
     * @param gc
     */
    public void render(GraphicsContext gc) {
        if (intro) {
            gc.drawImage(introImage, 0, 0);
            gc.drawImage(pointer, pointerX, pointerY);
        }
        if (play) {
            // paint our background
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, sceneWidth, sceneHeight);
            // draw our scenery first
            for (int i = 0; i < scenery.size(); i++) {
                GameObject temp = scenery.get(i);
                temp.render(gc);
            }
            // draw our player / enemies next
            for (int i = 0; i < gameObjects.size(); i++) {
                GameObject temp = gameObjects.get(i);
                temp.render(gc);
            }
        }
        if (paused) {
            if (!drawPauseBackground) {
                drawPauseBackground = true;
                gc.setFill(Color.color(1, 0.9922, 0.9922, 0.2706));
                gc.fillRect(0, 0, 600, 600);

                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Arial Black", 30));
                gc.fillText("PAUSED", 225, 300);
            }

        }
        if (endGame) {
            if (!drawPauseBackground) {
                drawPauseBackground = true;
                gc.setFill(Color.color(1, 0.9922, 0.9922, 0.2706));
                gc.fillRect(0, 0, 600, 600);

                gc.setFill(Color.WHITE);
                gc.setFont(new Font("Arial Black", 30));
                gc.fillText("Game Over", 220, 300);
            }

        }

    }

    // Detects enemies hitting player
    /**
     *
     */
    public void detectPlayerCollision() {
        for (int player = 0; player < gameObjects.size(); player++) {
            if (gameObjects.get(player) instanceof Player) {
                Player tempPlayer = (Player) gameObjects.get(player);
                // int j is for Enemy
                for (int enemy = 0; enemy < gameObjects.size(); enemy++) {

                    if (gameObjects.get(enemy) instanceof Enemy) {
                        Enemy tempEnemy = (Enemy) gameObjects.get(enemy);
                        // if our gameObjects's intersect in any way
                        if (gameObjects.get(player).getX() + gameObjects.get(player).getWidth() > gameObjects.get(enemy).getX()
                                && (gameObjects.get(player).getX()) < (gameObjects.get(enemy).getX() + gameObjects.get(enemy).getWidth())
                                && (gameObjects.get(player).getY() + gameObjects.get(player).getHeight()) > gameObjects.get(enemy).getY()
                                && (gameObjects.get(player).getY() < gameObjects.get(enemy).getY() + gameObjects.get(enemy).getHeight())) 
                        {
                            Game.score.set(Game.score.get() + tempEnemy.getPointValue());
                            gameObjects.get(enemy).setDeleteObject(true);
                            if (tempPlayer.getShield() > 0) 
                            {
                                // if there is shield left, take damage out of shield
                                if (tempPlayer.getShield() >= tempEnemy.getDamage()) 
                                {
                                    tempPlayer.setShield(tempPlayer.getShield() - (tempEnemy.getDamage() * (2 / tempPlayer.getShieldStrength())));
                                } 
                                else // if no sheld take damage out of health
                                {
                                    tempPlayer.setShield(tempPlayer.getShield() - (tempEnemy.getDamage() * (2 / tempPlayer.getShieldStrength())));
                                    tempPlayer.setHealth(tempPlayer.getHealth() - (tempEnemy.getDamage() - tempPlayer.getShield()));
                                }
                            } 
                            else 
                            {
                                tempPlayer.setHealth(tempPlayer.getHealth() - tempEnemy.getDamage());
                            }
                        }
                    }
                }
            }

        }
    }

    // This detects enemies hitting enemies.
    /*
    public void detectEnemyCollision() {
        for(int weapon = 0; weapon < gameObjects.size(); weapon++) {
            if(gameObjects.get(weapon) instanceof Enemy){
                // int j is for Enemy
                for (int enemy = weapon + 1; enemy < gameObjects.size(); enemy++) {

                    if(gameObjects.get(enemy) instanceof Enemy){

                        // if our gameObjects's intersect in any way
                        if(gameObjects.get(weapon).getX() + gameObjects.get(weapon).getWidth()  > gameObjects.get(enemy).getX()
                                && (gameObjects.get(weapon).getX()) < (gameObjects.get(enemy).getX() + gameObjects.get(enemy).getWidth())
                                && (gameObjects.get(weapon).getY()  + gameObjects.get(weapon).getHeight()) > gameObjects.get(enemy).getY()
                                && (gameObjects.get(weapon).getY() < gameObjects.get(enemy).getY() + gameObjects.get(enemy).getHeight())){

                            gameObjects.get(enemy).setDeleteObject(true);
                            gameObjects.get(weapon).setDeleteObject(true);


                        }
                    }
                }
            }

        }
    } */


    public void detectWeaponCollision() {
        
        for (int weapon = 0; weapon < gameObjects.size(); weapon++) {
            if (gameObjects.get(weapon) instanceof Weapon) {
                
                for (int enemy = 0; enemy < gameObjects.size(); enemy++) {
                    if (gameObjects.get(enemy) instanceof Enemy) {
                        Enemy tempEnemy = (Enemy) gameObjects.get(enemy);
                        if (gameObjects.get(weapon).getX() + gameObjects.get(weapon).getWidth() > gameObjects.get(enemy).getX()
                                && (gameObjects.get(weapon).getX()) < (gameObjects.get(enemy).getX() + gameObjects.get(enemy).getWidth())
                                && (gameObjects.get(weapon).getY() + gameObjects.get(weapon).getHeight()) > gameObjects.get(enemy).getY()
                                && (gameObjects.get(weapon).getY() < gameObjects.get(enemy).getY() + gameObjects.get(enemy).getHeight())) {
                            tempEnemy.setHealth(tempEnemy.getHealth() - 1);
                            if (tempEnemy.getHealth() == 0) {
                                Game.score.set(Game.score.get() + tempEnemy.getPointValue());
                                gameObjects.get(enemy).setDeleteObject(true);
                            }
                            gameObjects.get(weapon).setDeleteObject(true);

                        }
                    }
                }
            }

        }
    }


    public void addObject(GameObject object) {
        this.gameObjects.add(object);
    }

    public void addScenery(GameObject scenery) {
        this.scenery.add(scenery);
    }


    public void removeScenery(GameObject scenery) {
        this.scenery.remove(scenery);
    }

    public void removeObject(GameObject object) {
        this.gameObjects.remove(object);
    }

    public void fireWeapon() {
        double x = 0, y = 0;
        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof Player) {
                x = gameObjects.get(i).getX();
                y = gameObjects.get(i).getY();
            }
        }
        addObject(new Weapon(x + 16, y, 3, 3, ID.Weapon));
    }

    // moves the selection pointer at intro screen up
    public void pointerUp() {
        pointerY -= 55;
        --pointerPosition;
    }

    // moves the selection pointer at intro screen down
    public void pointerDown() {
        pointerY += 55;
        ++pointerPosition;
    }

    // draws the game while in play (not paused, intro, or end of game)
    public void gamePlay() {
        if (!endGame) {
            intro = false;
            paused = false;
            play = true;
            drawPauseBackground = false;
        }

    }

    // draw game pause screen
    public void gamePause() {
        if (!endGame) {
            paused = true;
            play = false;
        }
    }

    // draw end of game screen
    public void endGame() {
        paused = false;
        play = false;
        endGame = true;
    }

    
    public void saveGame() throws IOException {
        try (
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("gamesave.dat", false));) {
            try {
                output.flush();
                Player temp = getPlayer();
                temp.saveColors();
                temp.setSaveHealth();
                temp.setSaveScore();
                temp.setSaveShield();
                temp.setWeaponSpeed(Game.weaponSpeed);
                Integer numObjects = gameObjects.size();
                output.writeObject(numObjects);
                output.writeObject(gameObjects);
            } finally {
                output.close();
            }

        } catch (IOException e) {
            throw e;
        }
    }

    public void loadGame() throws IOException, ClassNotFoundException {

        if (file.exists()) {
            gameObjects.clear();
            try (
                    ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));) {
                try {
                    Object objectIn = input.readObject();
                    Integer numObjects = (Integer) objectIn;

                    List temp = (List) input.readObject();
                    for (int i = 0; i < numObjects; ++i) {
                        gameObjects.add((GameObject) temp.get(i));
                    }

                    Player tempPlayer = getPlayer();
                    tempPlayer.resetPlayerColor();
                    tempPlayer.loadHealth();
                    tempPlayer.loadScore();
                    tempPlayer.loadShield();
                    Game.weaponSpeed = tempPlayer.loadWeaponSpeed();
                } finally {
                    input.close();
                }
            } catch (IOException e) {
                throw e;
            }
        } else {
            throw new IOException();

        }
    }

    // returns the game player (required when user loads new game to update 
    // player across across other classes
    public Player getPlayer() {
        Player tempPlayer = null;
        for (int player = 0; player < gameObjects.size(); player++) {
            if (gameObjects.get(player).getID() == ID.Player) {
                tempPlayer = (Player) gameObjects.get(player);
            }
        }
        return tempPlayer;
    }




}
