package kolokvijum2;

import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import com.google.common.math.Quantiles.Scale;

import org.checkerframework.checker.units.qual.A;

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

public class zadatak1 implements Drawing {

    @GadgetAnimation
    double time = 0.0;

    double dt0 = 2.0; // Trajanje stanja 0 - Podizanje poklopca
    double dt1 = 2.0; // Trajanje stanja 1 - Ubacivanje lopte
    double dt2 = 3.0; // Trajanje stanja 2 - Spustanje poklopca

    // Vremena keyfremova (trenutke prelaza izmedju stanja) racunamo sabiranjem
    // trajanja stanja

    double time0 = 0.0; // Vreme pocetka stanja 0
    double time1 = time0 + dt0; // Vreme kraja stanja 0 = Vreme pocetka stanja 1
    double time2 = time1 + dt1; // Vreme kraja stanja 1 = Vreme pocetka stanja 2
    double time3 = time2 + dt2; // Vreme kraja stanja 2

    double l = 100;

    double smoothStep(double x) {
        return x * x * (3 - 2 * x);
    }

    double bounceOut(double x) {
        if (x < 1.00 / 2.75) {
            return 7.5625 * x * x;
        } else if (x < 2.00 / 2.75) {
            return 7.5625 * (x -= (1.5 / 2.75)) * x + 0.75;
        } else if (x < 2.50 / 2.75) {
            return 7.5625 * (x -= (2.25 / 2.75)) * x + 0.9375;
        } else {
            return 7.5625 * (x -= (2.625 / 2.75)) * x + 0.984375;
        }
    }

    double bounceOutNormalized(double x) {
        if (x < time2) {
            return 0;
        }
        double vrednost = bounceOut(x - time2);
        // vrednost = (vrednost))
        return vrednost > 1 ? 1 : vrednost;
    }

    double easeOutBack(double x) {
        return 1 - ((x = 1 - x) * x * (2.70158 * x - 1.70158));
    }

    double tBox(double time) {
        if (time < time0)
            return 0;
        // if (time < time1)
        // return smoothStep((time - time0) / dt0);

        return time;
    }

    double tShaft(double time) {
        if (time < time0) {
            return 0;
        }
        if (time < time1) {
            return 0;
        }
        return time - time1;
    }

    void drawBox(View view, double t) {
        double phi = 2 * l * t;
        Vector a = new Vector(-l, 0);
        Vector b = new Vector(-l, -l);
        view.setLineCap(StrokeLineCap.ROUND);
        view.setLineJoin(StrokeLineJoin.ROUND);
        view.setLineWidth(4);

        view.setFill(Color.hsb(120, 0.5, 0.6));
        view.setStroke(Color.hsb(120, 0.5, 1.0));

        double lLoss = 30 * t;
        if (lLoss > l) {
            view.fillPolygon(a, b);
            view.strokePolyline(a, b);
        } else {
            Vector c = new Vector(0 - lLoss, -l);
            Vector d = new Vector(0 - lLoss, 0);

            view.fillPolygon(a, b, c, d, a);
            view.strokePolyline(a, b, c, d, a);
        }

    }

