package com.lizhiqiang.marketingassist.accessibilityservice.context;

public class AccessibilityContext {

    private static AccessibilityContext context;

    private WechatPosition position;

    public static AccessibilityContext getInstance() {
        if (context == null) {
            context = new AccessibilityContext();
        }
        return context;
    }

    public WechatPosition getPosition() {
        return position;
    }

    public void setPosition(WechatPosition position) {
        this.position = position;
    }
}