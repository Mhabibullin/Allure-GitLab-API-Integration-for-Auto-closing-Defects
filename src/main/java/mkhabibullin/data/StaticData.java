package mkhabibullin.data;

public class StaticData {

    public static final String GITLAB_API_KEY = System.getenv("GITLAB_API_KEY");
    public static final String GITLAB_BASE_URL = System.getenv("GITLAB_BASE_URL");
    public static final String ALLURE_ENDPOINT = System.getenv("ALLURE_BASE_URL");
    public static final String ALLURE_TOKEN = System.getenv("ALLURE_API_KEY");
    public static final String GITLAB_PROJECT = "Your Gitlab project name";

    public static final int ALLURE_PROJECT_ID = 1;
    // Your Allure project id

}
