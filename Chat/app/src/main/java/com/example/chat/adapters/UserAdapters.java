package com.example.chat.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat.databinding.UserViewBinding;
import com.example.chat.listeners.UserListener;
import com.example.chat.models.User;

import java.util.List;


public class UserAdapters extends RecyclerView.Adapter<UserAdapters.UserViewHolder>{

    private final List<User> users;
    private final UserListener userListener;

    public UserAdapters(List<User> users,UserListener userListener) {

        this.users = users;
        this.userListener=userListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        UserViewBinding userViewBinding=UserViewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(userViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapters.UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        UserViewBinding binding;
        UserViewHolder(UserViewBinding userViewBinding){
            super(userViewBinding.getRoot());
            binding=userViewBinding;
        }
        void setUserData(User user){
            binding.textName.setText(user.name);
            binding.textNumber.setText(user.number);
            binding.imageProfile.setImageBitmap(getUserImage(user.image));
            binding.getRoot().setOnClickListener(v ->userListener.onUserClicked(user) );
        }

    }
    private Bitmap getUserImage(String encodedImage){
        byte[] bytes= Base64.decode(encodedImage,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }
}
