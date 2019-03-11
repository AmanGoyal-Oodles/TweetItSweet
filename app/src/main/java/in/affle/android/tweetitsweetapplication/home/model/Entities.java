package in.affle.android.tweetitsweetapplication.home.model;

import java.util.ArrayList;

public class Entities {


    private ArrayList<HashTags> hashtags;
    private ArrayList<Object> symbols;
    private ArrayList<UserMention> user_mentions;
    private ArrayList<Url> urls;

    public ArrayList<HashTags> getHashtags() {
        return hashtags;
    }

    public void setHashtags(ArrayList<HashTags> hashtags) {
        this.hashtags = hashtags;
    }

    public ArrayList<Object> getSymbols() {
        return symbols;
    }

    public void setSymbols(ArrayList<Object> symbols) {
        this.symbols = symbols;
    }

    public ArrayList<UserMention> getUser_mentions() {
        return user_mentions;
    }

    public void setUser_mentions(ArrayList<UserMention> user_mentions) {
        this.user_mentions = user_mentions;
    }

    public ArrayList<Url> getUrls() {
        return urls;
    }

    public void setUrls(ArrayList<Url> urls) {
        this.urls = urls;
    }
}
