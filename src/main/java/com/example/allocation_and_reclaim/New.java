package com.example.allocation_and_reclaim;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Bean.PCBFactory;
import view.methods;

public class New {
    public Button Button_Create;
    @FXML
    public ComboBox<String> Combo_Priority;
    public Button close;
    @FXML
    private TextField Text_Size;
    @FXML
    public TextField Text_Runtime;
    @FXML
    public TextField Text_PID;
    @FXML
    public TextField Text_PCBPtr;

    public void Create(ActionEvent event) {
        String pid = Text_PID.getText();
        String runtime = Text_Runtime.getText();
        String size = Text_Size.getText();
        if(pid.equals("")||runtime.equals("")||size.equals("")){
            return;
        }
        int priority = Combo_Priority.getSelectionModel().getSelectedIndex();//0~7
        methods.addToNewStack(PCBFactory.CreateProcess(pid,Integer.parseInt(runtime),priority,Integer.parseInt(size)));
        ((Stage)(Button_Create.getScene().getWindow())).close();
    }
    public void Close(ActionEvent actionEvent) {
        ((Stage)(close.getScene().getWindow())).close();
    }
}
