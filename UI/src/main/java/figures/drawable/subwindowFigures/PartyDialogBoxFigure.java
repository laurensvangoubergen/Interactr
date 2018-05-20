package figures.drawable.subwindowFigures;

import diagram.party.Actor;
import figures.drawable.diagramFigures.RadioButtonFigure;
import figures.drawable.diagramFigures.SelectedRadioButtonFigure;
import figures.drawable.diagramFigures.TextBoxFigure;
import window.dialogbox.PartyDialogBox;

import java.awt.*;

// TODO dialog box for a party shows
//two text boxes and two radio buttons, for editing the instance name and the class
//name and for choosing between the actor and object form, respectively.
public class PartyDialogBoxFigure extends SubwindowFigure {

    private PartyDialogBox dialogBox;

    /**
     * @param dialogBox
     */
    public PartyDialogBoxFigure(PartyDialogBox dialogBox) {
        super(dialogBox.getFrame());
        this.dialogBox = dialogBox;
    }

    @Override
    public void draw(Graphics graphics, int minX, int minY, int maxX, int maxY) {
        super.draw(graphics, minX, minY, maxX, maxY);
        drawRadioButtons(graphics, minX, minY, maxX, maxY);
        drawTextBoxes(graphics, minX, minY, maxX, maxY);
    }

    private void drawTextBoxes(Graphics graphics, int minX, int minY, int maxX, int maxY) {
        new TextBoxFigure(dialogBox.getInstanceTextBox(), PartyDialogBox.INSTANCE_DESCRIPTION)
                .draw(graphics, minX, minY, maxX, maxY);
        new TextBoxFigure(dialogBox.getClassTextBox(), PartyDialogBox.CLASS_DESCRIPTION)
                .draw(graphics, minX, minY, maxX, maxY);
    }

    public void drawRadioButtons(Graphics graphics, int minX, int minY, int maxX, int maxY){
        new RadioButtonFigure(dialogBox.getToActor(), PartyDialogBox.TOACTOR_DESCRIPTION)
                .draw(graphics, minX, minY, maxX, maxY);
        new RadioButtonFigure(dialogBox.getToObject(), PartyDialogBox.TOOBJECT_DESPCRIPTION)
                .draw(graphics, minX, minY, maxX, maxY);
        drawSelectedRadioButton(graphics, minX, minY, maxX, maxY);
    }

    private void drawSelectedRadioButton(Graphics graphics, int minX, int minY, int maxX, int maxY) {
        if (dialogBox.getParty() instanceof Actor) {
            new SelectedRadioButtonFigure(dialogBox.getToActor(), "")
                    .draw(graphics, minX, minY, maxX, maxY);
        } else if (dialogBox.getParty() instanceof  Object) {
            new SelectedRadioButtonFigure(dialogBox.getToObject(),"")
                    .draw(graphics, minX, minY, maxX, maxY);
        }
    }
}
