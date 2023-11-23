import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DictionaryApp extends Application {
    private DictionaryCommandline commandLine;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        commandLine = new DictionaryCommandline();

        primaryStage.setTitle("English-Vietnamese Dictionary");
        VBox layout = new VBox(10);

        // Components
        TextField searchField = new TextField();
        TextArea resultArea = new TextArea();
        resultArea.setEditable(false);
        Button searchButton = new Button("Search");

        // Event handlers
        searchButton.setOnAction(e -> {
            String searchTerm = searchField.getText();
            String result = commandLine.dictionaryLookup(searchTerm);
            resultArea.setText(result);
        });

        // Layout setup
        layout.getChildren().addAll(searchField, searchButton, resultArea);
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}