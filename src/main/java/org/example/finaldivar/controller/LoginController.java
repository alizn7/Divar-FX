package org.example.finaldivar.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.finaldivar.model.ListingDatabase;
import org.example.finaldivar.model.User;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    private boolean isLoginMode = true;

    @FXML
    private void handleLoginRegister(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "خطا", "لطفاً نام کاربری و رمز عبور را وارد کنید");
            return;
        }

        ListingDatabase db = ListingDatabase.getInstance();

        if (isLoginMode) {
            // Login mode
            if (db.authenticateUser(username, password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
                    Parent root = loader.load();

                    MainController controller = loader.getController();
                    controller.setCurrentUser(username);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 800, 600);
                    stage.setTitle("دیوار - صفحه اصلی");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "خطا", "خطا در بارگذاری صفحه اصلی: " + e.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "خطا در ورود", "نام کاربری یا رمز عبور اشتباه است");
            }
        } else {
            // Register mode
            String phone = phoneField.getText();
            String email = emailField.getText();

            if (phone.isEmpty() || email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "خطا", "لطفاً تمام فیلدها را پر کنید");
                return;
            }

            try {
                User newUser = new User(username, password, phone, email);
                db.addUser(newUser);
                showAlert(Alert.AlertType.INFORMATION, "ثبت نام موفق", "ثبت نام با موفقیت انجام شد. اکنون می‌توانید وارد شوید");
                toggleMode(null);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "خطا در ثبت نام", e.getMessage());
            }
        }
    }

    @FXML
    private void toggleMode(ActionEvent event) {
        isLoginMode = !isLoginMode;
        phoneField.setVisible(!isLoginMode);
        phoneLabel.setVisible(!isLoginMode);
        emailField.setVisible(!isLoginMode);
        emailLabel.setVisible(!isLoginMode);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
