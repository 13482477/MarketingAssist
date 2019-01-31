package com.lizhiqiang.marketingassist.accessibilityservice;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class MarketingAccessibilityService extends AccessibilityService {

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.i("hello", "packageName=" + event.getPackageName() + ";" +
                "eventName=" + AccessibilityUtils.getAccessibilityEventNameByValue(event.getEventType()) + ";" +
                "actionName=" + AccessibilityUtils.getAccessibilityActionName(event.getAction()) + ";" +
                "actionValue=" + event.getAction());
    }

    @Override
    public void onInterrupt() {

    }
}
