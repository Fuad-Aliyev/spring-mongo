package com.tutorial.service.impl;

import com.tutorial.model.StatePoulation;
import com.tutorial.service.StatePopulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatePopulationServiceImpl implements StatePopulationService {
    private final MongoTemplate mongoTemplate;

    @Override
    public StatePoulation getSmallestState() {
        GroupOperation sumTotalCityPop = Aggregation.group("state", "city").sum("pop").as("cityPop");
        GroupOperation averageStatePop = Aggregation.group("_id.state").avg("cityPop").as("avgCityPop");
        SortOperation sortByAvgPopAsc = Aggregation.sort(Sort.by(Sort.Direction.ASC, "avgCityPop"));
        LimitOperation limitToOnlyFirstDoc = Aggregation.limit(1);
        ProjectionOperation projectToMatchModel = Aggregation.project()
                .andExpression("_id").as("state")
                .andExpression("avgCityPop").as("statePop");

        Aggregation aggregation = Aggregation.newAggregation(sumTotalCityPop, averageStatePop, sortByAvgPopAsc,
                limitToOnlyFirstDoc, projectToMatchModel);

        AggregationResults<StatePoulation> result = mongoTemplate.aggregate(aggregation, "zips", StatePoulation.class);
        StatePoulation smallestState = result.getUniqueMappedResult();
        return smallestState;
    }

    @Override
    public Document getStateWithMaxAndMinimumZipCodes() {
        GroupOperation sumZips = Aggregation.group("state").count().as("zipCount");
        SortOperation sortByCount = Aggregation.sort(Sort.Direction.ASC, "zipCount");
        GroupOperation groupFirstAndLast = Aggregation.group().first("_id").as("minZipState")
                .first("zipCount").as("minZipCount").last("_id").as("maxZipState")
                .last("zipCount").as("maxZipCount");

        Aggregation aggregation = Aggregation.newAggregation(sumZips, sortByCount, groupFirstAndLast);

        AggregationResults<Document> result = mongoTemplate
                .aggregate(aggregation, "zips", Document.class);
        Document document= result.getUniqueMappedResult();
        return document;
    }
}
