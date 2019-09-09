package projDRIVERSLICENSE.GUI;

import projDRIVERSLICENSE.Database.*;
import projDRIVERSLICENSE.LicensePics.LicenseImage;
import javafx.animation.*;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller implements Initializable {

    @FXML private AnchorPane home, main, searchResults, dark;
    @FXML private Button cancel, reset;
    @FXML private TableColumn<License,String> nameCol, lastCol, addressCol, cityCol, stateCol, genderCol, hairCol, eyeCol, heightCol, idCol, zipCol, weightCol, donorCol, issuedCol, expiresCol, birthCol;
    @FXML private TableView<License> table;
    @FXML private ComboBox<String> genderFilter,hairFilter, eyeFilter, donorFilter, searchType;
    @FXML private TextField searchBar;
    @FXML private ScrollPane scrollResults;
    @FXML private ImageView start, licensePreview, car;
    @FXML private Label nameInfo, addressInfo, genderInfo, hairInfo, eyeInfo,heightInfo, weightInfo,issueInfo,expireInfo,dobInfo,idInfo,donorInfo;

    private SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy");
    private LicenseDatabase database = new LicenseDatabase(License.class.getResource("data.csv").getPath());
    private ObservableList<License> list;

    @Override public void initialize(URL location, ResourceBundle resources) {
        FadeTransition fade = new FadeTransition(Duration.seconds(1.4),start);
        fade.setFromValue(1);
        fade.setToValue(0.5);
        fade.setAutoReverse(true);
        fade.setCycleCount(-1);
        fade.play();
        start.setOnMouseClicked(event -> {
            fade.stop();
            translate(home,-905,1);
            translate(main,-905,1);
            startCar(car);
        });
        translate(main,905,0.1);
        searchResults.setVisible(false);
        dark.setVisible(false);


        list = FXCollections.observableArrayList(licenses());
        table.setItems(list);
        nameCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        lastCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        addressCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getAddress()));
        cityCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getCity()));
        stateCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getState()));
        genderCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getGender()));
        hairCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHairCol()));
        eyeCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEyeCol()));
        heightCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getHeight()));
        idCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getId()));
        zipCol.setCellValueFactory(param -> new SimpleStringProperty(""+param.getValue().getZipCode()));
        weightCol.setCellValueFactory(param -> new SimpleStringProperty(""+param.getValue().getWeight()));
        donorCol.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().isDonor()?"Y":"N"));
        issuedCol.setCellValueFactory(param -> new SimpleStringProperty(fmt.format(param.getValue().getIssuedOn())));
        expiresCol.setCellValueFactory(param -> new SimpleStringProperty(fmt.format(param.getValue().getExpiresOn())));
        birthCol.setCellValueFactory(param -> new SimpleStringProperty(fmt.format(param.getValue().getBirthDate())));
        nameCol.setComparator(String::compareTo);
        lastCol.setComparator(String::compareTo);
        addressCol.setComparator((o1, o2) -> {
            String[] add1 = o1.split(" ",2);
            String[] add2 = o2.split(" ",2);
            if (!add1[1].equals(add2[1]))
                return add1[1].compareTo(add2[1]);
            else return Integer.compare(Integer.parseInt(add1[0]),Integer.parseInt(add2[0]));
        });
        cityCol.setComparator(String::compareTo);
        stateCol.setComparator(String::compareTo);
        genderCol.setComparator(String::compareTo);
        hairCol.setComparator(String::compareTo);
        eyeCol.setComparator(String::compareTo);
        heightCol.setComparator((o1, o2) -> {
            String[] height1 = o1.split("\\D+");
            String[] height2 = o2.split("\\D+");
            if (height1[0].equals(height2[0])) {
                return Integer.compare(Integer.parseInt(height1[1]),Integer.parseInt(height2[1]));
            } else return Integer.compare(Integer.parseInt(height1[0]),Integer.parseInt(height2[0]));
        });
        weightCol.setComparator(Comparator.comparingInt(Integer::parseInt));
        donorCol.setComparator(Comparator.reverseOrder());
        issuedCol.setComparator((o1, o2) -> {
            try {
                Date d1 = fmt.parse(o1);
                Date d2 = fmt.parse(o2);
                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        expiresCol.setComparator((o1, o2) -> {
            try {
                Date d1 = fmt.parse(o1);
                Date d2 = fmt.parse(o2);
                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        birthCol.setComparator((o1, o2) -> {
            try {
                Date d1 = fmt.parse(o1);
                Date d2 = fmt.parse(o2);
                return d1.compareTo(d2);
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        });
        idCol.setComparator(String::compareTo);
        zipCol.setComparator(Comparator.comparingInt(Integer::parseInt));


        genderFilter.setItems(FXCollections.observableArrayList("ANY","M","F"));
        hairFilter.setItems(FXCollections.observableArrayList("ANY","BAL","BLK","BLN","BLU","BRO","GRY","GRN","ONG","PNK","PLE","RED","SDY","WHI"));
        eyeFilter.setItems(FXCollections.observableArrayList("ANY","BLK","BLU","BRO","GRY","GRN","HAZ","MAR"));
        donorFilter.setItems(FXCollections.observableArrayList("ANY","Y","N"));
        genderFilter.getSelectionModel().select(0);
        hairFilter.getSelectionModel().select(0);
        eyeFilter.getSelectionModel().select(0);
        donorFilter.getSelectionModel().select(0);
        genderFilter.setOnAction(event -> {
            filter(list,getActiveFilters(genderFilter,hairFilter,eyeFilter,donorFilter));
        });
        hairFilter.setOnAction(event -> {
            filter(list,getActiveFilters(genderFilter,hairFilter,eyeFilter,donorFilter));
        });
        eyeFilter.setOnAction(event -> {
            filter(list,getActiveFilters(genderFilter,hairFilter,eyeFilter,donorFilter));
        });
        donorFilter.setOnAction(event -> {
            filter(list,getActiveFilters(genderFilter,hairFilter,eyeFilter,donorFilter));
        });
        reset.setOnAction(event -> {
            genderFilter.getSelectionModel().select(0);
            hairFilter.getSelectionModel().select(0);
            eyeFilter.getSelectionModel().select(0);
            donorFilter.getSelectionModel().select(0);
            filter(list,getActiveFilters(genderFilter,hairFilter,eyeFilter,donorFilter));
        });


        scrollResults.setFitToWidth(true);
        searchType.setItems(FXCollections.observableArrayList("NAME","ADDRESS","CITY","ZIPCODE"));
        searchType.getSelectionModel().select(0);
        searchBar.setOnKeyPressed(event -> {
            if (!searchBar.getText().isEmpty() && event.getCode() == KeyCode.ENTER) {
                List<License> resultList = new LinkedList<>();

                if (searchType.getSelectionModel().getSelectedItem().equals("NAME")) {
                    for (License l: licenses())
                        if (l.getName().toLowerCase().contains(searchBar.getText().toLowerCase()))
                            resultList.add(l);
                }
                if (searchType.getSelectionModel().getSelectedItem().equals("ADDRESS")) {
                    for (License l: licenses())
                        if (l.getAddress().toLowerCase().contains(searchBar.getText().toLowerCase()))
                            resultList.add(l);
                }
                if (searchType.getSelectionModel().getSelectedItem().equals("CITY")) {
                    for (License l: licenses())
                        if (l.getCity().toLowerCase().contains(searchBar.getText().toLowerCase()))
                            resultList.add(l);
                }
                if (searchType.getSelectionModel().getSelectedItem().equals("ZIPCODE")) {
                    for (License l: licenses())
                        if ((""+l.getZipCode()).contains(searchBar.getText()))
                            resultList.add(l);
                }

                if (resultList.isEmpty()) {
                    Label label = new Label("No results.");
                    label.setFont(Font.font("Hiragino Sans W1",30));
                    label.setPrefSize(672,450);
                    label.setAlignment(Pos.CENTER);
                    Pane pane = new Pane(label);
                    scrollResults.setContent(pane);
                } else {
                    VBox vbox = new VBox();
                    vbox.setPrefSize(670,resultList.size()*100);
                    int index = 0;
                    for (License l: resultList) {
                        Pane pane = new Pane();
                        if (index%2==1)
                            pane.setStyle("-fx-background-color: rgb(240,240,240)");
                        pane.setPrefSize(670,100);
                        Label name = new Label(l.getName());
                        name.setPrefSize(400,100);
                        name.setAlignment(Pos.CENTER);
                        name.setFont(Font.font(25));
                        Button view = new Button("VIEW INFO");
                        view.setPrefSize(100,40);
                        view.setLayoutX(485);
                        view.setLayoutY(30);
                        view.setStyle("-fx-background-color: rgb(200,200,200)");
                        view.setOnAction(event1 -> {
                            fillInfo(l);
                            searchResults.setVisible(false);
                            dark.setVisible(false);
                        });
                        pane.getChildren().addAll(name,view);
                        vbox.getChildren().add(pane);
                        index++;
                    }
                    scrollResults.setContent(vbox);
                    table.getSelectionModel().select(-1);
                    table.getSelectionModel().focus(-1);
                }
                dark.setVisible(true);
                searchResults.setVisible(true);
            }
        });
        table.setOnMouseClicked(event -> fillInfo(list.get(table.getSelectionModel().getFocusedIndex())));
        table.setOnKeyPressed(event -> {
            int index = table.getSelectionModel().getFocusedIndex();
            if (event.getCode() == KeyCode.DOWN && index < list.size() - 1)
                fillInfo(list.get(index + 1));
            if (event.getCode() == KeyCode.UP && index > 1)
                fillInfo(list.get(index - 1));
        });
        licensePreview.setOnMouseClicked(event -> {
            if (licensePreview.getImage() != null) {
                Stage stage = new Stage();
                ImageView image = new ImageView(licensePreview.getImage());
                Scene scene = new Scene(new Pane(image));
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            }
        });
        cancel.setOnAction(event -> {
            searchResults.setVisible(false);
            dark.setVisible(false);
        });

    }
    private void filter(ObservableList<License> list, List<ComboBox<String>> activeFilters) {
        list.clear();
        outer: for (License l: licenses()) {
            for (ComboBox box : activeFilters) {
                if (box == genderFilter && !l.getGender().equals(genderFilter.getSelectionModel().getSelectedItem()) && !genderFilter.getSelectionModel().getSelectedItem().equals("ANY"))
                        continue outer;
                if (box == hairFilter && !l.getHairCol().equals(hairFilter.getSelectionModel().getSelectedItem()) && !hairFilter.getSelectionModel().getSelectedItem().equals("ANY"))
                    continue outer;
                if (box == eyeFilter && !l.getEyeCol().equals(eyeFilter.getSelectionModel().getSelectedItem()) && !eyeFilter.getSelectionModel().getSelectedItem().equals("ANY"))
                    continue outer;
                if (box == donorFilter && (l.isDonor() != (donorFilter.getSelectionModel().getSelectedItem().equals("Y"))) && !donorFilter.getSelectionModel().getSelectedItem().equals("ANY"))
                    continue outer;
            }
            list.add(l);
        }
    }
    @SafeVarargs private final List<ComboBox<String>> getActiveFilters(ComboBox<String>... combos) {
        List<ComboBox<String>> list = new LinkedList<>();
        for (ComboBox box: combos)
            if (box.getSelectionModel().getSelectedIndex() > -1)
                list.add(box);
            return list;
    }
    private void startCar(ImageView car) {
        double seconds = 8;

        TranslateTransition left = new TranslateTransition(Duration.seconds(seconds), car);
        left.setByX(-1690);
        left.setInterpolator(Interpolator.EASE_BOTH);

        TranslateTransition right = new TranslateTransition(Duration.seconds(seconds),car);
        right.setByX(1690);
        right.setInterpolator(Interpolator.EASE_BOTH);

        left.setOnFinished(event -> {
            car.setRotate(180);
            right.play();
        });
        right.setOnFinished(event -> {
            car.setRotate(0);
            left.play();
        });

        left.play();
    }
    private void fillInfo(License license) {
        nameInfo.setText(license.getName());
        addressInfo.setText(license.getAddress() + "\n" + license.getCity() + ", " + license.getState() + " " + license.getZipCode());
        genderInfo.setText(license.getGender().equals("M") ? "MALE" : "FEMALE");

        String hairCol = license.getHairCol();
        String hair;
        switch(hairCol) {
            case "BAL": hair = "BALD";
            break;
            case "BLK": hair = "BLACK";
            break;
            case "BLN": hair = "BLOND";
            break;
            case "BLU": hair = "BLUE";
            break;
            case "BRO": hair = "BROWN";
            break;
            case "GRY": hair = "GRAY";
            break;
            case "GRN": hair = "GREEN";
            break;
            case "ONG": hair = "ORANGE";
            break;
            case "PNK": hair = "PINK";
            break;
            case "PLE": hair = "PURPLE";
            break;
            case "RED": hair = "RED";
            break;
            case "SDY": hair = "SANDY";
            break;
            case "WHI": hair = "WHITE";
            break;
            default: hair = "";
        }
        hairInfo.setText(hair);

        String eyeCol = license.getEyeCol();
        String eye;
        switch (eyeCol) {
            case "BLK": eye = "BLACK";
                break;
            case "BLU": eye = "BLUE";
                break;
            case "BRO": eye = "BROWN";
                break;
            case "GRY": eye = "GRAY";
                break;
            case "GRN": eye = "GREEN";
                break;
            case "HAZ": eye = "HAZEL";
                break;
            case "MAR": eye = "MAROON";
                break;
            default: eye = "";
        }
        eyeInfo.setText(eye);

        heightInfo.setText(license.getHeight());
        weightInfo.setText(license.getWeight() + " LBS");
        issueInfo.setText(fmt.format(license.getIssuedOn()));
        expireInfo.setText(fmt.format(license.getExpiresOn()));
        dobInfo.setText(fmt.format(license.getBirthDate()));
        idInfo.setText(license.getId());
        donorInfo.setText(license.isDonor() ? "YES" : "NO");

        if (license.getLicensePath().equals("")) {
            try {
                license.setLicensePath(LicenseImage.createImg(license));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }

        }
        licensePreview.setImage(new Image(new File(license.getLicensePath()).toURI().toString()));

    }
    private List<License> licenses() {
        return database.getLicenses();
    }
    private void translate (Node n, int dist, double duration) {
        TranslateTransition translate = new TranslateTransition(Duration.seconds(duration),n);
        translate.setByY(dist);
        translate.setInterpolator(Interpolator.EASE_BOTH);
        translate.play();
    }
}
