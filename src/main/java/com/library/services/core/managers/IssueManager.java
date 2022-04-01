package com.library.services.core.managers;

import com.library.services.db.dto.Issue;

import java.util.List;

public interface IssueManager {
    int issueBook(Issue issue);
    void returnBook(Integer issueID);
    List<Issue> getBooksIssuedByUser(Integer userID);
}
