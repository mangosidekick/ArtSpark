package com.mango.artsparkxml;

import java.util.ArrayList;
import java.util.List;

public class CardItem {
    private String id;
    private String title;
    private List<Image> images;

    public CardItem(String id, String title) {
        this.id = id;
        this.title = title;
        this.images = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImages() {
        return this.images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        this.images.add(image);
    }

    public static class Image {
        private String uri;
        private float x;
        private float y;
        private float scaleX;
        private float scaleY;

        public Image(String uri, float x, float y, float scaleX, float scaleY) {
            this.uri = uri;
            this.x = x;
            this.y = y;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
        }

        // Getters and setters

    }
}