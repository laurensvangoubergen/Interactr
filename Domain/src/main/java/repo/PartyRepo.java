package repo;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import diagram.party.Actor;
import diagram.party.Party;
import exceptions.DomainException;

public class PartyRepo {

    public static final int ACTORWIDTH = 50;
    public static final int ACTORHEIGHT = 50;

    public static final int OBJECTWIDTH = 80;
    public static final int OBJECTHEIGHT = 50;

    private Map<Party, Point2D> partyPoint2DMap;

    public PartyRepo(){
        this(new HashMap<>());
    }

    public PartyRepo(HashMap<Party, Point2D> labelPoint2DMap){
        this.partyPoint2DMap = labelPoint2DMap;
    }

    private Map<Party, Point2D> getMap(){
        return this.partyPoint2DMap;
    }

    public Party getPartyAtPosition(Point2D location) throws DomainException{
        return this.getMap().entrySet()
                .stream()
                .filter(entry -> entry.getValue().equals(location))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(DomainException::new);
    }

    public Point2D getLocationOfParty(Party party){
        return this.getMap().get(party);
    }

    public void addPartyWithLocation(Party party, Point2D location){
        this.getMap().put(party, location);
    }

    public void removeParty(Party party){
        this.getMap().remove(party);
    }

    public void removePartyByPosition(Point2D location) throws DomainException{
        Party l = this.getPartyAtPosition(location);
        this.removeParty(l);
    }

    public Set<Party> getClickedParties(Point2D clickedLocation){
        return this.partyPoint2DMap.entrySet()
                .stream()
                .filter(pair -> {
                    if(pair.getKey() instanceof Actor){
                        return isClickedActor(clickedLocation, getLocationOfParty(pair.getKey()));
                    }
                    else if(pair.getKey() instanceof Object){
                        return isClickedObject(clickedLocation, getLocationOfParty(pair.getKey()));
                    }
                    else{
                        return false;
                    }
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * @param clickedLocation
     *        The coordinates of the mouse where the user clicked
     * @param actorLocation
     *        The coordinates of the actor to check
     * @return
     *        True if the clicked coordinates are within the coordinates of the image of this actor
     */
    private boolean isClickedActor(Point2D clickedLocation, Point2D actorLocation) {
        double clickX = clickedLocation.getX();
        double clickY = clickedLocation.getY();
        double startX = actorLocation.getX() - ACTORWIDTH/2;
        double startY = actorLocation.getY();
        double endX = startX + ACTORWIDTH;
        double endY = startY + ACTORHEIGHT;
        return (clickX >= startX && clickX <= endX) && (clickY >= startY && clickY <= endY);
    }

    /**
     * @param clickedLocation
     *        The coordinates of the mouse where the user clicked
     * @param objectLocation
     *        The location of the object to check
     * @return
     *        True if the clicked coordinates are within the coordinates of the image of this message
     */
    private boolean isClickedObject(Point2D clickedLocation, Point2D objectLocation) {
        double clickX = clickedLocation.getX();
        double clickY = clickedLocation.getY();
        double startX = objectLocation.getX();
        double startY = objectLocation.getY();
        double endX = startX + OBJECTWIDTH;
        double endY = startY + OBJECTHEIGHT;
        return (clickX >= startX && clickX <= endX) && (clickY >= startY && clickY <= endY);
    }
}