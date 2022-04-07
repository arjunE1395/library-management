package com.library.services.core.impl;

import com.google.inject.Inject;
import com.library.services.db.dao.BookDAO;
import com.library.services.db.dto.Book;
import com.library.services.managed.DistributedCacheProvider;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.statement.UnableToCreateStatementException;
import org.jdbi.v3.core.statement.UnableToExecuteStatementException;
import org.redisson.api.RMap;

import javax.ws.rs.WebApplicationException;

@Slf4j
public class BookManagerImpl {
    private final BookDAO bookDAO;
    private final DistributedCacheProvider distributedCacheProvider;
    private RMap<String, Book> bookRMap;

    @Inject
    public BookManagerImpl(BookDAO bookDAO, DistributedCacheProvider distributedCacheProvider) {
        this.bookDAO = bookDAO;
        this.distributedCacheProvider = distributedCacheProvider;
        bookRMap = this.distributedCacheProvider.getBookMap();
    }

    public Object viewBook(String book_id) {
        if (book_id == null) {
            return bookDAO.findAll();
        } else {
            return bookRMap.computeIfAbsent(
                    book_id,
                    book -> bookDAO.findById(book_id).orElseThrow(() -> {
                        log.info("Invalid Book-ID");
                        throw new WebApplicationException("Invalid Book-ID : " + book_id,400);
                    })
            );
        }
    }

    public String addBook(Book book) {
        try {
            String book_id = bookDAO.create(book);
            bookRMap.remove(book_id);
            return book_id;
        } catch (UnableToExecuteStatementException | UnableToCreateStatementException e) {
            log.error("Unable to execute statement");
            throw new WebApplicationException("Unable to execute statement", 500);
        }
    }
}
