package view.diagram;

import diagram.Diagram;
import diagram.label.Label;
import diagram.label.MessageLabel;
import diagram.label.PartyLabel;
import diagram.message.InvocationMessage;
import diagram.message.Message;
import diagram.message.ResultMessage;
import diagram.party.Actor;
import diagram.party.Object;
import diagram.party.Party;
import org.junit.Before;
import org.junit.Test;
import view.label.LabelView;
import view.message.CommunicationMessageView;
import view.message.SequenceMessageView;
import view.party.PartyView;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SequenceRepoTest{

    private Party actor1;
    private Label label1;

    private Party object2;
    private Label label2;

    private Party actor3;
    private Label label3;

    private Message inv1;
    private Message res1;
    private Label invLabell;

    private Message inv2;
    private Message res2;
    private Label invLabel2;

    private Message inv3;
    private Message res3;
    private Label invLabel3;

    private Point2D validPoint1;
    private Point2D validPoint2;
    private Point2D validPoint3;

    private List<Message> messages;
    private List<Party> parties;

    private PartyView partyView;
    private LabelView labelView;

    private Diagram diagram;

    private Point2D notValidPoint;

    SequenceView sequenceRepo;

    @Before
    public void setUp() {
        try {
            validPoint1 = new Point2D.Double(50, 50);
            validPoint2 = new Point2D.Double(100, 50);
            validPoint3 = new Point2D.Double(150, 50);

            notValidPoint = new Point2D.Double(600, 200);

            label1 = new PartyLabel("a:A");
            actor1 = new Actor(label1);

            label2 = new PartyLabel("b:B");
            object2 = new Object(label2);

            label3 = new PartyLabel("c:C");
            actor3 = new Actor(label3);

            invLabell = new MessageLabel("invocation1");
            invLabel2 = new MessageLabel("invocation2");
            invLabel3 = new MessageLabel("invocation3");

            res3 = new ResultMessage(null, new MessageLabel(""), actor1, actor3);
            inv3 = new InvocationMessage(res3, invLabel3, actor3, actor1);

            res2 = new ResultMessage(inv3, new MessageLabel(""), actor1, object2);
            res1 = new ResultMessage(res2, new MessageLabel(""), object2, actor3);

            inv2 = new InvocationMessage(res1, invLabel2, actor3, object2);
            inv1 = new InvocationMessage(inv2, invLabell, object2, actor1);

            messages = new ArrayList<>();
            messages.add(inv1);
            messages.add(inv2);
            messages.add(res1);
            messages.add(res2);
            messages.add(inv3);
            messages.add(res3);

            parties = new ArrayList<>();
            parties.add(actor1);
            parties.add(object2);
            parties.add(actor3);

            diagram = new Diagram(parties, inv1);

            partyView = new PartyView();
            partyView.addPartyWithLocation(actor1, validPoint1);
            partyView.addPartyWithLocation(object2, validPoint2);
            partyView.addPartyWithLocation(actor3, validPoint3);

            labelView = new LabelView();

            sequenceRepo = new SequenceView();

        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void Test_default_constructor_works(){
        SequenceView s = new SequenceView();
        assertNotNull(s.getMessageView());
        assertNotNull(s.getLabelView());
        assertNotNull(s.getPartyView());
        assertTrue(s.getMessageView() instanceof SequenceMessageView);
        assertFalse(s.getMessageView() instanceof CommunicationMessageView);
    }

    @Test
    public void Test_isValidPartyLocation(){
        assertFalse(sequenceRepo.isValidPartyLocation(notValidPoint));
        assertTrue(sequenceRepo.isValidPartyLocation(validPoint1));
        assertTrue(sequenceRepo.isValidPartyLocation(validPoint2));
        assertTrue(sequenceRepo.isValidPartyLocation(validPoint3));
    }

    @Test
    public void Test_getValidPartyLocation(){
        assertEquals(new Point2D.Double(50,50), sequenceRepo.getValidPartyLocation(validPoint1));
        assertEquals(new Point2D.Double(100,50), sequenceRepo.getValidPartyLocation(validPoint2));
        assertEquals(new Point2D.Double(150,50), sequenceRepo.getValidPartyLocation(validPoint3));
        assertEquals(new Point2D.Double(600,50), sequenceRepo.getValidPartyLocation(notValidPoint));
    }

    @Test
    public void Test_isLifeLine(){
        assertFalse(sequenceRepo.isLifeLine(new Point2D.Double(20,20),20));
        assertFalse(sequenceRepo.isLifeLine(new Point2D.Double(20,60),50));
        assertFalse(sequenceRepo.isLifeLine(new Point2D.Double(20,60),20));
        assertFalse(sequenceRepo.isLifeLine(new Point2D.Double(20,60),10));
        assertTrue(sequenceRepo.isLifeLine(new Point2D.Double(20,120),10));
    }

    @Test
    public void Test_addNewParty_invalid_point_gets_corrected(){
        sequenceRepo.addNewPartyToRepos(actor1, notValidPoint);
        assertEquals(actor1, sequenceRepo.getPartyView().getPartyAtPosition(sequenceRepo.getValidPartyLocation(notValidPoint)));
    }
}
