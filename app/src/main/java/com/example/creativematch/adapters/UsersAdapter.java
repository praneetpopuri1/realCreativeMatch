package com.example.creativematch.adapters;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.content.Context;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativematch.OtherUser;
import com.example.creativematch.R;
import com.example.creativematch.activities.UsersActivity;
import com.example.creativematch.databinding.ItemContainerUserBinding;
import com.example.creativematch.listeners.UserListener;
import com.example.creativematch.models.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private final ArrayList<OtherUser> users;
    private final UserListener userListener;

    public UsersAdapter(ArrayList<OtherUser> users, UserListener userListener){
        this.users = users;
        this.userListener=userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UserViewHolder holder, int position) {
        OtherUser user = users.get(position);
        holder.setUserData(users.get(position));
        holder.checkBox.setOnCheckedChangeListener(null);

        //if true, your checkbox will be selected, else unselected
        holder.checkBox.setChecked(users.get(position).isChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //set your object's last status
                user.setChecked(isChecked);
            }
        });



    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        ItemContainerUserBinding binding;
        CheckBox checkBox;

        UserViewHolder(ItemContainerUserBinding itemContainerUserBinding){
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
            checkBox = (CheckBox) itemView.findViewById(R.id.RecyclerViewCheckBox);
        }

        void setUserData(OtherUser user){
            binding.textName.setText(user.getName());
            binding.textEmail.setText(user.getEmail());
            Bitmap bitmap = getUserImage(user.getImage());
            if(bitmap == null){
                binding.imageProfile.setImageResource(R.drawable.featured);
            }
            else{
                binding.imageProfile.setImageBitmap(bitmap);
            }
            binding.getRoot().setOnClickListener(view -> userListener.onUserClicked(user));
        }
    }

    private Bitmap getUserImage(String encodedImage){
        if( encodedImage == null) {
            return null;
        }
        else{
            byte[] bytes = Base64.decode(encodedImage, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}
