package xyz.carlesllobet.pushmarket.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by JEDI on 10/08/2015.
 */
public class RegisterActivity extends BaseActivity {

    Button btnRegister;

    EditText inputName;
    EditText inputLastName;
    EditText inputPassword;
    EditText inputEmail;
    EditText inputAge;
    EditText inputCountry;
    EditText inputCity;
    RadioGroup inputSex;

    TextView registerErrorMsg;

    private boolean clickable;

    ProgressDialog pDialog;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_NAME = "nombre";
    private static String KEY_LAST_NAME = "nombre";
    private static String KEY_EMAIL = "email";
    private static String KEY_EDAD = "edad";
    private static String KEY_COUNTRY = "pais";
    private static String KEY_CITY = "ciudad";
    private static String KEY_SEX = "sexo";

    private String nombre;
    private String apellido;
    private String password;
    private String email;
    private String age;
    private String country;
    private String city;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        clickable = true;
        // Importing all assets like buttons, text fields
        inputName = (EditText) findViewById(R.id.registerNombre);
        inputLastName = (EditText) findViewById(R.id.registerLastName);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        inputAge = (EditText) findViewById(R.id.registerAge);
        inputCountry = (EditText) findViewById(R.id.registerCountry);
        inputCity = (EditText) findViewById(R.id.registerCity);
        inputSex = (RadioGroup) findViewById(R.id.registerSex);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);

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
                    email = inputEmail.getText().toString();
                    age = inputAge.getText().toString();
                    country = inputCountry.getText().toString();
                    city = inputCity.getText().toString();
                    sex = "Undefined";
                    //Cojer sexo
                    int selectedId = inputSex.getCheckedRadioButtonId();
                    if (selectedId == R.id.radioMale) sex = "Male";
                    else if (selectedId == R.id.radioFemale) sex = "Female";

                    showProgress(true);

                    //Registrarlo

                    UserLoginTask AsyncLogin = new UserLoginTask();
                    AsyncLogin.execute(email, nombre, apellido, age, password, sex, country, city);
                }
            }
        });
    }

    public class UserLoginTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            UserFunctions userFunction = new UserFunctions();
            if (params.length != 7) return null;

            JSONObject json = userFunction.registerUser(params[0], params[1], params[2], params[3], params[4],
                    params[5], params[6], params[7]);

            return json;

        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json != null && json.getString(KEY_SUCCESS) != null) {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();

                    UserFunctions userFunction = new UserFunctions();
                    userFunction.logoutUser(getApplicationContext());

                    editor.putString(KEY_EMAIL, email);
                    editor.putString("password", password);
                    editor.putString(KEY_NAME, nombre);
                    editor.putString(KEY_LAST_NAME, apellido);
                    editor.putString(KEY_EDAD, age);
                    editor.putString(KEY_SEX, sex);
                    editor.putString(KEY_COUNTRY, country);
                    editor.putString(KEY_CITY, city);
                    editor.commit();

                    // Launch Dashboard Screen
                    Intent dashboard = new Intent(getApplicationContext(), GifActivity.class);

                    showProgress(false);
                    // Close all views before launching Dashboard
                    dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(dashboard);

                    // Close Login Screen
                    finish();
                } else {
                    // Error in register
                    registerErrorMsg.setText(R.string.error3);
                    showProgress(false);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void showProgress(final boolean show) {
        if (show) {
            pDialog = new ProgressDialog(RegisterActivity.this);
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
}
