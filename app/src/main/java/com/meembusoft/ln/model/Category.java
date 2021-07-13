package com.meembusoft.ln.model;


import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Category implements Suggestion, Comparable<Category> {

    private int id;
    private String name;
    private String image;
    private int newItemCount;
    private List<Subcategory> subcategory = new ArrayList<>();

    public Category() {
    }

    public Category(int id, String name, String image, int newItemCount, List<Subcategory> subcategory) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.newItemCount = newItemCount;
        this.subcategory = subcategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNewItemCount() {
        return newItemCount;
    }

    public void setNewItemCount(int newItemCount) {
        this.newItemCount = newItemCount;
    }

    public List<Subcategory> getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(List<Subcategory> subcategory) {
        this.subcategory = subcategory;
    }

    @Override
    public int compareTo(Category o) {
        return getName().compareToIgnoreCase(o.getName());
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", newItemCount=" + newItemCount +
                ", subcategory=" + subcategory +
                '}';
    }
}