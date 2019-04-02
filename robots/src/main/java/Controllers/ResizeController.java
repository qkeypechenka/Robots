package main.java.Controllers;

import java.awt.event.ComponentEvent;

public class ResizeController {

    private Resizable window;

    public ResizeController(Resizable window) {
        this.window = window;
    }

    public void didChangeSize(ComponentEvent event) {
        var size = event.getComponent();
        window.didChangeSize(size);
    }
}
