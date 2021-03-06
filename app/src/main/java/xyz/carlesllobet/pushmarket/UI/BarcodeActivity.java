package xyz.carlesllobet.pushmarket.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.Domain.Llista;
import xyz.carlesllobet.pushmarket.Domain.Product;

/**
 * Created by CarlesLlobet on 04/02/2016.
 */
public class BarcodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v("TAG", rawResult.getText()); // Prints scan results
        Log.v("TAG-FORMAT", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        UserFunctions userFunctions = new UserFunctions();
        Product nou = userFunctions.getProduct(getApplicationContext(),Long.valueOf(rawResult.getText()));
        Log.d("barcode:", rawResult.getText());
        if (nou == null) {
            //userFunctions.updateAllProducts(getApplicationContext());
        } else {
            Llista list = Llista.getInstance();
            list.addProduct(nou);
        }
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}