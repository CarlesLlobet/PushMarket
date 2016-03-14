package xyz.carlesllobet.pushmarket.UI;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by CarlesLlobet on 26/01/2016.
 */
public class HomeActivity extends xyz.carlesllobet.pushmarket.UI.BaseActivity {

    private UserFunctions uf;

    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle(R.string.tituloHome);

        uf = new UserFunctions();

        tb = (Toolbar) findViewById(R.id.tool_bar);
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
