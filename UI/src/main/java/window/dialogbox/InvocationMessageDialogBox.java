package window.dialogbox;

import action.*;
import diagram.DiagramElement;
import diagram.label.InvocationMessageLabel;
import diagram.message.InvocationMessage;
import exception.UIException;
import exceptions.DomainException;
import uievents.KeyEvent;
import uievents.MouseEvent;
import window.diagram.DiagramSubwindow;
import window.elements.DialogboxElement;
import window.elements.ListBox;
import window.elements.button.FakeButton;
import window.elements.button.TextualFakeButton;
import window.elements.textbox.ArgumentTextBox;
import window.elements.textbox.MethodTextBox;
import window.elements.textbox.TextBox;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * dialogbox for changing invocation messages
 */
public class InvocationMessageDialogBox extends DialogBox {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 300;

    private MethodTextBox methodTextBox;
    private ArgumentTextBox argumentTextBox;

    private ListBox argumentListBox;

    private FakeButton addArgument;
    private FakeButton deleteArgument;
    private FakeButton moveUp;
    private FakeButton moveDown;

    private DialogboxElement selected;

    private List<DialogboxElement> dialogboxElements;

    private InvocationMessageLabel invocationMessageLabel;
    private DiagramSubwindow subwindow;

    /**
     * create a new invocation message dialog box
     *
     * @param pos                    the new position for the invocation message dialog box
     * @param invocationMessageLabel the label this dialogbox servers a purpose for
     * @param subwindow              the subwindow this dialogbox was created for
     * @throws UIException if the pos is null
     */
    public InvocationMessageDialogBox(Point2D pos, InvocationMessageLabel invocationMessageLabel, DiagramSubwindow subwindow) throws UIException {
        super(pos);
        this.invocationMessageLabel = invocationMessageLabel;
        methodTextBox = new MethodTextBox(new Point2D.Double(10, 50), "method");
        argumentTextBox = new ArgumentTextBox(new Point2D.Double(10, 75), "argument");
        addArgument = new TextualFakeButton(new Point2D.Double(10, 100), "Add");
        deleteArgument = new TextualFakeButton(new Point2D.Double(50, 100), "Del");
        moveDown = new TextualFakeButton(new Point2D.Double(90, 100), "Down");
        moveUp = new TextualFakeButton(new Point2D.Double(130, 100), "Up");

        this.subwindow = subwindow;

        argumentListBox = new ListBox(new Point2D.Double(10, 140), "");

        dialogboxElements = new ArrayList<>();
        dialogboxElements.add(methodTextBox);
        dialogboxElements.add(argumentTextBox);
        dialogboxElements.add(addArgument);
        dialogboxElements.add(deleteArgument);
        dialogboxElements.add(moveDown);
        dialogboxElements.add(moveUp);
        dialogboxElements.add(argumentListBox);

        selected = methodTextBox;

        this.setHeight(HEIGHT);
        this.setWidth(WIDTH);

        updateFields((InvocationMessage) subwindow.getFacade().findParentElement(invocationMessageLabel));
        argumentListBox.setSelectedIndex(invocationMessageLabel.getArguments().size() - 1);
        invocationMessageLabel.setIndex(invocationMessageLabel.getArguments().size() - 1);
    }

    /**
     * @return the default width for this dialogbox type
     */
    public static int getWIDTH() {
        return WIDTH;
    }

    /**
     * @return the default height for this dialogbox type
     */
    public static int getHEIGHT() {
        return HEIGHT;
    }

    /**
     * @return the textbox for method
     */
    public MethodTextBox getMethodTextBox() {
        return methodTextBox;
    }

    /**
     * @return the textbox for arguments
     */
    public ArgumentTextBox getArgumentTextBox() {
        return argumentTextBox;
    }

    /**
     * @return the listbox containing the arguments
     */
    public ListBox getArgumentListBox() {
        return argumentListBox;
    }

    /**
     * @return the button responsible for adding arguments
     */
    public FakeButton getAddArgument() {
        return addArgument;
    }

    /**
     * @return the button responsible for deleting arguments
     */
    public FakeButton getDeleteArgument() {
        return deleteArgument;
    }

    /**
     * @return the button for moving up arguments
     */
    public FakeButton getMoveUp() {
        return moveUp;
    }

    /**
     * @return the button for moving down arguments
     */
    public FakeButton getMoveDown() {
        return moveDown;
    }

    /**
     * @return the currently selected dialogbox element
     */
    public DialogboxElement getSelected() {
        return selected;
    }

