package org.example.finaldivar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.finaldivar.model.Listing;
import org.example.finaldivar.model.ListingDatabase;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class NewListingController implements Initializable {
    @FXML
    private TextField titleField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField locationField;

    @FXML
    private TextField contactInfoField;

    @FXML
    private TextField imagePathField;

    @FXML
    private TextArea descriptionArea;

    private String currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize category dropdown
        categoryComboBox.getItems().addAll(
                "املاک", "وسایل نقلیه", "کالای دیجیتال", "خانه و آشپزخانه",
                "خدمات", "وسایل شخصی", "سرگرمی و فراغت", "اجتماعی", "استخدام"
        );
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }

    @FXML
    private void handleChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("انتخاب تصویر");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(titleField.getScene().getWindow());
        if (selectedFile != null) {
            imagePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void handleSubmit(ActionEvent event) {
        // Validate input
        if (titleField.getText().isEmpty() ||
                priceField.getText().isEmpty() ||
                categoryComboBox.getValue() == null ||
                locationField.getText().isEmpty() ||
                contactInfoField.getText().isEmpty() ||
                descriptionArea.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "خطا", "لطفاً تمام فیلدهای ضروری را پر کنید");
            return;
        }

        // Validate price is a number
        double price;
        try {
            price = Double.parseDouble(priceField.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "خطا", "لطفاً قیمت را به صورت عدد وارد کنید");
            return;
        }

        // Create new listing
        ListingDatabase db = ListingDatabase.getInstance();
        int newId = db.getNextListingId();

        Listing newListing = new Listing(
                newId,
                titleField.getText(),
                descriptionArea.getText(),
                price,
                categoryComboBox.getValue(),
                locationField.getText(),
                contactInfoField.getText(),
                currentUser,
                imagePathField.getText()
        );

        db.addListing(newListing);

        showAlert(Alert.AlertType.INFORMATION, "موفقیت", "آگهی شما با موفقیت ثبت شد");

        // Close the window
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
