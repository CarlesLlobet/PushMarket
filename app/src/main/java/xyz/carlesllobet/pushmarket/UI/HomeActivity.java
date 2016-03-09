package xyz.carlesllobet.pushmarket.UI.UI;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import co.mobiwise.materialintro.shape.Focus;
import co.mobiwise.materialintro.shape.FocusGravity;
import co.mobiwise.materialintro.view.MaterialIntroView;
import xyz.carlesllobet.pushmarket.R;
import xyz.carlesllobet.swissknife.DB.UserFunctions;

/**
 * Created by CarlesLlobet on 26/01/2016.
 */
public class HomeActivity extends BaseActivity {

    private UserFunctions uf;

    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle(R.string.tituloHome);

        uf = new UserFunctions();

        tb = (Toolbar) findViewById(R.id.tool_bar);

        if(uf.getTuto(getApplicationContext(),uf.getUserName(getApplicationContext()))) {

            new MaterialIntroView.Builder(HomeActivity.this)
                    .setFocusGravity(FocusGravity.CENTER)
                    .setDelayMillis(500)
                    .setFocusType(Focus.MINIMUM)
                    .enableFadeAnimation(true)
                    .enableDotAnimation(true)
                    .setMaskColor(R.color.colorAccent)
                    .performClick(true)
                    .setInfoText(getResources().getString(R.string.tutoWelcome))
                    .setTarget(tb.getChildAt(1))
                    .setUsageId(String.valueOf(Math.random())) //THIS SHOULD BE UNIQUE ID
                    .show();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(0);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
