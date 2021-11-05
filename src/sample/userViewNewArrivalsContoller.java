package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class userViewNewArrivalsContoller implements Initializable {
    public Button exitButton;
    public Pane carImage0;
    public TextField carPrice0;
    public TextField carName0;
    public TextField carPrice1;
    public Pane carImage1;
    public TextField carName1;

    static String[] carNames = new String[5];
    static String[] carUrls = new String[5];
    static String[] carPrices = new String[5];

    static String[] carNamesJV = new String[5];
    static String[] carPricesJV = new String[5];
    static String[] carUrlsJV = new String[5];

    public TextField carName2;
    public TextField carPrice2;
    public Pane carImage2;
    public TextField carPrice3;
    public TextField carName3;
    public TextField carPrice4;
    public TextField carName4;
    public Pane carImage4;
    public Pane carImage3;
    public Button loadCArUsed;
    public Pane JVcarImage0;
    public TextField JVcarPrice0;
    public TextField JVcarName0;
    public Pane JVcarImage1;
    public TextField JVcarPrice1;
    public TextField JVcarName1;
    public Pane JVcarImage2;
    public TextField JVcarPrice2;
    public TextField JVcarName2;
    public Pane JVcarImage3;
    public TextField JVcarPrice3;
    public TextField JVcarName3;
    public Pane JVcarImage4;
    public TextField JVcarPrice4;
    public TextField JVcarName4;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadBEFOWARD();
        loadJAPANESEVEHICLES();

    }

    public void exitButtonAction(ActionEvent actionEvent) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void loadBEFOWARD() {
        String url = "https://www.beforward.jp/";

        try {
            Document doc = Jsoup.connect(url).get();
            int i;
            for (i = 0; i <= 4; i++) {

                //FOR BEFOWARD
                //select new arrival div
                Elements vehicleBox = doc.getElementsByClass("fn-top-display-lists-top-lateststock-list top-display-lists-top-lateststock-list");

                //select first box of element[i] of div new arrival
                Elements vehicleDetails = vehicleBox.first().getElementsByClass("item" + i + "");


                //get the url of the image in the box
                String pictureUrl = vehicleDetails.first().getElementsByTag("img").attr("src");
                carUrls[i] = "https:" + pictureUrl + "";

                //get the price
                Elements vehiclePrice = vehicleDetails.first().getElementsByClass("top-fob-price");
                carPrices[i] = vehiclePrice.text();

                //get the name
                Elements vehicleName = vehicleDetails.first().getElementsByTag("h3");
                carNames[i] = vehicleName.text();
            }
            //Entry number 1
            Image hold = new Image(carUrls[0], 243, 139, false, false);
            ImageView carImage = new ImageView(hold);
            carImage0.getChildren().add(carImage);
            carName0.setText(carNames[0]);
            carPrice0.setText(carPrices[0]);
            System.out.println(carUrls[0]);
            //end of entry 1

            //Entry number 2
            Image hold1 = new Image(carUrls[1], 243, 139, false, false);
            carName1.setText(carNames[1]);
            carPrice1.setText(carPrices[1]);
            ImageView carImagecpy1 = new ImageView(hold1);
            carImage1.getChildren().add(carImagecpy1);
            System.out.println(carUrls[1]);
            //end of entry 2

            //Entry number 3
            Image hold3 = new Image(carUrls[2], 243, 139, false, false);
            carName2.setText(carNames[2]);
            carPrice2.setText(carPrices[2]);
            ImageView carImagecpy2 = new ImageView(hold3);
            carImage2.getChildren().add(carImagecpy2);
            System.out.println(carUrls[2]);
            //end of entry 3

            //Entry number 4
            Image hold4 = new Image(carUrls[3], 243, 139, false, false);
            carName3.setText(carNames[3]);
            carPrice3.setText(carPrices[3]);
            ImageView carImagecpy3 = new ImageView(hold4);
            carImage3.getChildren().add(carImagecpy3);
            System.out.println(carUrls[3]);
            //end of entry 4

            //Entry number 5
            Image hold5 = new Image(carUrls[4], 243, 139, false, false);
            carName4.setText(carNames[4]);
            carPrice4.setText(carPrices[4]);
            ImageView carImagecpy4 = new ImageView(hold5);
            carImage4.getChildren().add(carImagecpy4);
            System.out.println(carUrls[2]);
            //end of entry 5
            //END BEFORWAD

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadJAPANESEVEHICLES() {
        String url2 = "https://www.japanesevehicles.com/";

        try {
            //connect to website
            Document doc = Jsoup.connect(url2).get();
            Elements newArrivals = doc.getElementsByClass("new ");
            Element carBox = null;

            for (int i = 0; i <= 4; i++) {
                carBox = newArrivals.get(i);
                carNamesJV[i] = carBox.getElementsByClass("title_carlist").text();
                carPricesJV[i] = carBox.getElementsByClass("price_after").text();
                carUrlsJV[i] = carBox.getElementsByTag("img").attr("src");
            }
            //Entry number 1
            Image hold = new Image(carUrlsJV[0], 243, 139, false, false);
            ImageView carImage = new ImageView(hold);
            JVcarImage0.getChildren().add(carImage);
            JVcarName0.setText(carNamesJV[0]);
            JVcarPrice0.setText(carPricesJV[0]);
            System.out.println(carUrlsJV[0]);
            //end of entry 1

            //Entry number 2
            Image hold1 = new Image(carUrlsJV[1], 243, 139, false, false);
            ImageView carImage1 = new ImageView(hold1);
            JVcarImage1.getChildren().add(carImage1);
            JVcarName1.setText(carNamesJV[1]);
            JVcarPrice1.setText(carPricesJV[1]);
            System.out.println(carUrlsJV[1]);
            //end of entry 2

            //Entry number 3
            Image hold2 = new Image(carUrlsJV[2], 243, 139, false, false);
            ImageView carImage2 = new ImageView(hold2);
            JVcarImage2.getChildren().add(carImage2);
            JVcarName2.setText(carNamesJV[2]);
            JVcarPrice2.setText(carPricesJV[2]);
            System.out.println(carUrlsJV[2]);
            //end of entry 3

            //Entry number 4
            Image hold3 = new Image(carUrlsJV[3], 243, 139, false, false);
            ImageView carImage3 = new ImageView(hold3);
            JVcarImage3.getChildren().add(carImage3);
            JVcarName3.setText(carNamesJV[3]);
            JVcarPrice3.setText(carPricesJV[3]);
            System.out.println(carUrlsJV[3]);
            //end of entry 4

            //Entry number 5
            Image hold4 = new Image(carUrlsJV[4], 243, 139, false, false);
            ImageView carImage4 = new ImageView(hold4);
            JVcarImage4.getChildren().add(carImage4);
            JVcarName4.setText(carNamesJV[4]);
            JVcarPrice4.setText(carPricesJV[4]);
            System.out.println(carUrlsJV[4]);
            //end of entry 5


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
