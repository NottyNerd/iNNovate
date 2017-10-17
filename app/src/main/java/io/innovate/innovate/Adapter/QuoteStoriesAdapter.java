package io.innovate.innovate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.innovate.innovate.Model.InspirationResponse;
import io.innovate.innovate.Model.QuoteResponse;
import io.innovate.innovate.Model.StoreModel;
import io.innovate.innovate.R;
import io.innovate.innovate.ScrollingActivity;

//import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * Created by Beloved.Egbedion on 3/12/2015.
 */
public class QuoteStoriesAdapter extends RecyclerView.Adapter<QuoteStoriesAdapter.MyViewHolder>{


    private LayoutInflater mInflater;
    private ArrayList<QuoteResponse> arraylist;
    private String status;
    private final Context context;
    private DatabaseReference myRef;
    private List<QuoteResponse> lqr;

    public QuoteStoriesAdapter(Context context, ArrayList<QuoteResponse> data, DatabaseReference databaseReference) {

        mInflater = LayoutInflater.from(context);
        this.arraylist = data;
        this.context = context;
        this.myRef = databaseReference;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_stories_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int pos) {
    final int position =pos;

        holder.name.setText(arraylist.get(position).getQuoteAuth());
        holder.quote.setText(arraylist.get(position).getQuoteVal());
        holder.likesCount.setText(""+arraylist.get(position).getQuoteLike());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/*");
                String shareBody = "I just read a fantastic Quote by "+ arraylist.get(position).getQuoteAuth() +"\n\n"+arraylist.get(position).getQuoteVal()+"\n Download:"+ "http://bit.ly/iNNovateApp";
                share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Got iNNovate?");
                share.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(share, "Share via"));
            }
        });
holder.favorite.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onLikeClicked(myRef,position,holder);
    }
});
    }


    @Override
    public int getItemCount() {
        return arraylist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,likesCount;
        public TextView quote;
        public CircleImageView thumbnail;
        ImageView favorite,share;

        public MyViewHolder(View view) {
            super(view);
            thumbnail =(CircleImageView) view.findViewById(R.id.motivatorIcon);
            name = (TextView) view.findViewById(R.id.motivatorName);
            quote = (TextView) view.findViewById(R.id.motivatorQuote);
            favorite =(ImageView) view.findViewById(R.id.list_item_parallax_travel_icon_favorite);
            share =(ImageView) view.findViewById(R.id.list_item_parallax_travel_icon_share);
            likesCount=(TextView) view.findViewById(R.id.likesCount);

        }
    }


    public void clearAdapter()
    {
        arraylist.clear();
        notifyDataSetChanged();
    }


    private void onLikeClicked(DatabaseReference postRef, final int i, final MyViewHolder holder) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                GenericTypeIndicator<List<QuoteResponse>> t = new GenericTypeIndicator<List<QuoteResponse>>() {
                };

                lqr = mutableData.getValue(t);
                if (lqr == null) {
                    return Transaction.success(mutableData);
                }

                int jj =lqr.get(i).getQuoteLike()+1;

                lqr.get(i).setQuoteLike(jj);

                // Set value and report transaction success
                mutableData.setValue(lqr);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("TAG", "postTransaction:onComplete:" + databaseError);
                if(databaseError==null)
                {
                   holder.likesCount.setText(""+lqr.get(i).getQuoteLike());
                }
            }
        });
    }

}

