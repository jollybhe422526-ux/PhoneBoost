package com.phoneboost.app;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.Build;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Color;
import android.graphics.Typeface;
import java.text.DecimalFormat;

public class MainActivity extends Activity {

    LinearLayout layout;
    DecimalFormat format = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDashboard();
    }

    private String gb(long bytes) {
        return format.format(bytes / (1024.0 * 1024 * 1024)) + " GB";
    }

    private void addText(String text, int size, boolean bold) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(size);
        view.setTextColor(Color.WHITE);
        view.setPadding(0, 18, 0, 18);
        if (bold) view.setTypeface(null, Typeface.BOLD);
        layout.addView(view);
    }

    private void showDashboard() {
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(35, 40, 35, 30);
        layout.setBackgroundColor(Color.rgb(13, 22, 40));

        TextView title = new TextView(this);
        title.setText("PHONEBOOST");
        title.setTextSize(28);
        title.setTextColor(Color.WHITE);
        title.setTypeface(null, Typeface.BOLD);
        layout.addView(title);

        addText("Your Phone Performance Assistant", 14, false);
        addText("DEVICE INFORMATION", 18, true);
        addText("Model: " + Build.MODEL, 16, false);
        addText("Android: " + Build.VERSION.RELEASE, 16, false);

        addText("STORAGE INFORMATION", 18, true);

        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        long total = stat.getTotalBytes();
        long free = stat.getAvailableBytes();
        long used = total - free;

        addText("Total: " + gb(total), 16, false);
        addText("Used: " + gb(used), 16, false);
        addText("Available: " + gb(free), 16, false);

        addText("MEMORY INFORMATION", 18, true);

        ActivityManager manager =
            (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memory = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(memory);

        addText("Total RAM: " + gb(memory.totalMem), 16, false);
        addText("Available RAM: " + gb(memory.availMem), 16, false);

        Button refresh = new Button(this);
        refresh.setText("REFRESH INFORMATION");
        refresh.setOnClickListener(v -> showDashboard());
        layout.addView(refresh);

        addText("Monitor. Understand. Improve.", 14, false);

        setContentView(layout);
    }
}
