# Integration for Allure TestOps and GitLab via API for Auto-closing Allure Defects

This is a simple Java Maven TestNG integration that connects Allure TestOps and GitLab via API for automatically closing 
Allure defects.

It was made as an example of my working with the test framework as SDET.

This integration project extends built-in Allure TestOps functionality helps teams to automate the process of closing 
Allure defects linked with GitLab. On each test launch this integration will automatically search for an opened 
defects in Allure TestOps with linked issue in GitLab and if issue is resolved and closed in GitLab, the 
Allure defect will also be automatically closed.

## How to Use

To use this integration, you need to follow these steps:

1. Clone this repository to your local machine
2. Set the following environment variables in your system or in your IDE:

```bash
* ALLURE_API_KEY: Your Allure API key
* ALLURE_BASE_URL: The base URL of your Allure instance
* GITLAB_API_KEY: Your GitLab API key
* GITLAB_BASE_URL: The base URL of your GitLab instance
```
4. Build and run test launch using the command:
```bash
mvn test
```
## Contributing

Contributions to this project are welcome. If you find a bug or have an idea for a new feature, please open an issue or
submit a pull request.