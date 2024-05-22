package com.example.tasktracker.mapper;

public interface Mapper<S, D> {

	D map(S sourceObject);
}
