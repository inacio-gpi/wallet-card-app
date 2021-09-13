package com.psiu.helloworld.model;


public class Category
{
    public String type;
    public String background_color;
    public String image_path;

    public Category(String type, String background_color, String image_path) {
        this.type = type;
        this.background_color = background_color;
        this.image_path = image_path;
    }

    public Category() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}