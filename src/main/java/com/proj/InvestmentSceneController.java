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

import com.proj.model.InvestmentData;
import com.proj.model.UserData;


public class InvestmentSceneController {
    private UserData user;

    public InvestmentSceneController() {}

    public static Scene CreateScene(UserData user) throws Exception {
        URL sceneUrl = InvestmentSceneController.class.getResource("investment-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        InvestmentSceneController controller = loader.getController();
        controller.user = user;

        return scene;
    }

    @FXML
    protected Button btInvestmentRegister;
    @FXML
    protected TextField tfInvestmentReason;
    @FXML
    protected TextField tfInvestmentValue;
    @FXML
    protected DatePicker tfInvestmentDate;

    @FXML
    protected void submit(ActionEvent e) throws Exception {
        submit();
    }

    void submit() throws Exception {
        Session session = HibernateUtil
        .getSessionFactory()
        .getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String investmentReason = tfInvestmentReason.getText();
        String investmentValue = tfInvestmentValue.getText();
        Date investmentDate = Date.valueOf(tfInvestmentDate.getValue());

        LocalDate investmentLocalDate = Date.valueOf(tfInvestmentDate.getValue()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        int days = Period.between(investmentLocalDate, currentDate).getDays();
        
        if (!DayValidator.validate(days)) {
            Alert alert = new Alert(
            AlertType.ERROR,
            "Data Inválida.",
            ButtonType.OK
            );
            alert.showAndWait();
            return; 
        } else {

            session.save(new InvestmentData(
                user,
                Double.parseDouble(investmentValue),
                investmentDate,
                investmentReason
            ));
            transaction.commit();
    
            // Fechando o login
            Stage crrStage = (Stage)btInvestmentRegister
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