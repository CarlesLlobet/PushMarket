package xyz.carlesllobet.pushmarket.UI;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;

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

        //Escoltar NFC
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Log.d("NFC", "Your device does not support NFC");
            return;
        }
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (fabOpen){
                    llista.setVisibility(View.INVISIBLE);
                    barcode.setVisibility(View.INVISIBLE);
                    fabOpen = false;
                } else {
                    llista.setVisibility(View.VISIBLE);
                    barcode.setVisibility(View.VISIBLE);
                    fabOpen = true;
                }
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

        //Reescoltar NFC
        mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent){
        getTagInfo(intent);
    }

    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] techList = tag.getTechList();
        for (int i = 0; i < techList.length; i++) {
            if (techList[i].equals(MifareClassic.class.getName())) {

                MifareClassic mifareClassicTag = MifareClassic.get(tag);
                switch (mifareClassicTag.getType()) {
                    case MifareClassic.TYPE_CLASSIC:
                        //Type Clssic
                        break;
                    case MifareClassic.TYPE_PLUS:
                        //Type Plus
                        break;
                    case MifareClassic.TYPE_PRO:
                        //Type Pro
                        break;
                }
            } else if (techList[i].equals(MifareUltralight.class.getName())) {
                //For Mifare Ultralight
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:

                        break;
                }
            } else if (techList[i].equals(IsoDep.class.getName())) {
                // info[1] = "IsoDep";
                IsoDep isoDepTag = IsoDep.get(tag);

            } else if (techList[i].equals(Ndef.class.getName())) {
                Ndef.get(tag);

            } else if (techList[i].equals(NdefFormatable.class.getName())) {

                NdefFormatable ndefFormatableTag = NdefFormatable.get(tag);

            }
        }
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
