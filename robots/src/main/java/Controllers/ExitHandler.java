package main.java.Controllers;

import main.java.Serialization.WindowSerializer;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;


public class ExitHandler {

    private Closable window;
    public ResourceBundle resources;

    public ExitHandler(Closable window, ResourceBundle resources) {
        this.window = window;
        this.resources = resources;
    }

    private void dispose() {
        window.dispose();
    }

    private void exit() {
        System.exit(0);
    }

    public void onClose(CloseOptions option) {
        var decision = JOptionPane.showOptionDialog(new JFrame(), "Are you sure?", null,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Yes", "No"},
                JOptionPane.YES_OPTION);

        if (decision == JOptionPane.YES_OPTION) {
            switch (option) {
                case Exit:
                    WindowSerializer.serializeStored();
                    exit();
                case DispsoseOnly:
                    dispose();
            }
        }
    }
}
