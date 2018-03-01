package canvas;

import uievents.KeyEventFactory;
import uievents.MouseEventFactory;

import java.awt.*;
import java.awt.geom.Point2D;

public class InteractrCanvas extends CanvasWindow{
    /**
     * Initializes a CanvasWindow object.
     *
     * @param title Window title
     */

    private KeyEventFactory keyFactory;
    private MouseEventFactory mouseFactory;

    protected InteractrCanvas(String title) {
        super(title);
        keyFactory = new KeyEventFactory();
        mouseFactory = new MouseEventFactory();
    }

    public void paint(Graphics g){

    }

    @Override
    public void handleMouseEvent(int id, int x, int y, int clickCount){
        Point2D point = new Point2D.Double(x,y);
        mouseFactory.createMouseEvent(id, clickCount, point);
    }
    @Override
    public void handleKeyEvent(int id, int keyCode, char keyChar){
        keyFactory.createKeyEvent(id, keyCode, keyChar);
    }
}