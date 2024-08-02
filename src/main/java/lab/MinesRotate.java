package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MinesRotate extends Entity implements Collision {
    private Image minesImage = new Image(getClass().getResourceAsStream("minesRotate.PNG"),size.getX(),size.getY(),true,true);
    private ImageView imageView = new ImageView(minesImage);
    private double sinceLast = 0;
    private final double time = 2.0;
    public MinesRotate(Level lvl, Point2D position, Point2D size){
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
            lvl.handleCollision();
            sinceLast = 0;
        }
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }
}