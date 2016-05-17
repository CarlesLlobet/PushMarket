package xyz.carlesllobet.pushmarket.UI;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;
    private EditText inputEmail;
    private EditText inputPassword;

    private boolean clickable;

    private Toolbar toolbar;

    private String email;
    private String password;

    private ProgressDialog pDialog;

    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_NAME = "nom";
    private static String KEY_LAST_NAME = "cognoms";
    private static String KEY_EMAIL = "email";
    private static String KEY_EDAD = "data_naix";
    private static String KEY_COUNTRY = "pais";
    private static String KEY_SEX = "sexe";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickable = true;

        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        setTitle(R.string.tituloLogin);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (clickable) {
                    clickable = false;
                    email = inputEmail.getText().toString();
                    password = inputPassword.getText().toString();

                    showProgress(true);

                    /*if ((email.equals("admin@admin.com")) && (password.equals("4dm1n"))) {
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();

                        editor.putString("email", email);
                        editor.putString("password", password);
                        editor.putString("name", "Admin");
                        editor.putString("lastName", "Administrator");
                        editor.putString("edad", "21");
                        editor.putString("sexe", "Male");
                        editor.putString("pais", "Spain");
                        editor.putString("ciutat", "Barcelona");
                        editor.commit();

                        showProgress(false);

                        Intent dashboard = new Intent(getApplicationContext(), GifActivity.class);

                        // Close all views before launching Dashboard
                        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dashboard);

                        // Close Login Screen
                        finish();
                    } else {*/
                        UserLoginTask AsyncLogin = new UserLoginTask();
                        AsyncLogin.execute(email, password);
                    //}
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (clickable) {
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                }
            }
        });
    }

    public class UserLoginTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            UserFunctions userFunction = new UserFunctions();
            if (params.length != 2) return null;

            JSONObject json = userFunction.loginUser(params[0], params[1]);

            return json;

        }

        @Override
        protected void onPostExecute(JSONObject json) {
            //super.onPostExecute(logged);
            //your stuff
            //you can pass params, launch a new Intent, a Toast...

            // check for login response
            try {
                if (json != null && json.getString(KEY_SUCCESS) != null) {
                    String res = json.getString(KEY_SUCCESS);
                    if (Integer.parseInt(res) == 1) {
                        // user successfully logged in
                        // Store user details in SQLite Database
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();

                        UserFunctions userFunction = new UserFunctions();
                        userFunction.logoutUser(getApplicationContext());

                        JSONObject json_user = json.getJSONObject("usuari");

                        editor.putString(KEY_EMAIL, email);
                        editor.putString("password", password);
                        editor.putString(KEY_NAME, json_user.getString(KEY_NAME));
                        editor.putString(KEY_LAST_NAME, json_user.getString(KEY_LAST_NAME));
                        editor.putString(KEY_EDAD, json_user.getString(KEY_EDAD));
                        editor.putString(KEY_SEX, json_user.getString(KEY_SEX));
                        editor.putString(KEY_COUNTRY, json_user.getString(KEY_COUNTRY));
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
                        // Error in login
                        showProgress(false);
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle(R.string.error)
                                .setMessage(R.string.loginFail)
                                .setPositiveButton(R.string.btnRetry, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        clickable = true;
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } else {
                    clickable = true;
                    showProgress(false);
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle(R.string.error)
                            .setMessage(R.string.serverFail)
                            .setPositiveButton(R.string.btnRetry, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    clickable = true;
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void showProgress(final boolean show) {
        if (show) {
            pDialog = new ProgressDialog(LoginActivity.this);
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
    public void onBackPressed() {
        clickable = true;
        super.onBackPressed();  // optional depending on your needs
    }
}
