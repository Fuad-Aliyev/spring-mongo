package com.tutorial.service.impl;

import com.tutorial.model.Book;
import com.tutorial.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final MongoTemplate mongoTemplate;

    @Override
    public void save(Book book) {
        mongoTemplate.save(book);
    }

    @Override
    public void saveAll(List<Book> books) {
        mongoTemplate.insert(books);
    }

    @Override
    public List<Book> findAll() {
        return mongoTemplate.findAll(Book.class);
    }

    @Override
    public Book findById(Integer id) {
        return mongoTemplate.findById(id, Book.class);
    }

    @Override
    public void findAndUpdate() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(501));
        Update update = new Update();
        update.set("cost", 1065.25);
        update.set("name", "Core Java");

        mongoTemplate.findAndModify(query, update, Book.class);
    }

    @Override
    public void findAndRemove() {
        Query query= new Query();
        query.addCriteria(Criteria.where("cost").gte(1000.0));
        mongoTemplate.findAndRemove(query, Book.class);
    }

    @Override
    public void upsert() {
        Query query= new Query();
        query.addCriteria(Criteria.where("id").is(510));
        Update update = new Update();
        update.set("cost", 1065.25);
        update.set("name", "Core Java");
        mongoTemplate.upsert(query, update, Book.class);
    }


}
