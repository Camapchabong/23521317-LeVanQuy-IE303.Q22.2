package shoestore.utils;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class FadeAnimator {

    private static final int   INTERVAL_MS = 16;
    private static final float STEP        = 0.05f;

    public interface Callback {
        void onFrame(float alpha);
        void onFinish();
    }

    private Timer    timer;
    private float    alpha = 0f;
    private Callback callback;

    public void start(Callback callback) {
        stop();
        this.callback = callback;
        this.alpha    = 0f;
        timer = new Timer("FadeAnimator", true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                alpha += STEP;
                if (alpha >= 1f) {
                    alpha = 1f;
                    SwingUtilities.invokeLater(() -> {
                        FadeAnimator.this.callback.onFrame(1f);
                        FadeAnimator.this.callback.onFinish();
                    });
                    cancel();
                } else {
                    final float a = alpha;
                    SwingUtilities.invokeLater(() -> FadeAnimator.this.callback.onFrame(a));
                }
            }
        }, 0, INTERVAL_MS);
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
