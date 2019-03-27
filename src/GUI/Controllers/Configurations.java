package GUI.Controllers;

import Models.Watcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import persistence.DataBase;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Configurations implements Initializable {

    @FXML
    private TextField textFieldURLDB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void selectFile() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File root = new File("\\\\spobrvdivfs01\\FolderRedirection$\\sist_rpaprdrobot_01\\Desktop\\0. PROD\\Automation Anywhere Files\\Automation Anywhere\\My Tasks\\SERF\\");
        File file = directoryChooser.showDialog(null);
        if (file.exists()) {
            textFieldURLDB.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void save() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("The watcher will be stopped! You have sure, want do this?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            if (this.textFieldURLDB.getText().length() > 0) {
                DataBase.getInstance().setDatabse(this.textFieldURLDB.getText());
                Watcher.getInstance().stop();
            }
        }
    }
}
