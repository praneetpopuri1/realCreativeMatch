package com.example.creativematch.listeners;

import com.example.creativematch.OtherUser;

public interface UserListener {
    void onUserClicked(OtherUser user);
    void onUserClicked(int userSelected);
}
