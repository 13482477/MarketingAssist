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

    private String name;

    private String description;

    private boolean continued = false;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isContinued() {
        return continued;
    }

    public void setContinued(boolean continued) {
        this.continued = continued;
    }

    public abstract void doAction(AccessibilityService context, AccessibilityEvent event);
}
