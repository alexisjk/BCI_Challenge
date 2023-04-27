package com.bci.mapper;

public interface Mapper<Source, Target> {
    Target toDto(Source source);
    Source toEntity(Target target);
}