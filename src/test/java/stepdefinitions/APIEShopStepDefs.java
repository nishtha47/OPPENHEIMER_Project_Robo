package stepdefinitions;



import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import apilib.APIReusuableLibrary;
import helper.WorkingclassHeroRecord;
import io.restassured.response.ValidatableResponse;
//import com.fasterxml.jackson.core.type.TypeReference;


import pages.TheOppenheimerPage;

import java.util.Arrays;
import java.util.List;


public class APIEShopStepDefs extends MasterStepDefs {
    ValidatableResponse response;
    String strProductEndpointUri;
    String strInsertHeroSingleRecordEndpoint;
    String strInsertHeroMultipleRecordEndpoint;
    String strTaxReliefCalcutor;
    String strSingleRecordPayload;
    String strheroSerializationDataMapper;
    String strPostBodyContent;
    String strApiHost;
    String strApiHostURL;


    public APIEShopStepDefs() {
        String strBackendIP = System.getenv("backendPrivateIP");
        if (strBackendIP == null || strBackendIP.isEmpty()) {
            strApiHost = System.getProperty("apiHost");
            LOG.info("API URL Fetched from POM Settings");
        } else {
            strApiHost = strBackendIP;
            LOG.info("API URL Fetched from AWS ENVIRONMENT");
        }
        strApiHostURL = "http://" + strApiHost + ":8080";
        LOG.info("API URL : " + strApiHostURL);

        strInsertHeroSingleRecordEndpoint = strApiHostURL + readData().getProperty("InsertHeroSingleRecordEndpoint");
        strInsertHeroMultipleRecordEndpoint = strApiHostURL + readData().getProperty("InsertHeroMultipleRecordEndpoint");
        strTaxReliefCalcutor = strApiHostURL + readData().getProperty("TaxReliefCalcutor");
    }

    @Given("^An API endpoint for Products$")
    public void userSetGETPostsAPIEndpoint() {
        strProductEndpointUri = strProductEndpointUri + "all";
    }

    @When("^User send GET HTTP request$")
    public void sendGETHTTPRequest() {
        LOG.info("Executed this step");
    }

