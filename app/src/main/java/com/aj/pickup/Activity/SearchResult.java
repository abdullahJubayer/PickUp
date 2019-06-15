package com.aj.pickup.Activity;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.Toast;
import com.aj.pickup.Adapter.HomeRecyclerAdapter;
import com.aj.pickup.Model.SearchPhotoList.Result;
import com.aj.pickup.Model.ViewModel;
import com.aj.pickup.Provider.MySearchSuggestionProvider;
import com.aj.pickup.R;

import java.util.ArrayList;


public class SearchResult extends AppCompatActivity implements HomeRecyclerAdapter.MyClickListner {

    private ArrayList<Object> searchList=new ArrayList<>();
    private RecyclerView myRecyclerView;
    private HomeRecyclerAdapter myAdapter;
    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        myRecyclerView = findViewById(R.id.searhRecycler_id);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        viewModel= ViewModelProviders.of(this).get(ViewModel.class);

        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {

        setIntent(intent);
        handleIntent(intent);

    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String search = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySearchSuggestionProvider.AUTHORITY, MySearchSuggestionProvider.MODE);
            suggestions.saveRecentQuery(search, null);


            viewModel.getSearchPhotoData(search).observe(this, results -> {
                if (results != null){
                    searchList.addAll(results);
                }else {
                    Toast.makeText(this, "No Picture Found", Toast.LENGTH_SHORT).show();
                }
                myAdapter=new HomeRecyclerAdapter(SearchResult.this,searchList,SearchResult.this);
                myRecyclerView.setAdapter(myAdapter);
            });
        }
    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(SearchResult.this,RecyclerSingleItem.class).putExtra("SearchData",(Result)searchList.get(position)));

    }
}
