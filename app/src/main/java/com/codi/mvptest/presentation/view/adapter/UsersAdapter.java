package com.codi.mvptest.presentation.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codi.mvptest.R;
import com.codi.mvptest.presentation.model.UserModel;

import java.util.Collection;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Codi on 2015/2/12.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    private LayoutInflater mInflater;
    private List<UserModel> mUsersCollection;
    private OnItemClickListener mOnItemClickListener;

    public UsersAdapter(Context context, Collection<UserModel> usersCollection) {
        this.validateUsersCollection(usersCollection);
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mUsersCollection = (List<UserModel>) usersCollection;
    }

    public void setUsersCollection(Collection<UserModel> usersCollection) {
        this.validateUsersCollection(usersCollection);
        this.mUsersCollection = (List<UserModel>) usersCollection;
        this.notifyDataSetChanged();
    }

    private void validateUsersCollection(Collection<UserModel> usersCollection) {
        if (usersCollection == null) {
            throw new IllegalArgumentException("The list can not be null");
        }
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final UserModel userModel = this.mUsersCollection.get(position);
        holder.titleTv.setText(userModel.getFullName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onUserItemClicked(userModel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.mUsersCollection == null) ? 0 : this.mUsersCollection.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.title)
        TextView titleTv;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onUserItemClicked(UserModel userModel);
    }
}
