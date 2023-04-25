# Allure TestOps and GitLab API integration for auto-closing Allure Defects

This integration project extends built-in Allure TestOps functionality and helps teams to automate the process of closing 
Allure defects linked with GitLab. On each test launch this integration will automatically search for an opened 
defects in Allure TestOps with linked issue in GitLab and if issue is resolved and closed in GitLab, the 
Allure defect will also be automatically closed.

It was made as an example of my working with the test framework as SDET. 

## How to Use

1. Clone this repository to your local machine
2. Set the following environment variables in your system or in your IDE:

```bash
* ALLURE_API_KEY: Your Allure API key
* ALLURE_BASE_URL: The base URL of your Allure instance
* GITLAB_API_KEY: Your GitLab API key
* GITLAB_BASE_URL: The base URL of your GitLab instance
```
3. Build and run test launch using the command:
```bash
mvn test
```
4. Look at the result in console
