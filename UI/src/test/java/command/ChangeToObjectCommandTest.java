package command;

import command.changeType.ChangeToObjectCommand;
import diagram.label.Label;
import diagram.label.PartyLabel;
import diagram.party.Actor;
import diagram.party.Party;
import exceptions.DomainException;
import org.junit.Before;
import org.junit.Test;
import window.diagram.DiagramSubwindow;

import java.awt.geom.Point2D;

import static org.junit.Assert.assertTrue;

public class ChangeToObjectCommandTest {

    private ChangeToObjectCommand changeToObjectCommand;
    private DiagramSubwindow subwindow;
    private Party party;

    @Before
    public void setUp(){
        try {
            Label label = new PartyLabel(":Jos");
            party = new Actor(label);
            subwindow = new DiagramSubwindow(new Point2D.Double(100, 100));
            subwindow.getFacade().addPartyToRepo(party,new Point2D.Double(150,150));
            changeToObjectCommand = new ChangeToObjectCommand(subwindow, party);
        }catch(DomainException e){
            System.out.println("domainexception changetoobjectcommandtest setup");
        }
    }

    @Test
    public void test_performAction(){
        changeToObjectCommand.performAction();
        assertTrue(!subwindow.getFacade().getDiagram().getParties().contains(party));
    }
}
