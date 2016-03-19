package xyz.carlesllobet.pushmarket.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by CarlesLlobet on 26/01/2016.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private UserFunctions uf;

    private Toolbar tb;

    private FloatingActionButton afegir,llista,barcode;

    private Boolean fabOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle(R.string.tituloHome);

        afegir = (FloatingActionButton) findViewById (R.id.fab);
        llista = (FloatingActionButton) findViewById (R.id.fab1);
        barcode = (FloatingActionButton) findViewById (R.id.fab2);

        llista.setVisibility(View.INVISIBLE);
        barcode.setVisibility(View.INVISIBLE);
        fabOpen = false;

        uf = new UserFunctions();

        tb = (Toolbar) findViewById(R.id.tool_bar);

        afegir.setOnClickListener(this);
        llista.setOnClickListener(this);
        barcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                llista.setVisibility(View.VISIBLE);
                barcode.setVisibility(View.VISIBLE);
                fabOpen = true;
                break;
            case R.id.fab1:
                startActivity(new Intent(getApplicationContext(), ProductsActivity.class));
                break;
            case R.id.fab2:
                startActivity(new Intent(getApplicationContext(), BarcodeActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(0);

        llista.setVisibility(View.INVISIBLE);
        barcode.setVisibility(View.INVISIBLE);
        fabOpen = false;
    }

    @Override
    public void onBackPressed() {
        if (fabOpen){
            llista.setVisibility(View.INVISIBLE);
            barcode.setVisibility(View.INVISIBLE);
            fabOpen = false;
        } else {
            finish();
        }
    }
}
