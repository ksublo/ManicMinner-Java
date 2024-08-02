package lab;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Objects extends Entity implements Collision{
    private Image objectImage = new Image(getClass().getResourceAsStream("wall.JPEG"),size.getX(),size.getY(),true,true);
    private ImageView imageView = new ImageView(objectImage);
    public Objects(Level lvl, Point2D position, Point2D size){
        super(lvl, position, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        imageView.relocate(position.getX(),position.getY());
        gc.drawImage(objectImage, position.getX(), position.getY(),size.getX(),size.getY());
    }

    @Override
    public void simulate(double timeDelta) {

    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }

    public Point2D getPosition(){
        return position;
    }

    public Point2D getSize(){
        return size;
    }

}
