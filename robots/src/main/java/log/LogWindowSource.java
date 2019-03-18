package main.java.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class LogWindowSource
{
    private int queueLength;

    private Queue<LogEntry> messages;
    private final ArrayList<LogChangeListener> listeners;

    public LogWindowSource(int queueLength)
    {
        this.queueLength = queueLength;
        messages = new LinkedList<>();
        listeners = new ArrayList<>();
    }

    public void registerListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.add(listener);
        }
    }

    public void unregisterListener(LogChangeListener listener)
    {
        synchronized(listeners)
        {
            listeners.remove(listener);
        }
    }

    public void append(LogLevel logLevel, String strMessage)
    {
        LogEntry entry = new LogEntry(logLevel, strMessage);
        if (messages.size() >= queueLength) {
            messages.poll();
        }
        messages.add(entry);

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

    public Iterable<LogEntry> range(int startFrom, int count)
    {
        if (startFrom < 0 || startFrom >= messages.size())
        {
            return Collections.emptyList();
        }
        int indexTo = Math.min(startFrom + count, messages.size());
        return ((LinkedList<LogEntry>) messages).subList(startFrom, indexTo);
    }

    public Iterable<LogEntry> all()
    {
        return messages;
    }
}
