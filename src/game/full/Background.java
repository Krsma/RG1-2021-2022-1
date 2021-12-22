package game.full;

import javafx.scene.paint.Color;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;


public class Background {
	
	private final BackgroundParticleSystem ps = new BackgroundParticleSystem();
	

	public void draw(View view, double time) {
		DrawingUtils.clear(view, Color.hsb(0, 0, 0.1));
		ps.draw(view, time);
	}
	
	
	public void update(double time) {
		ps.updateInterval(time);
	}
}
