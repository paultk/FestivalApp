package com.example.paulthomaskorsvold.festivalappclean.models;

import java.io.Serializable;

/**
 * Created by paulthomaskorsvold on 10/7/17.
 */

public class ChecklistItem implements Serializable{

//    db labels
    public static final String TABLE = "checklist_item";
    public static final String KEY_ID = "id";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_STATUS = "status";
    public static final String KEY_TITLE = "title";

    private String mDescription, mDateCreated, mTitle, mStatus;
    private int mId;

    public ChecklistItem(int id, String mDescription, String mDateCreated, String mTitle, String mStatus) {
        mId = id;
        this.mDescription = mDescription;
        this.mDateCreated = mDateCreated;
        this.mTitle = mTitle;
        this.mStatus = mStatus;
    }

    public int getmId() {
        return mId;
    }

    public ChecklistItem(){}

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmDateCreated() {
        return mDateCreated;
    }

    public void setmDateCreated(String mDateCreated) {
        this.mDateCreated = mDateCreated;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }


    @Override
    public String toString() {
        return "ChecklistItem{" +
                "mDescription='" + mDescription + '\'' +
                ", mDateCreated='" + mDateCreated + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mStatus='" + mStatus + '\'' +
                ", mId=" + mId +
                '}';
    }
}
