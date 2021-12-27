package kolokvijum2;

import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetDouble;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.geometry.Vector;
import mars.utils.Numeric;

public class Sun implements Drawing {


    @GadgetAnimation(start = true)
    double time = 0.0;

    @GadgetDouble(min = 0, max = 200)
    double rSun = 100;
   
    @GadgetDouble(min = 0, max = 400)
    double rayMinLength = 150;
    
    @GadgetDouble(min = 0, max = 400)
    double rayMaxLength = 300;

    @GadgetInteger
    int nRayDiv2 = 2;

    public void rayVectors(View view) {
        double dL = rayMaxLength - rayMinLength;

        view.setLineWidth(8);
        view.setLineCap(StrokeLineCap.ROUND);
        for(int i = 0; i < 2*nRayDiv2; i++) {
            double temp = (1 + Numeric.sinT(time)) * 0.5; //interval [0,1] //PERIODICNO MAJKU MU
            double l1 = rayMinLength + (1-temp) * dL;
            double l2 = rayMinLength + temp * dL;

            Vector p;
            if(i % 2 == 1) {
                p = Vector.polar(l1, i/(2.0*nRayDiv2)); 
                view.setStroke(Color.hsb(50,0.7,0.8,1-0.5*temp));
            } else {
                p = Vector.polar(l2, i/(2.0*nRayDiv2));
                view.setStroke(Color.hsb(50,0.7,0.8,0.5*(1+temp)));
            }  
            view.strokeLine(Vector.ZERO, p);
        }
    }
    @Override
    public void draw(View view) {
        DrawingUtils.clear(view, Color.gray(0.15));
        view.setFill(Color.hsb(50,0.7,0.8));
        view.fillCircleCentered(Vector.ZERO, rSun);
        rayVectors(view);
    }

    public static void main(String[] args) {
        DrawingApplication.launch(600, 600);
    }
    
}
