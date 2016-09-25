package com.example.alexandercheng.hw5;

import java.net.URL;

// This is a simple data aggregation class that holds items of interest
// for a reddit post.  When you parse the reddit JSON, you will extract
// these items of interest and add records into your dynamic array adapter.
public class RedditRecord {
    public String title;
    public URL thumbnailURL;
    public URL imageURL;
}
