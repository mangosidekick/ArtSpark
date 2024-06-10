package com.mango.artsparkxml.Model;

import android.graphics.Bitmap;


import java.io.ByteArrayOutputStream;

public class ImageModel {

    private long id;
    private String uri;
    private byte[] imageByteArray;  // why not this?
    private Bitmap bitmap;
    private float x;
    private float y;
    private float scaleX;
    private float scaleY;

    public ImageModel() {}

    public ImageModel(String uri, float x, float y, float scaleX, float scaleY) {
        this.uri = uri;
        this.x = x;
        this.y = y;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    // Getter and Setter for Id
    public long getId() {
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    // Getter and Setter for uri
    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri){
        this.uri = uri;
    }

    // Getter and Setter for imageByteArray
    public byte[] getImageByteArray() {
        return this.imageByteArray;
    }

    public void setImageByteArray(byte[] imageByteArray) {
        this.imageByteArray = imageByteArray;
    }

    // Getter and Setter for Bitmap
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.imageByteArray = getByteArrayFromBitmap(bitmap);
    }

    // Getter and Setter for x
    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    // Getter and Setter for y
    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    // Getter and Setter for scaleX
    public float getScaleX() {
        return this.scaleX;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    // Getter and Setter for scaleY
    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }


public class ImageModel {
    private Bitmap image;

    public ImageModel(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

}
