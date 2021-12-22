package topic9_particle_systems;

import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.drawingx.gadgets.annotations.GadgetDoubleLogarithmic;
import mars.geometry.Vector;
import mars.utils.Graphics;

import java.util.ArrayList;
import java.util.List;


class Star {
	
	private final double t;
	private final double duration;
	private final Vector p, v;
	private final double size;
	private final Color color;
	
	
	
	public Star(double t, double duration, Vector p, Vector v, double size, Color color) {
		this.t = t;
		this.duration = duration;
		this.p = p;
		this.v = v;
		this.size = size;
		this.color = color;
	}
	
	
	public void draw(View view, double time) {
		double td = time - t;
		double k = td / duration;
		
		view.setFill(Graphics.scaleOpacity(color, 1 - k));
		view.fillCircleCentered(p.add(v.mul(td)), k * size);
	}
	
	
	public boolean isAlive(double time) {
		return time <= t + duration;
	}
	
}


public class Starfield implements Drawing {
	
	@GadgetAnimation(start = true)
	double time = 0;
	
	@GadgetDoubleLogarithmic(min = 1e-6, max = 1)
	double timeInterval = 0.001;

	@GadgetDoubleLogarithmic(min = 0.01, max = 10)
	double duration;
	
	@GadgetDouble(max = 600)
	double pStd = 0;
	
	@GadgetDouble(max = 300)
	double vStd = 10;
	
	@GadgetDoubleLogarithmic(min = 0.01, max = 1000)
	double size = 4;
	
	@GadgetDouble(max = 360)
	double hue = 30;
	
	List<Star> stars = new ArrayList<>();
	
	double timeNext = 0.0;
	
	
	
	
	@Override
	public void draw(View view) {
		DrawingUtils.clear(view, Color.BLACK);
		
		while (time >= timeNext) {
			Star star = new Star(
					timeNext,
					duration,
					Vector.randomGaussian(pStd),
					Vector.randomGaussian(vStd),
					size,
					Color.hsb(hue, 0.4 + 0.4 * Math.random(), 0.6 + 0.4 * Math.random())
			);
			
			stars.add(star);
			timeNext += timeInterval;
		}
		
		stars.removeIf(star -> !star.isAlive(time));
		stars.forEach(star -> star.draw(view, time));
	}
	
	
	public static void main(String[] args) {
		DrawingApplication.launch(700, 700);
	}
	
}
