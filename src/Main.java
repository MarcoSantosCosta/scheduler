import Models.Scheduler;
import Models.Watcher;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import persistence.DataBase;
import server.Server;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Watcher.getInstance();
        DataBase.getInstance();
        Scheduler.getInstance();
        Server s = new Server(12345);
        //s.start();

        Parent root = FXMLLoader.load(getClass().getResource("/GUI/Scheduler.fxml"));
        primaryStage.setTitle("Scheduler 1.0");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);

        primaryStage.setOnCloseRequest(e -> {
                    Watcher.getInstance().clearListners();
                    Watcher.getInstance().stop();
                }
        );
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
