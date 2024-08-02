package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DrawingThread extends AnimationTimer {

	private final GraphicsContext gc;
	private final Level lvl;
	private GameController controller;

	private long lastTime;

	public DrawingThread(Canvas canvas, Level lvl, GameController controller) {
		this.gc = canvas.getGraphicsContext2D();
		//this.lvl = new Level(canvas.getWidth(), canvas.getHeight());
		this.lvl = lvl;
		this.controller = controller;
	}

	@Override
	public void handle(long now) {
		gc.clearRect(0, 0, lvl.getWidth(), lvl.getHeight());

		this.lvl.draw(gc);
		if (lastTime > 0) {
			lvl.simulate((now - lastTime) / 1e9);
		}
		lastTime= now;

		controller.check();
	}

}