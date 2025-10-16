package org.example.finaldivar.model;

import java.time.LocalDateTime;

public class Listing {
    private int id;
    private String title;
    private String description;
    private double price;
    private String category;
    private String location;
    private String contactInfo;
    private LocalDateTime datePosted;
    private String postedBy;
    private String imageUrl;

    public Listing(int id, String title, String description, double price, String category,
                   String location, String contactInfo, String postedBy, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.category = category;
        this.location = location;
        this.contactInfo = contactInfo;
        this.datePosted = LocalDateTime.now();
        this.postedBy = postedBy;
        this.imageUrl = imageUrl;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return title + " - " + price + " تومان";
    }
}
