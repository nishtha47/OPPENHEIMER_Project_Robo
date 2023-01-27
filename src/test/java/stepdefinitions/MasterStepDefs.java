package stepdefinitions;


import apilib.APIReusuableLibrary;
import apilib.WebDriverFactory;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;


import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class MasterStepDefs {

    WebDriver driver;
    WebDriverWait wait;
    protected static final Logger LOG = LoggerFactory.getLogger(MasterStepDefs.class);
    protected APIReusuableLibrary apiDriver = new APIReusuableLibrary();
    private static final int TIMEOUT = 30;

    /**
     * Function to initialize Driver
     *
     * @param strDevice Device Name
     */


    public void initializeDriver(String strDevice) {
        String strAppHost;
        driver = WebDriverFactory.createWebDriverInstance(strDevice);
        wait = new WebDriverWait(driver, MasterStepDefs.TIMEOUT);
        String strFrontendIP = System.getenv("backendPrivateIP");
        if (strFrontendIP == null || strFrontendIP.isEmpty()) {
            strAppHost = System.getProperty("applicationHost");
            LOG.info("UI URL Fetched from POM Settings");
        } else {
            strAppHost = strFrontendIP;
            LOG.info("UI URL Fetched from AWS ENVIRONMENT");
        }

       // String strAppHostURL = "http://" + strAppHost + ":3000";
        String strAppHostURL = "http://" + strAppHost;
        String strAppUrl = "ApplicationUrl : " + strAppHostURL;
        LOG.info(strAppUrl);
        driver.get(strAppHostURL.trim());
        driver.manage().timeouts().implicitlyWait(MasterStepDefs.TIMEOUT, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    /**
     * Function to get Data
     *
     * @return Properties Object
     */

    public static Properties readData() {
        Properties objProp = new Properties();
        try {
            File file = new File("TestSettings.properties");
            FileInputStream fileInput = null;
            fileInput = new FileInputStream(file);
            objProp.load(fileInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objProp;
    }

    /**
     * Function to Take Screenshot
     *
     * @param driver Web driver
     */

    public static String getScreenhot(WebDriver driver) {
        TakesScreenshot newScreen = (TakesScreenshot) driver;
        String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
        return "data:image/jpg;base64, " + scnShot;
    }


    public String verifyFileHeader(String filePath) throws IOException {
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException ex) {
            // Do something with 'ex'
        }
        return br.readLine();
    }
    public Boolean verifyCSVFileValue(String filePath) throws IOException {
        BufferedReader br = null;
        Boolean returnval = false;
        try {
            br = new BufferedReader(new FileReader(filePath));
            br.readLine();
            while (br.readLine() != null) {
                returnval = true;
            }
        } catch (FileNotFoundException ex) {
            // Do something with 'ex'
        } catch (IOException ex2) {
            // Do something with 'ex2'
        }
        return returnval;
    }

    public boolean verifyNatIDwithMaskedCondition(List<String> val, String maskval){
        for(int i=0;i<val.size();i++){
           //System.out.println(val.get(i).toString());
            //System.out.println(val.get(i).indexOf("$"));
           // System.out.println(allCharactersSame("$"));
            Assert.assertTrue(allCharactersSame(maskval));
        }
        return true;
    }
    public Boolean allCharactersSame(String s)
    {
        int n = s.length();
        for (int i = 1; i < n; i++)
            if (s.charAt(i) != s.charAt(0))
                return false;

        return true;
    }


}
