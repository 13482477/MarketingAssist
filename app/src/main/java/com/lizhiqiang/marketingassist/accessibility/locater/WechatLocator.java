package com.lizhiqiang.marketingassist.accessibility.locater;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lizhiqiang.marketingassist.accessibility.context.AccessibilityContext;
import com.lizhiqiang.marketingassist.accessibility.context.AppPosition;
import com.lizhiqiang.marketingassist.accessibility.utils.AccessibilityNodeParser;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

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
        List<AccessibilityNodeInfo> nodeInfoList = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("android:id/text1");
        if (nodeInfoList.isEmpty()) {
            return false;
        }

        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            if (TextView.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getText(), "WeChat")) {
                return true;
            }
        }
        return false;
    }


    public static boolean inContacts(AccessibilityService context) {
        List<AccessibilityNodeInfo> nodeInfoList = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("android:id/text1");
        if (nodeInfoList.isEmpty()) {
            return false;
        }

        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            if (TextView.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getText(), "Contacts")) {
                return true;
            }
        }
        return false;
    }

    public static boolean inDiscover(AccessibilityService context) {
        List<AccessibilityNodeInfo> nodeInfoList = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("android:id/text1");
        if (nodeInfoList.isEmpty()) {
            return false;
        }

        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            if (TextView.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getText(), "Discover")) {
                return true;
            }
        }
        return false;
    }

    public static boolean inDiscoverMoments(AccessibilityService context) {
        List<AccessibilityNodeInfo> nodeInfoList = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.tencent.mm:id/jy");
        if (nodeInfoList.isEmpty()) {
            return false;
        }

        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            if (ImageButton.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getContentDescription(), "Share Photo")) {
                return true;
            }
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
        List<AccessibilityNodeInfo> nodeInfoList = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("android:id/title");
        if (nodeInfoList.isEmpty()) {
            return false;
        }

        boolean hasWechatPay = false;
        boolean hasFavorites = false;
        boolean hasMyPosts = false;
        boolean hasStickerGallery = false;
        boolean hasSettings = false;

        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            if (TextView.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getText(), "WeChat Pay")) {
                hasWechatPay = true;
            } else if (TextView.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getText(), "Favorites")) {
                hasFavorites = true;
            } else if (TextView.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getText(), "My Posts")) {
                hasMyPosts = true;
            } else if (TextView.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getText(), "Sticker Gallery")) {
                hasStickerGallery = true;
            } else if (TextView.class.getName().equals(nodeInfo.getClassName()) && StringUtils.startsWith(nodeInfo.getText(), "Settings")) {
                hasSettings = true;
            }
        }
        return hasWechatPay && hasFavorites && hasMyPosts && hasStickerGallery && hasSettings;
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
        List<AccessibilityNodeInfo> nodeInfoList = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bq");

        for (AccessibilityNodeInfo relativeLayoutNodeInfo : nodeInfoList) {
            if (RelativeLayout.class.getName().equals(relativeLayoutNodeInfo.getClassName())) {
                AccessibilityNodeInfo discover = relativeLayoutNodeInfo.getChild(2);
                if (RelativeLayout.class.getName().equals(discover.getClassName()) && discover.isClickable()) {
                    return discover;
                }
            }
        }
        return null;
    }

    public static AccessibilityNodeInfo buttonMe(AccessibilityService context) {
        int[] coordinate = new int[]{0, 4};
        return AccessibilityNodeParser.getNodeByCoordinate(context.getRootInActiveWindow(), coordinate);
    }

    public static AccessibilityNodeInfo buttonMoments(AccessibilityService context) {
        List<AccessibilityNodeInfo> nodeInfoList = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.tencent.mm:id/d7v");
        return nodeInfoList.get(0);
    }


}
