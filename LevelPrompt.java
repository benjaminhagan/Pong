import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LevelPrompt {

    private static int difficulty;

    public static int display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Difficulty");

        VBox vbox = new VBox();

        Text message = new Text("Choose your difficulty");
        message.setTextAlignment(TextAlignment.CENTER);
        message.setFont(new Font(15));

        VBox choices = new VBox();
        choices.setSpacing(10);
        choices.setPadding(new Insets(10, 0, 0, 0));
        choices.setAlignment(Pos.CENTER);

        Button easyButton = new Button("Easy");
        easyButton.setMinSize(125, 50);
        easyButton.setOnAction(e -> {
            difficulty = 2;
            window.close();
        });
        Button mediumButton = new Button("Medium");
        mediumButton.setMinSize(125, 50);
        mediumButton.setOnAction(e -> {
            difficulty = 4;
            window.close();
        });
        Button hardButton = new Button("Hard");
        hardButton.setMinSize(125, 50);
        hardButton.setOnAction(e -> {
            difficulty = 6;
            window.close();
        });

        choices.getChildren().addAll(easyButton,mediumButton, hardButton);
        vbox.setPadding(new Insets(10,0,0,0));
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.getChildren().addAll(message, choices);
        window.setScene(new Scene(vbox, 200, 225));
        window.showAndWait();

        return difficulty;
    }
}