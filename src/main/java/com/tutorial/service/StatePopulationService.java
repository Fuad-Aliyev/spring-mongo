package com.tutorial.service;

import com.tutorial.model.StatePoulation;
import org.springframework.data.mongodb.core.mapping.Document;

public interface StatePopulationService {
    StatePoulation getSmallestState();

    Document getStateWithMaxAndMinimumZipCodes();
}
