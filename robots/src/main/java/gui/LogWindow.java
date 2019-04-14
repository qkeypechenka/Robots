package main.java.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import main.java.Controllers.Closable;
import main.java.Controllers.CloseOptions;
import main.java.Controllers.ExitHandler;
import main.java.Serialization.Serializable;
import main.java.Serialization.WindowState;
import main.java.log.LogChangeListener;
import main.java.log.LogEntry;
import main.java.log.LogWindowSource;

public class LogWindow extends JInternalFrame implements LogChangeListener, Closable, Serializable
{
    private LogWindowSource logSource;
    private TextArea logContent;
    private ExitHandler exitHandler;

    public LogWindow(LogWindowSource logSource) 
    {
        super("Протокол работы", true, true, true, true);
        this.logSource = logSource;
        this.logSource.registerListener(this);
        logContent = new TextArea("");
        logContent.setSize(Constants.logContentWidth, Constants.logContentHeight);
        
        JPanel panel = new JPanel(new BorderLayout());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        panel.add(logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        updateLogContent();

        exitHandler = new ExitHandler(this);

        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                exitHandler.onClose(CloseOptions.DispsoseOnly);
            }
        });
    }

    private void updateLogContent()
    {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : logSource.all())
        {
            content.append(entry.getMessage()).append("\n");
        }
        logContent.setText(content.toString());
        logContent.invalidate();
    }

    public WindowState getState() {
        return this.isIcon() ? WindowState.Minimized : WindowState.Default;
    }
    
    @Override
    public void onLogChanged()
    {
        EventQueue.invokeLater(this::updateLogContent);
    }
}
