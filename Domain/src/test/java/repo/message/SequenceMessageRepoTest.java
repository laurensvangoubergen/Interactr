package repo.message;

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
import exceptions.DomainException;
import org.junit.Before;
import org.junit.Test;
import repo.label.LabelRepo;
import repo.party.PartyRepo;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SequenceMessageRepoTest {

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

    private PartyRepo partyRepo;
    private LabelRepo labelRepo;

    private SequenceMessageRepo smrepo;

    @Before
    public void setUp(){
        try {
            validPoint1 = new Point2D.Double(50,50);
            validPoint2 = new Point2D.Double(100,100);
            validPoint3 = new Point2D.Double(150,150);

            label1 = new PartyLabel("a:A");
            actor1 = new Actor(label1);

            label2 = new PartyLabel("b:B");
            object2 = new Object(label2);

            label3 = new PartyLabel("c:C");
            actor3 = new Actor(label3);

            invLabell = new MessageLabel("invocation1");
            invLabel2 = new MessageLabel("invocation2");
            invLabel3 = new MessageLabel("invocation3");

            res3 = new ResultMessage(null, new MessageLabel(""),actor1,actor3);
            inv3 = new InvocationMessage(res3, invLabel3,actor3,actor1);

            res2 = new ResultMessage(inv3, new MessageLabel(""), actor1,object2);
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

            partyRepo = new PartyRepo();
            partyRepo.addPartyWithLocation(actor1, validPoint1);
            partyRepo.addPartyWithLocation(object2, validPoint2);
            partyRepo.addPartyWithLocation(actor3, validPoint3);
            labelRepo = new LabelRepo();

            smrepo = new SequenceMessageRepo();
            smrepo.addMessages(messages, inv1, partyRepo, labelRepo);
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    public void Test_default_constructor_works(){
        MessageRepo r = new SequenceMessageRepo();
        assertNotNull(((SequenceMessageRepo) r).getMap());
    }

    @Test
    public void Test_advanced_constructor_works(){
        SequenceMessageRepo r = new SequenceMessageRepo();
        assertNotNull(r.getMap());
    }

    @Test
    public void Test_add_all_messages_stores_messages_correctly(){
        SequenceMessageRepo s = new SequenceMessageRepo();
        s.addMessages(messages, inv1, partyRepo, labelRepo);
        assertTrue(s.getAllMessages().containsAll(messages));
        assertTrue(s.getLocationOfMessage(inv1) == 120);
        assertTrue(s.getLocationOfMessage(inv2) == 155);
        assertTrue(s.getLocationOfMessage(res1) == 190);
        assertTrue(s.getLocationOfMessage(res2) == 225);
        assertTrue(s.getLocationOfMessage(inv3) == 260);
        assertTrue(s.getLocationOfMessage(res3) == 295);
        assertTrue(s.getAllMessages().size() == 6);
    }

    @Test
    public void Test_add_all_messsages_stores_labels_correctly(){
        assertEquals(new Point2D.Double(75, 105), labelRepo.getLocationOfLabel(invLabell));
        assertEquals(new Point2D.Double(125, 140), labelRepo.getLocationOfLabel(invLabel2));
        assertEquals(new Point2D.Double(100, 245), labelRepo.getLocationOfLabel(invLabel3));
    }

    @Test
    public void Test_getMessageAtPosition_works() throws DomainException {
        assertEquals(inv1, smrepo.getMessageAtPosition(120));
    }

    @Test (expected = DomainException.class)
    public void Test_getMessageAtPosition_with_empty_location_throws_exception() throws DomainException{
        smrepo.getMessageAtPosition(50);
    }

    @Test
    public void Test_removeMessage_removes_message(){
        smrepo.removeMessage(inv1);
        assertTrue(smrepo.getAllMessages().size() == 5);
        assertFalse(smrepo.getAllMessages().contains(inv1));
    }

    @Test
    public void Test_resetLabelPositionsForMovedParty_works(){
        Point2D newValidPoint = new Point2D.Double(200, 50);
        partyRepo.updatePartyPosition(newValidPoint, actor3);
        smrepo.resetLabelPositionsForMovedParty(labelRepo,partyRepo,actor3);
        assertEquals(new Point2D.Double(150, 140), labelRepo.getLocationOfLabel(invLabel2));
        assertEquals(new Point2D.Double(125, 245), labelRepo.getLocationOfLabel(invLabel3));
    }

    @Test
    public void test_findPrevious_message_works(){
        assertEquals(null, smrepo.findPreviousMessage(smrepo.getLocationOfMessage(inv1), inv1));
        assertEquals(inv1, smrepo.findPreviousMessage(smrepo.getLocationOfMessage(inv2), inv1));
        assertEquals(inv2, smrepo.findPreviousMessage(smrepo.getLocationOfMessage(res1), inv1));
        assertEquals(res1, smrepo.findPreviousMessage(smrepo.getLocationOfMessage(res2), inv1));
        assertEquals(res2, smrepo.findPreviousMessage(smrepo.getLocationOfMessage(inv3), inv1));
        assertEquals(inv3, smrepo.findPreviousMessage(smrepo.getLocationOfMessage(res3), inv1));
    }
}
