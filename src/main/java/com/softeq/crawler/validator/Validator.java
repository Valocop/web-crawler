package com.softeq.crawler.validator;

public interface Validator<T> {
    ResultValidator validate(T param);
}
