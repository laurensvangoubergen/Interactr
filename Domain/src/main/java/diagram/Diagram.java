package diagram;

import diagram.label.Label;
import diagram.label.MessageLabel;
import diagram.label.PartyLabel;
import diagram.message.ResultMessage;
import diagram.message.InvocationMessage;
import diagram.message.Message;
import diagram.party.Actor;
import diagram.party.Object;
import diagram.party.Party;
import exceptions.DomainException;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Superclass of diagrams, contains most of the business logic in changing the diagram
 */
public class Diagram {

    /**********************************************************************************************************/

    ////////////////////////////////////
    //  variables
    ////////////////////////////////////

    private List<Party> parties;

    private Message firstMessage;

    /**********************************************************************************************************/

    ////////////////////////////////////
    //  constructors
    ////////////////////////////////////

    /**
     * create a new blank diagram
     */
    public Diagram() {
        this(new ArrayList<Party>(), null);
    }

    /**
     * creates a new diagram with the given parties, the first message of the call stack
     *
     * @param parties
     * @param firstMessage
     */
    public Diagram(List<Party> parties, Message firstMessage) {
        this.setParties(parties);
        this.setFirstMessage(firstMessage);
        this.setMessageNumbers();
    }

    /**********************************************************************************************************/

    ////////////////////////////////////
    //  setters and getters
    ////////////////////////////////////

    /**
     * sets the first message of the message stack
     *
     * @param message the new firstMessage
     */
    private void setFirstMessage(Message message) {
        this.firstMessage = message;
    }

    /**
     * @return the first message of the message stack
     */
    public Message getFirstMessage() {
        return firstMessage;
    }

    /**
     * @return all the parties in this diagram
     */
    public List<Party> getParties() {
        return parties;
    }

    /**
     * sets the parties for this diagram
     *
     * @param parties the parties for this diagram
     */
    private void setParties(List<Party> parties) {
        if (parties != null) {
            this.parties = parties;
        } else {
            this.parties = new ArrayList<>();
        }
    }

    /**
     * adds a new party to the diagram
     *
     * @param party the party to be added
     */
    public void addParty(Party party) {
        this.getParties().add(party);
    }

    /**
     * removes a party from the diagram
     *
     * @param party the party to be removed
     */
    public void removeParty(Party party) {
        this.getParties().remove(party);
    }

    /**********************************************************************************************************/

    ////////////////////////////////////
    //  main part of business logic
    ////////////////////////////////////

