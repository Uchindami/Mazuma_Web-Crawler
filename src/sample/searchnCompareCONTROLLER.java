package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class searchnCompareCONTROLLER implements Initializable {

    public Pane pane0;
    public Button exitButton;
    public TextField carPrice0;
    public TextField carName0;
    public TextField carYear0;
    public TextField carDealer0;
    public Pane pane1;
    public TextField carPrice1;
    public TextField carName1;
    public TextField carYear1;
    public TextField carDealer1;
    public TextField nameInput;
    public Button searchButton;
    public Label notificationBox;

    int size;
    String[] carNamesJV = new String[5];
    String[] carUrlsJV = new String[5];
    String[] carPricesJV = new String[5];

    String[] carPricesBFCPY = new String[size + 3];
    String[] carNAMESBFCPY = new String[size + 3];
    String[] carURLSBFCPY = new String[size + 3];


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println(check("1,675"));
    }

    public void exitButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void searchBefoward(String nameInput) {
        String url = "https://www.beforward.jp/stocklist/client_wishes_id=/description=/make=/model=/fuel=/fob_price_" +
                "from=/fob_price_to=/veh_type=/steering=/mission=/mfg_year_from=/mfg_year_to=/mileage_from=/mileage_to=" +
                "/cc_from=/cc_to=/showmore=/drive_type=/color=/stock_country=/area=/seats_from=/seats_to=/max_load_min=" +
                "/max_load_max=/veh_type_sub=/view_cnt=10/page=1/sortkey=a/sar=/from_stocklist=1/keyword=" + nameInput + "/" +
                "kmode=and/";

        try {
            Document doc = Jsoup.connect(url).timeout(600000).maxBodySize(0).get();
            Elements vehicleBox = doc.getElementsByClass("stocklist-row");
            size = vehicleBox.size();
            Element singleVB = null;

            String[] carNamesBF = new String[(size + 3)];
            String[] carUrlsBF = new String[size + 3];
            String[] carPricesBF = new String[(size + 3)];
            carPricesBFCPY = carPricesBF;
            carNAMESBFCPY = carNamesBF;
            carURLSBFCPY = carUrlsBF;

            for (int i = 0; i <= size - 1; i++) {

                singleVB = vehicleBox.get(i);
                String test = singleVB.getElementsByClass("vehicle-url-link").text();
                if ((singleVB.getElementsByClass("vehicle-url-link").text()).equals("") || (singleVB.getElementsByClass("vehicle-price").text()).equals("")) {

                } else {
                    carNamesBF[i] = singleVB.getElementsByClass("vehicle-url-link").get(1).text();
                    carPricesBF[i] = singleVB.getElementsByClass("vehicle-price").text().split("\\$")[1];
                    String hold = singleVB.getElementsByClass("stocklist-col photo-col").first().getElementsByTag("img").attr("src");
                    carUrlsBF[i] = "https:" + hold + "";
                }

            }
            System.out.println(size);
            for (int i = 0; i <= size; i++) {
                // System.out.print(i+" : ");
                System.out.println(carNamesBF[i]);
                System.out.println(carPricesBF[i]);
                System.out.println(carUrlsBF[i]);
                System.out.println(" ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchJAPANESEVEHICLES(String keyword) {

        String url = "https://www.japanesevehicles.com/stocklist.php?qsearch=" + keyword + "";

        try {

            Document doc = Jsoup.connect(url).get();

            for (int i = 0; i < 2; i++) {
                Elements vehicleBox = doc.getElementsByClass("e-item-content-row js-stocklist-tr-" + i + "");
                carNamesJV[i] = vehicleBox.first().getElementsByClass("e-img").first().getElementsByTag("img").attr("alt").split(" S/N")[0];

                if (vehicleBox.first().getElementsByClass("e-price p-stocklist-price__discount-price").text().equals("")) {
                    carPricesJV[i] = vehicleBox.first().getElementsByClass("e-price ").text();
                } else {
                    carPricesJV[i] = vehicleBox.first().getElementsByClass("e-price p-stocklist-price__discount-price").text();
                }
                carUrlsJV[i] = vehicleBox.first().getElementsByClass("e-img").attr("data-src");
            }
            for (int i = 0; i <= 4; i++) {
                System.out.println(carNamesJV[i]);
                System.out.println(carPricesJV[i]);
                System.out.println(carUrlsJV[i]);
            }

        } catch (IOException e) {
            catchBeFoward();
            e.printStackTrace();
        }

    }

    public void searchButtonAction(ActionEvent actionEvent) {
        String keyword = nameInput.getText();
        if (keyword.equals("")) {

            notificationBox.setText("Enter Valid name");

        } else {
            notificationBox.setText("");
            searchBefoward(keyword);
            searchJAPANESEVEHICLES(keyword);
            sortAndDisplay();
        }
    }

    public void sortAndDisplay() {

        for (int i = 0; i < 1; i++) {

            if (carNAMESBFCPY[i].equals("")) {

                if (check(carPricesBFCPY[i + 1]) < check(carPricesJV[i])) {

                    System.out.println("Befoward has the chepest option");

                    //set pane 0
                    Image hold = new Image(carURLSBFCPY[1], 247, 200, false, false);
                    ImageView carImage = new ImageView(hold);
                    pane0.getChildren().add(carImage);
                    carName0.setText(carNamesJV[1]);
                    carPrice0.setText("MK " + check(carPricesBFCPY[1]));
                    carDealer0.setText("BeFoward");
                    //end of pane 0

                    //set pane 1
                    Image holdx = new Image(carURLSBFCPY[2], 247, 200, false, false);
                    ImageView carImagex = new ImageView(holdx);
                    pane1.getChildren().add(carImagex);
                    carName1.setText(carNAMESBFCPY[2]);
                    carPrice1.setText("MK " + check(carPricesBFCPY[2]));
                    carDealer1.setText("BeFoward");
                    //end of pane 1

                    if (check(carPricesBFCPY[i + 2]) < check(carPricesJV[i + 1])) {

                        //set pane 1
                        Image hold11 = new Image(carURLSBFCPY[i + 2], 247, 200, false, false);
                        ImageView carImage11 = new ImageView(hold11);
                        pane1.getChildren().add(carImage11);
                        carName1.setText(carNamesJV[i + 2]);
                        carPrice1.setText("MK " + check(carPricesBFCPY[i + 2]));
                        carDealer1.setText("BeFoward");
                        //end of pane 1

                    } else {
                        //set pane 1
                        System.out.println("JV has the chepest option");
                        Image hold2 = new Image(carPricesJV[1], 247, 200, false, false);
                        ImageView carImage2 = new ImageView(hold2);
                        pane1.getChildren().add(carImage);
                        carName1.setText(carNamesJV[1]);
                        carPrice1.setText("MK " + check(carPricesJV[1]));
                        carDealer1.setText("JapaneseVehicles");
                        //end of pane 1
                    }

                } else {

                    //set pane 0
                    System.out.println("JV has the chepest option");
                    Image hold = new Image(carPricesJV[0], 247, 200, false, false);
                    ImageView carImage = new ImageView(hold);
                    pane0.getChildren().add(carImage);
                    carName0.setText(carNamesJV[0]);
                    carPrice0.setText("MK " + check(carPricesJV[0]));
                    carDealer0.setText("JapaneseVehicles");
                    //end of pane 0

                    //set pane 1
                    System.out.println("JV has the chepest option");
                    Image hold2 = new Image(carPricesJV[1], 247, 200, false, false);
                    ImageView carImage2 = new ImageView(hold2);
                    pane1.getChildren().add(carImage);
                    carName1.setText(carNamesJV[1]);
                    carPrice1.setText("MK " + check(carPricesJV[1]));
                    carDealer1.setText("JapaneseVehicles");
                    //end of pane 1
                }

            } else {
                if (check(carPricesBFCPY[i]) < check(carPricesJV[i])) {
                    System.out.println("Befoward has the chepest option");

                    //set pane 0
                    Image hold = new Image(carURLSBFCPY[0], 247, 200, false, false);
                    ImageView carImage = new ImageView(hold);
                    pane0.getChildren().add(carImage);
                    carName0.setText(carNamesJV[0]);
                    carPrice0.setText("MK\t" + check(carPricesBFCPY[0]));
                    carDealer0.setText("BeFoward");
                    //end of pane 0

                    //set pane 1
                    Image holdy = new Image(carURLSBFCPY[1], 247, 200, false, false);
                    ImageView carImagey = new ImageView(holdy);
                    pane1.getChildren().add(carImagey);
                    carName1.setText(carNAMESBFCPY[1]);
                    carPrice1.setText("MK\t" + check(carPricesBFCPY[1]));
                    carDealer1.setText("BeFoward");
                    //end of pane 1

                } else {

                    System.out.println("JV has the chepest option");

                    //set pane 0
                    Image hold = new Image(carUrlsJV[0], 247, 200, false, false);
                    ImageView carImage = new ImageView(hold);
                    pane0.getChildren().add(carImage);
                    carName0.setText(carNamesJV[0]);
                    carPrice0.setText("MK " + check(carPricesJV[0]));
                    carDealer0.setText("JapaneseVehicles");
                    //end of pane 0
                }
            }
        }
    }

    private int check(@NotNull String passcheck) {
        int checkpass = 0;

        if (passcheck.length() < 4) {

            checkpass = Integer.parseInt(passcheck);
            checkpass = convertCARS(checkpass);
        } else {
            String passcheckcpy = null;
            passcheckcpy = passcheck.split(",")[0] + passcheck.split(",")[1];
            System.out.println(passcheckcpy);
            int checkpassCPY1 = Integer.parseInt(passcheckcpy);
            System.out.println(checkpassCPY1);
            checkpass = convertCARS(checkpassCPY1);
        }
        return checkpass;
    }

    public int convertCARS(int car) {

        int returnCar = 0;

        String url1 = "https://www.xe.com/currencyconverter/convert/?Amount=" + car + "&From=USD&To=MWK";
        System.out.println(car);

        try {
            Document doc = Jsoup.connect(url1).get();
            doc.select("faded-digits").remove();
            String hold = doc.getElementsByClass("result__BigRate-sc-1bsijpp-1 iGrAod").text().split(" M")[0];
            System.out.println(hold);
            String holdcpy;
            String holdcpy1 = null;

            if (car < 1251) {
                holdcpy = hold.split("\\.")[0];
                holdcpy1 = holdcpy.split(",")[0] + holdcpy.split(",")[1];
            } else {
                holdcpy = hold.split("\\.")[0];
                holdcpy1 = holdcpy.split(",")[0] + holdcpy.split(",")[1] + holdcpy.split(",")[2];
            }
            returnCar = Integer.parseInt(holdcpy1);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnCar;
    }

    public void catchBeFoward() {
        //set pane 0
        Image hold = new Image(carURLSBFCPY[1], 247, 200, false, false);
        ImageView carImage = new ImageView(hold);
        pane0.getChildren().add(carImage);
        carName0.setText(carNamesJV[1]);
        carPrice0.setText("MK " + check(carPricesBFCPY[1]));
        carDealer0.setText("BeFoward");
        //end of pane 0

        //set pane 1
        Image hold1 = new Image(carURLSBFCPY[2], 247, 200, false, false);
        ImageView carImage1 = new ImageView(hold1);
        pane1.getChildren().add(carImage);
        carName1.setText(carNamesJV[2]);
        carPrice1.setText("MK " + check(carPricesBFCPY[2]));
        carDealer1.setText("BeFoward");
        //end of pane 1
    }

}
