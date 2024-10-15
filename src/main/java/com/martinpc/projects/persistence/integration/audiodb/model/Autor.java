package com.martinpc.projects.persistence.integration.audiodb.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Autor(
    @JsonAlias("name") String name,
    @JsonAlias("birth_year") String birthDate
) {

}
