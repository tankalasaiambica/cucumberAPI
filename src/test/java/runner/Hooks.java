package runner;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before(order=1)
    public void beforeScenario()
    {
        System.out.println("Start the browser and Clear the cookies");
    }
    @Before(order = 0)
    public void beforeScenarioStart()
    {
        System.out.println("-------------------Start of Scenario-------------------");
    }

    @After(order=1)
    public void afterScenario()
    {
        System.out.println("-------------------End of Scenario-------------------");
    }
    @After(order = 0)
    public void afterScenarioStart()
    {
        System.out.println("Log out the user and close the browser");
    }



}
