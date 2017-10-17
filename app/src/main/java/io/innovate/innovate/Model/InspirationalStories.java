package io.innovate.innovate.Model;

/**
 * Created by NottyNerd on 18/10/2016.
 */
public class InspirationalStories {
    public String motivatorIcon;
    public String motivatorName;
    public String motivatorId;
    public String motivatorQuoteAndInspiringFact;
    public String motivatorStory;
    public int motivatorLikes;

    public String getMotivatorId() {
        return motivatorId;
    }

    public void setMotivatorId(String motivatorId) {
        this.motivatorId = motivatorId;
    }

    public String getMotivatorIcon() {
        return motivatorIcon;
    }

    public void setMotivatorIcon(String motivatorIcon) {
        this.motivatorIcon = motivatorIcon;
    }

    public String getMotivatorQuoteAndInspiringFact() {
        return motivatorQuoteAndInspiringFact;
    }

    public void setMotivatorQuoteAndInspiringFact(String motivatorQuoteAndInspiringFact) {
        this.motivatorQuoteAndInspiringFact = motivatorQuoteAndInspiringFact;
    }

    public int getMotivatorLikes() {
        return motivatorLikes;
    }

    public void setMotivatorLikes(int motivatorLikes) {
        this.motivatorLikes = motivatorLikes;
    }

    public String getMotivatorStory() {
        return motivatorStory;
    }

    public void setMotivatorStory(String motivatorStory) {
        this.motivatorStory = motivatorStory;
    }



    public String getMotivatorName() {
        return motivatorName;
    }


    public InspirationalStories(String motivatorName, String motivatorIcon, String motivatorId, String motivatorQuoteAndInspiringFact, String motivatorStory, int motivatorLikes) {
        this.motivatorName = motivatorName;
        this.motivatorIcon = motivatorIcon;
        this.motivatorId = motivatorId;
        this.motivatorQuoteAndInspiringFact = motivatorQuoteAndInspiringFact;
        this.motivatorStory = motivatorStory;
        this.motivatorLikes = motivatorLikes;
    }

    public void setMotivatorName(String motivatorName) {
        this.motivatorName = motivatorName;
    }


}
