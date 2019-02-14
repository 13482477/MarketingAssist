package com.lizhiqiang.marketingassist.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lizhiqiang.marketingassist.Main;
import com.lizhiqiang.marketingassist.accessibilityservice.context.AccessibilityContext;
import com.lizhiqiang.marketingassist.accessibilityservice.context.WechatPosition;
import com.lizhiqiang.marketingassist.accessibilityservice.model.Task;
import com.lizhiqiang.marketingassist.accessibilityservice.model.TaskStep;
import com.lizhiqiang.marketingassist.accessibilityservice.model.WechatAction;

import org.apache.commons.lang3.StringUtils;

public class MarketingAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityContext.getInstance().setAccessibilityEnable(true);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        if (!this.isWechatApp(event)) {
//            return;
//        }
        this.logSomeThing(event);
        this.locate(event);
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

    private boolean isWechatApp(AccessibilityEvent event) {
        return "com.tencent.mm".equals(event.getPackageName());
    }

    private void logSomeThing(AccessibilityEvent event) {
        Log.i("hello", "packageName=" + event.getPackageName() + ";" +
                "eventName=" + AccessibilityUtils.getAccessibilityEventNameByValue(event.getEventType()) + ";" +
                "actionName=" + AccessibilityUtils.getAccessibilityActionName(event.getAction()) + ";" +
                "actionValue=" + event.getAction() + ";" +
                "className=" + (this.getRootInActiveWindow() == null ? "null" : this.getRootInActiveWindow().getClassName()) + ";");

        if (AccessibilityEvent.TYPE_VIEW_CLICKED == event.getEventType() ||
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.getEventType() ||
                (AccessibilityContext.getInstance().getPosition() == WechatPosition.UNKNOWN && AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED == event.getEventType())) {
            AccessibilityUtils.printHierarchy(this.getRootInActiveWindow());
        }
    }

    private void locate(AccessibilityEvent event) {
        if (event.getSource() == null) {
            return;
        }
        if (this.inChats()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.CHATS);
        } else if (this.inContacts()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.CONTACTS);
        } else if (this.inDiscover()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.DISCOVER);
        } else if (this.inMe()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.ME);
        } else if (this.inMoments()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.MOMENTS);
        } else if (this.inMomentsSelectCameraOrAlbum()) {
            AccessibilityContext.getInstance().setPosition(WechatPosition.MOMENTS_SELECT_CAMERA_OR_ALBUM);
        } else {
            AccessibilityContext.getInstance().setPosition(WechatPosition.UNKNOWN);
        }
    }

    private boolean needMask() {
        if (this.getRootInActiveWindow() == null) {
            return false;
        }

        if (this.getRootInActiveWindow().getChildCount() == 1 && LinearLayout.class.getName().equals(this.getRootInActiveWindow().getChild(0).getClassName())) {
            return true;
        }
        return false;
    }

    private boolean inChats() {
        int[] coordinate = this.needMask() ? new int[]{0, 5, 0, 0} : new int[]{5, 0, 0};
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate);
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
        int[] coordinate = this.needMask() ? new int[]{0, 5, 0, 0} : new int[]{5, 0, 0};
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate);
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
        int[] coordinate = this.needMask() ? new int[]{0, 5, 0, 0} : new int[]{5, 0, 0};
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate);
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
        int[] coordinate5 = this.needMask() ? new int[]{0, 5} : new int[]{5};
        int[] coordinate50 = this.needMask() ? new int[]{0, 5, 0} : new int[]{5, 0};
        int[] coordinate500 = this.needMask() ? new int[]{0, 5, 0, 0} : new int[]{5, 0, 0};

        AccessibilityNodeInfo nodeInfo5 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate5);
        AccessibilityNodeInfo nodeInfo50 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate50);
        AccessibilityNodeInfo nodeInfo500 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate500);

        if (nodeInfo5 != null &&
                ViewGroup.class.getName().equals(nodeInfo5.getClassName()) &&
                nodeInfo50 != null &&
                RelativeLayout.class.getName().equals(nodeInfo50.getClassName()) &&
                nodeInfo500 == null) {
            return true;
        }
        return false;
    }

    private boolean inMoments() {
        int[] coordinate00 = new int[]{0, 0};
        int[] coordinate000 = new int[]{0, 0, 0};
        int[] coordinate0000 = new int[]{0, 0, 0, 0};
        int[] coordinate0001 = new int[]{0, 0, 0, 1};

        AccessibilityNodeInfo nodeInfo00 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate00);
        AccessibilityNodeInfo nodeInfo000 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate000);
        AccessibilityNodeInfo nodeInfo0000 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate0000);
        AccessibilityNodeInfo nodeInfo0001 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), coordinate0001);

        if (nodeInfo00 != null && ListView.class.getName().equals(nodeInfo00.getClassName()) &&
                nodeInfo000 != null && LinearLayout.class.getName().equals(nodeInfo000.getClassName()) && nodeInfo000.isClickable() &&
                nodeInfo0000 != null && ImageView.class.getName().equals(nodeInfo0000.getClassName()) && nodeInfo0000.isClickable() &&
                nodeInfo0001 != null && TextView.class.getName().equals(nodeInfo0001.getClassName())) {
            return true;
        }
        return false;
    }

    private boolean inMomentsSelectCameraOrAlbum() {
        AccessibilityNodeInfo nodeInfo0 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), new int[]{0});
        AccessibilityNodeInfo nodeInfo000 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), new int[]{0, 0, 0});
        AccessibilityNodeInfo nodeInfo010 = AccessibilityNodeParser.getNodeByCoordinate(this.getRootInActiveWindow(), new int[]{0, 1, 0});

        if (nodeInfo0 != null && ListView.class.getName().equals(nodeInfo0.getClassName()) &&
                nodeInfo000 != null && TextView.class.getName().equals(nodeInfo000.getClassName()) && "Camera".equals(nodeInfo000.getText()) &&
                nodeInfo010 != null && TextView.class.getName().equals(nodeInfo010.getClassName()) && "Select Photos or Videos from Album".equals(nodeInfo010.getText())) {
            return true;
        }

        return false;
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
