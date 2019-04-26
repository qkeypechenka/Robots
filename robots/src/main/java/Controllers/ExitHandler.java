package main.java.Controllers;

import main.java.Localization.Localization;
import main.java.Serialization.WindowSerializer;

import javax.swing.*;


public class ExitHandler {

    private Closable window;

    public ExitHandler(Closable window) {
        this.window = window;
    }

    private void dispose() {
        window.dispose();
    }

    private void exit() {
        System.exit(0);
    }

    public void onClose(CloseOptions option) {
        var decision = JOptionPane.showOptionDialog(new JFrame(), Localization.getConfirmExit(), null,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new Object[] {Localization.getConfirmYes(), Localization.getConfirmNo()},
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
