package in.affle.android.tweetitsweetapplication.home;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import in.affle.android.tweetitsweetapplication.home.model.Statuses;

public class StatusesDiffCallback extends DiffUtil.Callback {
    private final List<Statuses> mOldStatusesList;
    private final List<Statuses> mNewStatusesList;

    public StatusesDiffCallback(List<Statuses> oldStatusesList, List<Statuses> newStatusesList) {
        this.mOldStatusesList = oldStatusesList;
        this.mNewStatusesList = newStatusesList;
    }

    @Override
    public int getOldListSize() {
        return mOldStatusesList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewStatusesList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldStatusesList.get(oldItemPosition).getId() == mNewStatusesList.get(
                newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Statuses oldStatuses = mOldStatusesList.get(oldItemPosition);
        final Statuses newStatuses = mNewStatusesList.get(newItemPosition);

        return true;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
