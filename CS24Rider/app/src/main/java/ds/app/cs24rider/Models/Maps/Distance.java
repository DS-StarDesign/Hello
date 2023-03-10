package ds.app.cs24rider.Models.Maps;

import com.google.gson.annotations.SerializedName;

public class Distance {

    @SerializedName("text")
    private String text;

    @SerializedName("value")
    private String value;

    public Distance() {}

    public Distance(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
