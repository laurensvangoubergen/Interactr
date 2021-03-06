package view.diagram;

import diagram.DiagramElement;
import diagram.label.Label;
import diagram.message.Message;
import diagram.party.Party;
import view.label.LabelView;
import view.message.CommunicationMessageView;
import view.message.MessageView;
import view.message.SequenceMessageView;
import view.party.PartyView;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * abstract superclass for all types of diagramrepos, contains all logic for maintaining the state of the diagram
 */
public abstract class DiagramView implements Serializable {

    private LabelView labelView;
    private PartyView partyView;
    private MessageView messageView;

    /**
     * constructs a new DiagramView
     * @param labelView the labelrepo for this diagramrepo
     * @param partyView the partyrepo for this diagramrepo
     * @param messageView the messagerepo for this diagramrepo
     */
    public DiagramView(LabelView labelView, PartyView partyView, MessageView messageView) {
        this.setLabelView(labelView);
        this.setPartyView(partyView);
        this.setMessageView(messageView);
    }

    /**
     * @return the labelrepo of this diagramrepo
     */
    public LabelView getLabelView() {
        return labelView;
    }

    /**
     * sets the labelrepo for this diagramrepo to the given labelrepo
     * @param labelView the labelrepo for this diagramrepo
     * @throws IllegalArgumentException if the provided labelrepo is null
     */
    private void setLabelView(LabelView labelView) throws IllegalArgumentException {
        if (labelView == null) {
            throw new IllegalArgumentException("labelView may not be null");
        }
        this.labelView = labelView;
    }

    /**
     * @return the partyrepo of this diagramrepo
     */
    public PartyView getPartyView() {
        return partyView;
    }

    /**
     * sets the partyrepo for this diagramrepo to the given diagramrepo
     * @param partyView the partyrepo for this diagramrepo
     * @throws IllegalArgumentException if the provided partyrepo is null
     */
    private void setPartyView(PartyView partyView) throws IllegalArgumentException {
        if (partyView == null) {
            throw new IllegalArgumentException("partyView may not be null");
        }
        this.partyView = partyView;
    }

    /**
     * @return the messagerepo of this diagram
     */
    public MessageView getMessageView() {
        return messageView;
    }

    /**
     * sets the message of this diagramrepo to the provided messagerepo
     *
     * @param messageView the messagerepo for this diagramrepo
     * @throws IllegalArgumentException if the provided messagerepo is null
     */
    private void setMessageView(MessageView messageView) throws IllegalArgumentException{
        if (messageView == null) {
            throw new IllegalArgumentException("messageView may not be null");
        }
        this.messageView = messageView;
    }

    /**
     * Finds the element that has been clicked by the location of the MouseEvent
     *
     * @param clickedLocation the location of the MouseEvent
     * @return the element which has been clicked on
     */
    public DiagramElement getSelectedDiagramElement(Point2D clickedLocation){
        Set<DiagramElement> clickedElements = this.getPartyView().getClickedParties(clickedLocation);
        clickedElements.addAll(this.getLabelView().getClickedLabels(clickedLocation));
        if (clickedElements.size() == 1) {
            return clickedElements.stream().findFirst().orElse(null);
        } else if (clickedElements.size() > 1) {
            return findMostLikelyElement(clickedElements, clickedLocation);
        } else {
            for (Party party : getPartyView().getAllParties()) {
                if (isLifeLine(clickedLocation, getPartyView().getXLocationOfLifeline(party))) {
                    return new MessageStart(party, clickedLocation);
                }
            }
        }
        return null;
    }

    /**
     * if multiple elements overlap on the location of a mouseEvent, this function will determine which element was clicked
     *
     * @param clickedElements all overlapping elements
     * @param clickedLocation the location of the MouseClick
     * @return the element that has been clicked on, based on distance to the MouseEvent
     */
    private DiagramElement findMostLikelyElement(Set<DiagramElement> clickedElements, Point2D clickedLocation) {
        DiagramElement selected = null;
        double dist = Double.MAX_VALUE;
        for (DiagramElement element : clickedElements) {
            if (element instanceof Label) {
                Label l = (Label) element;
                if (getLabelView().getLocationOfLabel(l).distance(clickedLocation) < dist) {
                    selected = l;
                    dist = getLabelView().getLocationOfLabel(l).distance(clickedLocation);
                }
            } else if (element instanceof Party) {
                Party p = (Party) element;
                if (getPartyView().getLocationOfParty(p).distance(clickedLocation) < dist) {
                    selected = p;
                    dist = getPartyView().getLocationOfParty(p).distance(clickedLocation);
                }
            }
        }
        return selected;
    }

