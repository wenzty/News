package com.example.wenzty.news.activity;

import android.animation.Animator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wenzty.news.R;

public class GuideActivity extends BaseActivity {
    private ImageView mImageView;
    private Button btn_enter;
    private MediaPlayer mMediaPlayer;

    private int count = 0;
    private int[] imagesArray = new int[] {
            R.drawable.ad_new_version1_img1,
            R.drawable.ad_new_version1_img2,
            R.drawable.ad_new_version1_img3,
            R.drawable.ad_new_version1_img4,
            R.drawable.ad_new_version1_img5,
            R.drawable.ad_new_version1_img6,
            R.drawable.ad_new_version1_img7,
    };




    @Override
    protected int getLayoutRes() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initListener() {
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterMainActivity();
            }
        });
    }

    private void enterMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void initDate() {
        startAnimation();
    }

    @Override
    protected void initView() {
        mImageView = (ImageView) findViewById(R.id.iv_01);
        btn_enter = (Button) findViewById(R.id.btn_go);
    }

    private Handler mHandler = new Handler() {
        @Override   // mhandler发消息后，会执行此方法处理消息
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    startAnimation();
                    break;
            }
        }
    };

    private void startAnimation() {
        count ++;
        count = count % imagesArray.length;     // 取余数
        mImageView.setBackgroundResource(imagesArray[count]);

        mImageView.setScaleX(1.0f);       // 控件恢复为原来的大小，1倍
        mImageView.setScaleY(1.0f);

        mImageView.animate()
                .scaleX(1.2f)           // 控件放大到原来的1.2倍
                .scaleY(1.2f)
                .setDuration(3500)      // 动画执行时间是3.5秒
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // 延迟1秒后发消息，发消息后，会调用mHandler的handleMessage方法， 此处what为0，handleMessage会根据0作判断。
                        mHandler.sendEmptyMessageDelayed(0, 1000);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).start();

    }



    private void playBackgroundMusic() {
        try {
            mMediaPlayer = MediaPlayer.create(this, R.raw.new_version);
            mMediaPlayer.setLooping(true);
            mMediaPlayer.setVolume(1f, 1f);
            mMediaPlayer.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Activity界面显示时调用
    @Override
    protected void onStart() {
        super.onStart();
        // 开始播放
        playBackgroundMusic();
    }



    // Activity界面退出时调用
    @Override
    protected void onStop() {
        mHandler.removeCallbacksAndMessages(null);

        // 释放MediaPlayer资源
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onStop();
    }
}
