package com.yang.monitor.service.washer.beans;


import com.yang.monitor.record.Record;

import java.io.Serializable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueBean implements Serializable{
    private ConcurrentLinkedQueue<Record> queue;

    public QueueBean(ConcurrentLinkedQueue<Record> queue) {
        this.queue = queue;
    }

    public ConcurrentLinkedQueue<Record> getQueue() {
        return queue;
    }

    public void setQueue(ConcurrentLinkedQueue<Record> queue) {
        this.queue = queue;
    }
}
