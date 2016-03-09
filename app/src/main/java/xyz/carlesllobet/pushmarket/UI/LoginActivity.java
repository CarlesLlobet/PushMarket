package xyz.carlesllobet.pushmarket.UI.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import xyz.carlesllobet.swissknife.DB.UserFunctions;
import xyz.carlesllobet.swissknife.R;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText inputUser;
    private EditText inputPassword;

    private ImageView logo;

    private boolean clickable;

    private Toolbar toolbar;

    private String user;
    private String password;

    private Button btnRetry;
    private ImageView imageRetry;
    private int random;

    private FrameLayout card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        random = 0;
        clickable = true;

        setContentView(R.layout.activity_login);

        inputUser = (EditText) findViewById(R.id.loginUser);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnRetry = (Button) findViewById(R.id.btnRetry);
        imageRetry = (ImageView) findViewById(R.id.imageRetry);

        card = (FrameLayout) findViewById(R.id.cardLogin);

        logo = (ImageView) findViewById(R.id.imageView);

        logo.setImageResource(R.mipmap.ic_swissknife);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);

        setTitle(R.string.tituloLogin);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (clickable) {
                    clickable = false;
                    user = inputUser.getText().toString();
                    password = inputPassword.getText().toString();

                    UserFunctions userFunction = new UserFunctions();
                    if (userFunction.loginUser(getApplicationContext(), user, password)) {
                        // Launch Dashboard Screen
                        Intent dashboard = new Intent(getApplicationContext(), MenuActivity.class);

                        // Close all views before launching Dashboard
                        dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(dashboard);

                        // Close Login Screen
                        finish();
                    } else {
                        card.setVisibility(View.VISIBLE);
                        setImageRandom();
                    }
                }
            }
        });

        // Retry button click event
        btnRetry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                card.setVisibility(View.GONE);
                clickable = true;
                inputPassword.setText("");
            }
        });
    }

    public void setImageRandom(){
        String stringGenerated = "xyz.carlesllobet.swissknife:drawable/bush";
        switch (random) {
            case 0:
                stringGenerated = "xyz.carlesllobet.swissknife:drawable/bush";
                break;
            case 1:
                stringGenerated = "xyz.carlesllobet.swissknife:drawable/yisus";
                break;
            case 2:
                stringGenerated = "xyz.carlesllobet.swissknife:drawable/dicaprio";
                break;
            case 3:
                stringGenerated = "xyz.carlesllobet.swissknife:drawable/dogz";
                break;
        }

        int id = getResources().getIdentifier(stringGenerated, null, null);
        imageRetry.setImageResource(id);

        if (random < 3) ++random;
        else random=0;
    }
}