    /**
     * @return all dialogbox elements in this dialogbox
     */
    public List<DialogboxElement> getDialogboxElements() {
        return dialogboxElements;
    }

    /**
     * @return the label that's being edited by this invocation message label
     */
    public InvocationMessageLabel getInvocationMessageLabel() {
        return invocationMessageLabel;
    }

    /**
     * @return the subwindow this dialogbox was created for
     */
    public DiagramSubwindow getSubwindow() {
        return subwindow;
    }

    /**
     * handle a mouseEvent
     *
     * @param mouseEvent the mouseEvent to handle
     * @return an action detailing the outcome of the handling
     */
    @Override
    public Action handleMouseEvent(MouseEvent mouseEvent) {
        switch (mouseEvent.getMouseEventType()) {
            case PRESSED:
                return handlePressed(mouseEvent.getPoint());
            default:
                break;
        }
        return new EmptyAction();
    }

    /**
     * handle the moouse pressed event at the given location
     *
     * @param point the point of the event
     * @return an action detailing the outcome of the handling
     */
    private Action handlePressed(Point2D point) {
        if (methodTextBox.isClicked(point)) {
            selected = methodTextBox;
        } else if (argumentTextBox.isClicked(point)) {
            selected = argumentTextBox;
        } else if (addArgument.isClicked(point)) {
            selected = addArgument;
            return handleAddArgument();
        } else if (argumentListBox.isClicked(point)) {
            selected = argumentListBox;
            handleArgumentListBoxClick(point);
        }
        if (additionalButtonsAreActive()) {
            if (deleteArgument.isClicked(point)) {
                selected = deleteArgument;
                return handleDeleteArgument();
            } else if (moveDown.isClicked(point)) {
                selected = moveDown;
                return handleMoveDown();
            } else if (moveUp.isClicked(point)) {
                selected = moveUp;
                return handleMoveUp();
            }
        }
        return new EmptyAction();
    }

