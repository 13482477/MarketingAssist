package com.lizhiqiang.marketingassist.accessibility.utils;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class AccessibilityUtils {

    public static String getAccessibilityEventNameByValue(int value) {
        Field[] fields = AccessibilityEvent.class.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (Modifier.isStatic(field.getModifiers()) && StringUtils.startsWith(field.getName(), "TYPE_") && field.getInt(AccessibilityEvent.class) == value) {
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static String getAccessibilityActionName(int value) {
        Field[] fields = AccessibilityNodeInfo.AccessibilityAction.class.getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (Modifier.isStatic(field.getModifiers()) && StringUtils.startsWith(field.getName(), "ACTION_") && field.getType() == int.class && field.getInt(AccessibilityNodeInfo.AccessibilityAction.class) == value) {
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private static String getHierarchyText(AccessibilityNodeInfo nodeInfo, ArrayList<Integer> indexes) {
        if (nodeInfo == null) {
            return "";
        }

        String result = "";
        result += "indexes=[" + StringUtils.join(indexes, "|") + "];";
        result += "className=" + nodeInfo.getClassName() + ";";
        result += "textName=" + nodeInfo.getText() + ";";
        result += "clickable=" + nodeInfo.isClickable();
        result += "\n";

        for (int i = 0; i < nodeInfo.getChildCount(); i++) {
            ArrayList<Integer> indexes2 = (ArrayList<Integer>) indexes.clone();
            indexes2.add(i);
            result += getHierarchyText(nodeInfo.getChild(i), indexes2);
        }
        return result;
    }

//    private static String getPrefix(int level) {
//        String result = "";
//        for (int i = 0; i < level; i++) {
//            result += "—";
//        }
//        return result;
//    }

    public static void printHierarchy(AccessibilityNodeInfo nodeInfo) {
        String result = getHierarchyText(nodeInfo, new ArrayList<Integer>());
        System.out.print("\n" + result);
    }

}
