package com.library.services.core.impl;

import com.google.inject.Inject;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dto.Book;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.statement.UnableToCreateStatementException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;

import javax.ws.rs.WebApplicationException;

@Slf4j
public class BookManagerImpl {
    private final BookDAO bookDAO;

    @Inject
    public BookManagerImpl(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public Object viewBook(Integer book_id) {
        if (book_id == null) {
            return bookDAO.findAll();
        } else {
            return bookDAO.findById(book_id);
        }
    }

    public int addBook(Book book) {
        try {
            return bookDAO.create(book);
        } catch (UnableToExecuteStatementException | UnableToCreateStatementException e) {
            log.error("Unable to execute statement");
            throw new WebApplicationException("Unable to execute statement", 500);
        }
    }
}
