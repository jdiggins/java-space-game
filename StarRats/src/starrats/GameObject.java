package starrats;

import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;


/**
 * Abstract class containing outline of all our game objects.  Abstract
 * functions Update and Render must be overridden in subclasses.
 * Created by John on 4/22/17.
 */
public abstract class GameObject implements Serializable{

    protected double x, y;

    protected double width, height;

    protected double speedX, speedY;

    protected ID id;

    protected double maxX, maxY;

    // when set true object is deleted during next update
     
    protected boolean deleteObject = false;

    // generates random numbers for different sized objects
    protected transient NumberGenerator numberGen = new NumberGenerator();


    public GameObject(double x, double y, ID id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }


    public GameObject(double x, double y, double width, double height, ID id) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
    }

    public GameObject(ID id, double maxX, double maxY){
        this.id = id;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public GameObject(double x, double y, ID id, double maxX, double maxY) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public GameObject(ID id, double width, double height, double maxX, double maxY){
        this.id = id;
        this.width = width;
        this.height = height;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public GameObject(double x, double y, double width, double height, ID id, double maxX, double maxY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    // updates object position and state
    public abstract void update();

    // re-draws current object after update
    public abstract void render(GraphicsContext gc);

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }

    public void setDeleteObject(boolean deleteObject) { this.deleteObject = deleteObject; }

    public void setID(ID id) {
        this.id = id;
    }

    public double getX() { return x; }

    public double getY() { return y; }

    public double getSpeedX() { return speedX; }

    public double getSpeedY() { return speedY; }

    public ID getID() { return id; }

    public double getWidth() { return width; }

    public double getHeight() { return height; }

}
