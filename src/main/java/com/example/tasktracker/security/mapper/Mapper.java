package com.example.tasktracker.security.mapper;

public interface Mapper<S, D> {

	D map(S sourceObject);
}