    /**
     * adds the given party to the repos, with cascading effet
     * @param newParty the new Party to add
     * @param location the location of the new party
     */
    public void addNewPartyToRepos(Party newParty, Point2D location) {
        Point2D correctPartyLocation = getValidPartyLocation(location);
        if (newParty != null) {
            getPartyView().addPartyWithLocation(newParty, correctPartyLocation);
            getLabelView().addLabelWithLocation(newParty.getLabel(),
                    new Point2D.Double(correctPartyLocation.getX() + 10,
                            correctPartyLocation.getY() + 20));
        }
    }

    /**
     * changes the type of the oldParty to that of the newParty in all repos, with cascading effet
     *
     * @param oldParty the old party
     * @param newParty the new party
     */
    public void changePartyTypeInRepos(Party oldParty, Party newParty){
        Point2D location = getPartyView().getLocationOfParty(oldParty);
        Point2D labelLocation = getLabelView().getLocationOfLabel(oldParty.getLabel());

        getPartyView().removeParty(oldParty);
        getLabelView().removeLabelByPosition(labelLocation);

        getPartyView().addPartyWithLocation(newParty, location);
        Point2D labelPosition = getPartyView().getCorrectLabelPosition(newParty);
        getLabelView().addLabelWithLocation(newParty.getLabel(), labelPosition);
    }

    /**
     * deletes the given message from the repos with cascading effect and restores the location of the other messages
     * based on the provided firstmessage
     *
     * @param message the message to remove from the repos
     * @param firstMessage the first message of the diagram
     */
    public void deleteMessageInRepos(Message message, Message firstMessage) {
        getMessageView().removeMessage(message);
        getLabelView().removeLabel(message.getLabel());
        getMessageView().resetMessagePositions(firstMessage, getPartyView(), getLabelView());
    }

    /**
     * Checks whether the location of the UIEvent is a valid location to trigger a new Party instantiation
     *
     * Has to be implemented in subclass
     *
     * @param point2D the position of the UIEvent
     * @return true if the location will trigger the addition of a new party to the diagram, false otherwise
     */
    public abstract boolean isValidPartyLocation(Point2D point2D);

    /**
     * Returns a valid location for the position of a party based on the provided location of the UIEvent
     *
     * Has to implemented in subclasses
     *
     * @param point2D the original position of the UIEvent
     * @return Point2D, the appropriate location for a new Party
     */
    public abstract Point2D getValidPartyLocation(Point2D point2D);

    /**
     * Determines if the location belongs to the lifeline of the given Party
     *
     * @param location              the location of the ClickEvent
     * @param xCoordinateOfLifeline location of the parties lifeline
     * @return true if the location belongs to the lifeline of the party, false otherwise
     */
    public abstract boolean isLifeLine(Point2D location, double xCoordinateOfLifeline);

    /**
     * Finds the receiver of a message based on the endlocation of the messageDrag
     *
     * @param endlocation the location where the dragging for the message stopped
     * @return the party that corresponds to the location
     */
    public Party findReceiver(Point2D endlocation) {
        for (Party party : this.getPartyView().getAllParties()) {
            if (isLifeLine(endlocation, this.getPartyView().getXLocationOfLifeline(party))) {
                return party;
            }
        }
        return null;
    }

    /**
     * returns a deep copy of the provided original diagramRepo
     * @param orig the original diagram view
     * @return the deep copy of the provided original
     */
    public static DiagramView copy(DiagramView orig) {
        PartyView partyView = new PartyView(new HashMap<>(orig.getPartyView().getMap()));
        LabelView labelView = new LabelView(new HashMap<>(orig.getLabelView().getMap()));
        if(orig.getMessageView() instanceof SequenceMessageView){
            SequenceMessageView smrepo = (SequenceMessageView) orig.getMessageView();
            SequenceMessageView newSmr = new SequenceMessageView(new HashMap<>(smrepo.getMap()));
            return new SequenceView(labelView, partyView, newSmr);
        }
        else{
            CommunicationMessageView comrepo = (CommunicationMessageView) orig.getMessageView();
            CommunicationMessageView newCom = new CommunicationMessageView(new ArrayList<>(comrepo.getMap()));
            return new CommunicationView(labelView, partyView, newCom);
        }
    }

    /**
     * Class to help adding messages, stocks the Startlocation and Sender of a new message
     */
    public class MessageStart extends DiagramElement {

        Party party;
        Point2D startloction;

        private MessageStart(Party party, Point2D startLocation) {
            this.party = party;
            this.startloction = startLocation;
        }

        public Party getParty() {
            return this.party;
        }

        public Point2D getStartloction() {
            return this.startloction;
        }
    }
}
