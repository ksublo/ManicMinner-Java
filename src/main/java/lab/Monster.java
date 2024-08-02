package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Monster extends Entity implements Collision {

    private Image monster = new Image(getClass().getResourceAsStream("monster.PNG"),size.getX(),size.getY(),true,true);
    private ImageView imageView = new ImageView(monster);
    private Point2D speed;
    private double pointFrom;
    private double pointTo;
    private double sinceLast = 0;
    private final double time = 1.0;

    public Monster(Level lvl, Point2D position, Point2D size, Point2D speed, double pointFrom, double pointTo) {
        super(lvl, position, size);
        this.speed = speed;
        this.pointFrom = pointFrom;
        this.pointTo = pointTo;
    }

    @Override
    public void draw(GraphicsContext gc) {
        imageView.relocate(position.getX(),position.getY());
        gc.drawImage(monster, position.getX(), position.getY(),size.getX(),size.getY());
    }

    @Override
    public void simulate(double timeDelta) {
        sinceLast += timeDelta;
        position = position.add(speed.multiply(timeDelta));

        if (position.getX() >= pointTo || position.getX() <= pointFrom) {
            speed = new Point2D(-speed.getX(), speed.getY());
        }

        if (sinceLast >= time && this.getBounds().intersects(lvl.getManic().getBounds())) {
            lvl.handleCollision();
            sinceLast = 0;
        }
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }
}
