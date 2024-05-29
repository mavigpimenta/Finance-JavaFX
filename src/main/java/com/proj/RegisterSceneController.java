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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.proj.model.UserData;

public class RegisterSceneController {

  // Vamos fazer uma função CreateScene que irá criar a cena apartir de um FXMLLoader carregando o .fxml.
  public static Scene CreateScene() throws Exception
  {
    URL sceneUrl = RegisterSceneController.class.getResource("register-scene.fxml");
    Parent root = FXMLLoader.load(sceneUrl);
    Scene scene = new Scene(root);
    return scene;
  }

  @FXML
  protected Button btRegister;
  @FXML
  protected TextField tfUsername;
  @FXML
  protected PasswordField pfUserpass;
  @FXML
  protected TextField tfUserEmail;
  @FXML
  protected DatePicker tfUserBirth;

  @FXML
  protected void submit(ActionEvent e) throws Exception {
    Authentification auth = Authentification.tryLogin(
      tfUsername.getText(), pfUserpass.getText()
    );

    if (auth.userExists()) {
      Alert alert = new Alert(
        AlertType.ERROR,
        "Usuário já existente.",
        ButtonType.OK
      );
      alert.showAndWait();
      return; 
    } else {
      Session session = HibernateUtil
      .getSessionFactory()
      .getCurrentSession();
      Transaction transaction = session.beginTransaction();

      String username = tfUsername.getText();
      String password = pfUserpass.getText();
      Date birth = Date.valueOf(tfUserBirth.getValue());
      String email = tfUserEmail.getText();

      LocalDate userBirthDate = Date.valueOf(tfUserBirth.getValue()).toLocalDate();
      LocalDate currentDate = LocalDate.now();
      int age = Period.between(userBirthDate, currentDate).getYears();

      if (!AgeValidator.validate(age)) {
        Alert alert = new Alert(
          AlertType.ERROR,
          "Usuário menor de idade.",
          ButtonType.OK
      );
        alert.showAndWait();
        return; 
      } else {

        session.save(new UserData(username, birth, email, password));
        transaction.commit();

        // Fechando o login
        Stage crrStage = (Stage)btRegister
        .getScene().getWindow();
        crrStage.close();

        // Abrindo a tela principal
        Stage stage = new Stage();
        Scene scene = LoginSceneController.CreateScene();
        stage.setScene(scene);
        stage.show();
      }
    }
  }
} 
