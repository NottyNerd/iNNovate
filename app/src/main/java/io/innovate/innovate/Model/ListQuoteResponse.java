package io.innovate.innovate.Model;

import java.util.List;

/**
 * Created by NottyNerd on 10/02/2017.
 */

public class ListQuoteResponse {
    public List<QuoteResponse> getQuote() {
        return Quote;
    }

    public void setQuote(List<QuoteResponse> quote) {
        Quote = quote;
    }

    @Override
    public String toString() {
        return "{" +
                "Quote=" + Quote +
                '}';
    }

    private List<QuoteResponse> Quote;
}
