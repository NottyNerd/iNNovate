package io.innovate.innovate.Model;

/**
 * Created by NottyNerd on 09/02/2017.
 */



//-----------------------------------io.innovate.innovate.Model.Response.java-----------------------------------

        import java.util.List;

public class Response {

    public Response() {
    }

    public List<InspirationResponse> getInspiration() {
        return Inspiration;
    }

    public void setInspiration(List<InspirationResponse> inspiration) {
        Inspiration = inspiration;
    }

    public List<QuoteResponse> getQuote() {
        return Quote;
    }

    public void setQuote(List<QuoteResponse> quote) {
        Quote = quote;
    }

    public List<InspirationResponse> Inspiration;
    public List<QuoteResponse> Quote;



    @Override
    public String toString() {
        return "Response{" +
                "inspiration=" + Inspiration +
                ", quote=" + Quote +
                '}';
    }
}