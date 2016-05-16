package xyz.carlesllobet.pushmarket.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.Domain.Llista;
import xyz.carlesllobet.pushmarket.Domain.RecyclerItemClickListener;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by CarlesLlobet on 26/01/2016.
 */
public class ProductsActivity extends BaseActivity {

    private UserFunctions uf;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        setTitle(R.string.tituloProducts);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        mLinearLayout = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(mLinearLayout);

        final ProductsAdapter adapter = new ProductsAdapter(getApplicationContext());

        mRecyclerView.setAdapter(adapter);

        uf = new UserFunctions();

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Llista list = Llista.getInstance();
                        list.addProduct(adapter.getItem(position));
                        Toast.makeText(getApplicationContext(), "Producte afegit", Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(1);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        finish();
    }
}