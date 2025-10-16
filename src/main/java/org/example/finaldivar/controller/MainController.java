package org.example.finaldivar.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.finaldivar.model.Listing;
import org.example.finaldivar.model.ListingDatabase;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private ListView<Listing> listingsListView;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private Label welcomeLabel;

    private String currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize category dropdown
        categoryComboBox.setItems(FXCollections.observableArrayList(
                "همه دسته‌ها", "املاک", "وسایل نقلیه", "کالای دیجیتال", "خانه و آشپزخانه",
                "خدمات", "وسایل شخصی", "سرگرمی و فراغت", "اجتماعی", "استخدام"
        ));
        categoryComboBox.getSelectionModel().selectFirst();

        // Set up list view
        listingsListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Listing item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getTitle() + " - " + item.getPrice() + " تومان - " + item.getLocation());
                }
            }
        });

        // Handle double-click on listing
        listingsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Listing selectedListing = listingsListView.getSelectionModel().getSelectedItem();
                if (selectedListing != null) {
                    openListingDetails(selectedListing);
                }
            }
        });

        // Load initial listings
        refreshListings();
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
        welcomeLabel.setText("خوش آمدید، " + username);
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        refreshListings();
    }

    @FXML
    private void handleNewListing(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/New Listing.fxml"));
            Parent root = loader.load();

            NewListingController controller = loader.getController();
            controller.setCurrentUser(currentUser);

            Stage stage = new Stage();
            stage.setTitle("ثبت آگهی جدید");
            stage.setScene(new Scene(root, 600, 650));
            stage.setOnHidden(e -> refreshListings()); // Refresh listings when returning
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "خطا", "خطا در بارگذاری صفحه ثبت آگهی: " + e.getMessage());
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setTitle("دیوار - ورود به سیستم");
            stage.setScene(new Scene(root, 450, 500));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "خطا", "خطا در بازگشت به صفحه ورود: " + e.getMessage());
        }
    }

    private void refreshListings() {
        ListingDatabase db = ListingDatabase.getInstance();

        String searchQuery = searchField.getText();
        String selectedCategory = categoryComboBox.getValue();

        // If "All Categories" is selected, pass null to search all categories
        if (selectedCategory.equals("همه دسته‌ها")) {
            selectedCategory = null;
        }

        List<Listing> listings = db.searchListings(searchQuery, selectedCategory);
        listingsListView.setItems(FXCollections.observableArrayList(listings));
    }

    private void openListingDetails(Listing listing) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Listing Details.fxml"));
            Parent root = loader.load();

            ListingController controller = loader.getController();
            controller.setListing(listing);

            Stage stage = new Stage();
            stage.setTitle(listing.getTitle());
            stage.setScene(new Scene(root, 700, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "خطا", "خطا در بارگذاری جزئیات آگهی: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
