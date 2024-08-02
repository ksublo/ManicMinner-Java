package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Manic extends Entity implements Collision{

    private Image manicRight = new Image(getClass().getResourceAsStream("manic-right.PNG"),size.getX(),size.getY(),true,true);
    private Image manicLeft =  new Image(getClass().getResourceAsStream("manic-left.PNG"),size.getX(),size.getY(),true,true);
    private ImageView imageView = new ImageView(manicRight);

    //pro movments
    private boolean movingRight = false;
    private boolean movingLeft = false;
    private boolean jumping = false;
    private double verticalVelocity = 0;
    private final double moveSpeed = 80;
    private final double jumpSpeed = 100;
    private final double gravity = 65.0;

    public Manic(Level lvl, Point2D position, Point2D size){
        super(lvl, position, size);
    }

    @Override
    public void draw(GraphicsContext gc) {
        imageView.relocate(position.getX(),position.getY());
        gc.drawImage(imageView.getImage(), position.getX(), position.getY(),size.getX(),size.getY());
    }

    @Override
    public void simulate(double timeDelta) {

        if (position.getX() < 20) {
            position = new Point2D(20, position.getY());
        }
        else if (position.getX() > 780 - size.getX()) {
            position = new Point2D(780 - size.getX(), position.getY());
        }

        if (movingRight) {
            position = position.add(moveSpeed * timeDelta, 0);
        }
        else if (movingLeft) {
            position = position.add(-moveSpeed * timeDelta, 0);
        }

        if (!onAnyPlatform() && !jumping) {
            jumping = true;
            verticalVelocity += gravity * timeDelta;
            position = position.add(0, verticalVelocity * timeDelta);
            for (Objects platform : lvl.getPlatforms()) {
                if (this.getBounds().intersects(platform.getBounds())) {
                    position = new Point2D(position.getX(), platform.getPosition().getY() - size.getY());
                }
            }
        }

        if (jumping) {
            position = position.add(0, verticalVelocity * timeDelta);
            verticalVelocity += gravity * timeDelta;

            boolean onGround = false;
            for (Objects platform : lvl.getPlatforms()) {
                if (this.getBounds().intersects(platform.getBounds())) {
                    double manicBottom = position.getY() + size.getY();
                    double platformTop = platform.getPosition().getY();
                    double verticalDistance = manicBottom - platformTop;

                    if (verticalDistance >= 0 && verticalDistance <= 5) {
                        position = new Point2D(position.getX(), platformTop - size.getY());
                        verticalVelocity = 0;
                        jumping = false;
                        onGround = true;
                        break;
                    }
                }
            }

            /*if (!jumping && !onAnyPlatform()) {
                jumping = true;
                verticalVelocity = 0;
            }*/

            if (!onAnyPlatform()) {
                verticalVelocity += gravity * timeDelta;
                position = position.add(0, verticalVelocity * timeDelta);
            }
        }

    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
        imageView.setImage(manicRight);
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
        imageView.setImage(manicLeft);
    }

    public void jump() {
        if (!jumping) {
            jumping = true;
            verticalVelocity = -jumpSpeed;
        }
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }

    private boolean onAnyPlatform() {
        for (Objects platform : lvl.getPlatforms()) {
            double platformTop = platform.getPosition().getY();
            double manicBottom = position.getY() + size.getY();

            if (manicBottom == platformTop && position.getX() < platform.getPosition().getX()
                    + platform.getSize().getX() && position.getX() + size.getX() > platform.getPosition().getX()) {
                return true;
            }
        }
        return false;
    }

    public void setPosition(Point2D newPosition) {
        this.position = newPosition;
    }

}
