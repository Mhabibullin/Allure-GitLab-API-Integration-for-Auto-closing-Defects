package mkhabibullin.core;

import io.restassured.response.Response;
import mkhabibullin.models.Content;
import mkhabibullin.models.DefectPatchDto;
import mkhabibullin.models.DefectResponse;
import mkhabibullin.specifications.AllureSpecs;
import org.apache.log4j.Logger;
import org.gitlab.api.models.GitlabIssue;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static mkhabibullin.data.StaticData.*;


public class AllureTestOpsApi {
    private static final Logger logger = Logger.getLogger(AllureTestOpsApi.class);


    public void checkOpenedAllureDefectsWithGitlabClosedIssues(String projectName) {
        logger.info("Checking for closed Gitlab issues with open Allure defects");
        try {
            DefectResponse response = getGitlabIssuesListOfOpenedDefects();
            HashMap<String, String> closedGitlabIssueWithOpenedAllureDefect = new HashMap<>();
            GitlabApi gitlabApi = new GitlabApi();
            gitlabApi.getApi();
            for (int i = 0; i < response.getContent().size(); i++) {
                gitlabApi.gitLabIssue(projectName, response.getIssueCleanUrl(i));
                GitlabIssue gitLabIssue = gitlabApi.getGitLabIssue();
                String state = gitLabIssue.getState();
                if (state.equals(GitlabIssue.STATE_CLOSED)) {
                    closedGitlabIssueWithOpenedAllureDefect.put(response.getDefectId(i), response.getName(i));
                }
            }
            if (!closedGitlabIssueWithOpenedAllureDefect.isEmpty()) {
                for (Map.Entry<String, String> entry : closedGitlabIssueWithOpenedAllureDefect.entrySet()) {
                    closeAllureDefect(entry.getKey(), entry.getValue());
                }
            }
        }
        catch (Exception | AssertionError e){
            logger.error("Checking failed, reason:");
            e.printStackTrace();
        }
    }

    private DefectResponse getGitlabIssuesListOfOpenedDefects() {
        logger.info("Getting a list of open defects from the AllureTestOps API");
        AllureSpecs allureSpecs = new AllureSpecs();
        DefectResponse response = given().spec(allureSpecs.initialRequestSpec())
            .queryParam("projectId", ALLURE_PROJECT_ID)
            .queryParam("status", "open")
            .when().get("/defect")
            .then()
            .extract().as(DefectResponse.class);
        ArrayList<Content> rawContent = response.getContent();
        ArrayList<Content> contentOnlyWithUrls = new ArrayList<>();
        for (Content content : rawContent) {
            try {
                content.getIssueCleanUrl();
                contentOnlyWithUrls.add(content);
            } catch (NullPointerException ignore) {
            }
        }
        response.setContent(contentOnlyWithUrls);
        return response;
    }

    private void closeAllureDefect(String defectId, String defectName) {
        logger.info("Found open defects with a closed issue, close the defect: " + defectId);
        DefectPatchDto closeDefectBody = new DefectPatchDto();
        closeDefectBody.setName(defectName + ". Defect was closed automatically");
        closeDefectBody.setClosed(true);
        AllureSpecs allureSpecs = new AllureSpecs();
        Response response = given().spec(allureSpecs.initialRequestSpec())
            .queryParam("projectId", ALLURE_PROJECT_ID)
            .pathParam("id", defectId)
            .contentType(JSON)
            .body(closeDefectBody)
            .when().patch("/defect/{id}");
        response.then().spec(allureSpecs.okResponseSpec());
        logger.info("The defect has been closed");
    }


}
