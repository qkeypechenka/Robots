package main.java.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogWindowSource
{
    private int queueLength;

    private Queue<LogEntry> messages;
    private final ArrayList<LogChangeListener> listeners;

    public LogWindowSource(int queueLength)
    {
        this.queueLength = queueLength;
        messages = new ConcurrentLinkedQueue<>();
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
        messages.offer(entry);

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
        return messages;
    }

    public Iterable<LogEntry> all()
    {
        return messages;
    }
}
