package lab;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public abstract class Entity implements DrawSimulate {
    protected final Level lvl;
    protected Point2D position;
    protected Point2D size;

    public Entity(Level lvl, Point2D position, Point2D size){
        this.lvl = lvl;
        this.position = position;
        this.size = size;
    }

}
