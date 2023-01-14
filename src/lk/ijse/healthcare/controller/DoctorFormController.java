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
import lk.ijse.healthcare.bo.custom.DoctorBo;
import lk.ijse.healthcare.dto.DoctorDto;
import lk.ijse.healthcare.view.tm.DoctorTm;

import java.io.IOException;
import java.util.Optional;

public class DoctorFormController {
    public AnchorPane doctorFormContext;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public JFXTextField txtContact;
    public TextField txtSearch;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colContact;
    public TableColumn colOption;
    public TableView<DoctorTm> tblDoctors;
    public TableColumn colId;
    public JFXButton btnSave;

    private String searchText="";

    DoctorBo bo = BoFactory.getInstance().getBo(BoFactory.BoType.DOCTOR);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        searchDoctors(searchText);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText=newValue;
            searchDoctors(searchText);
        });

        tblDoctors.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        setData(newValue);
                    }

                });
    }

    private void setData(DoctorTm tm) {
        txtId.setText(tm.getId());
        txtName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtContact.setText(tm.getContact());
        btnSave.setText("Update Doctor");
    }

    private void searchDoctors(String text) {
        ObservableList<DoctorTm> tmList =
                FXCollections.observableArrayList();
        try {
            for (DoctorDto dto : bo.search(text)
            ) {

                Button btn = new Button("Delete");
                tmList.add(new DoctorTm(dto.getdId(), dto.getName()
                        , dto.getAddress(), dto.getContact(), btn));

                btn.setOnAction(event -> {

                    Alert alert = new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "are you sure?",
                            ButtonType.YES,
                            ButtonType.NO
                    );
                    Optional<ButtonType> buttonType = alert.showAndWait();
                    if(buttonType.get().getButtonData().toString().equals("YES")){
                        System.out.println(dto.getdId()+" Deleted");
                    }
                    if (buttonType.get() == ButtonType.YES) {
                        try {
                            if (bo.deleteDoctor(dto.getdId())) {
                                searchDoctors(searchText);
                                new Alert(Alert.AlertType.CONFIRMATION,
                                        "Doctor Deleted!..").show();
                            } else {
                                new Alert(Alert.AlertType.WARNING,
                                        "Try Again!..").show();
                            }
                        } catch (Exception e) {
                            new Alert(Alert.AlertType.ERROR,
                                    "Error..").show();
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        tblDoctors.setItems(tmList);
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        DoctorDto dto = new DoctorDto(txtId.getText(), txtName.getText(),
                txtAddress.getText(), txtContact.getText());

        if (btnSave.getText().equalsIgnoreCase("Save Doctor")) {
            try {
                boolean isSaved = bo.saveDoctor(dto);
                if (isSaved) {
                    clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Doctor Saved!..").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!..").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Try Again!..").show();
            }
        } else {
            try {
                boolean isSaved = bo.updateDoctor(dto);
                if (isSaved) {
                    clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Doctor Updated!..").show();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Try Again!..").show();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Try Again!..").show();
            }
        }

        searchDoctors(searchText);
    }

    public void newDoctorOnAction(ActionEvent actionEvent) {
        btnSave.setText("Save Doctor");
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        setUI("DashboardForm");
    }

    private void clear() {
        txtId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
    }

    private void setUI(String location) throws IOException {
        Stage stage = (Stage) doctorFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
    }
}

