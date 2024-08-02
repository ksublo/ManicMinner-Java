package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Mines extends Entity implements Collision{
    private Image minesImage = new Image(getClass().getResourceAsStream("mines.PNG"),size.getX(),size.getY(),true,true);
    private ImageView imageView = new ImageView(minesImage);

    private double sinceLast = 0;
    private final double time = 1.0;

    public Mines(Level lvl, Point2D position, Point2D size){
        super(lvl, position, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        imageView.relocate(position.getX(),position.getY());
        gc.drawImage(minesImage, position.getX(), position.getY(),size.getX(),size.getY());
    }

    @Override
    public void simulate(double timeDelta) {
        sinceLast += timeDelta;

        if (sinceLast >= time && this.getBounds().intersects(lvl.getManic().getBounds())) {
            sinceLast = 0;
            lvl.handleCollision();
        }
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }
}
