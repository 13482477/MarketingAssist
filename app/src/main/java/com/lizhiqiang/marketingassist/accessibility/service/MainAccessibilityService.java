package com.lizhiqiang.marketingassist.accessibility.service;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.lizhiqiang.marketingassist.accessibility.context.AccessibilityContext;
import com.lizhiqiang.marketingassist.accessibility.context.AppPosition;
import com.lizhiqiang.marketingassist.accessibility.locater.WechatLocator;
import com.lizhiqiang.marketingassist.accessibility.task.Task;
import com.lizhiqiang.marketingassist.accessibility.task.TaskStep;
import com.lizhiqiang.marketingassist.accessibility.utils.AccessibilityUtils;

public class MainAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityContext.getInstance().setAccessibilityEnable(true);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("position", "position=" + AccessibilityContext.getInstance().getPosition());
        this.logSomeThing(event);

        Task task = AccessibilityContext.getInstance().getTaskQueue().peek();
        if (task == null) {
            return;
        }

        TaskStep step = task.getStepQueue().peek();
        if (step == null) {
            return;
        }

        if (!step.getPackageCriteria().contains(event.getPackageName())) {
            return;
        }

        if (!step.getEventCriteria().contains(event.getEventType())) {
            return;
        }

        this.locate(event);

        this.interval();
        step.doAction(this, event);

        if (!step.isContinued()) {
            task.getStepQueue().poll();
        }
        if (task.getStepQueue().isEmpty()) {
            AccessibilityContext.getInstance().getTaskQueue().poll();
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public boolean onUnbind(Intent intent) {
        AccessibilityContext.getInstance().setAccessibilityEnable(false);
        return super.onUnbind(intent);
    }

    private void interval() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void logSomeThing(AccessibilityEvent event) {
        if (AppPosition.APP_WECHAT_PACKAGE_NAME.equals(event.getPackageName())) {
            Log.i("hello", "packageName=" + event.getPackageName() + ";" +
                    "eventName=" + AccessibilityUtils.getAccessibilityEventNameByValue(event.getEventType()) + ";" +
                    "actionName=" + AccessibilityUtils.getAccessibilityActionName(event.getAction()) + ";" +
                    "actionValue=" + event.getAction() + ";" +
                    "className=" + (this.getRootInActiveWindow() == null ? "null" : this.getRootInActiveWindow().getClassName()) + ";");
        }

        if (AccessibilityEvent.TYPE_VIEW_CLICKED == event.getEventType()) {
            AccessibilityUtils.printHierarchy(this.getRootInActiveWindow());
        }
    }

    private void locate(AccessibilityEvent event) {
        if (event.getSource() == null) {
            return;
        }

        if (AppPosition.APP_WECHAT_PACKAGE_NAME.equals(event.getPackageName())) {
            AccessibilityContext.getInstance().setApp(AppPosition.APP_WECHAT);
            WechatLocator.locateWechatPosition(this);
        } else {
            AccessibilityContext.getInstance().setApp(AppPosition.APP_UNKNOWN);
            AccessibilityContext.getInstance().setPosition("");
        }
    }

}
