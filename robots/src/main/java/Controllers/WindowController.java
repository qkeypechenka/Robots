package main.java.Controllers;

import javax.swing.*;


public class WindowController{

    private Closable window;

    public WindowController(Closable window) {
        this.window = window;
    }

    private void dispose() {
        window.dispose();
    }

    private void exit() {
        System.exit(0);
    }

    public void onClose(CloseOptions option) {
        var desicion = JOptionPane.showOptionDialog(new JFrame(), "Are you sure?", null,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Yes", "No"},
                JOptionPane.YES_OPTION);
        if (desicion == JOptionPane.YES_OPTION) {
            switch (option) {
                case Full:
                    dispose();
                    exit();
                case DispsoseOnly:
                    dispose();
            }
        }
    }
}
