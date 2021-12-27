package kolokvijum2;

import javafx.scene.paint.Color;
import mars.drawingx.application.DrawingApplication;
import mars.drawingx.drawing.Drawing;
import mars.drawingx.drawing.DrawingUtils;
import mars.drawingx.drawing.View;
import mars.drawingx.gadgets.annotations.GadgetAnimation;
import mars.drawingx.gadgets.annotations.GadgetBoolean;
import mars.drawingx.gadgets.annotations.GadgetInteger;
import mars.drawingx.gadgets.annotations.GadgetVector;
import mars.geometry.Transformation;
import mars.geometry.Vector;

public class zadatak2 implements Drawing {

    @GadgetAnimation(min = 0, max = 1, speed = 0.2)
    double t = 0.0;

    Vector p0 = new Vector(-310, -100);

    Vector p1 = new Vector(-110, 230);

    Vector p2 = new Vector(180, -250);

    Vector p3 = new Vector(320, 100);

    double r = 6;

    public void drawCar(View view, Vector posVector, double angle) {
        view.stateStore();
        view.addTransformation(new Transformation().translate(posVector.sub(new Vector(0, 15))));
        view.addTransformation(new Transformation().rotate(angle));
        view.setFill(Color.GRAY);
        view.fillRectCentered(Vector.ZERO.add(new Vector(0, 30)), new Vector(30, 10));
        view.setFill(Color.YELLOW);
        view.fillRectCentered(Vector.ZERO.add(new Vector(0, 40)), new Vector(10, 10));
        view.setFill(Color.BLACK);
        view.fillCircleCentered(Vector.ZERO.add(new Vector(-20, 20)), 10);
        view.fillCircleCentered(Vector.ZERO.add(new Vector(20, 20)), 10);

        view.stateRestore();
    }

    @Override
    public void draw(View view) {
        DrawingUtils.clear(view, Color.gray(0.125));

        view.setLineWidth(2.0);

        view.setStroke(Color.WHITE);
        view.beginPath();
        view.moveTo(p0);
        view.bezierCurveTo(p1, p2, p3);
        view.stroke();
        // view.fill();

        Vector p01 = Vector.lerp(p0, p1, t);
        Vector p12 = Vector.lerp(p1, p2, t);
        Vector p23 = Vector.lerp(p2, p3, t);

        Vector p012 = Vector.lerp(p01, p12, t);
        Vector p123 = Vector.lerp(p12, p23, t);

        Vector p0123 = Vector.lerp(p012, p123, t);
        Vector pNext = Vector.lerp(p012, p123, t+0.01);


        view.setFill(Color.hsb(240, 0.6, 1));

        view.setFill(Color.hsb(0, 0, 1));
        // view.fillCircleCentered(p0123, r);
        // view.fillRectCentered(p0123.add(new Vector(0, 10)), new Vector(30, 10));
        drawCar(view, p0123, pNext.sub(p0123).angle());
    }

    public static void main(String[] args) {
        DrawingApplication.launch(800, 600);
    }

}
