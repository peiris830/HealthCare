package lk.ijse.healthcare.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.healthcare.bo.BoFactory;
import lk.ijse.healthcare.bo.custom.PatientBo;
import lk.ijse.healthcare.dto.PatientDto;
import lk.ijse.healthcare.view.tm.PatientTm;

import java.io.IOException;
import java.util.Optional;

public class PatientFormController {
    public AnchorPane patientsFormContext;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public TextField txtSearch;
    public TableView tblPatient;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colOption;
    public JFXButton btnSavePatient;

    private PatientBo bo = BoFactory.getInstance().getBo(BoFactory.BoType.PATIENT);

    public void initialize(){
        clear();
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));



        searchPatient(sText);

        tblPatient.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                setData((PatientTm)newValue);
            }
        });

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            sText=newValue;
            searchPatient(sText);
        });


    }

    private String sText="";

    private void clear() {
        txtId.setText(null);
        txtName.setText(null);
        txtAddress.setText(null);
        txtContact.setText(null);
    }

    private void setData(PatientTm tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtContact.setText(tm.getContact());
        btnSavePatient.setText("Update Patient");
    }

    private void searchPatient(String Text) {
        ObservableList<PatientTm> TmList = FXCollections.observableArrayList();
        try {
            for (PatientDto dto : bo.getAllPatient()) {
                Button btn = new Button("Delete");
                TmList.add(new PatientTm(dto.getpId(), dto.getName(), dto.getAddress(), dto.getContact(), btn));

                btn.setOnAction(event -> {
                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Are You Sure?",
                            ButtonType.YES,
                            ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    try {
                        if (buttonType.get() == ButtonType.YES) {
                            bo.deletePatient(dto.getpId());
                            searchPatient(Text);
                        }
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, "Try Againg").show();
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        tblPatient.setItems(TmList);

    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) patientsFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }

    public void patientSaveOnAction(ActionEvent actionEvent) {
        if(txtId.getText()==null||txtName.getText()==null||txtAddress.getText()==null||txtContact.getText()==null) {
            new Alert(Alert.AlertType.ERROR, "Please Enter All Information").show();
        }else {
            PatientDto dto = new PatientDto(txtId.getText(), txtName.getText(), txtAddress.getText(), txtContact.getText());
            if(btnSavePatient.getText().equals("Save Patient")){
                try {
                    boolean isSaved = bo.savePatient(dto);
                    if (isSaved) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Patient Saved...!").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try Againg").show();
                    }
                }catch (Exception e){
                    new Alert(Alert.AlertType.ERROR,"Try Againg").show();
                }
            }else{
                try {
                    boolean isUpdated = bo.updatePatient(dto);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Doctor Updated...!").show();
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Try Again").show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR,"Try Again").show();
                }
            }
        }
        searchPatient(sText);
    }

    public void newPatientOnAction(ActionEvent actionEvent) {
        clear();
        btnSavePatient.setText("Save Patient");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }
}
