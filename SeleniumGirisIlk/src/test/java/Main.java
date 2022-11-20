import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws InterruptedException {

        WebDriverManager.chromedriver().setup();  // driver kuruldu
ChromeOptions options = new ChromeOptions();
options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);   // driver i tanittik
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        driver.manage().window().maximize(); // acilacak pencereyi maximize ediyor

        driver.get("https://www.network.com.tr/");  // gidecegimiz adresi yazdik


        //TEST URL KONTROLU BASLANGIC
        String urlControlTest =driver.getCurrentUrl();

        if (Objects.equals(urlControlTest, "https://www.network.com.tr/")){

            System.out.println("Test : Network URL dogrulandi.");
        } //TEST URL KONTROLU BITIS


        WebElement cookies = driver.findElement(By.id("onetrust-accept-btn-handler"));
        cookies.click();


        WebElement search = driver.findElement(By.cssSelector("#search"));
        search.click();
        search.sendKeys("ceket\n");


        WebElement showMore = driver.findElement(By.cssSelector(".button.-secondary.-sm.relative"));
        Actions actions = new Actions(driver);
        actions.moveToElement(showMore);
        actions.perform();
        showMore.click();


        WebElement alternatif = driver.findElement(By.xpath("//div[@class=\"product__discountPercent\"]"));
        actions.moveToElement(alternatif);
        actions.perform();



        WebElement tikla= driver.findElement(By.xpath("//div[@class=\"product__header\"]"));
        tikla.click();

/*
   List<WebElement> jackets = driver.findElements(By.xpath("//div[@class=\"products__item col-6 col-md-4\"]"));
WebElement q;
for (int i=0;i<jackets.size();i++){
q = jackets.get(i);
q.click();
break;

}
*/




WebElement addToBasket = driver.findElement(By.cssSelector("button[class='product__button -addToCart btn -black']"));

        actions.moveToElement(addToBasket);
        actions.perform();

WebElement size = driver.findElement(By.xpath("//label[@class = 'radio-box__label']"));

size.click();
//bas
WebElement priceBeforeBasket = driver.findElement(By.cssSelector(".product__price.-actual"));
String priceBeforeBasketCompare = priceBeforeBasket.getText();
String sizeBeforeBasketCompare = size.getText();


        //son

addToBasket.click();

        WebElement myBasket = driver.findElement(By.xpath("//a[@class='button -primary header__basket--checkout header__basketModal -checkout']"));
        myBasket.click();
//Fiyat ve olcu 2
        WebElement PriceInBasket = driver.findElement(By.cssSelector(".cartItem__price.-sale"));
        String priceInBasketCompare = PriceInBasket.getText();
        WebElement sizeInBasket = driver.findElement(By.cssSelector("div[class='cartItem__attr -size'] span[class='cartItem__attrValue']"));
        String sizeInBasketCompare = sizeInBasket.getText();

        if(Objects.equals(priceBeforeBasketCompare, priceInBasketCompare) & Objects.equals(sizeBeforeBasketCompare, sizeInBasketCompare)){
            System.out.println("Test : Olcu ve fiyat bilgileri uyumlu.");
        }

  //fiyat ve olcu son2

        //Test fiyat - indirim kontrolu baslangic

        String discountedPriceString = priceInBasketCompare;
      discountedPriceString=  discountedPriceString.substring(0, discountedPriceString.length() - 6);

      float discountedPriceFloat = Float.parseFloat(discountedPriceString);


      WebElement oldPrice = driver.findElement(By.xpath("//span[@class='cartItem__price -old -labelPrice']"));
      String oldPriceString = oldPrice.getText();
     oldPriceString = oldPriceString.substring(0,oldPriceString.length() - 6);
     float oldPriceFloat = Float.parseFloat(oldPriceString);

if (oldPriceFloat>discountedPriceFloat){

    System.out.println("Test : Urunun eski fiyati indirimli fiyatından büyük.");
}



      //Test fiyat - indirim kontrolu son
        WebElement devamEt = driver.findElement(By.cssSelector(".continueButton.n-button.large.block.text-center.-primary"));
devamEt.click();

// csv baslangic

      String path = "C:/Users/Eren/Saved Games/bilgiler.csv";
      String line = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

             while ((line =br.readLine())!=null){

              String[] values =line.split(";");

              WebElement eMail = driver.findElement(By.cssSelector("#n-input-email"));
                 eMail.click();
                 eMail.sendKeys(values[0]);

             WebElement code =driver.findElement(By.cssSelector("#n-input-password"));
              code.click();
              code.sendKeys(values[1]);

             }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);}


//csv bitis


        //TEST GIRIS YAP BUTONU BASLANGIC
        WebElement signIn = driver.findElement(By.cssSelector("button[type='submit']"));

        if(signIn.isEnabled()){
            System.out.println("Test : Giris Yap butonu hazir.");
        }
        else {
            System.out.println("Test : Giris Yap butonu hazir degil.");
        }
        //TEST GIRIS YAP BUTONU BITIS


        WebElement NetworkLogo = driver.findElement(By.xpath("//a[@class='headerCheckout__logo']//*[name()='svg']"));
NetworkLogo.click();


WebElement bag =driver.findElement(By.xpath("//button[@class='header__basketTrigger js-basket-trigger -desktop']//*[name()='svg']"));
bag.click();


WebElement removeTheProduct =driver.findElement(By.xpath("/html[1]/body[1]/div[2]/header[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[2]/div[1]/div[3]/*[name()='svg'][1]/*[name()='use'][1]"));

removeTheProduct.click();


WebElement permissionForRemoving = driver.findElement(By.xpath("//button[contains(text(),'Çıkart')]"));
        permissionForRemoving.click();



//TEST sepet teyit baslangic
bag.click();
WebElement isBasketEmpty = driver.findElement(By.className("header__emptyBasketText"));

if(isBasketEmpty.isEnabled()){

    System.out.println("Test : Sepet Bos.");
}

//TEST sepet teyit bitis


        WebElement closeTheBasket = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/header[1]/div[1]/div[1]/div[3]/div[2]/div[1]/div[1]/div[1]/button[1]/*[name()='svg'][1]/*[name()='use'][1]"));
        closeTheBasket.click();



    }
}
