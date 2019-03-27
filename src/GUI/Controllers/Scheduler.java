package GUI.Controllers;


import GUI.MaskField;
import Models.Robot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import persistence.DataBase;
import controllers.WatcherListener;
import Models.Watcher;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

public class Scheduler implements Initializable, WatcherListener {

    @FXML
    private TextField textFieldTaskPath;
    @FXML
    private MaskField textFieldHour;
    @FXML
    private TextField textFieldCode;
    @FXML
    private TextField textFieldAbbreviation;
    @FXML
    private TextField textFieldName;
    @FXML
    private MaskField textFieldDay;

    @FXML
    private ComboBox<Models.Robot.Type> comboBoxType;
    @FXML
    private CheckBox checkBoxMonday;
    @FXML
    private CheckBox checkBoxTuesday;
    @FXML
    private CheckBox checkBoxWednesday;
    @FXML
    private CheckBox checkBoxThursday;
    @FXML
    private CheckBox checkBoxFriday;
    @FXML
    private CheckBox checkBoxSaturday;
    @FXML
    private CheckBox checkBoxSunday;
    @FXML
    private CheckBox checkBoxWeekdays;


    @FXML
    private TableView<Robot> tableViewSchedules;
    @FXML
    private TableColumn<Robot, String> tableColumnName;
    @FXML
    private TableColumn<Robot, String> tableColumnPathfile;
    @FXML
    private TableColumn<Robot, Integer> tableColumnType;
    @FXML
    private TableColumn<Robot, String> tableColumnHour;
    @FXML
    private TableColumn<Robot, Calendar> tableColumnLastRun;

    @FXML
    private Label labelWathcerStatus;
    @FXML
    private Label labelURLDB;
    @FXML
    private Label labelDaysAdded;

    @FXML
    private AnchorPane anchorPaneDay;

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonAddDay;

    @FXML
    private Button buttonEdit;

    @FXML
    private Button buttomSchedule;

    @FXML
    private Button buttomSave;

    @FXML
    private Tab tabScheduler;

    @FXML
    private TabPane tabPaneScheduler;

    @FXML
    private AnchorPane anchorPaneWeekdays;

    @FXML
    private AnchorPane anchorPaneSchedule;

    private ObservableList<Robot> robots;

    private ArrayList<Integer> days;

    private ListChangeListener<Robot> listener = e -> {
        fillSelected();
        viewMode();
    };


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.textFieldDay.setMaskType(MaskField.Type.DAY);
        this.textFieldDay.setMaskType(MaskField.Type.DAY);
        this.textFieldHour.setMaskType(MaskField.Type.HOUR);

        /**Initialize comboBox*/

        this.comboBoxType.getItems().add(Models.Robot.Type.DAILY);
        this.comboBoxType.getItems().add(Models.Robot.Type.WEEKLY);
        this.comboBoxType.getItems().add(Models.Robot.Type.MONTHLY);
        this.comboBoxType.getItems().add(Models.Robot.Type.ONE_TIME);
        this.comboBoxType.getSelectionModel().selectFirst();

        this.tableColumnName.setCellValueFactory(new PropertyValueFactory<>("nameString"));
        this.tableColumnType.setCellValueFactory(new PropertyValueFactory<>("typeString"));
        this.tableColumnHour.setCellValueFactory(new PropertyValueFactory<>("hourString"));
        this.tableColumnLastRun.setCellValueFactory(new PropertyValueFactory<>("lastRunString"));
        this.tableColumnPathfile.setCellValueFactory(new PropertyValueFactory<>("pathFileString"));

