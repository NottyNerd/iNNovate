package io.innovate.innovate.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NottyNerd on 06/02/2017.
 */
public class ServerResponse {

//        {"qotd_date":"2017-02-04T00:00:00.000+00:00","quote":{"id":15899,"dialogue":false,"private":false,"tags":["education","society"],"url":"https://favqs.com/quotes/gilbert-k-chesterton/15899-education-is--","favorites_count":0,"upvotes_count":1,"downvotes_count":0,"author":"Gilbert K. Chesterton","author_permalink":"gilbert-k-chesterton","body":"Education is simply the soul of a society as it passes from one generation to another."}}

//        -----------------------------------ServerResponse.java-----------------------------------//


    @SerializedName("qotd_date")
    @Expose
    private String qotdDate;
    @SerializedName("quote")
    @Expose
    private Quote quote;

    public String getQotdDate() {
        return qotdDate;
    }

    public void setQotdDate(String qotdDate) {
        this.qotdDate = qotdDate;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

}
//-----------------------------------Quote.java-----------------------------------//



