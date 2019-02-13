package com.lizhiqiang.marketingassist.accessibilityservice.model;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Task {

    private String taskName;

    private Queue<TaskStep> stepQueue = new LinkedBlockingQueue<>();

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Queue<TaskStep> getStepQueue() {
        return stepQueue;
    }

    public void setStepQueue(Queue<TaskStep> stepQueue) {
        this.stepQueue = stepQueue;
    }
}
