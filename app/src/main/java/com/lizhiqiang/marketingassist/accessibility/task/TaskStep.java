package com.lizhiqiang.marketingassist.accessibility.task;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import java.util.HashSet;
import java.util.Set;

public abstract class TaskStep {

    private Set<String> packageCriteria = new HashSet<>();

    private Set<Integer> eventCriteria = new HashSet<>();

    private Task task;

    private int index;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Set<String> getPackageCriteria() {
        return packageCriteria;
    }

    public void setPackageCriteria(Set<String> packageCriteria) {
        this.packageCriteria = packageCriteria;
    }

    public Set<Integer> getEventCriteria() {
        return eventCriteria;
    }

    public void setEventCriteria(Set<Integer> eventCriteria) {
        this.eventCriteria = eventCriteria;
    }

    public abstract void doAction(AccessibilityService context, AccessibilityEvent event);
}
