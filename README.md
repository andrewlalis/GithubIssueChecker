# GithubIssueChecker
Check if a set of repositories have open issues, and get a sneak peek of their contents.

Uses a credentials file called `credentials.txt`, and a list of repositories in `repositories.txt`. When you first boot up the program, these files will be generated based on your input. If you would like to update the data in these files, feel free to, but please adhere to the following formatting:

### `credentials.txt`
This file either has the following format:
```
token:myLongGithubApiTokenHere

```
or
```
username:myGithubUsername
password:myGithubPassword

```

Notice the newline at the end of the file.

### `repositories.txt`
This file has the following format:
```
organization:myGithubOrganizationName
repoName1
repoName2
repoName3

```