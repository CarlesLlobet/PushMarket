package xyz.carlesllobet.pushmarket.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by JEDI on 10/08/2015.
 */
public class EditActivity extends BaseActivity {

    Button btnRegister;

    private UserFunctions uf;

    EditText inputName;
    EditText inputLastName;
    EditText inputPassword;
    EditText inputAge;
    EditText inputCountry;
    RadioGroup inputSex;
    RadioButton inputMale;
    RadioButton inputFemale;

    TextView registerErrorMsg;

    private boolean clickable;

    ProgressDialog pDialog;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_NAME = "nom";
    private static String KEY_LAST_NAME = "cognoms";
    private static String KEY_EDAD = "data_naix";
    private static String KEY_COUNTRY = "pais";
    private static String KEY_SEX = "sexe";

    private String nombre;
    private String apellido;
    private String password;
    private String age;
    private String country;
    private String sex;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        clickable = true;
        // Importing all assets like buttons, text fields
        inputName = (EditText) findViewById(R.id.registerNombre);
        inputLastName = (EditText) findViewById(R.id.registerLastName);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        inputAge = (EditText) findViewById(R.id.registerAge);
        inputCountry = (EditText) findViewById(R.id.registerCountry);
        inputSex = (RadioGroup) findViewById(R.id.registerSex);
        inputMale = (RadioButton) findViewById(R.id.radioMale);
        inputFemale = (RadioButton) findViewById(R.id.radioFemale);

        uf = new UserFunctions();

        inputName.setText(uf.getName(getApplicationContext()));
        inputLastName.setText(uf.getLastName(getApplicationContext()));
        inputPassword.setText(uf.getPassword(getApplicationContext()));
        inputAge.setText(uf.getAge(getApplicationContext()));
        inputCountry.setText(uf.getCountry(getApplicationContext()));

        if (uf.getSex(getApplicationContext()).equals("Masculino")) inputMale.setChecked(true);
        else if (uf.getSex(getApplicationContext()).equals("Femenino")) inputFemale.setChecked(true);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);

        email = uf.getEmail(getApplicationContext());

        setTitle(R.string.tituloRegister);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (clickable) {
                    clickable = false;
                    nombre = inputName.getText().toString();
                    apellido = inputLastName.getText().toString();
                    password = inputPassword.getText().toString();
                    age = inputAge.getText().toString();
                    country = inputCountry.getText().toString();
                    sex = "Undefined";
                    //Cojer sexo
                    int selectedId = inputSex.getCheckedRadioButtonId();
                    if (selectedId == R.id.radioMale) sex = "Masculino";
                    else if (selectedId == R.id.radioFemale) sex = "Femenino";

                    showProgress(true);

                    //Registrarlo

                    UserLoginTask AsyncLogin = new UserLoginTask();
                    AsyncLogin.execute(email, nombre, apellido, age, password, sex, country);
                }
            }
        });
    }

    public class UserLoginTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            if (params.length != 7) return null;

            JSONObject json = uf.modifyUser(params[0], params[1], params[2], params[3], params[4],
                    params[5], params[6]);

            return json;

        }

        @Override
        protected void onPostExecute (JSONObject json){
            //super.onPostExecute(logged);
            //your stuff
            //you can pass params, launch a new Intent, a Toast...
            // check for login response
            try {
                if (json != null && json.getString(KEY_SUCCESS) != null) {
                    registerErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS);

                    if(Integer.parseInt(res) == 1){
                        clickable = true;
                        registerErrorMsg.setTextColor(Color.GREEN);
                        registerErrorMsg.setText("Modificat correctament");
                        showProgress(false);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("password", password);
                        editor.putString(KEY_NAME, nombre);
                        editor.putString(KEY_LAST_NAME, apellido);
                        editor.putString(KEY_EDAD, age);
                        editor.putString(KEY_SEX, sex);
                        editor.putString(KEY_COUNTRY, country);
                        editor.commit();

                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

                    }else{
                        // Error in login
                        clickable = true;
                        registerErrorMsg.setTextColor(Color.RED);
                        registerErrorMsg.setText("Canvi invalid");
                        showProgress(false);
                    }
                } else {
                    clickable = true;
                    registerErrorMsg.setTextColor(Color.RED);
                    registerErrorMsg.setText("El servidor no esta disponible");
                    showProgress(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showProgress(final boolean show) {
        if (show) {
            pDialog = new ProgressDialog(EditActivity.this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Procesando...");
            pDialog.setCancelable(true);
            pDialog.setMax(100);

            pDialog.setProgress(0);
            pDialog.show();
        } else {
            pDialog.dismiss();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_others, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        clickable = true;
        super.onBackPressed();  // optional depending on your needs
    }
}
