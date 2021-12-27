package kolokvijum2;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
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
import topic2_image_processing.filters.ColorFilter;
import topic2_image_processing.filters.CombinedFilter;
import topic2_image_processing.filters.ConvolutionFilter;
import topic2_image_processing.filters.DisplacementFilter;
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
import mars.random.RNG;

import mars.drawingx.gadgets.annotations.GadgetAnimation;


public class zadatak1 implements Drawing{
    @GadgetColorPicker
	Color colorPicker = Color.CORNFLOWERBLUE;
	
	@GadgetDouble(min=0, max = 1)
	double threshold = 0.5;

    @GadgetInteger
    int n = 1;

    @GadgetAnimation
    double time = 0.0;
    
	@GadgetBoolean
	boolean applyFilter = false;


    @Override
    public void draw(View view) {
        // TODO Auto-generated method stub
        DrawingUtils.clear(view, Color.LIGHTBLUE);
        
    }

    public static void main(String[] args) {
        DrawingApplication.launch(1200, 1200);
    }
}
