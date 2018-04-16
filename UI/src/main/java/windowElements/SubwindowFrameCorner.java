package windowElements;

import subwindow.Clickable;

import java.awt.geom.Point2D;

public class SubwindowFrameCorner implements Clickable {

    private final int OFFSET = 5;

    public Point2D center;

    public SubwindowFrameCorner(Point2D centerPoint){
        this.center = centerPoint;
    }


    public Point2D getCenter() {
        return center;
    }

    private void setCenter(Point2D center) {
        this.center = center;
    }

    @Override
    public boolean isClicked(Point2D location) {
        double startX = center.getX() - OFFSET;
        double endX = center.getX() + OFFSET;
        double startY = center.getY() - OFFSET;
        double endY = center.getY() + OFFSET;
        return (startX <= location.getX() && endX >= location.getX()) && (startY <= location.getY() && endY >= location.getY());
    }
}