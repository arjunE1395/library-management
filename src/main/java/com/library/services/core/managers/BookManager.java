package com.library.services.core.managers;

import com.library.services.db.dto.Book;

public interface BookManager {
    Object viewBook(Integer book_id);
    int addBook(Book book);
}
