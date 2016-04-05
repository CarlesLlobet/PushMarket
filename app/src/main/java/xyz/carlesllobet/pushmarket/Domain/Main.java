package xyz.carlesllobet.pushmarket.Domain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xyz.carlesllobet.pushmarket.DB.DatabaseHandler;
import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.UI.LoginActivity;
import xyz.carlesllobet.pushmarket.UI.GifActivity;

public class Main extends AppCompatActivity {

    private UserFunctions userFunctions;

    private Llista list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Crear Singletons
        list = new Llista();

        userFunctions = new UserFunctions();
        userFunctions.checkTestValues(getApplicationContext());

        if(userFunctions.isUserLoggedIn(getApplicationContext())){
            Intent menu = new Intent(getApplicationContext(), GifActivity.class);
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
