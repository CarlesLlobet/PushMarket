package xyz.carlesllobet.pushmarket.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xyz.carlesllobet.swissknife.R;


public class ProductsFragment extends Fragment {

    private RecyclerView recyclerList;
    private LinearLayoutManager linearLayout;

    private View rootView;

    private boolean prim;

    public ProductsFragment(){
        
    }

    public ProductsFragment(boolean primera){
        this.prim = primera;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_ranking, container, false);

        recyclerList = (RecyclerView) rootView.findViewById(R.id.mRecyclerView);
        linearLayout = new LinearLayoutManager(getContext());
        recyclerList.setLayoutManager(linearLayout);
        recyclerList.setAdapter(new ProductsAdapter(getContext(),prim));

        return rootView;
    }
}
