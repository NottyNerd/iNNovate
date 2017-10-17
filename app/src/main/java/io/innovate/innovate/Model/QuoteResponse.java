package io.innovate.innovate.Model;

/**
 * Created by NottyNerd on 09/02/2017.
 */

//-----------------------------------io.innovate.innovate.Model.QuoteResponse.java-----------------------------------



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;





public class QuoteResponse {

    public String date;
    public String dateadd;

    public QuoteResponse() {
    }

    public Integer id;
    public String quoteAuth;
    public Integer quoteLike;
    public String quoteVal;
    public Integer shown;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateadd() {
        return dateadd;
    }

    public void setDateadd(String dateadd) {
        this.dateadd = dateadd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuoteAuth() {
        return quoteAuth;
    }

    public void setQuoteAuth(String quoteAuth) {
        this.quoteAuth = quoteAuth;
    }

    public Integer getQuoteLike() {
        return quoteLike;
    }

    public void setQuoteLike(Integer quoteLike) {
        this.quoteLike = quoteLike;
    }

    public String getQuoteVal() {
        return quoteVal;
    }

    public void setQuoteVal(String quoteVal) {
        this.quoteVal = quoteVal;
    }

    public Integer getShown() {
        return shown;
    }

    public void setShown(Integer shown) {
        this.shown = shown;
    }


    @Override
    public String toString() {
        return "{" +
                "date='" + date + '\'' +
                ", dateadd='" + dateadd + '\'' +
                ", id=" + id +
                ", quoteAuth='" + quoteAuth + '\'' +
                ", quoteLike=" + quoteLike +
                ", quoteVal='" + quoteVal + '\'' +
                ", shown=" + shown +
                '}';
    }
}