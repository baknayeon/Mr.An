package com.example.dahae.myandroiice.Information;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.dahae.myandroiice.R;

/**
 * Created by b_newyork on 2016-02-04.
 */
public class informationOfSimplePlan extends Activity {

    ImageSwitcher switcher;

    Handler mHandler = new Handler();
    ImageThread thread;
    boolean running;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informaion_anbisa);

    switcher = (ImageSwitcher) findViewById(R.id.switcher);
    switcher.setVisibility(View.INVISIBLE);

    switcher.setFactory(new ViewSwitcher.ViewFactory() {
        public View makeView() {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setBackgroundColor(0xFF000000);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return imageView;
        }
    });

    startAnimation();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAnimation();
    }

    /**
     * 애니메이션 시작
     */
    private void startAnimation() {
        switcher.setVisibility(View.VISIBLE);

        thread = new ImageThread();
        thread.start();
    }

    /**
     * 애니메이션 중지
     */
    private void stopAnimation() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException ex) { }

        switcher.setVisibility(View.INVISIBLE);
    }

    /**
     * 이미지 처리 스레드
     * @author michael
     *
     */
    class ImageThread extends Thread {
        int duration = 3000;
        final int imageId[] = {
                R.drawable.simpletrigger,
                R.drawable.simpletrigger,
                R.drawable.simpletrigger1,
                R.drawable.simpletrigger2,
                R.drawable.simpletrigger3,
                R.drawable.simpletrigger4,
                R.drawable.simpletrigger5,
                R.drawable.simpletrigger6,
        };
        int currentIndex = 0;

        public void run() {
            running = true;
            while (running) {
                synchronized (this) {

                    if( currentIndex ==1 )
                        duration = 2000;
                    if( currentIndex == 5 )
                        duration = 2000;
                    if( currentIndex == 4 )
                        duration = 2000;
                    mHandler.post(new Runnable() {
                        public void run() {
                            switcher.setImageResource(imageId[currentIndex]);
                        }
                    });
                    duration = 4000;
                    currentIndex++;
                    if (currentIndex == imageId.length) {
                        currentIndex = imageId.length-1;
                    }

                    try {
                        Thread.sleep(duration);
                    } catch (InterruptedException ex) { }
                }
            }
            finish();
        }
    }
}
