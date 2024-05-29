package com.proj;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.proj.model.SpendingData;
import com.proj.model.UserData;


public class SpendingSceneController {
    private UserData user;

    public SpendingSceneController() {}

    public static Scene CreateScene(UserData user) throws Exception {
        URL sceneUrl = SpendingSceneController.class.getResource("spending-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        SpendingSceneController controller = loader.getController();
        controller.user = user;

        return scene;
    }

    @FXML
    protected Button btSpendingRegister;
    @FXML
    protected TextField tfSpendingReason;
    @FXML
    protected TextField tfSpendingValue;
    @FXML
    protected DatePicker tfSpendingDate;

    @FXML
    protected void submit(ActionEvent e) throws Exception {
        submit();
    }

    void submit() throws Exception {
        Session session = HibernateUtil
        .getSessionFactory()
        .getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String spendingReason = tfSpendingReason.getText();
        String spendingValue = tfSpendingValue.getText();
        Date spendingDate = Date.valueOf(tfSpendingDate.getValue());

        LocalDate spendingLocalDate = Date.valueOf(tfSpendingDate.getValue()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int days = Period.between(spendingLocalDate, currentDate).getDays();
        
        if (!DayValidator.validate(days)) {
            Alert alert = new Alert(
            AlertType.ERROR,
            "Data Inválida.",
            ButtonType.OK
            );
            alert.showAndWait();
            return; 
        } else {

            session.save(new SpendingData(user, Double.parseDouble(spendingValue), spendingDate, spendingReason));
            transaction.commit();
    
            // Fechando o login
            Stage crrStage = (Stage)btSpendingRegister
            .getScene().getWindow();
            crrStage.close();
        
            // Abrindo a tela principal
            Stage stage = new Stage();
            Scene scene = MainSceneController.CreateScene(user);
            stage.setScene(scene);
            stage.show();
        }
    }
}