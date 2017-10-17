package io.innovate.innovate.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.innovate.innovate.InspirationDetail;
import io.innovate.innovate.Model.InspirationResponse;
import io.innovate.innovate.Model.InspirationalStories;
import io.innovate.innovate.Model.Response;
import io.innovate.innovate.Model.StoreModel;
import io.innovate.innovate.R;
import io.innovate.innovate.ScrollingActivity;
import io.innovate.innovate.Util.Utilities;

//import de.keyboardsurfer.android.widget.crouton.Crouton;

/**
 * Created by Beloved.Egbedion on 3/12/2015.
 */
public class InspirationalStoriesAdapter extends RecyclerView.Adapter<InspirationalStoriesAdapter.MyViewHolder> implements Filterable{


    private LayoutInflater mInflater;
    private ArrayList<InspirationResponse> arraylist;
    private ArrayList<InspirationResponse> filteredArraylist;
    private String status;
    private final Context context;
    private DatabaseReference myRef;
    private List<InspirationResponse> lir;
    private List<InspirationResponse> filteredLir;
    private CustomFilter customFilter;

    public InspirationalStoriesAdapter(Context context, ArrayList<InspirationResponse> data, DatabaseReference databaseReference) {

        mInflater = LayoutInflater.from(context);
        this.arraylist = data;
        this.context = context;
        this.myRef = databaseReference;
        this.filteredArraylist = data;
        customFilter = new CustomFilter(InspirationalStoriesAdapter.this);

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inspirational_stories_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
    final int position =pos;

        try {

            Picasso.with(context)
                  //  .load(Utilities.getResId(arraylist.get(position).getMotivatorIcon(), context, R.drawable.class))
                    .load(filteredArraylist.get(position).getMotivatorIcon())
                    .noFade()
                    .placeholder(R.mipmap.toolbar_icon)   // optional
                    .error(R.mipmap.toolbar_icon)      // optional
                    .into(holder.thumbnail);


        } catch (Exception e) {
            Log.e("Exception loading", e.getMessage());
            Picasso.with(context).load(filteredArraylist.get(position).getMotivatorIcon()).into(holder.thumbnail);


        }
        holder.name.setText(filteredArraylist.get(position).getMotivatorName());
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onLikeClicked(myRef,position);
                Intent intent = new Intent(context, ScrollingActivity.class);
                intent.putExtra(StoreModel.inn_motivator_name,filteredArraylist.get(position).getMotivatorName() );
                intent.putExtra(StoreModel.inn_motivator_icon,filteredArraylist.get(position).getMotivatorIcon() );
                intent.putExtra(StoreModel.inn_motivator_id,filteredArraylist.get(position).getMotivatorId() );
                intent.putExtra(StoreModel.inn_motivator_likes,filteredArraylist.get(position).getMotivatorLikes() );
                intent.putExtra(StoreModel.inn_motivator_quote,filteredArraylist.get(position).getMotivatorQuoteAndInspiringFact());
                intent.putExtra(StoreModel.inn_motivator_story,filteredArraylist.get(position).getMotivatorStory() );
                intent.putExtra(StoreModel.inn_motivator_position,position);

                context.startActivity(intent);
            }

        });
    }


    @Override
    public int getItemCount() {
        return filteredArraylist.size();
    }

    @Override
    public Filter getFilter() {
        return customFilter;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public CircleImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            thumbnail =(CircleImageView) view.findViewById(R.id.motivatorIcon);
            name = (TextView) view.findViewById(R.id.motivatorName);

        }
    }



    public void clearAdapter()
    {
        arraylist.clear();
        filteredArraylist.clear();
        notifyDataSetChanged();
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
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("TAG", "postTransaction:onComplete:" + databaseError);
            }
        });
    }



    private class CustomFilter extends Filter {
        private InspirationalStoriesAdapter inspirationalStoriesAdapter;

        public CustomFilter(InspirationalStoriesAdapter inspirationalStoriesAdapter) {
            super();
            this.inspirationalStoriesAdapter = inspirationalStoriesAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String filteredString = charSequence.toString().toLowerCase();
            Filter.FilterResults results = new FilterResults();
            final  List<InspirationResponse> list = arraylist;
            int count =list.size();
            final ArrayList<InspirationResponse> mlist = new ArrayList<InspirationResponse>(count);
            String f1,f2;
            for (int i=0;i<count;i++)
            {
                f1 = list.get(i).getMotivatorName();
                f2 = list.get(i).getMotivatorQuoteAndInspiringFact();
                if(f1.toLowerCase().contains(filteredString)||f2.toLowerCase().contains(filteredString))
                {
                    mlist.add((list.get(i)));
                }
            }
            results.values = mlist;
            results.count = mlist.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            filteredArraylist =(ArrayList<InspirationResponse>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}

