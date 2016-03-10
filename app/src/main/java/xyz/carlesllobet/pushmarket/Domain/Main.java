package xyz.carlesllobet.pushmarket.Domain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xyz.carlesllobet.pushmarket.DB.DatabaseHandler;
import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.UI.LoginActivity;
import xyz.carlesllobet.pushmarket.UI.MenuActivity;

public class Main extends AppCompatActivity {

    private UserFunctions userFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // mirar si el usuari accedeix

        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
            Intent menu = new Intent(getApplicationContext(), MenuActivity.class);
            menu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(menu);
            // Closing menu
            finish();
        }
        else {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
    }
}
