package com.proj;

import java.net.URL;
import java.util.List;
import java.util.Locale;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.proj.model.InvestmentData;
import com.proj.model.RevenueData;
import com.proj.model.SpendingData;
import com.proj.model.UserData;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class MainSceneController {
    
    public MainSceneController() {}

    @FXML
    protected Label lbInvestment;
    @FXML
    protected Label lbInvestments;
    @FXML
    protected Label lbBalance;
    @FXML
    protected Label lbRevenue;
    @FXML
    protected Label lbSpending;
    @FXML
    protected Label lbSpendings;
    @FXML
    protected Label lbRevenues;
    @FXML
    protected Button btRevenue;
    @FXML
    protected Button btSpending;
    @FXML
    protected Button btInvestment;
    @FXML
    protected Button btDelete;

    private UserData user;

    public static Scene CreateScene(UserData user) throws Exception
    {
        URL sceneUrl = LoginSceneController.class.getResource("main-scene.fxml");
        FXMLLoader loader = new FXMLLoader(sceneUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        MainSceneController controller = loader.getController();
        controller.user = user;
        controller.loadtInvestimentsFromDataBase(user.getIDUser());
        controller.loadtRevenueFromDataBase(user.getIDUser());
        controller.loadtSpendingFromDataBase(user.getIDUser());
        controller.loadtBalance();

        return scene;
    }

    public void loadtInvestimentsFromDataBase(Long user) {
        Session session = HibernateUtil
            .getSessionFactory()
            .getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query queryInvestment = session.createQuery(
          "from InvestmentData where FKUser = :user"
        );
        queryInvestment.setParameter("user", user);
        List<InvestmentData> invests = queryInvestment.list();

        StringBuilder investmentText = new StringBuilder();
        for (InvestmentData investment : invests) {
            investmentText.append(investment.getInvestmentReason()).append(": $").append(investment.getInvestmentValue()).append("\n");
        }

        lbInvestments.setText(investmentText.toString());

        lbInvestment.setText(String.format(Locale.US, "%.2f",
        invests
            .stream()
            .mapToDouble(InvestmentData::getInvestmentValue)
            .sum()
        ));
    

        transaction.commit();
    }

    public void loadtRevenueFromDataBase(Long user) {
        Session session = HibernateUtil
        .getSessionFactory()
        .getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query queryRevenue = session.createQuery(
      "from RevenueData where FKUser = :user"
        );
        queryRevenue.setParameter("user", user);
        List<RevenueData> revenues = queryRevenue.list();

        StringBuilder revenueText = new StringBuilder();
        for (RevenueData revenue : revenues) {
            revenueText.append(revenue.getRevenueReason()).append(": $").append(revenue.getRevenueValue()).append("\n");
        }

        lbRevenues.setText(revenueText.toString());

        lbRevenue.setText(String.format(Locale.US, "%.2f",
        revenues
            .stream()
            .mapToDouble(RevenueData::getRevenueValue)
            .sum()
        ));

        transaction.commit();
    }

    public void loadtSpendingFromDataBase(Long user) {
        Session session = HibernateUtil
        .getSessionFactory()
        .getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query querySpending = session.createQuery(
      "from SpendingData where FKUser = :user"
        );
        querySpending.setParameter("user", user);
        List<SpendingData> spendings = querySpending.list();

        StringBuilder spendingText = new StringBuilder();
        for (SpendingData spending : spendings) {
            spendingText.append(spending.getSpendingReason()).append(": $").append(spending.getSpendingValue()).append("\n");
        }

        lbSpendings.setText(spendingText.toString());

        lbSpending.setText(String.format(Locale.US, "%.2f",
        spendings
            .stream()
            .mapToDouble(SpendingData::getSpendingValue)
            .sum()
        ));

        transaction.commit();  
    }

    public void loadtBalance() {
        double revenue = Double.parseDouble(lbRevenue.getText());
        double spending = Double.parseDouble(lbSpending.getText());
    
        // Calculate the balance
        double balance = revenue - spending;
    
        // Set the balance as text in lbBalance
        lbBalance.setText(String.format(Locale.US, "%.2f", balance));
    }

    @FXML
    protected void openInvestment(ActionEvent e) throws Exception {
        // Fechando o login
        Stage crrStage = (Stage)btInvestment
            .getScene().getWindow();
        crrStage.close();

        // Abrindo a tela principal
        Stage stage = new Stage();
        Scene scene = InvestmentSceneController.CreateScene(user);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void openRevenue(ActionEvent e) throws Exception {
        Stage crrStage = (Stage)btRevenue
            .getScene().getWindow();
        crrStage.close();

        Stage stage = new Stage();
        Scene scene = RevenueSceneController.CreateScene(user);
        stage.setScene(scene);
        stage.show();   
   }

   @FXML
   protected void openSpending(ActionEvent e) throws Exception {
        Stage crrStage = (Stage)btSpending
            .getScene().getWindow();
        crrStage.close();

        Stage stage = new Stage();
        Scene scene = SpendingSceneController.CreateScene(user);
        stage.setScene(scene);
        stage.show();
   }

    @FXML
    protected void deleteAccount(ActionEvent e) throws Exception {

        Session session = HibernateUtil
        .getSessionFactory()
        .getCurrentSession();
        Transaction transaction = session.beginTransaction();

        Query querySpending = session.createQuery(
        "delete from SpendingData where FKUser = :user"
        );
        querySpending.setParameter("user", user.getIDUser());
        querySpending.executeUpdate();

        querySpending = session.createQuery(
        "delete from InvestmentData where FKUser = :user"
        );
        querySpending.setParameter("user", user.getIDUser());
        querySpending.executeUpdate();

        querySpending = session.createQuery(
            "delete from RevenueData where FKUser = :user"
            );
            querySpending.setParameter("user", user.getIDUser());
            querySpending.executeUpdate();

        querySpending = session.createQuery(
                "delete from UserData where IDUser = :user"
                );
                querySpending.setParameter("user", user.getIDUser());
                querySpending.executeUpdate();
        transaction.commit();
        
        Alert alert = new Alert(
        AlertType.INFORMATION,
        "Conta Deletada",
        ButtonType.OK
        );
        alert.showAndWait();

        Stage crrStage = (Stage)btDelete
            .getScene().getWindow();
        crrStage.close();
    }
}   
