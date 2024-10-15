package com.martinpc.projects.persistence.integration.audiodb.service;

public interface IConversorData {

  <T> T getData(String json, Class<T> clase);
}
