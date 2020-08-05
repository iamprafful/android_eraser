package tech.greedylabs.eraserhardness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        final MyCustomView myCustomView = new MyCustomView(MainActivity.this);
        myCustomView.setHardness(1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                myCustomView.setHardness(i+1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        RelativeLayout rootLayout = findViewById(R.id.stickerIv);
        rootLayout.addView(myCustomView, dpToPixels(getResources().getDisplayMetrics(), 200), dpToPixels(getResources().getDisplayMetrics(), 200));

    }
    public static int dpToPixels(final DisplayMetrics display_metrics, final float dps) {
        final float scale = display_metrics.density;
        return (int) (dps * scale + 0.5f);
    }
}
