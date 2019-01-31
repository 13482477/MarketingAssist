package com.lizhiqiang.marketingassist.accessibilityservice;

import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
                if (Modifier.isStatic(field.getModifiers()) && StringUtils.startsWith(field.getName(), "ACTION_") &&  field.getType() == int.class && field.getInt(AccessibilityNodeInfo.AccessibilityAction.class) == value) {
                    return field.getName();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }



}