    void drawDoor(View view, double t) {
        view.setLineCap(StrokeLineCap.ROUND);
        view.setLineJoin(StrokeLineJoin.ROUND);
        view.setLineWidth(4);

        view.setFill(Color.hsb(120, 0.5, 0.6));
        view.setStroke(Color.hsb(120, 0.5, 1.0));

        Vector hinge1u = new Vector(l, l);
        Vector hinge1d = new Vector(l, -l);
        Vector hinge2u = new Vector(-l, l);
        Vector hinge2d = new Vector(-l, -l);
        Vector midUp = new Vector(0, l);
        Vector midDOwn = new Vector(0, -l);
        double shifter = t * 100;

        if (shifter >= l) {
            view.fillPolygon(hinge1d, hinge1u);
            view.strokePolyline(hinge1d, hinge1u);

            view.fillPolygon(hinge2d, hinge2u);
            view.strokePolyline(hinge2d, hinge2u);
        } else {
            view.fillPolygon(hinge1u, hinge1d, midDOwn.add(new Vector(shifter, 0)), midUp.add(new Vector(shifter, 0)),
                    hinge1u);
            view.strokePolyline(hinge1u, hinge1d, midDOwn.add(new Vector(shifter, 0)),
                    midUp.add(new Vector(shifter, 0)), hinge1u);

            view.fillPolygon(hinge2d, midDOwn.sub(new Vector(shifter, 0)), midUp.sub(new Vector(shifter, 0)), hinge2u,
                    hinge2d);
            view.strokePolyline(hinge2d, midDOwn.sub(new Vector(shifter, 0)), midUp.sub(new Vector(shifter, 0)),
                    hinge2u, hinge2d);
        }
    }

    void drawShaft(View view, double t) {
        view.setLineCap(StrokeLineCap.ROUND);
        view.setLineJoin(StrokeLineJoin.ROUND);
        view.setLineWidth(4);

        view.setFill(Color.hsb(60, 0.5, 0.6));
        view.setStroke(Color.hsb(120, 0.5, 1.0));

        Vector hinge1r = new Vector(l, l);
        Vector hinge2r = new Vector(l, -l);
        Vector hinge1l = new Vector(-l, l);
        Vector hinge2l = new Vector(-l, -l);
        Vector midR = new Vector(l, 0);
        Vector midL = new Vector(-l, 0);
        double shifter = t * 100;

        if (shifter >= l) {
            view.fillPolygon(hinge1r, hinge1l);
            view.strokePolyline(hinge1r, hinge1l);

            view.fillPolygon(hinge2r, hinge2l);
            view.strokePolyline(hinge2r, hinge2l);

        } else {
            view.fillPolygon(hinge1l, hinge1r, midR.add(new Vector(0, shifter)), midL.add(new Vector(0, shifter)),
                    hinge1l);
            view.strokePolyline(hinge1l, hinge1r, midR.add(new Vector(0, shifter)), midL.add(new Vector(0, shifter)),
                    hinge1l);

            view.fillPolygon(hinge2r, midR.sub(new Vector(0, shifter)), midL.sub(new Vector(0, shifter)), hinge2l,
                    hinge2r);
            view.strokePolyline(hinge2r, midR.sub(new Vector(0, shifter)), midL.sub(new Vector(0, shifter)), hinge2l,
                    hinge2r);
        }
    }

    void drawSmajli(View view, double t) {
        double r = 80; // Poluprecnik glave.
        double xEye = 20.0; // Udaljenost oka od centra po x-osi.
        double yEye = 15.0; // Udaljenost oka od centra po y-osi.
        double rEye = 5.0; // Poluprecnik oka.
        double phiMouth = 1.0 / 3;
        double rMouth = 20.0;

        view.stateStore();
        view.addTransformation(new Transformation().scale(t));
        view.setFill(Color.hsb(60, 0.9, 0.9));
        view.fillCircleCentered(new Vector(0, 0), r);

        // Oci
        view.setFill(Color.hsb(0, 0, 0));
        view.fillCircleCentered(new Vector(-xEye, yEye), rEye);
        view.fillCircleCentered(new Vector(xEye, yEye), rEye);

        // Usta
        view.setLineWidth(15);
        view.setStroke(Color.hsb(0, 0, 0));
        view.strokeArcCentered(new Vector(0, 0), new Vector(rMouth), 0.75 - phiMouth / 2, phiMouth);

        view.stateRestore();
    }

    @Override
    public void draw(View view) {
        DrawingUtils.clear(view, Color.hsb(0, 0, 0.125));
        // drawBox(view, time);
        drawSmajli(view, bounceOutNormalized(time));
        drawShaft(view, tShaft(time));
        drawDoor(view, time);

    }

    public static void main(String[] args) {
        DrawingApplication.launch(800, 800);
    }

}
