package mkhabibullin.listeners;

import mkhabibullin.core.AllureTestOpsApi;
import org.testng.ITestContext;
import org.testng.TestListenerAdapter;

import static mkhabibullin.data.StaticData.GITLAB_PROJECT;

public class AllureGitlabListener extends TestListenerAdapter {

    @Override
    public void onStart(ITestContext testContext) {
        AllureTestOpsApi allure = new AllureTestOpsApi();
        allure.checkOpenedAllureDefectsWithGitlabClosedIssues(GITLAB_PROJECT);

    }

}
