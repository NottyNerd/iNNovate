package io.innovate.innovate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.innovate.innovate.Model.Response;
import io.innovate.innovate.Model.StoreModel;
import io.innovate.innovate.Util.AlphaForegroundColorSpan;
import io.innovate.innovate.app.INNovate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class InspirationDetail extends Activity {

    int icon;
    String name;
    private TextView motivatorName;
    private ImageView header_logo;
    private ImageView mHeaderLogo;
    private static final String TAG = "InspirationDetail";
    private ScrollView scrollView;



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

        setContentView(R.layout.activity_inspiration_detail);
        icon = getIntent().getIntExtra(StoreModel.inn_motivator_icon,R.drawable.icon);
        name  = getIntent().getStringExtra(StoreModel.inn_motivator_name);

        header_logo=(ImageView) findViewById(R.id.header_logo);
        motivatorName = (TextView) findViewById(R.id.motivatorName);
        motivatorName.setText(name);

        scrollView = (ScrollView) findViewById(R.id.scrollView);

        try {

            Picasso.with(this)
                    .load(icon)
                    .placeholder(R.mipmap.ic_launcher)   // optional
                    .error(R.mipmap.ic_launcher)      // optional
                    .into(header_logo);


        } catch (Exception e) {
            Log.e("Exception loading", e.getMessage());
            Picasso.with(this).load(R.mipmap.ic_launcher).into(header_logo);


        }


    }



    private void onLikeClicked(DatabaseReference postRef, final int i) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Response p = mutableData.getValue(Response.class);
                if (p == null) {
                    return Transaction.success(mutableData);
                }

                int jj =p.getInspiration().get(i).getMotivatorLikes()+1;

               p.getInspiration().get(i).setMotivatorLikes(jj);

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }

}



