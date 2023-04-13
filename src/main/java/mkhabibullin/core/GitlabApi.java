package mkhabibullin.core;

import org.apache.log4j.Logger;
import org.gitlab.api.GitlabAPI;
import org.gitlab.api.TokenType;
import org.gitlab.api.models.GitlabIssue;

import static mkhabibullin.data.StaticData.*;


public class GitlabApi {
    private static final Logger logger = Logger.getLogger(GitlabApi.class);
    private GitlabAPI gitlabAPI;
    private GitlabIssue gitLabIssue;

    public void getApi() {

        TokenType tokenType = TokenType.PRIVATE_TOKEN;
        logger.info("Connecting to Gitlab api");
        this.gitlabAPI = GitlabAPI.connect(GITLAB_BASE_URL, GITLAB_API_KEY, tokenType);
    }

    public void gitLabIssue(String projectName, String numberIssue) {
        if (numberIssue != null && !numberIssue.isEmpty()) {
            try {
                int projectId = gitlabAPI.getProject(GITLAB_PROJECT, projectName).getId();
                this.gitLabIssue = gitlabAPI.getIssue(projectId, Integer.parseInt(numberIssue));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GitlabIssue getGitLabIssue() {
        return gitLabIssue;
    }

}
