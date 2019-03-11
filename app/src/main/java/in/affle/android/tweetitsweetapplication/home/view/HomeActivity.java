package in.affle.android.tweetitsweetapplication.home.view;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import in.affle.android.tweetitsweetapplication.BR;
import in.affle.android.tweetitsweetapplication.R;
import in.affle.android.tweetitsweetapplication.home.model.Statuses;
import in.affle.android.tweetitsweetapplication.home.viewModel.HomeViewModel;

public class HomeActivity extends AppCompatActivity {


    private HomeViewModel viewModel;

    //OAuth oauth_consumer_key="Ql5CsdpJgjUnkwUafmfm0oWlq",oauth_token="1103961594202148865-s6IF4CAgHF3fmOHMGXbgVCVgokR3Jo",oauth_signature_method="HMAC-SHA1",oauth_timestamp="1552045004",oauth_nonce="tVtgmnKQys0",oauth_version="1.0",oauth_signature="i4Yhh%2BdvdKQL41RaKflf%2B6uhLG0%3D"
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBindings(savedInstanceState);
    }


    private void setupBindings(Bundle savedInstanceState) {
        ViewDataBinding activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        if (savedInstanceState == null) {
            viewModel.init();
        }
        activityBinding.setVariable(BR.viewModel, viewModel);
        setupListUpdate();
    }

    private void setupListUpdate() {
        viewModel.loading.set(View.VISIBLE);
        viewModel.fetchTweetList("modi");
        viewModel.getmTweetList().observe(this, new Observer<ArrayList<Statuses>>() {
            @Override
            public void onChanged(ArrayList<Statuses> dogBreeds) {
                viewModel.loading.set(View.GONE);
                if (dogBreeds.size() == 0) {
                    viewModel.showEmpty.set(View.VISIBLE);
                } else {
                    viewModel.showEmpty.set(View.GONE);
                    viewModel.setTweetsInAdapter(dogBreeds);
                }
            }
        });
    }

}