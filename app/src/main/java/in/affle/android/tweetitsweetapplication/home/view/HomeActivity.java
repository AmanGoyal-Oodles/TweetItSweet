package in.affle.android.tweetitsweetapplication.home.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import in.affle.android.tweetitsweetapplication.BR;
import in.affle.android.tweetitsweetapplication.R;
import in.affle.android.tweetitsweetapplication.home.databinding.AppDataBindingComponent;
import in.affle.android.tweetitsweetapplication.home.model.Statuses;
import in.affle.android.tweetitsweetapplication.home.viewModel.HomeViewModel;

public class HomeActivity extends AppCompatActivity {


    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBindings(savedInstanceState);
    }

    private void setupBindings(Bundle savedInstanceState) {
        DataBindingUtil.setDefaultComponent(new AppDataBindingComponent());
        ViewDataBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        if (savedInstanceState == null) {
            viewModel.initHomeViews();
        }
        activityBinding.setVariable(BR.viewModel, viewModel);
        setupListUpdate();
    }

    private void setupListUpdate() {
        viewModel.getUpdatedTweetList().observe(this, new Observer<ArrayList<Statuses>>() {
            @Override
            public void onChanged(ArrayList<Statuses> tweetList) {
                viewModel.loading.set(View.GONE);
                if (tweetList.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                    viewModel.setTweetsInAdapter(tweetList);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setTweetsInAdapter(tweetList);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tweet_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort:
                makeSort();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeSort() {
        viewModel.sortTweetList();
    }

}