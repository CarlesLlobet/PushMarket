package xyz.carlesllobet.pushmarket.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by JEDI on 10/08/2015.
 */
public class RegisterActivity extends BaseActivity {

    Button btnRegister;

    EditText inputNombreCompleto;
    EditText inputUserName;
    EditText inputPassword;
    EditText inputAddress;
    Switch   inputTuto;

    TextView registerErrorMsg;

    private boolean tuto;
    private boolean clickable;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        clickable = true;
        // Importing all assets like buttons, text fields
        inputNombreCompleto = (EditText) findViewById(R.id.registerNombre);
        inputUserName = (EditText) findViewById(R.id.registerUserName);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        inputAddress = (EditText) findViewById(R.id.registerAddress);


        inputTuto.setChecked(true);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);

        setTitle(R.string.tituloRegister);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(clickable) {
                    clickable = false;
                    String nombreCompleto = inputNombreCompleto.getText().toString();
                    String user = inputUserName.getText().toString();
                    String password = inputPassword.getText().toString();
                    String address = inputAddress.getText().toString();
                    tuto = inputTuto.isChecked();

                    showProgress(true);

                    UserFunctions userFunction = new UserFunctions();
                    if(!userFunction.registerUser(RegisterActivity.this, nombreCompleto, user, password, address, tuto)){
                        registerErrorMsg.setText(R.string.error3);
                        showProgress(false);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.notifRegister), Toast.LENGTH_LONG).show();
                        inputUserName.setText("");
                        inputAddress.setText("");
                        inputNombreCompleto.setText("");
                        inputPassword.setText("");
                        if(!inputTuto.isChecked()) inputTuto.setChecked(true);
                        showProgress(false);
                    }
                }
            }
        });
    }

    private void showProgress(final boolean show) {
        if (show){
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Procesando...");
            pDialog.setCancelable(true);
            pDialog.setMax(100);

            pDialog.setProgress(0);
            pDialog.show();
        }

        else {
            pDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_others, menu);
        return true;
    }
}
