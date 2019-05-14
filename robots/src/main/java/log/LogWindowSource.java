package main.java.log;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogWindowSource
{
    private int queueLength;

    private Queue<LogEntry> messages;
    private final ArrayList<LogChangeListener> listeners;
    private Queue<LogEntry> waitingMessages;

    public LogWindowSource(int queueLength)
    {
        this.queueLength = queueLength;
        messages = new ConcurrentLinkedQueue<>();
        listeners = new ArrayList<>();
        waitingMessages = new ConcurrentLinkedQueue<>();
    }

    public void registerListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.add(listener);
        }
    }

    public void append(LogLevel logLevel, String strMessage)
    {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        waitingMessages.offer(entry);
        if (messages.size()+ waitingMessages.size() >= queueLength) {
            messages.poll();
        }

        var waiting = waitingMessages.poll();
        if (waiting != null) {
            messages.offer(waiting);
        }

        synchronized (listeners) {
            for (LogChangeListener listener : listeners) {
                listener.onLogChanged();
            }
        }
    }

    public int size()
    {
        return messages.size();
    }

    public Iterable<LogEntry> all()
    {
        return messages;
    }
}
