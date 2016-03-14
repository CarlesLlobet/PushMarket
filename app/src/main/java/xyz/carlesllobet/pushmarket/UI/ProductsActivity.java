package xyz.carlesllobet.pushmarket.UI;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import xyz.carlesllobet.pushmarket.DB.UserFunctions;
import xyz.carlesllobet.pushmarket.R;

/**
 * Created by CarlesLlobet on 26/01/2016.
 */
public class ProductsActivity extends BaseActivity {

    private UserFunctions uf;

    private Toolbar tb;

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

        mRecyclerView.setAdapter(new ProductsAdapter(getApplicationContext()));

        uf = new UserFunctions();

        tb = (Toolbar) findViewById(R.id.tool_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMenuItem(1);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
