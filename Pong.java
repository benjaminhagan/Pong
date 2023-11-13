import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Pong extends Application {

    private final int HEIGHT = 700;
    private final int WIDTH = 1400;
    private Circle ball;
    private Rectangle userRectangle;
    private Rectangle AIRectangle;
    private Text userScoreText;
    private Text AIScoreText;
    private Text endText;
    private int side;
    private int difficulty;
    private int userScore;
    private int AIScore;
    private int xVel;
    private int yVel;
    private int offset;
    private boolean start;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {

        Random generator = new Random();
        side = SidePrompt.display();
        difficulty = LevelPrompt.display();
        userScore = 0;
        AIScore = 0;
        xVel = 0;
        yVel = 0;
        offset = generator.nextInt(91) - 45;
        start = false;

        primaryStage.setTitle("Pong");
        StackPane stackPane = new StackPane();
        Rectangle background = new Rectangle(WIDTH, HEIGHT, Color.BLACK);
        Pane pane = new Pane();

        Line line = getMiddleLine();
        setUserScoreText();
        setAIScoreText();
        setUserRectangle();
        setAIRectangle();
        setEndText();
        ball = new Circle(WIDTH/2, HEIGHT/2, 10, Color.WHITE);
        pane.setOnMouseClicked(e -> {
            if (!start) {
                start = true;
                if (generator.nextInt(2) == 0) {
                    xVel = 5;
                } else {
                    xVel = -5;
                }
            }
        });

        pane.getChildren().addAll(line, userScoreText, AIScoreText, userRectangle, AIRectangle, ball, endText);
        pane.setOnMouseMoved(e -> userRectangle.setY(e.getY() - 50));
        stackPane.getChildren().addAll(background, pane);
        primaryStage.setScene(new Scene(stackPane, WIDTH, HEIGHT));
        primaryStage.show();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (start) {
                    moveBall();
                    moveAI();
                }
                if (userScore == 3) {
                    endText.setFill(Color.GREEN);
                    endText.setText("You Win!");
                    endText.setVisible(true);
                    timer.cancel();
                }
                if (AIScore == 3) {
                    endText.setFill(Color.RED);
                    endText.setText("You Lose!");
                    endText.setVisible(true);
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask, 0, 10);
    }

    public void setEndText() {
        endText = new Text("A");
        endText.setX(WIDTH/2 - 130);
        endText.setY(HEIGHT/2 - 200);
        endText.setTextAlignment(TextAlignment.CENTER);
        endText.setFont(new Font(70));
        endText.setFill(Color.GREEN);
        endText.setVisible(false);
    }

    public void moveAI() {
        if (ball.getCenterY() > AIRectangle.getY() + 50 + offset) {
            AIRectangle.setY(AIRectangle.getY() + difficulty);
        }
        if (ball.getCenterY() < AIRectangle.getY() + 50 + offset) {
            AIRectangle.setY(AIRectangle.getY() - difficulty);
        }
    }

    public void moveBall() {
        if (ball.getCenterX() <= 120) {
            if (side == 0 && (userRectangle.getY() > ball.getCenterY() || userRectangle.getY() < ball.getCenterY() - 100)) {
                increaseScore(0);
                reset();
            } else if (side == 1 && (AIRectangle.getY() > ball.getCenterY() || AIRectangle.getY() < ball.getCenterY() - 100)) {
                increaseScore(1);
                reset();
            } else if (side == 0) {
                xVel *= -1;
                yVel = (int) (ball.getCenterY() - userRectangle.getY() - 50) / 5;
            } else if (side == 1) {
                xVel *= -1;
                yVel = (int) (ball.getCenterY() - AIRectangle.getY() - 50) / 5;
                Random generator = new Random();
                offset = generator.nextInt(91) - 45;
            }
        }
        if (ball.getCenterX() >= 1280) {
            if (side == 0 && (AIRectangle.getY() > ball.getCenterY() || AIRectangle.getY() < ball.getCenterY() - 100)) {
                increaseScore(1);
                reset();
            } else if (side == 1 && (userRectangle.getY() > ball.getCenterY() || userRectangle.getY() < ball.getCenterY() - 100)) {
                increaseScore(0);
                reset();
            } else if (side == 0) {
                xVel *= -1;
                yVel = (int) (ball.getCenterY() - AIRectangle.getY() - 50) / 5;
                Random generator = new Random();
                int offset = generator.nextInt(91) - 45;
            } else if (side == 1) {
                xVel *= -1;
                yVel = (int) (ball.getCenterY() - userRectangle.getY() - 50) / 5;
            }
        }
        if (ball.getCenterY() <= 0 || ball.getCenterY() >= HEIGHT)
            yVel *= -1;

        ball.setCenterX(ball.getCenterX() + xVel);
        ball.setCenterY(ball.getCenterY() + yVel);

    }

    public void reset() {
        start = false;
        ball.setCenterX(WIDTH/2);
        ball.setCenterY(HEIGHT/2);
        xVel = 0;
        yVel = 0;
    }

    public void setUserRectangle() {
        userRectangle = new Rectangle(20, 100, Color.WHITE);
        if (side == 0) {
            userRectangle.setX(100);
            userRectangle.setY(HEIGHT/2 - 50);
        }
        if (side == 1) {
            userRectangle.setX(1280);
            userRectangle.setY(HEIGHT/2 -50);
        }
    }

    public void setAIRectangle() {
        AIRectangle = new Rectangle(20, 100, Color.WHITE);
        if (side == 0) {
            AIRectangle.setX(1280);
            AIRectangle.setY(HEIGHT/2 - 50);
        }
        if (side == 1) {
            AIRectangle.setX(100);
            AIRectangle.setY(HEIGHT/2 - 50);
        }

    }

    public void setUserScoreText() {
        userScoreText = new Text("0");
        userScoreText.setTextAlignment(TextAlignment.CENTER);
        userScoreText.setFill(Color.WHITE);
        userScoreText.setFont(new Font(30));
        if (side == 0) {
            userScoreText.setX(250);
            userScoreText.setY(100);
        }
        if (side == 1) {
            userScoreText.setX(1150);
            userScoreText.setY(100);
        }
    }

    public void setAIScoreText() {
        AIScoreText = new Text("0");
        AIScoreText.setTextAlignment(TextAlignment.CENTER);
        AIScoreText.setFill(Color.WHITE);
        AIScoreText.setFont(new Font(30));
        if (side == 0) {
            AIScoreText.setX(1150);
            AIScoreText.setY(100);
        }
        if (side == 1) {
            AIScoreText.setX(250);
            AIScoreText.setY(100);
        }
    }

    public Line getMiddleLine() {
        Line line = new Line(WIDTH/2, 0, WIDTH/2, HEIGHT);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);
        line.getStrokeDashArray().addAll(25.0, 20.0);
        return line;
    }

    public void increaseScore(int player) {
        if (player == 0) {
            AIScore++;
            AIScoreText.setText(Integer.toString(AIScore));
        }
        if (player == 1) {
            userScore++;
            userScoreText.setText(Integer.toString(userScore));
        }
    }

}
