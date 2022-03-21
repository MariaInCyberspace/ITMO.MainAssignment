package com.mariaincyberspace.lostandfound_1.utils;

public class Literals {

    public static class Nodes {

        public static final String USER_KEY = "Users";
        public static final String ITEM_KEY = "Items";
        public static final String CHAT_KEY = "Chats";
        public static final String MESSAGE_KEY = "Messages";
    }

    public static class Toasts {

        public static final String USER_SIGNED_UP = "User signed up";
        public static final String USER_NOT_SIGNED_UP = "User not signed up";
        public static final String USER_LOGGED_IN = "User logged in";
        public static final String USER_NOT_LOGGED_IN = "User not logged in";
        public static final String VALUES_STORED = "Values stored in the database";
        public static final String VALUES_NOT_STORED = "Values could not be stored";
        public static final String INPUT_PROMPT = "Please input ";
        public static final String INPUT_EMAIL_AND_PASSWORD = "email and password";
        public static final String INPUT_NAME = "name";
        public static final String INPUT_NAME_EMAIL_PASSWORD = "name, email and password";
        public static final String ITEM_ADDED_SUCCESS = "Item added successfully";
        public static final String ITEM_NOT_ADDED = "Failed to add item";
        public static final String INPUT_EMAIL = "email";
        public static final String INPUT_PASSWORD = "password";
        public static final String ITEM_DELETED = "Item deleted";
        public static final String USER_ADDED = "User added successfully";
        public static final String USER_NOT_ADDED = "User couldn't be added";
        public static final String USER_DELETED = "User deleted";
    }

    public static class ItemFields {

        public static final String NAME = "name";
        public static final String USER_ID = "userId";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String PHOTO_URI = "photoUri";
        public static final String ADDRESS = "address";
        public static final String TIMESTAMP = "timestamp";

    }

    public static class ChatFields {
        public static final String CHAT_ID = "chatId";
        public static final String OWNER_ID = "ownerId";
        public static final String FINDER_ID = "finderId";
    }

    public static class MessageFields {
        public static final String CHAT_ID = "chatId";
        public static final String MESSAGE_TEXT = "messageText";
        public static final String TIMESTAMP = "timestamp";
    }

    public static class UserFields {
        public static final String UID = "uid";
        public static final String NAME = "name";
    }

    public static class Api {
        public static final String API = "AIzaSyBVgj7xfYYcbq1TT0JebB5RIIBH0RPrlmE";
    }

    public static class BundleName {
        public static final String SELECTED_ITEM = "selected_item";
    }
    
}
