package by.ansgar.drawwithme.entity;

import java.io.Serializable;

/**
 * Created by kirila on 27.3.17.
 */

public class Room implements Serializable {

    private String mUuid;
    private String mName;
    private int mMembersCount;

    public Room() {

    }

    public Room(String uuid, String name, int membersCount) {
        mUuid = uuid;
        mName = name;
        mMembersCount = membersCount;
    }

    public String getUuid() {
        return mUuid;
    }

    public void setUuid(String uuid) {
        mUuid = uuid;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getMembersCount() {
        return mMembersCount;
    }

    public void setMembersCount(int membersCount) {
        mMembersCount = membersCount;
    }
}
