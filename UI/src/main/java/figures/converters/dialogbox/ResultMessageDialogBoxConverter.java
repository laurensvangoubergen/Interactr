package figures.converters.dialogbox;

import figures.drawable.subwindowFigures.ResultMessageDialogBoxFigure;
import window.dialogbox.ResultMessageDialogBox;

public class ResultMessageDialogBoxConverter extends DialogBoxConverter {

    private ResultMessageDialogBox dialogBox;

    public ResultMessageDialogBoxConverter(ResultMessageDialogBox dialogBox) {
        this.dialogBox = dialogBox;
        this.figure = new ResultMessageDialogBoxFigure(dialogBox);
    }
}
