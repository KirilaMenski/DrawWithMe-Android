package by.ansgar.drawwithme.entity;

import java.io.Serializable;

/**
 * Created by kirila on 16.3.17.
 */

public class User implements Serializable {

    private String mDeviceId;
    private String mName;
    private int mSex;

    public User(){

    }

    public User(String deviceId, String name, int sex){
        mDeviceId = deviceId;
        mName = name;
        mSex = sex;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public void setDeviceId(String deviceId) {
        mDeviceId = deviceId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getSex() {
        return mSex;
    }

    public void setSex(int sex) {
        mSex = sex;
    }
}
