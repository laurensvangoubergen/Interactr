package repo.message;

import diagram.message.Message;
import diagram.party.Party;
import repo.label.LabelRepo;
import repo.party.PartyRepo;

import java.util.List;

public abstract class MessageRepo {

    public static final int HEIGHT = 16;

    public MessageRepo(){
    }

    public abstract void removeMessage(Message message);

    /**
     * sets the yLocation of all messages in the tree to an appropriate number
     */
    public abstract void resetMessagePositions(Message firstMessage, PartyRepo partyRepo, LabelRepo labelRepo);

    public abstract void resetLabelPositionsForMovedParty(LabelRepo labelRepo, PartyRepo partyRepo, Party movedParty);

    public abstract Message findPreviousMessage(int yLocation, Message firstMessage);

    public abstract void addMessages(List<Message> messages, Message firstMessage, PartyRepo partyRepo, LabelRepo labelRepo);
}