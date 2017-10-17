package io.innovate.innovate;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.lucasr.twowayview.widget.TwoWayView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import io.innovate.innovate.Adapter.InspirationalStoriesAdapter;
import io.innovate.innovate.Model.AppConstant;
import io.innovate.innovate.Model.ErrorEvent;
import io.innovate.innovate.Model.InspirationResponse;
import io.innovate.innovate.Model.InspirationalStories;
import io.innovate.innovate.Model.QuoteResponse;
import io.innovate.innovate.Model.ServerEvent;
import io.innovate.innovate.Model.ServerResponse;
import io.innovate.innovate.Util.BProvider;
import io.innovate.innovate.Util.DialogUtility;
import io.innovate.innovate.Util.Interface;
import io.innovate.innovate.Util.ShareImage;
import io.innovate.innovate.Util.Utilities;
import io.innovate.innovate.app.INNovate;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    TwoWayView inspirational_stories;
    RelativeLayout shareRelativeLayout;
    TwoWayView is;
    InspirationalStoriesAdapter aItems;
    ArrayList<InspirationResponse> insp;
    TextView quotedOD, quotedAuthor;
    TextView moreQuotes,search_bar;
    LinearLayout linearQuotes;
    ImageView share, share_background, like;
    int[] backgrounds={R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5};
    Runnable runner;
    Handler handler;
    private int currentItem=0;
    private ImageView[] dots;
    private int dotsCount;
    private Timer timer;
    private String pushedMessage;
    private DialogUtility dialogUtility;
    EditText edit_search;
    TextView search_x;
    LinearLayout linSearch;
    ImageView app_icon;
    String lastAlert="";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public INNovate getApp()
    {
        return (INNovate) this.getApplication();
    }


    List<InspirationResponse> lir = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        linearQuotes = (LinearLayout) findViewById(R.id.linearQuotes);
        shareRelativeLayout = (RelativeLayout) findViewById(R.id.shareRelativeLayout);

        moreQuotes= (TextView) findViewById(R.id.moreQoutes);
        quotedOD = (TextView) findViewById(R.id.quotedOD);
        quotedAuthor = (TextView) findViewById(R.id.quotedAuthor);
        share =(ImageView) findViewById(R.id.share);
        share_background =(ImageView) findViewById(R.id.share_background);
        like=(ImageView) findViewById(R.id.like);
        app_icon =(ImageView) findViewById(R.id.app_icon);
        search_bar=(TextView) findViewById(R.id.search_bar);
        edit_search = (EditText) findViewById(R.id.edit_search);
        search_x = (TextView) findViewById(R.id.search_x);
        linSearch =(LinearLayout) findViewById(R.id.linSearch);

        dialogUtility = new DialogUtility(MainActivity.this);
        showPush();

        moreQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MoreQuotesActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "innovateHome.jpg";
                like.setVisibility(View.VISIBLE);
                share.setVisibility(View.GONE);
                try
                {
                    FileOutputStream ostream = MainActivity.this.openFileOutput( fileName, Context.MODE_WORLD_READABLE);
                    new ShareImage().getBitmapFromView(shareRelativeLayout).compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("image/*");
                String shareBody = "I use iNNovate...I just read a fantastic quote by "+ quotedAuthor.getText()+"\n Download:"+ "http://bit.ly/iNNovateApp";
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Got iNNovate?");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                shareIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.fromFile( new File( MainActivity.this.getFileStreamPath( fileName).getAbsolutePath())));
                startActivity(Intent.createChooser(shareIntent, "Share via"));

                share.setVisibility(View.VISIBLE);
                like.setVisibility(View.GONE);
            }
        });

        is = (TwoWayView) findViewById(R.id.inspirationalStories);



        // Read from the database
        getApp().myInspirationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                GenericTypeIndicator<List<InspirationResponse>> t = new GenericTypeIndicator<List<InspirationResponse>>() {
                };

                lir  = dataSnapshot.getValue(t);
                Log.d("LOG", "size of LIR is: " + lir.size());
                preloadList(lir);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("LOG", "Failed to read value.", error.toException());
            }
        });


        linearQuotes.getLayoutTransition().setAnimateParentHierarchy(true);


        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linSearch.setVisibility(View.VISIBLE);
            }
        });
        search_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_search.setText("");
                linSearch.setVisibility(View.GONE);
             //   edit_search.setFocusable(true);
            }
        });

        app_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lastAlert = Utilities.getPersistedData(Utilities.CONSTANT_EXT,MainActivity.this);
                if (!TextUtils.isEmpty(lastAlert)) {
                    dialogUtility.showDialog(lastAlert, "Most Recent Alert!");
                }
            }
        });

        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            String txt = editable.toString().toLowerCase(Locale.getDefault());
            aItems.getFilter().filter(txt);
            }
        });

        scrollTimer();

    }




    void preloadList(List<InspirationResponse> value)
    {
        processInspirationalStories(value);
        aItems= new InspirationalStoriesAdapter(this, insp,getApp().myInspirationRef);
        is.setAdapter(aItems);
        linearQuotes.setVisibility(View.VISIBLE);
        linearQuotes.getLayoutTransition().setAnimateParentHierarchy(true);
        ViewGroup itemsPar = (ViewGroup) linearQuotes.getParent();
        itemsPar.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);


    }


    public void processInspirationalStories(List<InspirationResponse> inspirationStoriesResponse) {

        try {
            Gson gson = new Gson();
            int length =inspirationStoriesResponse.size();
            insp =  new ArrayList<InspirationResponse>(length);

            for (int i = 0; i < length; i++) {
                insp.add(inspirationStoriesResponse.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void qotd()
    {
        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(AppConstant.URL)
                .build();
        Interface service = retrofit.create(Interface.class);

        Call<ServerResponse> call =service.get();

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                String author=serverResponse.getQuote().getAuthor();
                String quote = serverResponse.getQuote().getBody();
                quotedOD.setText( quote);
                quotedAuthor.setText( author);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                // handle execution failures like no internet connectivity
            //    BProvider.getInstance().post(new ErrorEvent(-2,t.getMessage()));
                Log.e("LOGGER",t.getMessage().toString());
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
    //    qotd();
    }

    @Override
    protected void onStart() {
        super.onStart();
        qotd();
    }


    private void scrollTimer()
    {
        handler = new Handler();
        runner = new Runnable() {
            public void run() {

                if(currentItem>=backgrounds.length-1 ){
                    currentItem=0;
                    share_background.setImageResource(backgrounds[currentItem]);
                }else{
                    currentItem++;
                    share_background.setImageResource(backgrounds[currentItem]);
                }


            }
        };

        timer = new Timer();
        timer .schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runner);
            }
        }, 2000, 60000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runner);
    }

    void showPush(){
        pushedMessage = getIntent().getStringExtra("push");

//        pushedMessage = getResources().getString(R.string.story);
        if(!TextUtils.isEmpty(pushedMessage)){
            Utilities.savePersistedData(Utilities.CONSTANT_EXT,pushedMessage,this);
            dialogUtility.showDialog(pushedMessage,"Alert!");
        }
    }

}
