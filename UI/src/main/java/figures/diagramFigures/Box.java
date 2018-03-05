package figures.diagramFigures;

import figures.basicShapes.Rectangle;

import java.awt.*;
import java.awt.geom.Point2D;

public class Box extends Figure {

    private Point2D tl, br;
    public Box(Point2D start, Point2D end) {
        tl = start;
        br = end;
    }

    @Override
    public void draw(Graphics graphics) {
        new Rectangle(tl,br).draw(graphics);
    }
}
