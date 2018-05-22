package controller;

import command.closeWindow.CloseSubwindowCommand;
import exception.UIException;
import exceptions.DomainException;
import window.diagram.DiagramSubwindow;
import window.Subwindow;
import uievents.KeyEvent;
import uievents.MouseEvent;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Main layer between pure UI and the domain
 *
 * Interprets UIEvents and addresses the appropriate functions in the diagram.
 */
public class CanvasController implements IHighLevelController{

    private InteractionController activeInteractionController;
    private List<InteractionController> interactionControllers;

    /**
     * constructs a new empty canvascontroller
     */
    public CanvasController() {
        this.activeInteractionController = null;
        this.setInteractionControllers(new ArrayList<>());
    }

    public InteractionController getActiveInteractionController() {
        return activeInteractionController;
    }

    public void setActiveInteractionController(InteractionController activeInteractionController) {
        this.activeInteractionController = activeInteractionController;
    }

    public List<InteractionController> getInteractionControllers() {
        return interactionControllers;
    }

    public void setInteractionControllers(List<InteractionController> interactionControllers) {
        this.interactionControllers = interactionControllers;
    }

    public void addInteractionController(InteractionController interactionController){
        if(!this.getInteractionControllers().contains(interactionController)){
            this.interactionControllers.add(interactionController);
        }
        setActiveInteractionController(interactionController);
    }

    public void removeInteractionController(InteractionController interactionController){
        this.interactionControllers.remove(interactionController);
        if(this.getActiveInteractionController().equals(interactionController)){
            setActiveInteractionController(findHighestLevelInteractionController());
        }
    }

    public void changeActiveInteractionController(InteractionController interactionController){
        setActiveInteractionController(interactionController);
    }

    /**
     * handles a key event
     * @param keyEvent the keyEvent to handle
     */
    public void handleKeyEvent(KeyEvent keyEvent) throws DomainException, UIException {
        switch (keyEvent.getKeyEventType()) {
            case CTRLD:
                //checkForDeleteInteractionController();
                if(getActiveInteractionController() != null){
                    activeInteractionController.handleKeyEvent(keyEvent);
                }
                break;
            case CTRLN:
                createNewInteractionController();
                activeInteractionController.handleKeyEvent(keyEvent);
                break;
            default:
                if(this.getActiveInteractionController() != null){
                    activeInteractionController.handleKeyEvent(keyEvent);
                }
                break;
        }
    }

    /**
     * handles MouseEvent that affects the diagramSubwindow
     *          | if dragged == false, check if diagramSubwindow currently is being dragged (update the dragging boolean)
     *          | if dragged == true, check the mouseEventType
     *                          | if RELEASE, pass the mouseEvent to activeDiagramSubwindow and set dragging == false
     *                          | if LEFTCLICK, set dragging == false
     *          | otherwise the appropriate diagramSubwindow that was clicked
     *                          | if the diagramSubwindow exists
     *                              | if the diagramSubwindow is not the active one, set it as the active one
     *                              get the coordinates of the click on the diagramSubwindow, relative to the diagramSubwindow
     *                              pass the mouseEvent to the appropriate diagramSubwindow
     * @param mouseEvent the mouseEvent to handle
     */
    public void handleMouseEvent(MouseEvent mouseEvent) {

        if(activeInteractionController != null && activeInteractionController.isDragging()){
            activeInteractionController.handleMouseEvent(mouseEvent);
        }
        else if (getAppropriateInteractionController(mouseEvent.getPoint()) != null) {
            InteractionController ic = getAppropriateInteractionController(mouseEvent.getPoint());
            if (!ic.equals(getActiveInteractionController())) {
                changeActiveInteractionController(ic);
            }
            ic.handleMouseEvent(mouseEvent);
        }
    }


    private void createNewInteractionController(){
        InteractionController interactionController = new InteractionController();
        this.getInteractionControllers().add(interactionController);
        this.changeActiveInteractionController(interactionController);
    }

    private void checkForDeleteInteractionController(){
        if(activeInteractionController.getActiveDiagramSubwindow() == null){
            if(activeInteractionController.getSubwindows().isEmpty()){
                this.activeInteractionController = null;
            }
        }
        //getSubwindows() kan nog dialogboxes bevatten?
    }


    public InteractionController findHighestLevelInteractionController(){
        InteractionController result = null;
        int level = -1;
        for (InteractionController ic : getInteractionControllers()) {
            Subwindow s = ic.getHighestLevelSubwindow();
            if(s.getLevel() > level){
                level = s.getLevel();
                result = ic;
            }
        }
        return result;
    }


    public InteractionController getAppropriateInteractionController(Point2D clickedLocation){
        InteractionController result = null;
        int level = -1;
        for(InteractionController ic : getInteractionControllers()){
            if(ic.getAppropriateSubwindow(clickedLocation) != null){
                Subwindow s = ic.getAppropriateSubwindow(clickedLocation);
                if(s.getLevel() > level){
                    result = ic;
                    level = s.getLevel();
                }
            }
        }
        return result;
    }
}
