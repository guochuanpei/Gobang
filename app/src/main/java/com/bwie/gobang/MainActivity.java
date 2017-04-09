package com.bwie.gobang;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomView mCustomview;
    private Button mBtn;
    private static Repaint mRepaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    public static void setRepaint(Repaint repaint) {
        mRepaint = repaint;
    }

    private void initView() {
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mRepaint.RepaintListener(true);
    }

    //再来一局重绘接口
    public interface Repaint {
        void RepaintListener(boolean b);
    }
}
