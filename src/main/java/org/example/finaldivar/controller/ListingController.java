package org.example.finaldivar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.finaldivar.model.Listing;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class ListingController {
    @FXML
    private Label titleLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label categoryLabel;

    @FXML
    private Label locationLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label sellerLabel;

    @FXML
    private Label contactInfoLabel;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ImageView listingImageView;

    private Listing listing;

    public void setListing(Listing listing) {
        this.listing = listing;

        titleLabel.setText(listing.getTitle());
        priceLabel.setText(String.format("%,.0f تومان", listing.getPrice()));
        categoryLabel.setText(listing.getCategory());
        locationLabel.setText(listing.getLocation());
        dateLabel.setText(listing.getDatePosted().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
        sellerLabel.setText(listing.getPostedBy());
        contactInfoLabel.setText(listing.getContactInfo());
        descriptionArea.setText(listing.getDescription());

        // Load image if available
        String imagePath = listing.getImageUrl();
        if (imagePath != null && !imagePath.isEmpty()) {
            try {
                // Try to load from file path first
                File file = new File(imagePath);
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    listingImageView.setImage(image);
                } else {
                    System.err.println("Image file does not exist: " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage stage = (Stage) titleLabel.getScene().getWindow();
        stage.close();
    }
}
