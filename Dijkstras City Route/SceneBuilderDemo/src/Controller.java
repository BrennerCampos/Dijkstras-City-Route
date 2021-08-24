import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private double x1;
    private double y1;
    private double x2;
    public double y2;

    public HBox hbox;
    @FXML
    private AnchorPane anchor;

    @FXML
    private Pane bgPane;

    @FXML
    private ImageView background;

    @FXML
    private ListView<String> cityview;

    @FXML
    private MenuBar menuBar;

    @FXML
    public Menu menuFile;

    @FXML
    private MenuItem menuFileClose;

    @FXML
    private Button getPathButton;

    @FXML
    private TextArea outputArea;

    @FXML
    private Text labelBoston;
    @FXML
    private Circle pointBoston;

    @FXML
    private Text labelBrockton;
    @FXML
    private Circle pointBrockton;


    @FXML
    private Text labelCambridge;
    @FXML
    private Circle pointCambridge;

    @FXML
    private Text labelLowell;
    @FXML
    private Circle pointLowell;

    @FXML
    private Text labelLynn;
    @FXML
    private Circle pointLynn;

    @FXML
    private Text labelNewBedford;
    @FXML
    private Circle pointNewBedford;

    @FXML
    private Text labelSpringfield;
    @FXML
    private Circle pointSpringfield;

    @FXML
    private Text labelWorcester;
    @FXML
    private Circle pointWorcester;


    @FXML
    private Line line0;
    @FXML
    private  Line line1;
    @FXML
    private  Line line2;
    @FXML
    private  Line line3;
    @FXML
    private  Line line4;
    @FXML
    private  Line line5;
    @FXML
    private  Line line6;
    @FXML
    private  Line line7;

    @FXML
    void getShortestPath(ActionEvent e) {

        String[] sortedCities = new String[Dijkstra.CITIES.length];
        Dijkstra dijkstraObj;

        String cityStr = "City", compDistStr = "Total Distance", nextDistStr = "To Next Destination";
        String text = String.format(    "|-----------------------------------------------------|\n" +
                        "| %1$-13s %2$-16s %3$-20s |\n" +
                        "|-----------------------------------------------------|\n",
                cityStr, compDistStr, nextDistStr);
        String text2 = null;
        Timeline time = new Timeline();

        clearCities();

        if (cityview.getSelectionModel().getSelectedIndex() != -1){
            dijkstraObj = new Dijkstra(Dijkstra.getCity(cityview.getSelectionModel().getSelectedIndex()));
            dijkstraObj.printResult();

            int counter = 0;
            for (int i = 0; i < sortedCities.length; i++){
                int shortestIndex = Dijkstra.getCityIndex(Dijkstra.getCity(Dijkstra.distIndex[i]));


                if (i < dijkstraObj.distCompound.length-1) {
                    String tempString = dijkstraObj.getDistance(dijkstraObj.distances, Dijkstra.distIndex[i],
                            Dijkstra.distIndex[i + 1]) + " (to " + Dijkstra.getCity(Dijkstra.distIndex[i + 1]) + ")";
                    text = text + String.format("| %1$-13s %2$-16s %3$-20s |\n", Dijkstra.getCity(Dijkstra.distIndex[i]), dijkstraObj.distCompound[i], tempString);
                    text2 = text + "|-----------------------------------------------------|\n";

                    time = lineAnimation(shortestIndex, i, time, counter);

                } else {
                    text = text + String.format("| %1$-13s %2$-16s %3$-20s |\n", Dijkstra.getCity(Dijkstra.distIndex[i]), dijkstraObj.distCompound[i], "Final Destination");
                    text2 = text + "|-----------------------------------------------------|\n";

                    time = lineAnimation(shortestIndex, i, time, counter);

                }

                // build animation effect
                String finalText = text2;
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> outputArea.setText(finalText)));

                counter += 2;

                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line1.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line2.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line3.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line4.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line5.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line6.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line7.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line0.setStroke(Color.NAVY)));

            }
        } // end of outer if statement

        time.setAutoReverse(false);
        time.setCycleCount(1);
        time.play();

    } // end of getShortestPath

    @FXML
    void menuSelected(ActionEvent event){
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String cityStr = "City", compDistStr = "total Distance", nextDistStr = "Distance to next Destination";
        String text = String.format(    "|-------------------------------------------------------------|\n" +
                        "| %1$-13s %2$-16s %3$-28s |\n" +
                        "|-------------------------------------------------------------|\n",
                cityStr, compDistStr, nextDistStr);
        outputArea.setText(text);


        for (int i = 0; i < Dijkstra.CITIES.length; i++) {
            cityview.getItems().add(Dijkstra.CITIES[i]);
        }
    }


    public Timeline lineAnimation(int shortestIndex, int i, Timeline time, int counter){


        // Reseting Line color to be Navy after each timeline iteration
        time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line1.setStroke(Color.NAVY)));
        time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line2.setStroke(Color.NAVY)));
        time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line3.setStroke(Color.NAVY)));
        time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line4.setStroke(Color.NAVY)));
        time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line5.setStroke(Color.NAVY)));
        time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line6.setStroke(Color.NAVY)));
        time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line7.setStroke(Color.NAVY)));
        time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line0.setStroke(Color.NAVY)));



        if (i>0) {
            x2 = x1;
            y2 = y1;
        }

        // Checking current city and getting its x and y location
        if (shortestIndex == 0) {
            x1 = pointBoston.getLayoutX();
            y1 = pointBoston.getLayoutY();
        } else if (shortestIndex == 1) {
            x1 = pointWorcester.getLayoutX();
            y1 = pointWorcester.getLayoutY();
        } else if (shortestIndex == 2) {
            x1 = pointSpringfield.getLayoutX();
            y1 = pointSpringfield.getLayoutY();
        } else if (shortestIndex == 3) {
            x1 = pointCambridge.getLayoutX();
            y1 = pointCambridge.getLayoutY();
        } else if (shortestIndex == 4) {
            x1 = pointLowell.getLayoutX();
            y1 = pointLowell.getLayoutY();
        } else if (shortestIndex == 5) {
            x1 = pointBrockton.getLayoutX();
            y1 = pointBrockton.getLayoutY();
        } else if (shortestIndex == 6) {
            x1 = pointLynn.getLayoutX();
            y1 = pointLynn.getLayoutY();
        } else if (shortestIndex == 7) {
            x1 = pointNewBedford.getLayoutX();
            y1 = pointNewBedford.getLayoutY();
        }

        // Now making the lines
        if (i>0) {
            if (i == 1) {
                line1.setStartX(x1);
                line1.setStartY(y1);
                line1.setEndX(x2);
                line1.setEndY(y2);
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line1.setOpacity(1)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line1.setStroke(Color.RED)));

            } else if (i == 2) {
                line2.setStartX(x1);
                line2.setStartY(y1);
                line2.setEndX(x2);
                line2.setEndY(y2);
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line2.setOpacity(1)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line2.setStroke(Color.RED)));
            } else if (i == 3) {
                line3.setStartX(x1);
                line3.setStartY(y1);
                line3.setEndX(x2);
                line3.setEndY(y2);
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line3.setOpacity(1)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line3.setStroke(Color.RED)));
            } else if (i == 4) {
                line4.setStartX(x1);
                line4.setStartY(y1);
                line4.setEndX(x2);
                line4.setEndY(y2);
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line4.setOpacity(1)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line4.setStroke(Color.RED)));

            } else if (i == 5) {
                line5.setStartX(x1);
                line5.setStartY(y1);
                line5.setEndX(x2);
                line5.setEndY(y2);
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line5.setOpacity(1)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line5.setStroke(Color.RED)));

            } else if (i == 6) {
                line6.setStartX(x1);
                line6.setStartY(y1);
                line6.setEndX(x2);
                line6.setEndY(y2);
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line6.setOpacity(1)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line6.setStroke(Color.RED)));

            } else {
                line7.setStartX(x1);
                line7.setStartY(y1);
                line7.setEndX(x2);
                line7.setEndY(y2);
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line7.setOpacity(1)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> line7.setStroke(Color.RED)));
            }
        }

        if (shortestIndex == 0) {

            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointBoston.setOpacity(1)));
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelBoston.setOpacity(1)));
            if (counter == 0) {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointBoston.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelBoston.setFill(Color.BLUE)));
            } else {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointBoston.setStroke(Color.BLACK)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelBoston.setFill(Color.BLACK)));
            }

        } else if (shortestIndex == 1) {
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointWorcester.setOpacity(1)));
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelWorcester.setOpacity(1)));
            if (counter == 0) {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointWorcester.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelWorcester.setFill(Color.BLUE)));
            } else {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointWorcester.setStroke(Color.BLACK)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelWorcester.setFill(Color.BLACK)));
            }

        } else if (shortestIndex == 2) {
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointSpringfield.setOpacity(1)));
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelSpringfield.setOpacity(1)));
            if (counter == 0) {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointSpringfield.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelSpringfield.setFill(Color.BLUE)));
            } else {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointSpringfield.setStroke(Color.BLACK)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelSpringfield.setFill(Color.BLACK)));
            }

        } else if (shortestIndex == 3) {
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointCambridge.setOpacity(1)));
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelCambridge.setOpacity(1)));
            if (counter == 0) {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointCambridge.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelCambridge.setFill(Color.BLUE)));
            } else {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointCambridge.setStroke(Color.BLACK)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelCambridge.setFill(Color.BLACK)));
            }

        } else if (shortestIndex == 4) {
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointLowell.setOpacity(1)));
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelLowell.setOpacity(1)));
            if (counter == 0) {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointLowell.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelLowell.setFill(Color.BLUE)));
            } else {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointLowell.setStroke(Color.BLACK)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelLowell.setFill(Color.BLACK)));
            }

        } else if (shortestIndex == 5) {
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointBrockton.setOpacity(1)));
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelBrockton.setOpacity(1)));
            if (counter == 0) {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointBrockton.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelBrockton.setFill(Color.BLUE)));
            } else {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointBrockton.setStroke(Color.BLACK)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelBrockton.setFill(Color.BLACK)));
            }

        } else if (shortestIndex == 6) {
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointLynn.setOpacity(1)));
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelLynn.setOpacity(1)));
            if (counter == 0) {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointLynn.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelLynn.setFill(Color.BLUE)));
            } else {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointLynn.setStroke(Color.BLACK)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelLynn.setFill(Color.BLACK)));
            }

        } else if (shortestIndex == 7) {
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointNewBedford.setOpacity(1)));
            time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelNewBedford.setOpacity(1)));
            if (counter == 0) {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointNewBedford.setStroke(Color.NAVY)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelNewBedford.setFill(Color.BLUE)));
            } else {
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> pointNewBedford.setStroke(Color.BLACK)));
                time.getKeyFrames().add(new KeyFrame(Duration.seconds(counter), event -> labelNewBedford.setFill(Color.BLACK)));
            }

        }
        return time;
    }


    public void clearCities(){
        // Making components initially invisible
        line0.setOpacity(0);
        line1.setOpacity(0);
        line2.setOpacity(0);
        line3.setOpacity(0);
        line4.setOpacity(0);
        line5.setOpacity(0);
        line6.setOpacity(0);
        line7.setOpacity(0);

        labelBoston.setOpacity(0);
        labelBrockton.setOpacity(0);
        labelCambridge.setOpacity(0);
        labelLowell.setOpacity(0);
        labelLynn.setOpacity(0);
        labelNewBedford.setOpacity(0);
        labelSpringfield.setOpacity(0);
        labelWorcester.setOpacity(0);

        pointBoston.setOpacity(0);
        pointBrockton.setOpacity(0);
        pointCambridge.setOpacity(0);
        pointLowell.setOpacity(0);
        pointLynn.setOpacity(0);
        pointNewBedford.setOpacity(0);
        pointSpringfield.setOpacity(0);
        pointWorcester.setOpacity(0);

    }

} // end of controller

