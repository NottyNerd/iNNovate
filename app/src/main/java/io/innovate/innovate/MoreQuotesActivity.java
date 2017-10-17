package io.innovate.innovate;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.innovate.innovate.Adapter.InspirationalStoriesAdapter;
import io.innovate.innovate.Adapter.QuoteStoriesAdapter;
import io.innovate.innovate.Model.InspirationResponse;
import io.innovate.innovate.Model.ListQuoteResponse;
import io.innovate.innovate.Model.Quote;
import io.innovate.innovate.Model.QuoteResponse;
import io.innovate.innovate.Model.Response;
import io.innovate.innovate.app.INNovate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MoreQuotesActivity extends AppCompatActivity {

    List<QuoteResponse> lqr = null;
    RecyclerView listView;
    QuoteStoriesAdapter aItems;
    ArrayList<QuoteResponse> insp;
    ProgressBar progress;
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
        setContentView(R.layout.activity_more_quotes);



        listView = (RecyclerView) findViewById(R.id.listView);
        progress = (ProgressBar) findViewById(R.id.progress);


        // Read from the database
        getApp().myQuoteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                GenericTypeIndicator<List<QuoteResponse>> t = new GenericTypeIndicator<List<QuoteResponse>>() {
                };

                lqr  = dataSnapshot.getValue(t);
//                Log.d("LOG", "Value is: " + lqr);
//                Log.d("LOG", "size of LQR is: " + lqr.size());
                preloadList(lqr);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOG", "Failed to read value.", error.toException());
            }
        });

    }



    void preloadList(List<QuoteResponse> value)
    {
        processInspirationalStories(value);
        aItems= new QuoteStoriesAdapter(this, insp,getApp().myQuoteRef);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(llm);
        listView.setAdapter(aItems);
progress.setVisibility(View.GONE);

    }


    public void processInspirationalStories(List<QuoteResponse> quoteStoriesResponse) {

        try {
            Gson gson = new Gson();
            int length =quoteStoriesResponse.size();
            insp =  new ArrayList<QuoteResponse>(length);

            for (int i = 0; i < length; i++) {
                insp.add(quoteStoriesResponse.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
