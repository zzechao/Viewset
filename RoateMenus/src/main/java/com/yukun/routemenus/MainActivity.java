package com.yukun.routemenus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mMenuButton;
    private Button mItemButton1;
    private Button mItemButton2;
    private Button mItemButton3;
    private Button mItemButton4;
    private Button mItemButton5;
    private boolean mIsMenuOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mMenuButton = (Button) findViewById(R.id.menu);
        mMenuButton.setOnClickListener(this);

        mItemButton1 = (Button) findViewById(R.id.item1);
        mItemButton1.setOnClickListener(this);

        mItemButton2 = (Button) findViewById(R.id.item2);
        mItemButton2.setOnClickListener(this);

        mItemButton3 = (Button) findViewById(R.id.item3);
        mItemButton3.setOnClickListener(this);

        mItemButton4 = (Button) findViewById(R.id.item4);
        mItemButton4.setOnClickListener(this);

        mItemButton5 = (Button) findViewById(R.id.item5);
        mItemButton5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mMenuButton) {
            if (!mIsMenuOpen) {
                mIsMenuOpen = true;
                AnimUtils.doAnimateOpen(mItemButton1, 0, 5, 300);
                AnimUtils.doAnimateOpen(mItemButton2, 1, 5, 300);
                AnimUtils.doAnimateOpen(mItemButton3, 2, 5, 300);
                AnimUtils.doAnimateOpen(mItemButton4, 3, 5, 300);
                AnimUtils.doAnimateOpen(mItemButton5, 4, 5, 300);
            } else {
                mIsMenuOpen = false;
                AnimUtils.doAnimateClose(mItemButton1, 0, 5, 300);
                AnimUtils.doAnimateClose(mItemButton2, 1, 5, 300);
                AnimUtils.doAnimateClose(mItemButton3, 2, 5, 300);
                AnimUtils.doAnimateClose(mItemButton4, 3, 5, 300);
                AnimUtils.doAnimateClose(mItemButton5, 4, 5, 300);
            }
        } else {
            Toast.makeText(this, "你点击了", Toast.LENGTH_SHORT).show();
        }
    }

}
