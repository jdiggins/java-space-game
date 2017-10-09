package starrats;

import java.util.Random;

/**
 * Generate random numbers between a min and max.
 * Created by John on 5/10/17.
 */
public class NumberGenerator {

    Random random = new Random();

    public NumberGenerator(){

    }

    public double getRandDouble() {
        return random.nextDouble();
    }

    public double getRandDouble(double min, double max) {
        return (random.nextDouble() * (max - min)) + min;
    }

    public int getRandInt(int min, int max) {
        return random.nextInt(max + 1 - min) + min;
    }

}