    /*@Then("^User receive valid HTTP response code \"([^\"]*)\"$")
    public void UserReceiveValidHTTPResponseCode(String strStatusCode) {
        try {
            response = apiDriver.sendNReceive(strProductEndpointUri, APIReusuableLibrary.SERVICEMETHOD.GET, null, Integer.parseInt(strStatusCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    @Then("^User receive valid HTTP response code \"([^\"]*)\"$")
    public void UserReceiveValidHTTPResponseCode(String strStatusCode) {
        try {
            response = apiDriver.sendNReceive(strProductEndpointUri, APIReusuableLibrary.SERVICEMETHOD.GET, null, Integer.parseInt(strStatusCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("^An API endpoint for Products with \"([^\"]*)\"$")
    public void userSetGETPostsAPIEndpointFor(String strName) {
        strProductEndpointUri = strProductEndpointUri + "search?productName=" + strName;
    }

    @And("^User set request body$")
    public void setRequestBody() {
        strPostBodyContent = apiDriver.readInput(readData().getProperty("PositivePaymentInputTemplate"));
    }

    @And("User set request body with single hero record")
    public void userSetRequestBodyWithSingleHeroRecord() {
        strPostBodyContent = apiDriver.readInput(readData().getProperty("SingleHeroInputTemplate"));
    }

    @And("User set request body with multiple hero records")
    public void userSetRequestBodyWithMultipleHeroRecords() {
        strPostBodyContent = apiDriver.readInput(readData().getProperty("MultipleHeroInputTemplate"));
    }


    @And("^User set invalid request body$")
    public void setInvalidRequestBody() {
        strPostBodyContent = apiDriver.readInput(readData().getProperty("NegativePaymentInputTemplate"));
    }

    @And("^User send POST HTTP request$")
    public void sendPOSTHTTPRequest() {
        LOG.info("Executed this step");
    }

    @Then("^User receive HTTP response code \"([^\"]*)\"$")
    public void UserReceiveHTTPResponseCode(String strStatusCode) {
        try {
            response = apiDriver.sendNReceive(strInsertHeroSingleRecordEndpoint, APIReusuableLibrary.SERVICEMETHOD.POST, APIReusuableLibrary.SERVICEFORMAT.JSON, strPostBodyContent, null,
                    Integer.parseInt(strStatusCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Then("User receives HTTP response code {string} for a single Hero record")
    public void userReceivesHTTPResponseCodeForASingleHeroRecord(String strStatusCode) {
        try {
            response = apiDriver.sendNReceive(strInsertHeroSingleRecordEndpoint, APIReusuableLibrary.SERVICEMETHOD.POST, APIReusuableLibrary.SERVICEFORMAT.JSON, strPostBodyContent, null,
                    Integer.parseInt(strStatusCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Then("User receives HTTP response code {string} for multiple Hero record")
    public void userReceivesHTTPResponseCodeForMultipleHeroRecord(String strStatusCode) {
        try {
            response = apiDriver.sendNReceive(strInsertHeroMultipleRecordEndpoint, APIReusuableLibrary.SERVICEMETHOD.POST, APIReusuableLibrary.SERVICEFORMAT.JSON, strPostBodyContent, null,
                    Integer.parseInt(strStatusCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Then("User receives HTTP response code {string} for single Hero record using Serialization")
    public void userReceivesHTTPResponseCodeForSingleHeroRecordUsingSerialization(String strStatusCode) {
          try {
              response = apiDriver.sendNReceive(strInsertHeroSingleRecordEndpoint, APIReusuableLibrary.SERVICEMETHOD.POST, APIReusuableLibrary.SERVICEFORMAT.JSON, strheroSerializationDataMapper, null,
                      Integer.parseInt(strStatusCode));
          }catch (Exception e){
              e.printStackTrace();
          }
    }


    @Given("An API endpoint for Hero Record")
    public void anAPIEndpointForHeroRecord() {
        LOG.info("Executed this step");
    }

    @Then("User receive response code {string}")
    public void userReceiveResponseCode(String strStatusCode) {
        try {
            response = apiDriver.sendNReceive(strInsertHeroSingleRecordEndpoint, APIReusuableLibrary.SERVICEMETHOD.POST, APIReusuableLibrary.SERVICEFORMAT.JSON, strSingleRecordPayload, null,
                    Integer.parseInt(strStatusCode));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Given("An API endpoint for Calculator Tax Releif")
    public void anAPIEndpointForCalculatorTaxReleif() {
        LOG.info("Executed this step");
    }

    @Then("User verify valid HTTP response code {string} for Calculator Tax Releif")
    public void userVerifyValidHTTPResponseCodeForCalculatorTaxReleif(String strStatusCode) {
        try {
            String strInsertHeroSingleRecord = readData().getProperty("TaxReliefCalcutor");
            strTaxReliefCalcutor = strApiHostURL + strInsertHeroSingleRecord;
            System.out.println(strTaxReliefCalcutor);
            response = apiDriver.sendNReceive(strTaxReliefCalcutor, APIReusuableLibrary.SERVICEMETHOD.GET, null, Integer.parseInt(strStatusCode));

            List<String> jsonResponse = response.extract().jsonPath().getList("$");
            System.out.println(jsonResponse.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @When("User verifies natid field must be masked from the fifth character onwards with dollar sign {string}")
    public void userVerifiesNatidFieldMustBeMaskedFromTheFifthCharacterOnwardsWithDollarSign(String arg0) throws Exception {
        String strInsertHeroSingleRecord = readData().getProperty("TaxReliefCalcutor");
        strTaxReliefCalcutor = strApiHostURL + strInsertHeroSingleRecord;
        System.out.println(strTaxReliefCalcutor);
        response = apiDriver.sendNReceive(strTaxReliefCalcutor, APIReusuableLibrary.SERVICEMETHOD.GET, null, Integer.parseInt("200"));
        List<String> jsonResponse = response.extract().jsonPath().getList("natid");
        verifyNatIDwithMaskedCondition(jsonResponse, arg0);
    }

    @When("User verifies computation of the tax relief is using the formula as described")
    public void userVerifiesComputationOfTheTaxReliefIsUsingTheFormulaAsDescribed() throws Exception {
        String strInsertHeroSingleRecord = readData().getProperty("TaxReliefCalcutor");
        strTaxReliefCalcutor = strApiHostURL + strInsertHeroSingleRecord;
        System.out.println(strTaxReliefCalcutor);
        //  response = apiDriver.sendNReceive(strTaxReliefCalcutor, APIReusuableLibrary.SERVICEMETHOD.GET, null, Integer.parseInt("200"));
        response = apiDriver.sendNReceive(strTaxReliefCalcutor, APIReusuableLibrary.SERVICEMETHOD.GET, null, Integer.parseInt("200"));
        //List<Object> jsonResponse = response.extract().jsonPath().getList("$");
        List<Object> jsonResponse = response.extract().jsonPath().getList("relief");
        System.out.println(jsonResponse.toString());

    }


    @When("User verifies tax relief computation values with uploaded data {string}, {string}, {string}, {string}, {string}, {string}")
    public void userVerifiesTaxReliefComputationValuesWithUploadedData(String strBirthday, String strGender, String strName, String strNatid, String strSalary, String strTax) throws Exception {

        response = apiDriver.sendNReceive(strTaxReliefCalcutor, APIReusuableLibrary.SERVICEMETHOD.GET, null, Integer.parseInt("200"));
//        JsonPath jsonPathEvaluator = response.extract().jsonPath();
        TheOppenheimerPage theOppenheimerPage = new TheOppenheimerPage(driver);
        theOppenheimerPage.compareApiAndCalculatedvalue(strSalary,strTax,strGender,strBirthday,strName,response);
     }



    @And("User set request body with {string}, {string}, {string}, {string}, {string}, {string}")
    public void userSetRequestBodyWith(String strBirthday, String strGender, String strName, String strNatid, String strSalary, String strTax) {
        strSingleRecordPayload = apiDriver.registrationPayload(strBirthday,  strGender,  strName,  strNatid,  strSalary,  strTax);
        strSingleRecordPayload = apiDriver.heroSerializationDataMapper(strBirthday,  strGender,  strName,  strNatid,  strSalary,  strTax);
//
    }


    @And("User set serialization request body with {string}, {string}, {string}, {string}, {string}, {string}")
    public void userSetSerializationRequestBodyWith(String strBirthday, String strGender, String strName, String strNatid, String strSalary, String strTax) {
        strheroSerializationDataMapper = apiDriver.registrationPayload(strBirthday,  strGender,  strName,  strNatid,  strSalary,  strTax);
    }

    @Then("User verifies tax relief values for each {string}")
    public void userVerifiesTaxReliefValuesForEach(String arg0) {
        try {
            response = apiDriver.sendNReceive(strTaxReliefCalcutor, APIReusuableLibrary.SERVICEMETHOD.GET, null, Integer.parseInt("200"));
             List<WorkingclassHeroRecord> returnedArtworks = Arrays.asList(response.extract().as(WorkingclassHeroRecord[].class));
            System.out.println(returnedArtworks.size());
            System.out.println(returnedArtworks.get(0).getName().toString());
            System.out.println(returnedArtworks.get(1).getName().toString());
            System.out.println(returnedArtworks.get(2).getName().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
