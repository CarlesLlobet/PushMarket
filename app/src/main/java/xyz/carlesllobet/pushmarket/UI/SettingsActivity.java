package xyz.carlesllobet.pushmarket.UI.UI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Locale;

import xyz.carlesllobet.swissknife.DB.UserFunctions;
import xyz.carlesllobet.swissknife.R;

/**
 * Created by JEDI on 17/08/2015.
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private Button btnRegister;
    private Button btnChangePassword;
    private Button btnLogout;

    LocationManager lm;

    private ImageButton pic;

    private UserFunctions userFunctions;

    private String name;
    private String un;
    private String direc;
    private Uri foto;
    private Integer puntuacion;
    private Switch tuto;

    private TextView nombre;
    private TextView username;
    private TextView punt;
    private TextView addr;
    private TextView lnotif;
    private Switch switch1;

    private FrameLayout cardLoc;

    private TextView ubic;
    private Button btnClose;

    private boolean clickable;

    private MenuItem language;

    private String lang;

    Uri selectedImage;
    Uri mImageUri;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        clickable = true;

        userFunctions = new UserFunctions();

        if (userFunctions.isUserAdmin(getApplicationContext())) {
            setContentView(R.layout.activity_settings_admin);

            btnRegister = (Button) findViewById(R.id.btnRegister);
            btnRegister.setOnClickListener(this);
        } else setContentView(R.layout.activity_settings);

        ubic = (TextView) findViewById(R.id.ubic);
        btnClose = (Button) findViewById(R.id.btnClose);

        cardLoc = (FrameLayout) findViewById(R.id.cardLoc);

        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                cardLoc.setVisibility(View.GONE);
                clickable = true;
            }
        });

        setTitle(R.string.tituloSettings);

        un = userFunctions.getUserName(getApplicationContext());

        nombre = (TextView) findViewById(R.id.nombre);
        username = (TextView) findViewById(R.id.username);
        punt = (TextView) findViewById(R.id.punt);
        addr = (TextView) findViewById(R.id.address);
        lnotif = (TextView) findViewById(R.id.lnotif);
        switch1 = (Switch) findViewById(R.id.switch1);

        tuto = (Switch) findViewById(R.id.switch1);
        tuto.setChecked(userFunctions.getTuto(getApplicationContext(), un));

        name = userFunctions.getName(getApplicationContext(), un);
        puntuacion = userFunctions.getPunt(getApplicationContext(), un);
        direc = userFunctions.getAddress(getApplicationContext(), un);
        foto = userFunctions.getFoto(getApplicationContext(), un);

        if (!userFunctions.getLastNotif(getApplicationContext(), un).equals("")) {
            lnotif.setText(userFunctions.getLastNotif(getApplicationContext(), un));
        }

        pic = (ImageButton) findViewById(R.id.pic);
        if (foto != null) pic.setImageURI(foto);
        pic.setOnClickListener(this);

        nombre.setText(name);
        username.setText(un);
        punt.setText(puntuacion.toString());
        addr.setText(direc);

        btnChangePassword = (Button) findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(this);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);


        tuto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                userFunctions.setTuto(getApplicationContext(), un, isChecked);
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        if (clickable) {
            switch (v.getId()) {
                case R.id.btnRegister:
                    startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                    break;
                case R.id.btnChangePassword:
                    startActivity(new Intent(getApplicationContext(), PasswordActivity.class));
                    break;
                case R.id.btnLogout:
                    UserFunctions userFunctions = new UserFunctions();
                    userFunctions.logoutUser(getApplicationContext());
                    Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    //tancar menu
                    break;
                case R.id.pic:
                    CharSequence profilePic[] = new CharSequence[]{"Galería", "Cámara"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.dialog1);
                    builder.setItems(profilePic, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(pickPhoto, 0);
                                    break;
                                case 1:
                                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    //Anem a provar una solucio
                                    ContentValues values = new ContentValues();
                                    values.put(MediaStore.Images.Media.TITLE, un);
                                    mImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                                    takePicture.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                                    //fins aqui
                                    startActivityForResult(takePicture, 1);
                                    break;
                            }
                        }
                    });
                    builder.show();
                    break;
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    selectedImage = imageReturnedIntent.getData();
                    //guardar la foto a la ruta de local de SendMyFiles
                    String selectedImagePath = getRealPathFromUri(this, selectedImage);
                    //Poner en el ImageButton
                    //Bitmap bmp = BitmapFactory.decodeFile(selectedImagePath);
                    //pic.setImageBitmap(bmp);
                    Uri imgUri = Uri.parse(selectedImagePath);
                    pic.setImageURI(imgUri);
                    //Guardar la foto a la BD
                    userFunctions.setFoto(getApplicationContext(), un, imgUri);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    mImageUri = imageReturnedIntent.getData();
                    //guardar la foto a la ruta de local de SendMyFiles
                    String selectedImagePath = getRealPathFromUri(this, mImageUri);
                    //Poner en el ImageButton
                    //Bitmap bmp = BitmapFactory.decodeFile(selectedImagePath);
                    //pic.setImageBitmap(bmp);
                    Uri imgUri = Uri.parse(selectedImagePath);
                    pic.setImageURI(imgUri);
                    //Guardar la foto a la BD
                    userFunctions.setFoto(getApplicationContext(), un, imgUri);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        language = menu.findItem(R.id.action_language);
        String aux = Locale.getDefault().toString();
        if (aux.equals("ca")) language.setIcon(R.mipmap.catalonia);
        if (aux.equals("en")) language.setIcon(R.mipmap.uk);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_location:
                Location loc = null;
                lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                cardLoc.setVisibility(View.VISIBLE);
                clickable = false;
                if (loc != null) {
                    ubic.setText("Latitud:   " + String.valueOf(loc.getLatitude())+ "\n" +
                                 "Longitud:  " + String.valueOf(loc.getLongitude())+ "\n" +
                                 "Altitud:   " + String.valueOf(loc.getAltitude()));
                } else {
                    ubic.setText("Latitud:   " + "  desconocida" + "\n" +
                                 "Longitud:  " + "desconocida" + "\n" +
                                 "Altitud:   " + "  desconocida");
                }
                break;
            case R.id.action_spa:
                Locale locale = new Locale("es");
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getApplicationContext().getResources().updateConfiguration(config, null);
                language.setIcon(R.mipmap.spain);
                lang = "es";
                userFunctions.setLang(getApplicationContext(),userFunctions.getUserName(getApplicationContext()),lang);
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
                break;
            case R.id.action_cat:
                Locale locale2 = new Locale("ca");
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                getApplicationContext().getResources().updateConfiguration(config2, null);
                language.setIcon(R.mipmap.catalonia);
                lang = "ca";
                userFunctions.setLang(getApplicationContext(),userFunctions.getUserName(getApplicationContext()),lang);
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
                break;
            case R.id.action_en:
                Locale locale3 = new Locale("en");
                Locale.setDefault(locale3);
                Configuration config3 = new Configuration();
                config3.locale = locale3;
                getApplicationContext().getResources().updateConfiguration(config3, null);
                language.setIcon(R.mipmap.uk);
                lang = "en";
                userFunctions.setLang(getApplicationContext(),userFunctions.getUserName(getApplicationContext()),lang);
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                finish();
                break;
            case R.id.action_help:
                Intent intent = new Intent(this, HelpSettingsActivity.class);
                this.startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mImageUri != null) outState.putString("cameraImageUri", mImageUri.toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("cameraImageUri")) mImageUri = Uri.parse(savedInstanceState.getString("cameraImageUri"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(5);
    }

    @Override
    public void onBackPressed() {
        if (!clickable) cardLoc.setVisibility(View.GONE);
        else {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}