    /**
     * Adds a new party in the form of an object
     *
     * @return the newly added party
     */
    public Party addNewParty() {
        PartyLabel label;
        Party object = null;
        try {
            label = new PartyLabel("|");
            object = new Object(label);
            this.addParty(object);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return object;
    }

    /**
     * changes the party type of the Party on the provided location
     *
     * @param oldParty the party to change the type off
     * @return the newly created party
     */
    public Party changePartyType(Party oldParty) {
        Party newParty = null;
        if (oldParty instanceof Object) {
            Object o = (Object) oldParty;
            newParty = new Actor(o.getLabel());
            this.updateMessagesForChangedParty(o, newParty);
            this.removeParty(o);
            this.addParty(newParty);
        } else {
            Actor a = (Actor) oldParty;
            newParty = new Object(a.getLabel());
            this.updateMessagesForChangedParty(a, newParty);
            this.removeParty(a);
            this.addParty(newParty);
        }
        return newParty;
    }

    /**
     * deletes the element whom the given label belongs too
     *
     * @param label the label of the element to delete
     * @return a Set of diagramelements to remove the positions of
     */
    public Set<DiagramElement> deleteElementByLabel(Label label) {
        Set<DiagramElement> deletedElements = new HashSet<>();
        DiagramElement element = findParentElement(label);
        if (element instanceof Party) {
            Party p = (Party) element;
            deletedElements.addAll(deleteParty(p));
        } else if (element instanceof Message) {
            Message m = (Message) element;
            deletedElements.addAll(deleteMessage(m));
        }
        return deletedElements;
    }

    private DiagramElement findParentElement(Label label) {
        for (Party p : parties) {
            if (p.getLabel().equals(label)) {
                return p;
            }
        }
        if (this.getFirstMessage() != null) {
            Message message = this.getFirstMessage();
            while (message != null) {
                if (message.getLabel().equals(label)) {
                    return message;
                }
            }
        }
        return null;
    }

    /**
     * Rearranges the message tree upon deletion of the provided party
     *
     * @param party the party that will be deleted
     * @return a set of messages that have been deleted
     */
    private Set<DiagramElement> rearrangeMessageTreeByParty(Party party) {
        Set<DiagramElement> deletedElements = new HashSet<>();
        deletedElements.add(party);
        if (this.getFirstMessage() != null) {
            Message previous = getFirstMessage();
            if (getFirstMessage().getSender().equals(party) || getFirstMessage().getReceiver().equals(party)) {
                deletedElements.add(getFirstMessage());
                firstMessage = null;
            } else {
                Message message = previous.getNextMessage();
                while (message != null) {
                    if (message.getSender().equals(party) || message.getReceiver().equals(party)) {
                        deletedElements.add(message);
                        List<Message> dependentMessages = skipOverDependentMessages(message, -1);
                        if (dependentMessages.size() > 1) {
                            message = dependentMessages.get(dependentMessages.size() - 1);
                            deletedElements.addAll(dependentMessages.subList(0, dependentMessages.size() - 1));
                            if (previous != null) {
                                previous.setNextMessage(message);
                                previous = message.getNextMessage();
                            }
                        } else if (dependentMessages.size() == 1) {
                            message = dependentMessages.get(dependentMessages.size() - 1);
                            if (previous != null) {
                                previous.setNextMessage(message);
                                previous = message.getNextMessage();
                            }
                        }
                    }
                    message = message.getNextMessage();
                }
            }
        }
        this.setMessageNumbers();
        return deletedElements;
    }

    /**
     * Finds the first message that doesn't directly or indirectly depend on the provided Message
     *
     * @param message the message of which the next not depending descendant message must be found
     * @param stack   integer counting the stack of the messages
     * @return the message that are between the provided message and the next not dependent message, with t he next
     * not dependent message included at the last position
     */
    private List<Message> skipOverDependentMessages(Message message, int stack) {
        List<Message> dependentMessages = new ArrayList<>();
        while (stack < 0) {
            message = message.getNextMessage();
            if (message != null) {
                if (message instanceof InvocationMessage) {
                    stack--;
                    if (stack < 0) {
                        dependentMessages.add(message);
                    }
                } else {
                    stack++;
                    if (stack < 0) {
                        dependentMessages.add(message);
                    }
                }
            } else {
                return null;
            }
        }
        dependentMessages.add(message.getNextMessage());
        return dependentMessages;
    }

    /**
     * loops over the callStack to give each invocation their appropriate messageNumber
     */
    private void setMessageNumbers() {
        if (this.getFirstMessage() != null) {
            boolean active = false;
            int stack = 0;
            Message message = this.getFirstMessage();
            int N = 1;
            int e = 1;
            while (message != null) {
                if (message instanceof InvocationMessage) {
                    stack++;
                    InvocationMessage m = (InvocationMessage) message;
                    if (!active) {
                        m.setMessageNumber("" + N);
                        N++;
                        active = true;
                    } else {
                        N--;
                        m.setMessageNumber("" + N + "." + e);
                        e++;
                        N++;
                    }
                } else {
                    stack--;
                }
                if (stack == 0) {
                    active = false;
                    e = 1;
                }
                message = message.getNextMessage();
            }
        }
    }

    /**
     * Deletes a party from the diagram.
     *
     * @param party the party that will be deleted
     * @return a set of diagramelements that need to be deleted
     */
    private Set<DiagramElement> deleteParty(Party party) {
        Set<DiagramElement> deletedElements = new HashSet<>();
        deletedElements.addAll(rearrangeMessageTreeByParty(party));
        this.removeParty(party);
        return deletedElements;
    }

    /**
     * deletes a message by adding the next independent message to the message preceding it, cutting out the dependent part of the message stack
     *
     * @param message the message to be deleted
     * @return a set of diagremelements that are deleted
     */
    private Set<DiagramElement> deleteMessage(Message message) {
        Set<DiagramElement> deletedElements = new HashSet<>();
        if (message instanceof InvocationMessage) {
            if (!message.equals(this.getFirstMessage())) {
                Message iter = this.getFirstMessage();
                Message previous;
                while (iter.getNextMessage() != null && !iter.getNextMessage().equals(message)) {
                    iter = iter.getNextMessage();
                }
                previous = iter;
                List<Message> dependentMessages = skipOverDependentMessages(message, -1);
                Message next;
                if (dependentMessages.size() > 1) {
                    next = dependentMessages.get(dependentMessages.size() - 1);
                    deletedElements.addAll(dependentMessages.subList(0, dependentMessages.size() - 1));
                    if (previous != null) {
                        previous.setNextMessage(next);
                    }
                } else if (dependentMessages.size() == 1) {
                    next = dependentMessages.get(dependentMessages.size() - 1);
                    if (previous != null) {
                        previous.setNextMessage(next);
                    }
                }
            } else {
                List<Message> dependentMessages = skipOverDependentMessages(message, -1);
                this.setFirstMessage(dependentMessages.get(dependentMessages.size() - 1));
                if (deletedElements.size() > 1) {
                    deletedElements.addAll(dependentMessages.subList(0, dependentMessages.size() - 1));
                }
            }
        }
        return deletedElements;
    }

    /**
     * Determines if the call stack is still in order
     *
     * @param sender the party to check the call stack of
     * @return true if the party is on top of the call stack, false otherwise
     */
    private boolean checkCallStack(Message previous, Party sender) {
        /*if(previous == null || (previous instanceof InvocationMessage && previous.getReceiver().equals(sender))){
            return true;
        }*/
        if (previous == null) {
            return true;
        }
        if (previous.getReceiver().equals(sender)) {
            return true;
        } else if (this.getFirstMessage() != null) {
            return this.getFirstMessage().getSender().equals(sender) && !(previous instanceof InvocationMessage);
        }
        return false;
    }

    /**
     * updates all the messasges to relink them to the appropriate party
     *
     * @param old      the old party of the message
     * @param newParty the new party of the message
     */
    private void updateMessagesForChangedParty(Party old, Party newParty){
        Message message = firstMessage;
        while (message != null) {
            if (message.getReceiver().equals(old)) {
                try {
                    message.setReceiver(newParty);
                } catch (DomainException exc) {
                    System.out.println(exc.getMessage());
                }
            }
            if (message.getSender().equals(old)) {
                try {
                    message.setSender(newParty);
                } catch (DomainException exc) {
                    System.out.println(exc.getMessage());
                }
            }
            message = message.getNextMessage();
        }
    }

    /**
     * Adds a new message to the messagestack, with the receiver being the owner of the lifeline containing the provided location
     *
     * @param sender          sender of the new message
     * @param receiver        receiver of the new message
     * @param previousMessage the message  preceding the new message
     * @return a List containing all newly created messages, on order of the call stack
     * @throws IllegalStateException if the location doesn't respond to a receiving party
     */
    public List<Message> addNewMessage(Party sender, Party receiver, Message previousMessage) throws IllegalStateException {
        ArrayList<Message> newMessages = new ArrayList<>();
        if (!receiver.equals(sender)) {
            if (checkCallStack(previousMessage, sender)) {
                try {
                    Message next;
                    if (previousMessage == null) {
                        next = this.getFirstMessage();
                    } else {
                        next = previousMessage.getNextMessage();
                    }
                    Message resultMessage = new ResultMessage(next, new MessageLabel(""), sender, receiver);
                    MessageLabel messageLabel = new MessageLabel("|");
                    Message invocation = new InvocationMessage(resultMessage, messageLabel, receiver, sender);
                    if (previousMessage != null) {
                        previousMessage.setNextMessage(invocation);
                    } else {
                        this.setFirstMessage(invocation);
                    }
                    newMessages.add(invocation);
                    newMessages.add(resultMessage);
                } catch (DomainException exc) {
                    System.out.println(exc.getMessage());
                }
            }
        }
        setMessageNumbers();
        return newMessages;
    }
}
