package com.lizhiqiang.marketingassist.accessibilityservice.context;

import com.lizhiqiang.marketingassist.accessibilityservice.model.Task;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class AccessibilityContext {

    private static AccessibilityContext context;

    private WechatPosition position;

    private boolean accessibilityEnable = false;

    private boolean oldAccessibilityEnable = false;

    private Queue<Task> taskQueue = new LinkedBlockingDeque<>();

    public static AccessibilityContext getInstance() {
        if (context == null) {
            context = new AccessibilityContext();
        }
        return context;
    }

    public WechatPosition getPosition() {
        return position;
    }

    public void setPosition(WechatPosition position) {
        this.position = position;
    }

    public boolean isAccessibilityEnable() {
        return accessibilityEnable;
    }

    public synchronized void setAccessibilityEnable(boolean accessibilityEnable) {
        this.accessibilityEnable = accessibilityEnable;
    }

    public boolean isOldAccessibilityEnable() {
        return oldAccessibilityEnable;
    }

    public synchronized void setOldAccessibilityEnable(boolean oldAccessibilityEnable) {
        this.oldAccessibilityEnable = oldAccessibilityEnable;
    }

    public Queue<Task> getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(Queue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }
}