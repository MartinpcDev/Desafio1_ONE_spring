package com.martinpc.projects.persistence.integration.audiodb.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Book(
    @JsonAlias("title") String title,
    @JsonAlias("authors") List<Autor> autors,
    @JsonAlias("languages") List<String> languages,
    @JsonAlias("download_count") Double downloads
) {

}
