package com.tutorial.service.impl;

import com.tutorial.model.GroceryItem;
import com.tutorial.model.User;
import com.tutorial.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<User> find() {
        Query query = new Query();
        query.fields().include("name").exclude("id");
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public void aggregate() {
        MatchOperation matchStage = Aggregation.match(new Criteria("quantity").gt(2));
        ProjectionOperation projectionStage = Aggregation.project("id", "name");

        Aggregation aggregation = Aggregation.newAggregation(matchStage, projectionStage);

        AggregationResults<GroceryItem> result =
                mongoTemplate.aggregate(aggregation, "groceryitems", GroceryItem.class);

    }
}
