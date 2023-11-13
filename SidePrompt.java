import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SidePrompt {

    private static int side;

    public static int display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Side Choice");

        VBox vbox = new VBox();

        Text message = new Text("Which side do you want to play on?");
        message.setTextAlignment(TextAlignment.CENTER);
        message.setFont(new Font(15));

        FlowPane choices = new FlowPane();
        choices.setHgap(20);
        choices.setPadding(new Insets(10, 0, 0, 15));

        Button leftButton = new Button("Left");
        leftButton.setMinSize(100, 50);
        leftButton.setOnAction(e -> {
            side = 0;
            window.close();
        });
        Button rightButton = new Button("Right");
        rightButton.setMinSize(100, 50);
        rightButton.setOnAction(e -> {
            side = 1;
            window.close();
        });

        choices.getChildren().addAll(leftButton, rightButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(message, choices);
        window.setScene(new Scene(vbox, 250, 100));
        window.showAndWait();

        return side;
    }
}
