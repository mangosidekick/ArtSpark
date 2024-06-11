package com.mango.artsparkxml.Model;

import android.graphics.Bitmap;

public class ProfileImageModel {
    private Bitmap image;

    public ProfileImageModel(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
