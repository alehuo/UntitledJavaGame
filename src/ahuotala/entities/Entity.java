package ahuotala.entities;

/**
 *
 * @author Aleksi Huotala
 */
abstract class Entity {

    protected int x;

    protected int y;

    public Entity() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incrementX(int step) {
        x -= step;
    }

    public void incrementY(int step) {
        y -= step;
    }

}
