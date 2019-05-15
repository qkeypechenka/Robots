package main.java.log;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogWindowSource
{
    private int queueLength;

    private Queue<LogEntry> messages;
    private final ArrayList<LogChangeListener> listeners;

    public LogWindowSource(int queueLength)
    {
        this.queueLength = queueLength;
        messages = new ArrayBlockingQueue<>(queueLength);
        listeners = new ArrayList<>();
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
        if (messages.size() >= queueLength) {
            messages.poll();
        }
        var state = messages.offer(entry);
        while (!state){
            messages.poll();
            state = messages.offer(entry);
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
