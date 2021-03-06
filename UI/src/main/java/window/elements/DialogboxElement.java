package window.elements;

import exception.UIException;
import window.Clickable;

import java.awt.geom.Point2D;

/**
 * Abstract superclass for dialogboxelements, they are clickable
 */
public abstract class DialogboxElement implements Clickable {

    private Point2D coordinate;
    private String description;

    protected DialogboxElement() {

    }

    /**
     * makes a new dialogboxelement with the given parameters
     *
     * @param coordinate  the new coordinate for this dialogbox
     * @param description the description for this dialogbox
     * @throws UIException if an illegal coordinate is given
     */
    public DialogboxElement(Point2D coordinate, String description) throws UIException {
        this.setCoordinate(coordinate);
        this.setDescription(description);
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * sets the description to the given description
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the coordinate of this dialogboxelement
     */
    public Point2D getCoordinate() {
        return coordinate;
    }

    /**
     * @param coordinate the new coordinate for this dialogboxelement
     * @throws UIException if the given coordinate is null
     */
    public void setCoordinate(Point2D coordinate) throws UIException {
        if (coordinate == null) {
            throw new UIException("Coordinate may not be null for dialogboxElements");
        }
        this.coordinate = coordinate;
    }

    /**
     * @param coordinate the location of the click
     * @return true if this element is clicked on the given location
     */
    @Override
    public abstract boolean isClicked(Point2D coordinate);
}
