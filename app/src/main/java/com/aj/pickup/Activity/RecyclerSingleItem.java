package com.aj.pickup.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aj.pickup.Model.ImageDownloader;
import com.aj.pickup.Model.PhotoList.PhotoListModel;
import com.aj.pickup.Model.SearchPhotoList.Result;
import com.aj.pickup.R;
import com.squareup.picasso.Picasso;
import java.util.UUID;

public class RecyclerSingleItem extends AppCompatActivity {
    private LinearLayout love_lay,download_lay;
    private TextView love_txt;
    private ImageView image;
    private PhotoListModel photoData;
    private Result searchData;
    private String ImageUrl=null;
    private boolean isPermissionGranted=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_recycler_single_item);

        love_lay=findViewById(R.id.layout_love);
        love_txt=findViewById(R.id.love_count_id);
        download_lay=findViewById(R.id.layout_download);
        image=findViewById(R.id.photo_id);

        getPermission();

        PhotoListModel root= (PhotoListModel) getIntent().getSerializableExtra("PhotoData");
        if (root != null){
            photoData=root;
            ImageUrl=photoData.getUrls().getRegular();
            Picasso.get().load(photoData.getUrls().getRegular()).into(image);
            love_txt.setText(photoData.getLikes()+"");

        }
        Result searchList= (Result) getIntent().getSerializableExtra("SearchData");
        if (searchList != null){
            searchData=searchList;
            ImageUrl=searchData.getUrls().getRegular();
            Picasso.get().load(searchData.getUrls().getRegular()).into(image);
            love_txt.setText(searchData.getLikes()+"");
            //capture.setText("Capture :"+data.getUser().getName());

        }

        download_lay.setOnClickListener(v -> {
            if (ImageUrl != null){

                String nam= UUID.randomUUID().toString()+".jpg";
                if (isPermissionGranted){
                    Picasso.get().load(ImageUrl).into(new ImageDownloader(RecyclerSingleItem.this,getApplicationContext().getContentResolver()
                    ,nam,"Description"));
                }
                else {
                    Toast.makeText(RecyclerSingleItem.this, "You Should accept the Permission", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(RecyclerSingleItem.this, "Imag Url Null", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getPermission() {
        if (ActivityCompat.checkSelfPermission(RecyclerSingleItem.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RecyclerSingleItem.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else {
            isPermissionGranted=true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0){
            isPermissionGranted=true;
        }
        else {
            isPermissionGranted=false;
            Toast.makeText(this, "You Should accept the Permission", Toast.LENGTH_SHORT).show();
        }
    }
}
