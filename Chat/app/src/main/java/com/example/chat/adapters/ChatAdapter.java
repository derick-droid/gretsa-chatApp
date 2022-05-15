package com.example.chat.adapters;

import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat.databinding.ItemContainerRecievedMessageBinding;
import com.example.chat.databinding.ItemContainerSentMessageBinding;
import com.example.chat.models.ChatMessage;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private final List<ChatMessage> chatMessages;
    private final String senderId;

    public static  final int VIEW_TYPE_SENT=1;
    public static final int VIEW_TYPE_RECEIVED=2;

    public ChatAdapter(List<ChatMessage> chatMessages, String senderId) {
        this.chatMessages = chatMessages;
        this.senderId = senderId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_SENT){
            return new SentMessageViewHolder(
                        ItemContainerSentMessageBinding.inflate(
                                LayoutInflater.from(parent.getContext()),
                                parent,
                                false
                        )
            );
        }else{
            return new ReceiverMessageViewHolder(
                    ItemContainerRecievedMessageBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    )
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(getItemViewType(position)==VIEW_TYPE_SENT){
            ((SentMessageViewHolder)holder).setData(chatMessages.get(position));
        }else{
            ((ReceiverMessageViewHolder)holder).setData(chatMessages.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(chatMessages.get(position).senderId.equals(senderId)){
            return VIEW_TYPE_SENT;
        }else{
            return VIEW_TYPE_RECEIVED;
        }

    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerSentMessageBinding binding;
        SentMessageViewHolder(ItemContainerSentMessageBinding itemContainerSentMessageBinding){
            super(itemContainerSentMessageBinding.getRoot());
            binding=itemContainerSentMessageBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);

        }
    }
    static class ReceiverMessageViewHolder extends RecyclerView.ViewHolder{
        private final ItemContainerRecievedMessageBinding binding;
        ReceiverMessageViewHolder(ItemContainerRecievedMessageBinding itemContainerRecievedMessageBinding){
            super(itemContainerRecievedMessageBinding.getRoot());
            binding=itemContainerRecievedMessageBinding;
        }
        void setData(ChatMessage chatMessage){
            binding.textMessage.setText(chatMessage.message);
            binding.textDateTime.setText(chatMessage.dateTime);
        }
    }
}
