package figures;

import diagram.Diagram;
import diagram.message.InvocationMessage;
import diagram.message.Message;
import diagram.party.Party;
import figures.Drawer.DiagramSpecificDrawers.CommunicationActorDrawer;
import figures.Drawer.DiagramSpecificDrawers.CommunicationInvokeMessageDrawer;
import figures.Drawer.DiagramSpecificDrawers.CommunicationObjectDrawer;
import figures.Drawer.DiagramSpecificDrawers.CommunicationResponseMessageDrawer;
import figures.Drawer.Drawer;
import repo.diagram.DiagramRepo;
import repo.message.CommunicationMessageRepo;
import repo.message.MessageRepo;
import repo.message.SequenceMessageRepo;
import repo.party.PartyRepo;
import util.PartyPair;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;

public class CommunicationFigureConverter extends Converter{

    private Drawer actorDrawingStrategy,
            objectDrawingStrategy,
            invokeMessageDrawingStrategy,
            responseMessageDrawingStrategy;
    /**
     * default constructor
     */
    public CommunicationFigureConverter() {
        actorDrawingStrategy = new CommunicationActorDrawer();
        objectDrawingStrategy = new CommunicationObjectDrawer();
        invokeMessageDrawingStrategy = new CommunicationInvokeMessageDrawer();
        responseMessageDrawingStrategy = new CommunicationResponseMessageDrawer();
    }

    @Override
    public void draw(Graphics graphics, DiagramRepo repo, Diagram diagram) {
        drawParties(graphics, repo.getPartyRepo(), actorDrawingStrategy, objectDrawingStrategy);
        drawMessages(graphics, repo.getMessageRepo(), repo.getPartyRepo().getMap(), null);
        drawLabels(graphics, repo.getLabelRepo());
        //drawSelectionBox( ... );
    }

    @Override
    protected void drawMessages(Graphics graphics, MessageRepo messageRepo, Map<Party, Point2D> partyMap, Message firstMessage) {
        CommunicationMessageRepo repo = (CommunicationMessageRepo)messageRepo;

        List<PartyPair> pairs = repo.getMap();

        for (PartyPair pair : pairs) {
            int spread = PartyRepo.OBJECTHEIGHT / pair.getNumberOfMessages();
            Point2D start, end;

            for (int i = 0; i < pair.getNumberOfMessages(); i++) {
                start = calculateStart(i * spread, pair, partyMap);
                end = calculateEnd(i * spread, pair, partyMap);
                invokeMessageDrawingStrategy.draw(graphics, start, end, "");
            }
        }
    }

    /**
     * calculates start point of an arrow, position depends on how many messages are sent from the first party to the second
     *
     * @param spaceBetweenArrows
     * @param pair
     * @param partyMap
     * @return start point of the arrow
     */
    public Point2D calculateStart(int spaceBetweenArrows, PartyPair pair, Map<Party, Point2D> partyMap) {
        double x, y;
        x = partyMap.get(pair.getSender()).getX();
        y = partyMap.get(pair.getSender()).getY();
        return new Point2D.Double(x, y + spaceBetweenArrows);
    }

    /**
     * calculates end point of an arrow, position depends on how many messages are sent from the first party to the second
     *
     * @param spaceBetweenArrows
     * @param pair
     * @param partyMap
     * @return end point of the arrow
     */
    public Point2D calculateEnd(int spaceBetweenArrows, PartyPair pair, Map<Party, Point2D> partyMap) {
        double x, y;
        x = partyMap.get(pair.getReceiver()).getX();
        y = partyMap.get(pair.getReceiver()).getY();
        return new Point2D.Double(x, y + spaceBetweenArrows);
    }
}