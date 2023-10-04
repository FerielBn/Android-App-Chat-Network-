package Interfaces;

import java.util.ArrayList;

import AppClasses.Message;

public interface OnDataRecievedMessages {
    void callback(ArrayList<Message> messages);
}
