package com.example.dahae.myandroiice.Adapter;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by b_newyork on 2016-01-27.
 */
public class Keyword implements Serializable {

    String keyword;
    String keywordInfo;

    public Keyword(String keyword, String keywordInfo) {
        this.keyword = keyword;
        this.keywordInfo = keywordInfo;
    }

    public Keyword(String keyword) {
        this.keyword = keyword;
        this.keywordInfo = "";
    }

    public String getKeyword() { return keyword;}

    public String getKeywordInfo() { return keywordInfo;}

}
