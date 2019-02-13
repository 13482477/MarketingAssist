package com.lizhiqiang.marketingassist.accessibilityservice.model;

import android.view.accessibility.AccessibilityEvent;

import java.util.HashSet;
import java.util.Set;

public class TaskStep {

    private Set<String> packageCriteria = new HashSet<>();

    private Set<Integer> eventCriteria = new HashSet<>();

    private Task task;

    private int index;

    private WechatAction action;

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

    public WechatAction getAction() {
        return action;
    }

    public void setAction(WechatAction action) {
        this.action = action;
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
}
