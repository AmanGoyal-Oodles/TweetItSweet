package in.affle.android.tweetitsweetapplication.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import in.affle.android.tweetitsweetapplication.BR;
import in.affle.android.tweetitsweetapplication.home.model.Statuses;
import in.affle.android.tweetitsweetapplication.home.viewModel.HomeViewModel;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetsHolder> {

    private int mLayoutId;
    private ArrayList<Statuses> mTweetList;
    private HomeViewModel mViewModel;

    public TweetsAdapter(@LayoutRes int layoutId, HomeViewModel viewModel) {
        mLayoutId = layoutId;
        mViewModel = viewModel;
    }

    private int getLayoutIdForPosition(int position) {
        return mLayoutId;
    }

    @Override
    public TweetsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new TweetsHolder(binding);
    }

    @Override
    public void onBindViewHolder(TweetsHolder holder, int position) {
        holder.bind(mViewModel, position);
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    @Override
    public int getItemCount() {
        return mTweetList == null ? 0 : mTweetList.size();
    }

    public void setmTweetList(ArrayList<Statuses> mTweetList) {
        this.mTweetList.clear();
        this.mTweetList.addAll(mTweetList);
        notifyDataSetChanged();
    }

    public class TweetsHolder extends RecyclerView.ViewHolder {

        final ViewDataBinding mBinding;

        public TweetsHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(HomeViewModel viewModel, int position) {
            mBinding.setVariable(BR.viewModel, viewModel);
            mBinding.setVariable(BR.position, position);
            mBinding.executePendingBindings();
        }
    }
}
