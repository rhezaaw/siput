/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.application.pengeluaran;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author fachrul
 */
public class Pengeluaran implements Initializable {

    @FXML
    private BorderPane bpContent;
    @FXML
    private Label lblView;
    @FXML
    private MenuItem btnAddEmployee;
    @FXML
    private MenuItem btnViewEmployee;
    @FXML
    private ImageView ivEmployeIcon;
    @FXML
    private StackPane spEmployeContent;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAddEmployeeOnClick(ActionEvent event) {
    }

    @FXML
    private void btnViewEmployeeOnAction(ActionEvent event) {
    }
    
}
