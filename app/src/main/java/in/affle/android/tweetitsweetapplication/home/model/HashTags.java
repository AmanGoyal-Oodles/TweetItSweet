package in.affle.android.tweetitsweetapplication.home.model;

import java.util.ArrayList;

public class HashTags {

    private String text;
    private ArrayList<Integer> indices;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<Integer> getIndices() {
        return indices;
    }

    public void setIndices(ArrayList<Integer> indices) {
        this.indices = indices;
    }
}
