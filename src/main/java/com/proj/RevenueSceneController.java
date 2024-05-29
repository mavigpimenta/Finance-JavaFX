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

import com.proj.model.RevenueData;
import com.proj.model.UserData;


public class RevenueSceneController {
    private UserData user;

    public RevenueSceneController() {}

    public static Scene CreateScene(UserData user) throws Exception {
        URL sceneUrl = RevenueSceneController.class.getResource("revenue-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        RevenueSceneController controller = loader.getController();
        controller.user = user;

        return scene;
    }

    @FXML
    protected Button btRevenueRegister;
    @FXML
    protected TextField tfRevenueReason;
    @FXML
    protected TextField tfRevenueValue;
    @FXML
    protected DatePicker tfRevenueDate;

    @FXML
    protected void submit(ActionEvent e) throws Exception {
        submit();
    }

    void submit() throws Exception {
        Session session = HibernateUtil
        .getSessionFactory()
        .getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String revenueReason = tfRevenueReason.getText();
        String revenueValue = tfRevenueValue.getText();
        Date revenueDate = Date.valueOf(tfRevenueDate.getValue());

        LocalDate revenueLocalDate = Date.valueOf(tfRevenueDate.getValue()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int days = Period.between(revenueLocalDate, currentDate).getDays();
        
        if (!DayValidator.validate(days)) {
            Alert alert = new Alert(
            AlertType.ERROR,
            "Data Inválida.",
            ButtonType.OK
            );
            alert.showAndWait();
            return; 
        } else {

            session.save(new RevenueData(user, Double.parseDouble(revenueValue), revenueDate, revenueReason));
            transaction.commit();
    
            // Fechando o login
            Stage crrStage = (Stage)btRevenueRegister
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
