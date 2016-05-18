package xyz.carlesllobet.pushmarket.Domain;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import xyz.carlesllobet.pushmarket.DB.DatabaseHandler;
import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;
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

        String lang = userFunctions.getLang(getApplicationContext());

        if (lang == "es") {
            Locale locale = new Locale("es");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
        } else if (lang == "ca") {
            Locale locale = new Locale("ca");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
        } else if (lang == "en") {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getApplicationContext().getResources().updateConfiguration(config, null);
        }

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
