package featuresteps.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import featuresteps.modules.Google;

/**
 * Created by Sajeev_230526 on 2/2/18.
 * Project : automation-tests
 */


public class GoogleSearch {


    @Given("^I launch the google url$")
    public void iLaunchTheGoogleUrl() throws Throwable {
        new Google().openGoogle();

    }

    @And("^enter the search text \"([^\"]*)\"$")
    public void enterTheSearchText(String arg0) throws Throwable {
        new Google().searchGoogle(arg0);

    }

    @When("^click on search button$")
    public void clickOnSearchButton() throws Throwable {
        new Google().searchButton();

    }

    @Then("^the result page is loaded$")
    public void theResultPageIsLoaded() throws Throwable {

    }
}
