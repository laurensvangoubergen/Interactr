package handler;

import canvascomponents.CanvasComponent;

public class SetPartyTypeHandler extends Handler{

    public SetPartyTypeHandler(CanvasComponent canvasComponent, UIEvent uiEvent){
        super(canvasComponent, uiEvent);
    }

    @Override
    public boolean HandleEvent(CanvasComponent component, UIEvent uiEvent) {
        return false;
    }


}
