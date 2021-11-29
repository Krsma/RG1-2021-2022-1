package kolokvijum;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetColorPicker;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.drawingx.gadgets.annotations.GadgetBoolean;
import mars.geometry.Transformation;
import mars.geometry.Vector;
import topic2_image_processing.filters.CombinedFilter;
import topic2_image_processing.filters.ConvolutionFilter;
import topic2_image_processing.filters.Filter;
import topic2_image_processing.filters.color.Accent;
import topic2_image_processing.filters.color.Colorize;
import topic2_image_processing.filters.color.Desaturate;
import topic2_image_processing.filters.color.Grayscale;
import topic2_image_processing.filters.color.Invert;
import topic2_image_processing.filters.color.Saturate;
import topic2_image_processing.filters.color.Sepia;
import topic2_image_processing.filters.displacement.FlipHorizontal;
import topic2_image_processing.filters.displacement.FlipVertical;
import topic2_image_processing.filters.displacement.Jitter;
import topic2_image_processing.filters.displacement.Lens;
import topic2_image_processing.filters.displacement.Rotate180;
import topic2_image_processing.filters.displacement.Swirl;
import topic2_image_processing.filters.displacement.Wave;
import topic2_image_processing.filters.displacement.Zoom;
import topic2_image_processing.filters.misc.Sobel;
import topic2_image_processing.filters.misc.Vignette;

public class klkZad2 implements Drawing {

	@GadgetInteger(min = 1, max = 50)
	int n = 2;

	

	@Override
	public void draw(View view) {
		DrawingUtils.clear(view,Color.GRAY);
		double r = 200;
		//Vector pomeraj = new Vector(-r/n, 0);
		double gap = r / n;
		Vector centar = Vector.ZERO;
		view.stateStore();
		// for(int i = 0; i < n; i++)
		// {
		// 	view.setFill(i%2==0? Color.DARKBLUE : Color.LIGHTBLUE);



		// 	view.fillCircleCentered(centar, r);
		// 	r = r * 0.8;
		// 	//.addTransformation(Transformation.translation(new Vector(-0.25*r, 0)));

		// 	//view.addTransformation(Transformation.scaling(0.8));
		// 	centar = centar.add(new Vector(-r*0.4,0));

		// 	//view.addTransformation(Transformation.translation(new Vector(r/n, 0)));
		// }

		for(int i = 0; i < n; i++)
		{
			view.setFill(i%2==0? Color.DARKBLUE : Color.LIGHTBLUE);
			view.fillCircleCentered(centar, r);
			centar = centar.add(new Vector(-gap, 0));
			r = r - gap;			
		}
		centar = centar.add(new Vector(+gap, 0)); // vracam go nazad u centar prethodnog kruga
		r = r + 0.5*gap;	
		int koef = n % 2 == 0 ? 0 : 1;		// ofset menajca boja kada imamo neparan broj krugova, inace dodje do preklapanja
		for(int i = 0; i < n; i++)
		{
			
			//view.setFill(i%2==0 ? Color.LIGHTBLUE : Color.DARKBLUE );
			view.setFill((i + koef)%2==1 ? Color.LIGHTBLUE : Color.DARKBLUE );

			view.fillCircleCentered(centar, r);
			centar = centar.add(new Vector(+2*gap, 0));
			//r = r - gap;	
		}
		view.stateRestore();
	}

	

	public static void main(String[] args) {

		DrawingApplication.launch(600, 600);
	}
}