package xyz.carlesllobet.pushmarket.UI;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import android.provider.Settings;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.Domain.Llista;
import xyz.carlesllobet.pushmarket.Domain.Product;
import xyz.carlesllobet.pushmarket.Domain.RecyclerItemClickListener;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by CarlesLlobet on 26/01/2016.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private UserFunctions uf;

    private FloatingActionButton afegir, llista, barcode;

    private ImageButton enviar;
    private boolean enviant;

    private TextView ajuda;

    private Boolean fabOpen;

    NfcAdapter mAdapter;
    ProgressDialog pDialog;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;

    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle(R.string.tituloHome);

        afegir = (FloatingActionButton) findViewById(R.id.fab);
        llista = (FloatingActionButton) findViewById(R.id.fab1);
        barcode = (FloatingActionButton) findViewById(R.id.fab2);

        enviant = false;

        ajuda = (TextView) findViewById(R.id.textView10);
        enviar = (ImageButton) findViewById(R.id.enviar);

        if (Llista.getInstance().getAllProducts().isEmpty()) {
            ajuda.setVisibility(View.VISIBLE);
            enviar.setVisibility(View.INVISIBLE);
        }
        else{
            ajuda.setVisibility(View.INVISIBLE);
            enviar.setVisibility(View.VISIBLE);
        }

        llista.setVisibility(View.INVISIBLE);
        barcode.setVisibility(View.INVISIBLE);
        fabOpen = false;

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        mLinearLayout = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLinearLayout);

        final HomeAdapter adapter = new HomeAdapter();

        mRecyclerView.setAdapter(adapter);

        uf = new UserFunctions();

        afegir.setOnClickListener(this);
        llista.setOnClickListener(this);
        barcode.setOnClickListener(this);
        enviar.setOnClickListener(this);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Llista list = Llista.getInstance();
                        list.borraUn(position);
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                })
        );

        //Escoltar NFC
        mAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mAdapter == null) {
            Toast.makeText(this, "Your device does not support NFC", Toast.LENGTH_LONG).show();
            return;
        }

        if (!mAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is disabled", Toast.LENGTH_LONG).show();
        }

        handleIntent(getIntent());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if (fabOpen) {
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
            case R.id.enviar:
                sendFile(v);
                Toast.makeText(getApplicationContext(), "Llista preparada per enviar", Toast.LENGTH_SHORT).show();
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
        if (mAdapter != null) {
            if (!mAdapter.isEnabled()) {
                Log.d("NFC", "Your NFC is not enabled");
                return;
            }
            setupForegroundDispatch(this, mAdapter);
        }
    }

    @Override
    protected void onPause() {
        if (mAdapter != null) {
            stopForegroundDispatch(this, mAdapter);
        }
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called. For more information have a look
         * at the documentation.
         *
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        handleIntent(intent);
    }

    private void showProgress(final boolean show) {
        if (show) {
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Preparando lista para enviar...");
            pDialog.setCancelable(true);
            pDialog.setMax(100);

            pDialog.setProgress(0);
            pDialog.show();
        } else {
            pDialog.dismiss();
        }
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * @param activity The corresponding {@link BaseActivity} requesting to stop the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Log.d("HEY!","ndef");
            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
            Log.d("HEY!","tech");
            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (fabOpen) {
            llista.setVisibility(View.INVISIBLE);
            barcode.setVisibility(View.INVISIBLE);
            fabOpen = false;
        } else {
            finish();
        }
    }

    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // NDEF is not supported by this Tag.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }

            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {
        /*
         * See NFC forum specification for "Text Record Type Definition" at 3.2.1
         *
         * http://www.nfc-forum.org/specs/
         *
         * bit_7 defines encoding
         * bit_6 reserved for future use, must be 0
         * bit_5..0 length of IANA language code
         */

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding = null;
            if ((payload[0] & 128)==0){
                textEncoding = "UTF-8";
            }else{
                textEncoding = "UTF-16";
            }

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                // Do something with the result here
                UserFunctions userFunctions = new UserFunctions();
                Product nou = userFunctions.getProduct(getApplicationContext(), Long.valueOf(result));
                if (nou == null) {
                    //userFunctions.updateAllProducts(getApplicationContext());
                } else {
                    Llista list = Llista.getInstance();
                    list.addProduct(nou);
                }
                recreate();
            }
        }
    }

    public void sendFile(View view) {
        mAdapter = NfcAdapter.getDefaultAdapter(this);

        // Check whether NFC is enabled on device
        if(!mAdapter.isEnabled()){
            // NFC is disabled, show the settings UI
            // to enable NFC
            Toast.makeText(this, "Please enable NFC.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        }
        // Check whether Android Beam feature is enabled on device
        else if(!mAdapter.isNdefPushEnabled()) {
            // Android Beam is disabled, show the settings UI
            // to enable Android Beam
            Toast.makeText(this, "Please enable Android Beam.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }
        else {
            // NFC and Android Beam both are enabled
            try{
                exportListInCSV();
            } catch (IOException e){
                e.printStackTrace();
            }

            // File to be transferred
            String fileName = "List.csv";

            // Retrieve the path to the user's public pictures directory
            File fileDirectory = new File(Environment.getExternalStorageDirectory() + "/PushMarket");

            // Create a new file using the specified directory and name
            File fileToTransfer = new File(fileDirectory, fileName);
            fileToTransfer.setReadable(true, false);

            mAdapter.setBeamPushUris(
                    new Uri[]{Uri.fromFile(fileToTransfer)}, this);
        }
    }

    public void exportListInCSV() throws IOException {
        {
            borrarCarpeta();
            File folder = new File(android.os.Environment.getExternalStorageDirectory(),File.separator+"PushMarket");

            if (!folder.exists()) folder.mkdir();

            final String filename = folder.toString() + "/" + "List.csv";

            new Thread() {
                public void run() {
                    try {

                        FileWriter fw = new FileWriter(filename);

                        ArrayList<Product> productes = Llista.getInstance().getAllProducts();
                        ArrayList<Integer> cantitats = Llista.getInstance().getAllCants();
                        String res = uf.getEmail(HomeActivity.this);
                        for (int i = 0; i < productes.size(); ++i){
                            for (int j = 0; j < cantitats.get(i); ++j){
                                res += ",";
                                res += productes.get(i).getId().toString();
                            }
                        }
                        fw.append(res);
                        fw.append('\n');

                        // fw.flush();
                        fw.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }



    public boolean borrarCarpeta(){
        boolean borrada = false;
        File list = new File(android.os.Environment.getExternalStorageDirectory(),File.separator+"PushMarket"+File.separator+"List.csv");
        if (list.exists()){
            borrada = list.delete();
        }
        return borrada;
    }
}
