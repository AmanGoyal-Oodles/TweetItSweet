package in.affle.android.tweetitsweetapplication.utils.util;

import android.util.Log;

import java.util.Comparator;

import in.affle.android.tweetitsweetapplication.home.model.Statuses;

public class TweetsByPopularityComparator implements Comparator<Statuses> {

    @Override
    public int compare(Statuses tweet1, Statuses tweet2) {
        Log.d("Between Sort", "done");
        int retweetCountTweet1 = Integer.valueOf(tweet1.getRetweet_count());
        int favoriteCountTweet1 = Integer.valueOf(tweet1.getFavorite_count());
        int retweetCountTweet2 = Integer.valueOf(tweet2.getRetweet_count());
        int favoriteCountTweet2 = Integer.valueOf(tweet2.getFavorite_count());
        if ((retweetCountTweet1 + favoriteCountTweet1) > (retweetCountTweet2 + favoriteCountTweet2)) {
            return -1;
        } else if ((retweetCountTweet1 + favoriteCountTweet1) < (retweetCountTweet2 + favoriteCountTweet2)) {
            return 1;
        } else {
            return tweet1.getCreated_at().compareTo(tweet2.getCreated_at());
        }
    }

}