package com.lizhiqiang.marketingassist.accessibility.task;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.lizhiqiang.marketingassist.accessibility.locater.WechatLocator;

import java.util.Arrays;
import java.util.List;

public class TaskBuilder {

    public static Task buildSendMomentsTask() {
        Task task = new Task();
        task.setTaskName("朋友圈");

        TaskStep step1 = new TaskStep() {
            @Override
            public void doAction(AccessibilityService context, AccessibilityEvent event) {
                if (!WechatLocator.inChats(context) &&
                        !WechatLocator.inContacts(context) &&
                        !WechatLocator.inDiscover(context) &&
                        !WechatLocator.inMe(context)) {
                    context.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                    this.setContinued(true);
                } else {
                    AccessibilityNodeInfo button = WechatLocator.buttonDiscover(context);
                    if (button != null) {
                        button.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                    this.setContinued(false);
                }
            }
        };
        step1.setTask(task);
        step1.setName("进入微信");
        step1.setName("进入微信，如果在微信首页，点击朋友圈，如果不在，则调用全局返回");
        step1.getPackageCriteria().addAll(Arrays.asList("com.tencent.mm"));
        step1.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED, AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED));
        step1.setIndex(1);
        task.getStepQueue().add(step1);

        TaskStep step2 = new TaskStep() {
            @Override
            public void doAction(AccessibilityService context, AccessibilityEvent event) {
                AccessibilityNodeInfo button = WechatLocator.buttonMoments(context);
                if (button != null) {
                    button.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        };
        step2.setTask(task);
        step2.setName("点击朋友圈");
        step2.getPackageCriteria().addAll(Arrays.asList("com.tencent.mm"));
        step2.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_VIEW_CLICKED));
        step2.setIndex(2);
        task.getStepQueue().add(step2);

        TaskStep step3 = new TaskStep() {
            @Override
            public void doAction(AccessibilityService context, AccessibilityEvent event) {
                List<AccessibilityNodeInfo> list = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.tencent.mm:id/jy");
                AccessibilityNodeInfo button = list.get(0);
                if (button != null) {
                    button.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
                }
            }
        };
        step3.setTask(task);
        step3.setName("长按发送朋友圈按钮");
        step3.getPackageCriteria().addAll(Arrays.asList("com.tencent.mm"));
        step3.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_VIEW_CLICKED));
        step3.setIndex(3);
        task.getStepQueue().add(step3);


        TaskStep step4 = new TaskStep() {
            @Override
            public void doAction(AccessibilityService context, AccessibilityEvent event) {
                List<AccessibilityNodeInfo> list = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.tencent.mm:id/cqx");
                if (list.size() == 0) {
                    return;
                } else {
                    AccessibilityNodeInfo editView = list.get(0);
                    editView.performAction(AccessibilityNodeInfo.ACTION_FOCUS);

                    Bundle arguments = new Bundle();
                    arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, this.getContent());
                    editView.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
                }
            }
        };
        step4.setTask(task);
        step4.setName("输入朋友圈内容");
        step4.getPackageCriteria().addAll(Arrays.asList("com.tencent.mm"));
        step4.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_VIEW_LONG_CLICKED));
        step4.setContent("你好你好你好");
        step4.setIndex(4);
        task.getStepQueue().add(step4);


        TaskStep step5 = new TaskStep() {
            @Override
            public void doAction(AccessibilityService context, AccessibilityEvent event) {
                List<AccessibilityNodeInfo> list = context.getRootInActiveWindow().findAccessibilityNodeInfosByViewId("com.tencent.mm:id/jx");
                if (list.size() == 0) {
                    return;
                } else {
                    AccessibilityNodeInfo button = list.get(0);
                    button.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        };
        step5.setTask(task);
        step5.setName("点击POST");
        step5.getPackageCriteria().addAll(Arrays.asList("com.tencent.mm"));
        step5.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED));
        step5.setIndex(5);
        task.getStepQueue().add(step5);

        TaskStep stepEnd = new TaskStep() {
            @Override
            public void doAction(AccessibilityService context, AccessibilityEvent event) {

            }
        };
        stepEnd.setTask(task);
        stepEnd.setName("结束");
        stepEnd.getPackageCriteria().addAll(Arrays.asList("com.tencent.mm"));
        stepEnd.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_VIEW_CLICKED));
        stepEnd.setIndex(6);
        task.getStepQueue().add(stepEnd);
//
//        TaskStep step4 = new TaskStep();
//        step4.setTask(task);
//        step4.getPackageCriteria().addAll(Arrays.asList("com.tencent.mm"));
//        step4.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_VIEW_CLICKED));
//        step4.setIndex(4);
//        step4.setAction(WechatAction.ClickSelctPhotoFromAlbum);
//        task.getStepQueue().add(step4);
//
//        TaskStep step5 = new TaskStep();
//        step5.setTask(task);
//        step5.getPackageCriteria().addAll(Arrays.asList("null"));
//        step5.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_WINDOWS_CHANGED));
//        step5.setIndex(5);
//        step5.setAction(WechatAction.GloabHome);
//        task.getStepQueue().add(step5);
//
//        TaskStep step6 = new TaskStep();
//        step6.setTask(task);
//        step6.getPackageCriteria().addAll(Arrays.asList(""));
//        step6.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_WINDOWS_CHANGED));
//        step6.setIndex(6);
//        step6.setAction(WechatAction.ReturnWechat);
//        task.getStepQueue().add(step6);
//
//        TaskStep step7 = new TaskStep();
//        step7.setTask(task);
//        step7.getPackageCriteria().addAll(Arrays.asList("null"));
//        step7.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_WINDOWS_CHANGED));
//        step7.setIndex(7);
//        step7.setAction(WechatAction.SelectPhotoInAlbum);
//        task.getStepQueue().add(step7);
//
//        TaskStep step8 = new TaskStep();
//        step8.setTask(task);
//        step8.getPackageCriteria().addAll(Arrays.asList("com.tencent.mm"));
//        step8.getEventCriteria().addAll(Arrays.asList(AccessibilityEvent.TYPE_VIEW_CLICKED));
//        step8.setIndex(8);
//        step8.setAction(WechatAction.ClickDoneInAlbum);
//        task.getStepQueue().add(step8);
//
//        TaskStep step9 = new TaskStep();
//        step9.setTask(task);
//        step9.setIndex(9);
//        step9.setAction(WechatAction.EntryTextInMoments);
//        task.getStepQueue().add(step9);
//
//        TaskStep step10 = new TaskStep();
//        step10.setTask(task);
//        step10.setIndex(10);
//        step10.setAction(WechatAction.ClickPostInSendMoment);
//        task.getStepQueue().add(step10);
        return task;
    }

}
