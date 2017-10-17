package io.innovate.innovate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.innovate.innovate.Model.InspirationResponse;
import io.innovate.innovate.Model.StoreModel;
import io.innovate.innovate.Util.ShareImage;
import io.innovate.innovate.Util.Utilities;
import io.innovate.innovate.app.INNovate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {

    CollapsingToolbarLayout collapsinToolbar;
    String icon;
    String name;
    String quotes,story,id;
    int likes;
    int pos;
    private TextView motivatorName, favQuote,message, likesCount;
    private CircleImageView header_logo;
    LinearLayout content_scrolling;
    ImageView likesImage;
    RelativeLayout relLike;
    List<InspirationResponse> lir;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public INNovate getApp()
    {
        return (INNovate) this.getApplication();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        icon = getIntent().getStringExtra(StoreModel.inn_motivator_icon);
        name  = getIntent().getStringExtra(StoreModel.inn_motivator_name);
        story = getIntent().getStringExtra(StoreModel.inn_motivator_story);
        quotes  = getIntent().getStringExtra(StoreModel.inn_motivator_quote);
         id = getIntent().getStringExtra(StoreModel.inn_motivator_id);
        likes  = getIntent().getIntExtra(StoreModel.inn_motivator_likes,0);
        pos  = getIntent().getIntExtra(StoreModel.inn_motivator_position,0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setIcon(getResources().getDrawable(R.drawable.toolbar_icon));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        collapsinToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsinToolbar.setTitle(name);
        collapsinToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.holo_blue_dark));
        collapsinToolbar.setExpandedTitleColor(getResources().getColor(R.color.holo_blue_dark));
        content_scrolling=(LinearLayout) findViewById(R.id.content_scrolling);


        relLike = (RelativeLayout) findViewById(R.id.relLike);
        relLike.setOnClickListener(this);


        likesCount = (TextView) findViewById(R.id.likesCount);
        likesCount.setText(""+likes);

        header_logo=(CircleImageView) findViewById(R.id.header_logo);
        motivatorName = (TextView) findViewById(R.id.motivatorName);
        motivatorName.setText(name);

        favQuote = (TextView) findViewById(R.id.favQuote);
        message = (TextView) findViewById(R.id.message);
        favQuote.setText(Html.fromHtml("&ldquo;<b>"+ quotes + "</b>&rdquo;"));
        message.setText(Html.fromHtml(story));

        try {

            Picasso.with(this)
             //       .load(Utilities.getResId(icon,ScrollingActivity.this,R.drawable.class))
                    .load(icon)
                    .placeholder(R.mipmap.toolbar_icon)   // optional
                    .error(R.mipmap.toolbar_icon)      // optional
                    .into(header_logo);


        } catch (Exception e) {
            Log.e("Exception loading", e.getMessage());
            Picasso.with(this).load(R.mipmap.toolbar_icon).into(header_logo);


        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                String fileName = "innovate.jpg";
                try
                {
                    FileOutputStream ostream = ScrollingActivity.this.openFileOutput( fileName, Context.MODE_WORLD_READABLE);
                    new ShareImage().getBitmapFromView(content_scrolling).compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("image/*");
                String shareBody = "I use iNNovate...I just read a fantastic story of "+ name+"\n Download:"+ "http://bit.ly/iNNovateApp";
                share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Got iNNovate?");
                share.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                share.putExtra(android.content.Intent.EXTRA_STREAM, Uri.fromFile( new File( ScrollingActivity.this.getFileStreamPath( fileName).getAbsolutePath())));
                startActivity(Intent.createChooser(share, "Share via"));

            }
        });
    }

    @Override
    public void onClick(View view) {
        onLikeClicked(getApp().myInspirationRef,pos);
    }

    private void onLikeClicked(DatabaseReference postRef, final int i) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                GenericTypeIndicator<List<InspirationResponse>> t = new GenericTypeIndicator<List<InspirationResponse>>() {
                };

                lir = mutableData.getValue(t);
                if (lir == null) {
                    return Transaction.success(mutableData);
                }

                int jj =lir.get(i).getMotivatorLikes()+1;

                lir.get(i).setMotivatorLikes(jj);

                // Set value and report transaction success
                mutableData.setValue(lir);
//
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("TAG", "postTransaction:onComplete:" + databaseError);
                if(databaseError==null) {
                    likesCount.setText("" + lir.get(pos).getMotivatorLikes());
                    Toast.makeText(ScrollingActivity.this,"Upvoted",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
