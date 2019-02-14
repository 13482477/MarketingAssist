package com.lizhiqiang.marketingassist.accessibility.service;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lizhiqiang.marketingassist.accessibility.context.AppPosition;
import com.lizhiqiang.marketingassist.accessibility.locater.WechatLocator;
import com.lizhiqiang.marketingassist.accessibility.utils.AccessibilityNodeParser;
import com.lizhiqiang.marketingassist.accessibility.utils.AccessibilityUtils;
import com.lizhiqiang.marketingassist.accessibility.context.AccessibilityContext;
import com.lizhiqiang.marketingassist.accessibility.task.Task;
import com.lizhiqiang.marketingassist.accessibility.task.TaskStep;
import com.lizhiqiang.marketingassist.accessibility.task.WechatAction;

public class MainAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityContext.getInstance().setAccessibilityEnable(true);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("position", "position=" + AccessibilityContext.getInstance().getPosition());

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
        this.logSomeThing(event);

        if (step.getAction() == WechatAction.ClickDiscover) {
            task.getStepQueue().poll();
            AccessibilityNodeInfo button = this.buttonDiscover();
            if (button != null) {
                button.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        } else if (step.getAction() == WechatAction.ClickMoments) {
            AccessibilityNodeInfo button = this.buttonMoments();
            if (button != null) {
                button.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        } else {
            return;
        }
//        if (AccessibilityEvent.TYPE_VIEW_CLICKED == event.getEventType() && WechatPosition.DISCOVER == AccessibilityContext.getInstance().getPosition()) {
//            AccessibilityNodeInfo buttonChats = AccessibilityNodeParser.getClickableNodeByTextName(this.getRootInActiveWindow(), "Chats");
//            if (buttonChats != null) {
//                buttonChats.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//            }
//        }
    }

    @Override
    public void onInterrupt() {
        AccessibilityContext.getInstance().setAccessibilityEnable(false);
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

    private AccessibilityNodeInfo buttonChats() {
        int[] coordinate = new int[]{0, 1};
        return AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate);
    }

    private AccessibilityNodeInfo buttonContacts() {
        int[] coordinate = new int[]{0, 2};
        return AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate);
    }

    private AccessibilityNodeInfo buttonDiscover() {
        int[] coordinate = new int[]{0, 3};
        return AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate);
    }

    private AccessibilityNodeInfo buttonMe() {
        int[] coordinate = new int[]{0, 4};
        return AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate);
    }

    private AccessibilityNodeInfo buttonMoments() {
        int[] coordinate = new int[]{0, 0, 3, 0};
        return AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate);
    }
}
