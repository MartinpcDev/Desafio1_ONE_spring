package com.martinpc.projects.persistence.integration.audiodb.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookReponse(
    @JsonAlias("results") List<Book> results
) {

}
