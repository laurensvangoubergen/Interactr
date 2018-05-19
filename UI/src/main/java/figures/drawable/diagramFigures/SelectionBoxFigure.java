package figures.drawable.diagramFigures;

import java.awt.*;
import java.awt.geom.Point2D;

public class SelectionBoxFigure extends Box {

    public SelectionBoxFigure(Point2D start, Point2D end){
        super(start,end);
    }
    @Override
    public void draw(Graphics graphics, int minX, int minY, int maxX, int maxY) {
        Color temp = graphics.getColor();
        graphics.setColor(Color.RED);
        super.draw(graphics,minX,minY,maxX,maxY);
        graphics.setColor(temp);
    }
}