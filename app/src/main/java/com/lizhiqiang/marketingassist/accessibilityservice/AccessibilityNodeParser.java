package com.lizhiqiang.marketingassist.accessibilityservice;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AccessibilityNodeParser {
    public static AccessibilityNodeInfo getNodeByCoordinate(AccessibilityNodeInfo nodeInfo, int[] indexes) {
        AccessibilityNodeInfo currentNode = nodeInfo;
        try {
            for (int i : indexes) {
                currentNode = currentNode.getChild(i);
            }
        } catch (Exception e) {
//            Log.e("error", "Index parse error", e);
            return null;
        }
        return currentNode;
    }

    public static AccessibilityNodeInfo getClickableNodeByTextName(AccessibilityNodeInfo nodeInfo, String text) {
        List<AccessibilityNodeInfo> nodeInfos = nodeInfo.findAccessibilityNodeInfosByText("Chats");
//        Log.i("info", "nodeInfos size " + nodeInfos.size());
        if (nodeInfos.size() == 0) {
            return null;
        }

        AccessibilityNodeInfo node = nodeInfos.get(1);
        if (node.isClickable()) {
            return node;
        }

        while (true) {
            node = node.getParent();
            if (node.isClickable()) {
                return node;
            }
        }
    }

}
