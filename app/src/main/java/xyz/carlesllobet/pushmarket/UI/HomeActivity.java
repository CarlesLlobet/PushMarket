package xyz.carlesllobet.pushmarket.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by CarlesLlobet on 26/01/2016.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private UserFunctions uf;

    private Toolbar tb;

    private Button afegir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle(R.string.tituloHome);

        afegir = (Button) findViewById (R.id.afegirProducte);

        uf = new UserFunctions();

        tb = (Toolbar) findViewById(R.id.tool_bar);

        afegir.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.afegirProducte:
                startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
                break;
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
