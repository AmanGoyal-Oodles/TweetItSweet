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
            // If we don't do this, you'll see the old image appear briefly
            // before it's replaced with the current image
            /*if (imageView.getTag(R.id.image_url) == null || !imageView.getTag(R.id.image_url).equals(imageUrl)) {
                imageView.setImageBitmap(null);
                imageView.setTag(R.id.image_url, imageUrl);
                Glide.with(imageView).load(imageUrl).into(imageView);
            }*/
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
            //   imageView.setTag(R.id.image_url, null);
            Glide.with(imageView)
                    .load(R.mipmap.ic_app_logo)
                    .into(imageView);
        }
    }

}