    /**
     * handle a keyEvent
     *
     * @param keyEvent the keyEvent to handle
     * @return an action detailing the outcome of the handling
     */
    @Override
    public Action handleKeyEvent(KeyEvent keyEvent) {
        try {
            switch (keyEvent.getKeyEventType()) {
                case BACKSPACE:
                    return handleBackSpace();
                case CHAR:
                    return handleChar(keyEvent);
                case ARROWKEYDOWN:
                    handleArrowKeyDown();
                    break;
                case ARROWKEYUP:
                    handleArrowKeyUp();
                    break;
                case SPACE:
                    return handleSpace();
                case TAB:
                    cycleSelectedElement();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new EmptyAction();
    }

    /**
     * handle the backspace event
     *
     * @return an action detailing the outcome of the handling
     * @throws DomainException if illegal modifications are made
     */
    private Action handleBackSpace() throws DomainException {
        if (selected instanceof TextBox) {
            TextBox t = (TextBox) selected;
            t.deleteLastCharFromContents();
            if (t == methodTextBox) {
                return changeMethod();
            }
        }
        return new EmptyAction();
    }

    /**
     * handle a char KeyEvent
     *
     * @param keyEvent the keyEvent containing the char
     * @return an action detailing the outcome of the handling
     * @throws DomainException if illegal modifications are made
     */
    private Action handleChar(KeyEvent keyEvent) throws DomainException {
        if (selected instanceof TextBox) {
            TextBox t = (TextBox) selected;
            t.addCharToContents(keyEvent.getKeyChar());
            if (t == methodTextBox) {
                return changeMethod();
            }
        }
        return new EmptyAction();
    }

    /**
     * change the method of the invocation message
     *
     * @return an action detailing the outcome of the handling
     * @throws DomainException if illegal modifications are made
     */
    private Action changeMethod() throws DomainException {
        if (methodTextBox.hasValidContents()) {
            invocationMessageLabel.setLabel(methodTextBox.getContents());
            return new UpdateLabelAction(subwindow.getFacade().findParentElement(invocationMessageLabel), invocationMessageLabel);
        }
        return new EmptyAction();
    }

    /**
     * handle the space event
     *
     * @return an action detailing the outcome of the handling
     */
    private Action handleSpace() {
        if (addArgument == selected) {
            return handleAddArgument();
        } else if (additionalButtonsAreActive()) {
            if (deleteArgument == selected) {
                return handleDeleteArgument();
            } else if (moveDown == selected) {
                return handleMoveDown();
            } else if (moveUp == selected) {
                return handleMoveUp();
            }
        }
        return new UpdateLabelContainersAction(invocationMessageLabel);
    }

    /**
     * checks if the additional buttons are active
     *
     * @return true if the additional buttons are active
     */
    private boolean additionalButtonsAreActive() {
        return argumentListBox.hasSelectedArgument();
    }

    /**
     * handle adding an argument
     *
     * @return an action detailing the outcome of the handling
     */
    private Action handleAddArgument() {
        if (argumentTextBox.hasValidContents()) {
            String argumentString = argumentTextBox.getContents();
            argumentListBox.addArgument(argumentString);
            invocationMessageLabel.addArgument(argumentString);
            return new UpdateLabelAction(subwindow.getFacade().findParentElement(invocationMessageLabel), invocationMessageLabel);
        }
        return new EmptyAction();
    }

    /**
     * handles a click on the argument list box
     *
     * @param point the point to handle
     */
    private void handleArgumentListBoxClick(Point2D point) {
        selected = argumentListBox;
        argumentListBox.selectArgument(point);
        invocationMessageLabel.setIndex(argumentListBox.getSelectedIndex());
    }

    /**
     * handles deleting an argument
     *
     * @return an action detailing the outcome of the handling
     */
    private Action handleDeleteArgument() {
        invocationMessageLabel.deleteArgument(argumentListBox.getSelectedIndex());
        argumentListBox.removeArgument();
        return new UpdateLabelAction(subwindow.getFacade().findParentElement(invocationMessageLabel), invocationMessageLabel);
    }

    /**
     * handles moving up arguments
     *
     * @return an action detailing the outcome of the handling
     */
    private Action handleMoveUp() {
        argumentListBox.moveUp();
        invocationMessageLabel.moveUp();
        return new UpdateLabelAction(subwindow.getFacade().findParentElement(invocationMessageLabel), invocationMessageLabel);
    }

    /**
     * handle moving down arguments
     *
     * @return an action detailing the outcome of the handling
     */
    private Action handleMoveDown() {
        argumentListBox.moveDown();
        invocationMessageLabel.moveDown();
        return new UpdateLabelAction(subwindow.getFacade().findParentElement(invocationMessageLabel), invocationMessageLabel);
    }

    /**
     * handle the array key down event
     */
    private void handleArrowKeyDown() {
        argumentListBox.setSelectedIndex(argumentListBox.getSelectedIndex() + 1);
        invocationMessageLabel.setIndex(invocationMessageLabel.getIndex() + 1);
    }

    /**
     * handle the arrow key up event
     */
    private void handleArrowKeyUp() {
        argumentListBox.setSelectedIndex(argumentListBox.getSelectedIndex() - 1);
        invocationMessageLabel.setIndex(invocationMessageLabel.getIndex() - 1);
    }

    /**
     * cycle the selected argument
     */
    private void cycleSelectedElement() {
        int oldIndex = dialogboxElements.indexOf(selected);
        selected = dialogboxElements.get((oldIndex + 1) % 7);
    }

    /**
     * handles an action that could change this dialogbox type
     *
     * @param action the action to handle
     */
    @Override
    public void handleAction(Action action) {
        if (action instanceof RemoveInViewsAction) {
            RemoveInViewsAction a = (RemoveInViewsAction) action;
            for(DiagramElement element : a.getDeletedElements()) {
                if(element instanceof InvocationMessage) {
                    InvocationMessage inv = (InvocationMessage) element;
                    if (inv.getLabel() == invocationMessageLabel) {
                        this.getFrame().close();
                    }
                }
            }
        }
        if (action instanceof UpdateLabelAction) {
            UpdateLabelAction a = (UpdateLabelAction) action;
            InvocationMessage invocationMessage = (InvocationMessage) subwindow.getFacade().findParentElement(invocationMessageLabel);
            if (a.getElement().equals(invocationMessage)) {
                updateFields((InvocationMessage) a.getElement());
            }
        }
    }

    /**
     * update all fields for the given invocation message
     *
     * @param invocationMessage the message to change the fields for
     */
    private void updateFields(InvocationMessage invocationMessage) {
        if (invocationMessage != null) {
            InvocationMessageLabel label = (InvocationMessageLabel) invocationMessage.getLabel();
            methodTextBox.setContents(label.getLabel());
            argumentListBox.setArguments(label.getArguments().stream()
                                            .map(s -> s.toString())
                                            .collect(Collectors.toList()));
        }
    }

    /**
     * sets the given element as the selected
     *
     * @param dialogboxElement the new selected dialogboxelement
     */
    public void setSelected(DialogboxElement dialogboxElement) {
        this.selected = dialogboxElement;
    }
}
