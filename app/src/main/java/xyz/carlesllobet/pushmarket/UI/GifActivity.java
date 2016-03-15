package xyz.carlesllobet.pushmarket.UI;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import xyz.carlesllobet.pushmarket.Domain.PlayGifView;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by CarlesLlobet on 10/03/2016.
 */
public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gif);

        PlayGifView pGif = (PlayGifView) findViewById(R.id.viewGif);
        pGif.setImageResource(R.drawable.pushmarket_gif);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                    Intent intent = new Intent(getApplicationContext(), xyz.carlesllobet.pushmarket.UI.HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(GifActivity.this).toBundle());
            }
        }, 1350);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}