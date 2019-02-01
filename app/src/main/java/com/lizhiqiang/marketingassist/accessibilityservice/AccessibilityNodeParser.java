package com.lizhiqiang.marketingassist.accessibilityservice;

import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

public class AccessibilityNodeParser {
    public static AccessibilityNodeInfo getNodeByIndexPath(AccessibilityNodeInfo nodeInfo, int[] indexes) {
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
}
