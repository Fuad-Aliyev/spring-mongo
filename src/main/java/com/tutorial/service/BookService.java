package com.tutorial.service;

import com.tutorial.model.Book;

import java.util.List;

public interface BookService {
    void save(Book book);

    void saveAll(List<Book> books);

    List<Book> findAll();

    Book findById(Integer id);

    void findAndUpdate();

    void findAndRemove();

    void upsert();
}
