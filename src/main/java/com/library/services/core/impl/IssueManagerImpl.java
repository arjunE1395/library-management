package com.library.services.core.impl;

import com.library.services.core.managers.IssueManager;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dao.IssueDAO;
import com.library.services.db.dao.UserDAO;
import com.library.services.db.dto.Issue;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.WebApplicationException;
import java.util.List;

@Slf4j
public class IssueManagerImpl implements IssueManager {
    private final IssueDAO issueDAO;
    private final BookDAO bookDAO;
    private final UserDAO userDAO;

    public IssueManagerImpl(IssueDAO issueDAO, BookDAO bookDAO, UserDAO userDAO) {
        this.issueDAO = issueDAO;
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
    }

    @Override
    public int issueBook(Issue issue) {
        validate(issue);
        isBookAvailable(issue);
        return issueDAO.create(issue);
    }

    @Override
    public void returnBook(Integer issueID) {
        isIssueAvailable(issueID);
        issueDAO.delete(issueID);
    }

    @Override
    public List<Issue> getBooksIssuedByUser(Integer userID) {
        userDAO.findById(userID)
                .orElseThrow(() -> {
                    log.info("Invalid User");
                    throw new WebApplicationException("Invalid User",400);
                });

        return issueDAO.findByUserId(userID);
    }

    private void validate(Issue issue) {
        bookDAO.findById(issue.getBookID())
                .orElseThrow(() -> {
                    log.info("Invalid Book");
                    throw new WebApplicationException("Invalid Book",400);
                });

        userDAO.findById(issue.getUserID())
                .orElseThrow(() -> {
                    log.info("Invalid User");
                    throw new WebApplicationException("Invalid User",400);
                });
    }

    private void isBookAvailable(Issue issue) {
        if(issueDAO.findByBookId(issue.getBookID()).isPresent()) {
            log.info("Book not available");
            throw new WebApplicationException("Book not available", 400);
        }
    }

    private void isIssueAvailable(Integer issueID) {
        issueDAO.findById(issueID)
                .orElseThrow(() -> {
                    log.info("Invalid Issue");
                    throw new WebApplicationException("Invalid Issue",400);
                });
    }
}
