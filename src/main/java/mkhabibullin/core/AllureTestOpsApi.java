package mkhabibullin.core;

import mkhabibullin.models.Content;
import mkhabibullin.models.DefectPatchDto;
import mkhabibullin.models.DefectResponse;
import org.apache.log4j.Logger;
import org.gitlab.api.models.GitlabIssue;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        } catch (Exception | AssertionError e) {
            logger.error("Checking failed, reason:");
            e.printStackTrace();
        }
    }

    private DefectResponse getGitlabIssuesListOfOpenedDefects() throws Exception {
        logger.info("Getting a list of open defects from the AllureTestOps API");
        ApiExecution apiExecution = new ApiExecution();
        Header header = new Header();
        header.append("Accept", "application/json");
        header.append("Authorization", ALLURE_TOKEN);
        String result = apiExecution.executeGetJson(ALLURE_ENDPOINT + "/defect", "projectId=" + ALLURE_PROJECT_ID + "&status=open",
                header);
        JsonHelper jsonHelper = new JsonHelper();
        DefectResponse defectResponse = (DefectResponse) jsonHelper.readValue(result, DefectResponse.class);
        ArrayList<Content> rawContent = defectResponse.getContent();
        ArrayList<Content> contentOnlyWithUrls = new ArrayList<>();
        for (Content content : rawContent) {
            try {
                content.getIssueCleanUrl();
                contentOnlyWithUrls.add(content);
            } catch (NullPointerException ignore) {
            }
        }
        defectResponse.setContent(contentOnlyWithUrls);
        return defectResponse;
    }


    private void closeAllureDefect(String defectId, String defectName) throws Exception {
        logger.info("Found open defects with a closed issue, close the defect: " + defectId);
        DefectPatchDto closeDefectBody = new DefectPatchDto(defectName + ". Defect has been closed automatically", true);
        ApiExecution apiExecution = new ApiExecution();
        Header header = new Header();
        header.append("Accept", "application/json");
        header.append("Authorization", ALLURE_TOKEN);
        JsonHelper jsonHelper = new JsonHelper();
        JSONObject body = jsonHelper.toJSONObject(closeDefectBody);
        apiExecution.executePatchJson(ALLURE_ENDPOINT + "/defect/" + defectId, "projectId=" + ALLURE_PROJECT_ID,
                header, body.toString());
        logger.info("The defect has been closed");
    }


}
