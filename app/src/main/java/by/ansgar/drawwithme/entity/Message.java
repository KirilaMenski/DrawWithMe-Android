package by.ansgar.drawwithme.entity;

import java.io.Serializable;

/**
 * Created by kirila on 28.3.17.
 */

public class Message implements Serializable {

    private String mDate;
    private String mAuthor;
    private String mMessage;

    public Message(){

    }

    public Message(String date, String author, String message) {
        mDate = date;
        mAuthor = author;
        mMessage = message;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }
}
