package com.lizhiqiang.marketingassist.accessibility.locater;

import android.accessibilityservice.AccessibilityService;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lizhiqiang.marketingassist.accessibility.context.AccessibilityContext;
import com.lizhiqiang.marketingassist.accessibility.context.AppPosition;
import com.lizhiqiang.marketingassist.accessibility.utils.AccessibilityNodeParser;

import org.apache.commons.lang3.StringUtils;

public class WechatLocator {

    public static void locateWechatPosition(AccessibilityService context) {
        if (AppPosition.APP_WECHAT.equals(AccessibilityContext.getInstance().getApp())) {
            if (WechatLocator.inChats(context)) {
                AccessibilityContext.getInstance().setPosition(AppPosition.POSITION_WECHAT_CHATS);
            } else if (WechatLocator.inContacts(context)) {
                AccessibilityContext.getInstance().setPosition(AppPosition.POSITION_WECHAT_CONTACTS);
            } else if (WechatLocator.inDiscover(context)) {
                AccessibilityContext.getInstance().setPosition(AppPosition.POSITION_WECHAT_DISCOVER);
            } else if (WechatLocator.inDiscoverMoments(context)) {
                AccessibilityContext.getInstance().setPosition(AppPosition.POSITION_WECHAT_DISCOVER_MOMENTS);
            } else if (WechatLocator.inDiscoverMomentsSelectcamera(context)) {
                AccessibilityContext.getInstance().setPosition(AppPosition.POSITION_WECHAT_DISCOVER_MOMENTS_SELECTCAMERA);
            } else if (WechatLocator.inMe(context)) {
                AccessibilityContext.getInstance().setPosition(AppPosition.POSITION_WECHAT_ME);
            } else {
                AccessibilityContext.getInstance().setPosition(AppPosition.POSITION_WECHAT_UNKNOWN);
            }
        }
    }

    private static boolean needMask(AccessibilityService context) {
        if (context.getRootInActiveWindow() == null) {
            return false;
        }

        if (context.getRootInActiveWindow().getChildCount() == 1 && LinearLayout.class.getName().equals(context.getRootInActiveWindow().getChild(0).getClassName())) {
            return true;
        }
        return false;
    }

    public static boolean inChats(AccessibilityService context) {
        int[] coordinate = needMask(context) ? new int[]{0, 5, 0, 0} : new int[]{5, 0, 0};
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
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


    public static boolean inContacts(AccessibilityService context) {
        int[] coordinate = needMask(context) ? new int[]{0, 5, 0, 0} : new int[]{5, 0, 0};
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
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

    public static boolean inDiscover(AccessibilityService context) {
        int[] coordinate = needMask(context) ? new int[]{0, 5, 0, 0} : new int[]{5, 0, 0};
        AccessibilityNodeInfo nodeInfo = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
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

    public static boolean inDiscoverMoments(AccessibilityService context) {
        int[] coordinate00 = new int[]{0, 0};
        int[] coordinate000 = new int[]{0, 0, 0};
        int[] coordinate0000 = new int[]{0, 0, 0, 0};
        int[] coordinate0001 = new int[]{0, 0, 0, 1};

        AccessibilityNodeInfo nodeInfo00 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate00);
        AccessibilityNodeInfo nodeInfo000 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate000);
        AccessibilityNodeInfo nodeInfo0000 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate0000);
        AccessibilityNodeInfo nodeInfo0001 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate0001);

        if (nodeInfo00 != null && ListView.class.getName().equals(nodeInfo00.getClassName()) &&
                nodeInfo000 != null && LinearLayout.class.getName().equals(nodeInfo000.getClassName()) && nodeInfo000.isClickable() &&
                nodeInfo0000 != null && ImageView.class.getName().equals(nodeInfo0000.getClassName()) && nodeInfo0000.isClickable() &&
                nodeInfo0001 != null && TextView.class.getName().equals(nodeInfo0001.getClassName())) {
            return true;
        }
        return false;
    }

    public static boolean inDiscoverMomentsSelectcamera(AccessibilityService context) {
        AccessibilityNodeInfo nodeInfo0 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), new int[]{0});
        AccessibilityNodeInfo nodeInfo000 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), new int[]{0, 0, 0});
        AccessibilityNodeInfo nodeInfo010 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), new int[]{0, 1, 0});

        if (nodeInfo0 != null && ListView.class.getName().equals(nodeInfo0.getClassName()) &&
                nodeInfo000 != null && TextView.class.getName().equals(nodeInfo000.getClassName()) && "Camera".equals(nodeInfo000.getText()) &&
                nodeInfo010 != null && TextView.class.getName().equals(nodeInfo010.getClassName()) && "Select Photos or Videos from Album".equals(nodeInfo010.getText())) {
            return true;
        }

        return false;
    }

    public static boolean inMe(AccessibilityService context) {
        int[] coordinate5 = needMask(context) ? new int[]{0, 5} : new int[]{5};
        int[] coordinate50 = needMask(context) ? new int[]{0, 5, 0} : new int[]{5, 0};
        int[] coordinate500 = needMask(context) ? new int[]{0, 5, 0, 0} : new int[]{5, 0, 0};

        AccessibilityNodeInfo nodeInfo5 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate5);
        AccessibilityNodeInfo nodeInfo50 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate50);
        AccessibilityNodeInfo nodeInfo500 = AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate500);

        if (nodeInfo5 != null &&
                ViewGroup.class.getName().equals(nodeInfo5.getClassName()) &&
                nodeInfo50 != null &&
                RelativeLayout.class.getName().equals(nodeInfo50.getClassName()) &&
                nodeInfo500 == null) {
            return true;
        }
        return false;
    }

    public static AccessibilityNodeInfo buttonChats(AccessibilityService context) {
        int[] coordinate = new int[]{0, 1};
        return AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
    }

    public static AccessibilityNodeInfo buttonContacts(AccessibilityService context) {
        int[] coordinate = new int[]{0, 2};
        return AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
    }

    public static AccessibilityNodeInfo buttonDiscover(AccessibilityService context) {
        int[] coordinate = new int[]{0, 3};
        return AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
    }

    public static AccessibilityNodeInfo buttonMe(AccessibilityService context) {
        int[] coordinate = new int[]{0, 4};
        return AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
    }

    public static AccessibilityNodeInfo buttonMoments(AccessibilityService context) {
        int[] coordinate = new int[]{0, 0, 3, 0};
        return AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
    }


}
