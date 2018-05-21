package figures.converters;

import controller.CanvasController;

import controller.InteractionController;
import figures.converters.diagramSub.CommunicationConverter;
import figures.converters.diagramSub.SequenceConverter;
import figures.converters.dialogbox.*;
import window.Subwindow;
import window.diagram.DiagramSubwindow;
import view.diagram.CommunicationView;
import view.diagram.SequenceView;
import window.dialogbox.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is the most general of the converter classes. All the drawing starts here.
 * Objects get seperated here to be drawn by their specific converter
 */
public class FigureConverter {

    private SubwindowConverter converter;

    /**
     * main draw function
     *
     * @param graphics         object used to draw on the program's window
     * @param canvasController the controller that has acces to all the stuff to be drawn
     */
    public void draw(Graphics graphics, CanvasController canvasController) {
        drawBackGroundColor(graphics);
        drawSubwindows(graphics, sortDiagramSubwindows(canvasController));

    }

    //TODO information expert, deze zou in canvasController kunenn staan en dan "new FigureConverter('een List<Subs>').draw(g)"

    /**
     * method that gathers and sorts all the Subwindows
     *
     * @param canvasController
     * @return List containing all subwindows
     */
    private List<Subwindow> sortDiagramSubwindows(CanvasController canvasController) {
        List<Subwindow> result = new ArrayList<Subwindow>();

        for (InteractionController interaction : canvasController.getInteractionControllers()) {
            result.addAll(interaction.getSubwindows());
        }

        Collections.sort(result);
        return result;
    }

    /**
     * function that draws all the subwindows from the given list
     *
     * @param graphics      object used to draw on the program's window
     * @param allSubwindows List containing all subwindows
     */
    private void drawSubwindows(Graphics graphics, List<Subwindow> allSubwindows) {
        DiagramSubwindow tempDS;
        for (Subwindow sub : allSubwindows) {
            setConverter(sub);
            converter.draw(graphics);
        }
    }

    /**
     * function that draws the backround gray
     *
     * @param graphics object used to draw on the program's window
     */
    private void drawBackGroundColor(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, 2000, 1000);
        graphics.setColor(Color.BLACK);
    }

    //TODO in DiagramSubwindow methode "public boolean isSequence() en ... isCommunication"
    //TODO instance van conveters?
    /**
     * creates and sets the correct converter for a subwindow
     *
     * @param sub subwindow that we want a converter for
     */
    public void setConverter(Subwindow sub) {
        if (sub instanceof DiagramSubwindow) {
            DiagramSubwindow temp = ((DiagramSubwindow) sub);
            if (temp.getFacade().getActiveView() instanceof SequenceView) {
                converter = new SequenceConverter(temp);
            } else if (temp.getFacade().getActiveView() instanceof CommunicationView) {
                converter = new CommunicationConverter(temp);
            }
        } else if (sub instanceof PartyDialogBox) {
            converter = new PartyDialogBoxConverter((PartyDialogBox) sub);
        } else if (sub instanceof InvocationMessageDialogBox) {
            converter = new InvocationMessageDialogBoxConverter((InvocationMessageDialogBox) sub);
        } else if (sub instanceof ResultMessageDialogBox) {
            converter = new ResultMessageDialogBoxConverter((ResultMessageDialogBox) sub);
        } else if (sub instanceof DiagramDialogBox) {
            converter = new DiagramDialogBoxConverter((DiagramDialogBox) sub);
        }
    }
}