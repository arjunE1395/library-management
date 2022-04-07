package com.library.services.core.impl;

import com.google.inject.Inject;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dao.IssueDAO;
import com.library.services.db.dao.UserDAO;
import com.library.services.db.dto.Book;
import com.library.services.db.dto.Issue;
import com.library.services.managed.DistributedCacheProvider;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMap;

import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class IssueManagerImpl {
    private final IssueDAO issueDAO;
    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private final DistributedCacheProvider distributedCacheProvider;
    private RMap<String, List<Book>> userBookRMap;

    @Inject
    public IssueManagerImpl(IssueDAO issueDAO, BookDAO bookDAO, UserDAO userDAO, DistributedCacheProvider distributedCacheProvider) {
        this.issueDAO = issueDAO;
        this.bookDAO = bookDAO;
        this.userDAO = userDAO;
        this.distributedCacheProvider = distributedCacheProvider;
        userBookRMap = distributedCacheProvider.getUserBookMap();
    }

    public String issueBook(Issue issue) {
        validate(issue);
        isBookAvailable(issue);
        String issue_id = issueDAO.create(issue);
        userBookRMap.remove(issue.getUserID());
        return issue_id;
    }

    public void returnBook(String issueID) {
        Issue issue = isIssueAvailable(issueID);
        issueDAO.delete(issue.getId());
        userBookRMap.remove(issue.getUserID());
    }

    public List<Book> getBooksIssuedByUser(String userID) {
        userDAO.findById(userID)
                .orElseThrow(() -> {
                    log.info("Invalid User");
                    throw new WebApplicationException("Invalid User",400);
                });

        return userBookRMap.computeIfAbsent(
                userID,
                bookList ->
                     issueDAO.findBookByUserId(userID)
                             .stream().map(bookID -> bookDAO.findById(bookID).get())
                             .collect(Collectors.toList())
        );
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

    private Issue isIssueAvailable(String issueID) {
        return issueDAO.findById(issueID)
                .orElseThrow(() -> {
                    log.info("Invalid Issue");
                    throw new WebApplicationException("Invalid Issue",400);
                });
    }
}
