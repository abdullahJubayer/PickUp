package com.aj.pickup.Activity;


import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.aj.pickup.Adapter.HomeRecyclerAdapter;
import com.aj.pickup.Model.PhotoList.PhotoListModel;
import com.aj.pickup.Model.ViewModel;
import com.aj.pickup.R;



import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeRecyclerAdapter.MyClickListner {

    private ArrayList<Object> newPhotoList=new ArrayList<>();
    private RecyclerView myRecyclerView;
    private HomeRecyclerAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        myRecyclerView=findViewById(R.id.home_recyclerView_id);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        ViewModel viewModel= ViewModelProviders.of(this).get(ViewModel.class);

        viewModel.getNewPhotoData().observe(this, photoListModels -> {
            if (photoListModels != null){
                newPhotoList.addAll(photoListModels);
                myAdapter=new HomeRecyclerAdapter(HomeActivity.this,newPhotoList,HomeActivity.this);
                myAdapter.notifyDataSetChanged();
                myRecyclerView.setAdapter(myAdapter);

            }else {
                Toast.makeText(HomeActivity.this, "Data Not Found", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem search_item=menu.findItem(R.id.search_id);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(search_item);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.menu.search_menu){
            onSearchRequested();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        startActivity(new Intent(HomeActivity.this,RecyclerSingleItem.class).putExtra("PhotoData",(PhotoListModel)newPhotoList.get(position)));
    }
}
