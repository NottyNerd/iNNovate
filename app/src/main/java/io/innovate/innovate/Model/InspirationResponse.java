package io.innovate.innovate.Model;

/**
 * Created by NottyNerd on 09/02/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//-----------------------------------io.innovate.innovate.Model.Inspiration.java-----------------------------------



public class InspirationResponse {
    public InspirationResponse() {
    }

    public String motivatorIcon;
    public String motivatorId;
    public Integer motivatorLikes;
    public String motivatorName;
    public String motivatorQuoteAndInspiringFact;
    public String motivatorStory;

    public String getMotivatorIcon() {
        return motivatorIcon;
    }

    public void setMotivatorIcon(String motivatorIcon) {
        this.motivatorIcon = motivatorIcon;
    }

    public String getMotivatorId() {
        return motivatorId;
    }

    public void setMotivatorId(String motivatorId) {
        this.motivatorId = motivatorId;
    }

    public Integer getMotivatorLikes() {
        return motivatorLikes;
    }

    public void setMotivatorLikes(Integer motivatorLikes) {
        this.motivatorLikes = motivatorLikes;
    }

    public String getMotivatorName() {
        return motivatorName;
    }

    public void setMotivatorName(String motivatorName) {
        this.motivatorName = motivatorName;
    }

    public String getMotivatorQuoteAndInspiringFact() {
        return motivatorQuoteAndInspiringFact;
    }

    public void setMotivatorQuoteAndInspiringFact(String motivatorQuoteAndInspiringFact) {
        this.motivatorQuoteAndInspiringFact = motivatorQuoteAndInspiringFact;
    }

    public String getMotivatorStory() {
        return motivatorStory;
    }

    public void setMotivatorStory(String motivatorStory) {
        this.motivatorStory = motivatorStory;
    }


    @Override
    public String toString() {
        return "{" +
                "motivatorIcon='" + motivatorIcon + '\'' +
                ", motivatorId='" + motivatorId + '\'' +
                ", motivatorLikes=" + motivatorLikes +
                ", motivatorName='" + motivatorName + '\'' +
                ", motivatorQuoteAndInspiringFact='" + motivatorQuoteAndInspiringFact + '\'' +
                ", motivatorStory='" + motivatorStory + '\'' +
                '}';
    }
}