        this.robots = tableViewSchedules.getSelectionModel().getSelectedItems();
        this.robots.addListener(listener);
        this.anchorPaneDay.setVisible(false);
        this.days = new ArrayList<>();
        this.updateDays();
        this.populateTable();
        this.fillSelected();
        this.addMode();
        Watcher.addListener(this);
    }


    private void fillSelected() {
        buttonDelete.setDisable(false);
        Robot robot = tableViewSchedules.getSelectionModel().getSelectedItem();
        if (robot != null) {
            this.textFieldCode.setText(robot.getCode());
            this.textFieldAbbreviation.setText(robot.getAbbreviation());
            this.textFieldName.setText(robot.getName());
            this.textFieldTaskPath.setText(robot.getPathFile());
            this.textFieldHour.setText(robot.getHourString());
            System.out.println(tableViewSchedules.getSelectionModel().getSelectedItem().getKey());
            this.comboBoxType.getSelectionModel().select(robot.getType());
            System.out.println(robot.getDaysOfWeek());
            checkBoxMonday.setSelected(robot.getDaysOfWeek().contains(Calendar.MONDAY));
            checkBoxTuesday.setSelected(robot.getDaysOfWeek().contains(Calendar.TUESDAY));
            checkBoxWednesday.setSelected(robot.getDaysOfWeek().contains(Calendar.WEDNESDAY));
            checkBoxThursday.setSelected(robot.getDaysOfWeek().contains(Calendar.THURSDAY));
            checkBoxFriday.setSelected(robot.getDaysOfWeek().contains(Calendar.FRIDAY));
            checkBoxSaturday.setSelected(robot.getDaysOfWeek().contains(Calendar.SATURDAY));
            checkBoxSunday.setSelected(robot.getDaysOfWeek().contains(Calendar.SUNDAY));
            this.days = robot.getDaysOfMonth();
            this.updateDays();
        } else {
            this.textFieldCode.clear();
            this.textFieldAbbreviation.clear();
            this.textFieldName.clear();
            this.textFieldTaskPath.clear();
            this.textFieldHour.clear();
            this.comboBoxType.getSelectionModel().select(0);
            checkBoxMonday.setSelected(true);
            checkBoxTuesday.setSelected(true);
            checkBoxWednesday.setSelected(true);
            checkBoxThursday.setSelected(true);
            checkBoxFriday.setSelected(true);
            checkBoxSaturday.setSelected(true);
            checkBoxSunday.setSelected(true);
            this.days.clear();
        }
    }

    private void viewMode() {
        if (tableViewSchedules.getSelectionModel().getSelectedItem() != null) {
            this.textFieldCode.setDisable(true);
            this.textFieldAbbreviation.setDisable(true);
            this.textFieldName.setDisable(true);
            this.textFieldTaskPath.setDisable(true);
            this.textFieldHour.setDisable(true);
            this.comboBoxType.setDisable(true);
            this.checkBoxWeekdays.setDisable(true);
            for (Node node : this.anchorPaneWeekdays.getChildren()) {
                node.setDisable(true);
            }
            this.anchorPaneDay.getChildren();
            this.buttonAddDay.setDisable(true);
            this.buttonClear.setDisable(true);

            this.buttonDelete.setVisible(true);
            this.buttonEdit.setVisible(true);
            this.buttomSchedule.setVisible(false);
            this.buttomSave.setVisible(false);

            this.tabScheduler.setText(tableViewSchedules.getSelectionModel().getSelectedItem().getNameString());
        } else {
            addMode();
        }
    }

    private void addMode() {
        this.tableViewSchedules.getSelectionModel().select(null);
        this.textFieldCode.setDisable(false);
        this.textFieldAbbreviation.setDisable(false);
        this.textFieldName.setDisable(false);
        this.textFieldTaskPath.setDisable(false);
        this.textFieldHour.setDisable(false);
        this.comboBoxType.setDisable(false);
        this.checkBoxWeekdays.setDisable(false);
        for (Node node : this.anchorPaneWeekdays.getChildren()) {
            node.setDisable(false);
        }
        this.buttonAddDay.setDisable(false);
        this.buttonClear.setDisable(false);
        this.buttonDelete.setVisible(false);
        this.buttonEdit.setVisible(false);
        this.buttomSave.setVisible(false);
        this.buttomSchedule.setVisible(true);

        this.tabScheduler.setText("New Schedule");
    }


    private void editMode() {


        this.textFieldCode.setDisable(false);
        this.textFieldAbbreviation.setDisable(false);
        this.textFieldName.setDisable(false);
        this.textFieldTaskPath.setDisable(false);
        this.textFieldHour.setDisable(false);
        this.comboBoxType.setDisable(false);
        this.checkBoxWeekdays.setDisable(false);
        for (Node node : this.anchorPaneWeekdays.getChildren()) {
            node.setDisable(false);
        }
        this.buttonAddDay.setDisable(false);
        this.buttonClear.setDisable(false);

        this.buttonDelete.setVisible(true);
        this.buttonEdit.setVisible(false);

        this.buttomSave.setVisible(true);

        this.tabScheduler.setText("Editing | " + tableViewSchedules.getSelectionModel().getSelectedItem().getNameString());
    }


    private void populateTable() {
        tableViewSchedules.setItems(FXCollections.observableArrayList(DataBase.getInstance().getAllBots()));
    }

    @FXML
    public void add() {
        this.addMode();
        this.fillSelected();
        tabPaneScheduler.getSelectionModel().select(0);
    }

    @FXML
    public void edit() {

        this.editMode();
        System.out.println("EDIT");
    }

    @FXML
    public void saveEdit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setContentText("Are you sure you want modify schduele: " + tableViewSchedules.getSelectionModel().getSelectedItem().getAbbreviation() + " at " + tableViewSchedules.getSelectionModel().getSelectedItem().getHourString());
        if (ButtonType.OK == alert.showAndWait().get()) {
            String pathFile = textFieldTaskPath.getText();
            String name = textFieldName.getText();
            String abbreviation = textFieldAbbreviation.getText();
            String code = textFieldCode.getText();
            String[] hourAux;
            int hour = 0;
            int minute = 0;
            if (textFieldHour.getText().length() > 0) {
                hourAux = textFieldHour.getText().split(":");
                hour = Integer.valueOf(hourAux[0]);
                minute = Integer.valueOf(hourAux[1]);
            }
            Robot.Type type = this.comboBoxType.getSelectionModel().getSelectedItem();
            new Robot(this.tableViewSchedules.getSelectionModel().getSelectedItem().getKey(), code, abbreviation, name, true, pathFile, hour, minute, getSelectedWeekDays(), days, type).update();
        }
        this.populateTable();

    }


    private ArrayList<Integer> getSelectedWeekDays() {
        ArrayList<Integer> daysOfWeek = new ArrayList<>();
        if (this.checkBoxMonday.isSelected()) {
            daysOfWeek.add(Calendar.MONDAY);
        }
        if (this.checkBoxTuesday.isSelected()) {
            daysOfWeek.add(Calendar.TUESDAY);
        }
        if (this.checkBoxWednesday.isSelected()) {
            daysOfWeek.add(Calendar.WEDNESDAY);
        }
        if (this.checkBoxThursday.isSelected()) {
            daysOfWeek.add(Calendar.THURSDAY);

        }
        if (this.checkBoxFriday.isSelected()) {
            daysOfWeek.add(Calendar.FRIDAY);
        }

        if (this.checkBoxSaturday.isSelected()) {
            daysOfWeek.add(Calendar.SATURDAY);
        }
        if (this.checkBoxSunday.isSelected()) {
            daysOfWeek.add(Calendar.SUNDAY);
        }
        return daysOfWeek;
    }


    private void updateDays() {

        StringBuilder aux = new StringBuilder("[");
        for (Integer c : days) {
            aux.append(c).append(",");
        }
        aux.replace(aux.length() - 1, aux.length(), "]");

        this.labelDaysAdded.setText(aux.toString());

        if (this.days.size() < 1) {
            this.labelDaysAdded.setText("[]");
        }
    }


    @FXML
    public void onAddDay(ActionEvent e) {
        int day = new Integer(textFieldDay.getText().trim());
        textFieldDay.setText("");
        this.days.add(day);
        this.refresh();
    }


    @FXML
    public void refresh() {
        this.populateTable();
        this.updateDays();

        labelWathcerStatus.setText(Watcher.getStatus());
        labelURLDB.setText(DataBase.getInstance().getDatabse());
    }

    @FXML
    public void fileChooser() {
        FileChooser fileChooser = new FileChooser();
        File root = new File("\\\\spobrvdivfs01\\FolderRedirection$\\sist_rpaprdrobot_01\\Desktop\\0. PROD\\Automation Anywhere Files\\Automation Anywhere\\My Tasks\\SERF\\");
        if (root.exists()) {
            fileChooser.setInitialDirectory(root);
        }
        File file = fileChooser.showOpenDialog(null);
        if (file != null && file.exists()) {
            textFieldTaskPath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void showConfig() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../Configurations.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Configurations");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void delete() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setContentText("Are you sure you want delete schduele: " + tableViewSchedules.getSelectionModel().getSelectedItem().getAbbreviation() + " at " + tableViewSchedules.getSelectionModel().getSelectedItem().getHourString());
        if (ButtonType.OK == alert.showAndWait().get()) {
            tableViewSchedules.getSelectionModel().getSelectedItem().remove();
            tableViewSchedules.getSelectionModel().getSelectedItem().update();
            this.populateTable();
        }
    }

    private void checkAllDAys() {
        this.checkBoxMonday.setSelected(true);
        this.checkBoxTuesday.setSelected(true);
        this.checkBoxWednesday.setSelected(true);
        this.checkBoxThursday.setSelected(true);
        this.checkBoxFriday.setSelected(true);
        if (!this.checkBoxWeekdays.isSelected()) {
            this.checkBoxSaturday.setSelected(true);
            this.checkBoxSunday.setSelected(true);
        }
    }

    @FXML
    public void onChageType(ActionEvent e) {
        this.anchorPaneDay.setVisible(false);
        this.clearDays();
        switch (this.comboBoxType.getSelectionModel().getSelectedItem()) {
            case DAILY:
                checkAllDAys();
                break;
            case MONTHLY:
                System.out.println("SOME CARALHO");
                this.anchorPaneDay.setVisible(true);
                break;

        }
    }

    @FXML
    public void schedule() {
        if (textFieldHour.getText().length() != 5 || textFieldHour.getText().length() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fill Error");
            alert.setContentText("Please verify the followin fields: Hour, File");
            alert.show();
        } else {
            String pathFile = textFieldTaskPath.getText();
            String name = textFieldName.getText();
            String abbreviation = textFieldAbbreviation.getText();
            String code = textFieldCode.getText();
            String[] hourAux;
            int hour = 0;
            int minute = 0;
            if (textFieldHour.getText().length() > 0) {
                hourAux = textFieldHour.getText().split(":");
                hour = Integer.valueOf(hourAux[0]);
                minute = Integer.valueOf(hourAux[1]);
            }
            Robot.Type type = this.comboBoxType.getSelectionModel().getSelectedItem();
            Models.Scheduler.getInstance().addSchedule(code, abbreviation, name, pathFile, hour, minute, this.getSelectedWeekDays(), days, type);
            this.addMode();
            refresh();
        }
    }

    @FXML
    public void runWatcher() {
        Watcher watcher = Watcher.getInstance();
        watcher.start();
        refresh();
    }

    @FXML
    public void pauseWatcher() {
        Watcher watcher = Watcher.getInstance();
        watcher.pause();
        refresh();
    }

    @FXML
    private void clearDays() {
        this.days.clear();
        this.updateDays();
    }


    @FXML
    public void stopWatcher() {
        Watcher watcher = Watcher.getInstance();
        watcher.stop();
        refresh();
    }

    @Override
    public void onUpdate() {
        this.populateTable();
    }


    @FXML
    public void onSetOnlhyWeekDays() {
        if (this.checkBoxWeekdays.isSelected()) {
            this.checkBoxSaturday.setSelected(false);
            this.checkBoxSunday.setSelected(false);
            this.checkBoxSaturday.setDisable(true);
            this.checkBoxSunday.setDisable(true);
        } else {

            this.checkBoxSaturday.setDisable(false);
            this.checkBoxSunday.setDisable(false);
        }
    }


}
