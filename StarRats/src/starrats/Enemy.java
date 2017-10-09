package starrats;

import javafx.scene.canvas.GraphicsContext;

/**
 * Enemy class that player is trying to kill.  Has health, size, damage, point value
 * Created by John Diggins on 4/28/17.
 */
public class Enemy extends GameObject {
    int count;
    int damage;
    int pointValue;

    public int health;

    public Enemy(ID id, double maxX, double maxY){
        super(id, maxX, maxY);
        spawnTop();
        this.count = 0;

    }

    public Enemy(ID id, double width, double height, double maxX, double maxY){
        super(id, width, height, maxX, maxY);
        spawnTop();
        this.count = 0;
    }

    @Override
    public void update() {
        x += speedX;
        y += speedY;
        checkBounds();
    }

    @Override
    public void render(GraphicsContext gc) {

    }


    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() { return health; }

    /**
     *  Randomly spawns enemies outside of view at top of screen
     */
    public void spawnTop() {
        x = (int)(Math.random() * maxX);
        y = -200;
    }

    /**
     * If object goes out of bounds (bottom of screen), player looses a point
     */
    public void checkBounds() {

        if (y > maxY + height) {
            Game.score.set(Game.score.get() - 1);
            deleteObject = true;
        }
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() { return damage; }

    public void setPointValue(int pointValue) {
        this.pointValue = pointValue;
    }

    public int getPointValue() { return pointValue; }

}