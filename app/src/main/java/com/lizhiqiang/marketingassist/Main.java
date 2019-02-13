package com.lizhiqiang.marketingassist;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lizhiqiang.marketingassist.accessibilityservice.context.AccessibilityContext;
import com.lizhiqiang.marketingassist.accessibilityservice.model.Task;
import com.lizhiqiang.marketingassist.accessibilityservice.model.TaskBuilder;
import com.lizhiqiang.marketingassist.accessibilityservice.model.TaskStep;
import com.lizhiqiang.marketingassist.accessibilityservice.model.WechatAction;

public class Main extends AppCompatActivity {

    private Button btnGoConfig;

    private Button btnSendMoments;

    private TextView tvAccessibilityStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        this.tvAccessibilityStatus = this.findViewById(R.id.tvAccessibilityStatus);
        this.tvAccessibilityStatus.setText(AccessibilityContext.getInstance().isAccessibilityEnable() ? "是" : "否");

        this.btnGoConfig = this.findViewById(R.id.buttonGoConfig);
        this.btnSendMoments = this.findViewById(R.id.btnSendMoments);
        this.btnGoConfig.setOnClickListener((v -> {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            startActivity(intent);
        }));

        this.btnSendMoments.setOnClickListener((v) -> {
            if (!AccessibilityContext.getInstance().isAccessibilityEnable()) {
                Toast.makeText(Main.this, "请打开设置", Toast.LENGTH_SHORT).show();
                return;
            }

            AccessibilityContext.getInstance().getTaskQueue().add(TaskBuilder.buildSendMomentsTask());

            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        });

        new Thread(new AccessibilityStatusWatcher()).start();
    }

    private class AccessibilityStatusWatcher implements Runnable {

        private Handler mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Main.this.tvAccessibilityStatus.setText(((boolean) msg.obj) ? "是" : "否");
            }
        };

        @Override
        public void run() {
            while (true) {
                if (AccessibilityContext.getInstance().isOldAccessibilityEnable() != AccessibilityContext.getInstance().isAccessibilityEnable()) {
                    Message msg = new Message();

                    msg.obj = AccessibilityContext.getInstance().isAccessibilityEnable();
                    this.mHandler.sendMessage(msg);
                    AccessibilityContext.getInstance().setOldAccessibilityEnable(AccessibilityContext.getInstance().isAccessibilityEnable());
                }
            }
        }
    }

}
