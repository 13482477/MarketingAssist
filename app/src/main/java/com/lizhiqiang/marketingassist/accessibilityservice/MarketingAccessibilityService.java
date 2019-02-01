package com.lizhiqiang.marketingassist.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lizhiqiang.marketingassist.accessibilityservice.context.AccessibilityContext;
import com.lizhiqiang.marketingassist.accessibilityservice.context.WechatPosition;

import org.apache.commons.lang3.StringUtils;

public class MarketingAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (!this.isWechatApp(event)) {
            return;
        }
        this.logSomeThing(event);
        this.locate(event);

        Log.i("position", "position=" + AccessibilityContext.getInstance().getPosition());
    }

    @Override
    public void onInterrupt() {

    }

    private boolean isWechatApp(AccessibilityEvent event) {
        return "com.tencent.mm".equals(event.getPackageName());
    }

    private void logSomeThing(AccessibilityEvent event) {
//        Log.i("hello", "packageName=" + event.getPackageName() + ";" +
//                "eventName=" + AccessibilityUtils.getAccessibilityEventNameByValue(event.getEventType()) + ";" +
//                "actionName=" + AccessibilityUtils.getAccessibilityActionName(event.getAction()) + ";" +
//                "actionValue=" + event.getAction() + ";" +
//                "className=" + this.getRootInActiveWindow().getClassName() + ";");
        if (AccessibilityEvent.TYPE_VIEW_CLICKED == event.getEventType()) {
//            AccessibilityUtils.printHierarchy(this.getRootInActiveWindow());
//            Log.i("hello", this.getRootInActiveWindow().getChild(1).getChild(1).getText().toString());
//            Log.i("hello", this.getRootInActiveWindow().getChild(1).isClickable() + "");
//            this.getRootInActiveWindow().getChild(1).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    private void locate(AccessibilityEvent event) {
        if (this.inChats()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.CHATS);
        } else if (this.inContacts()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.CONTACTS);
        } else if (this.inDiscover()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.DISCOVER);
        } else if (this.inMe()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.ME);
        } else {
            AccessibilityContext.getInstance().setPosition(WechatPosition.UNKNOWN);
        }
    }

    private boolean inChats() {
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByIndexPath(this.getRootInActiveWindow(), new int[]{5, 0, 0});
        if (nodeInfo == null) {
            return false;
        }

        if (!TextView.class.getName().equals(nodeInfo.getClassName())) {
            return false;
        }

        if (StringUtils.startsWith(nodeInfo.getText(), "WeChat")) {
            return true;
        }
        return false;
    }

    private boolean inContacts() {
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByIndexPath(this.getRootInActiveWindow(), new int[]{5, 0, 0});
        if (nodeInfo == null) {
            return false;
        }

        if (!TextView.class.getName().equals(nodeInfo.getClassName())) {
            return false;
        }

        if (StringUtils.startsWith(nodeInfo.getText(), "Contacts")) {
            return true;
        }
        return false;
    }

    private boolean inDiscover() {
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByIndexPath(this.getRootInActiveWindow(), new int[]{5, 0, 0});
        if (nodeInfo == null) {
            return false;
        }

        if (!TextView.class.getName().equals(nodeInfo.getClassName())) {
            return false;
        }

        if (StringUtils.startsWith(nodeInfo.getText(), "Discover")) {
            return true;
        }
        return false;
    }

    private boolean inMe() {
        AccessibilityNodeInfo nodeInfo5 = AccessibilityNodeParser.getNodeByIndexPath(this.getRootInActiveWindow(), new int[]{5});
        AccessibilityNodeInfo nodeInfo50 = AccessibilityNodeParser.getNodeByIndexPath(this.getRootInActiveWindow(), new int[]{5, 0});
        AccessibilityNodeInfo nodeInfo500 = AccessibilityNodeParser.getNodeByIndexPath(this.getRootInActiveWindow(), new int[]{5, 0, 0});

        if (nodeInfo5 != null &&
                ViewGroup.class.getName().equals(nodeInfo5.getClassName()) &&
                nodeInfo50 != null &&
                RelativeLayout.class.getName().equals(nodeInfo50.getClassName()) &&
                nodeInfo500 == null) {
            return true;
        }
        return false;
    }
}
