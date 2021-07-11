package com.meembusoft.ln.model.colormatchtab;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Product implements Suggestion, Comparable<Product> {

    private int id;
    private String name;
    private boolean isFavorite = false;
    private boolean hasOffer = false;
    private List<Image> images;
    private String vendor;
    private String note;
    private boolean hasDependency = false;
    private boolean availability = true;
    private List<Unit> units = new ArrayList<>();
    // Helper variables
    private HashMap<String, Integer> selectedQuantity = new HashMap<>();
    private Unit selectedUnit;

    public Product() {
        units.clear();
        selectedQuantity.clear();
    }

    public Product(int id, String name, boolean isFavorite, boolean hasOffer, List<Image> images, String vendor, String note, boolean hasDependency, boolean availability, List<Unit> units) {
        this.id = id;
        this.name = name;
        this.isFavorite = isFavorite;
        this.hasOffer = hasOffer;
        this.images = images;
        this.vendor = vendor;
        this.note = note;
        this.hasDependency = hasDependency;
        this.availability = availability;
        this.units = units;
        this.selectedQuantity.clear();
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isHasOffer() {
        return hasOffer;
    }

    public void setHasOffer(boolean hasOffer) {
        this.hasOffer = hasOffer;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isHasDependency() {
        return hasDependency;
    }

    public void setHasDependency(boolean hasDependency) {
        this.hasDependency = hasDependency;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public int getSelectedQuantity(String unitName) {
        Integer quantity = 0;
        if (isAddedToCartBefore()) {
            quantity = selectedQuantity.get(unitName);
            if (quantity == null) {
                quantity = 0;
            }
        }
        return quantity;
    }

    public void setSelectedQuantity(String unitName, int quantity) {
        selectedQuantity.put(unitName, quantity);
    }

    public boolean isAddedToCartBefore() {
        return selectedQuantity.size() > 0;
    }

    public Unit getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(Unit selectedUnit) {
        this.selectedUnit = selectedUnit;
    }

    @Override
    public int compareTo(Product o) {
        return getName().compareToIgnoreCase(o.getName());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isFavorite=" + isFavorite +
                ", hasOffer=" + hasOffer +
                ", images=" + images +
                ", vendor='" + vendor + '\'' +
                ", note='" + note + '\'' +
                ", hasDependency=" + hasDependency +
                ", availability=" + availability +
                ", units=" + units +
                ", selectedQuantity=" + selectedQuantity +
                ", selectedUnit=" + selectedUnit +
                '}';
    }
}