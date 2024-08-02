package lab;

import javafx.scene.canvas.GraphicsContext;

public interface DrawSimulate {
    void draw(GraphicsContext gc);
    void simulate(double timeDelta);
}
