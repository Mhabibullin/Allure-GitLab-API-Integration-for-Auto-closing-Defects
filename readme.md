# Allure TestOps and GitLab API integration for auto-closing Allure Defects

This integration project extends built-in Allure TestOps functionality and helps teams to automate the process of closing 
Allure defects linked with GitLab. On each test launch this integration will automatically search for an opened 
defects in Allure TestOps with linked issue in GitLab and if issue is resolved and closed in GitLab, the 
Allure defect will also be automatically closed.

It was made as an example of my working with the test framework as QA Automation. 

## How to Use

1. Clone this repository to your local machine
2. Set the following environment variables in your system or in your IDE:

```bash
* ALLURE_API_KEY (example: `Api-Token 00000000-0000-0000-0000-000000000000`)
* ALLURE_BASE_URL (example: `https://your.url/api/rs`)
* GITLAB_API_KEY (example: `00000000000000000000`)
* GITLAB_BASE_URL (example: `https://your.url`)
```
3. Insert your custom values into static strings in `src/main/java/mkhabibullin/data/StaticData.java`
```bash
* GITLAB_PROJECT - Your Gitlab project name
* ALLURE_PROJECT_ID - Your Allure project ID
```
4. Build and run example test execution using the command:
```bash
mvn test
```
5. Process and result will be displayed in the console logs


