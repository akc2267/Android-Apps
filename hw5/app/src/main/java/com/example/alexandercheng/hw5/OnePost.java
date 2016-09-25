package com.example.alexandercheng.hw5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alexandercheng on 10/8/15.
 */
public class OnePost extends AppCompatActivity {
    String titleText;
    String URLtext;
    TextView title;
    ImageView image;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.onepost_layout);

        title = (TextView)findViewById(R.id.textView);
        image = (ImageView)findViewById(R.id.imageView);

        Intent activityThatCalled = getIntent();


        titleText = activityThatCalled.getExtras().getString("title");
        URLtext = activityThatCalled.getExtras().getString("imageURL");

        title.setText(titleText);

        Bitmap bm = BitmapCache.getInstance().getBitmap(URLtext);
        if(bm!=null)
            image.setImageBitmap(bm);
        else
            image.setImageBitmap(BitmapCache.errorImageBitmap);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
