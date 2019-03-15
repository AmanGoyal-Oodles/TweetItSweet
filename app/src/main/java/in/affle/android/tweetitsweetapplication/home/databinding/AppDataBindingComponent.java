package in.affle.android.tweetitsweetapplication.home.databinding;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingComponent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.affle.android.tweetitsweetapplication.R;

public class AppDataBindingComponent implements DataBindingComponent {

    @BindingAdapter("setAdapter")
    public static void bindRecyclerViewAdapter(RecyclerView recyclerView, RecyclerView.Adapter<?> adapter) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter("imageUrl")
    public static void bindRecyclerViewAdapter(ImageView imageView, String imageUrl) {
        if (imageUrl != null) {
            RequestOptions options = new RequestOptions();
            options.error(R.mipmap.ic_app_logo);
            options.circleCrop();
            if (imageView.getVisibility() == View.VISIBLE) {
                options.placeholder(imageView.getDrawable().mutate());
            }

            Glide.with(imageView)
                    .load(imageUrl)
                    .apply(options)
                    .into(imageView);
        } else {
            Glide.with(imageView)
                    .load(R.mipmap.ic_app_logo)
                    .into(imageView);
        }
    }

}