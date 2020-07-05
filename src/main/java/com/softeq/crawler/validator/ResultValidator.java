package com.softeq.crawler.validator;

import lombok.extern.log4j.Log4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j
public class ResultValidator {
    private final Map<String, List<String>> exceptionMap = new HashMap<>();

    public boolean isValid() {
        if (exceptionMap.isEmpty()) {
            log.info("Result of validation is valid");
            return true;
        } else {
            log.info("[" + exceptionMap.toString() + "Result of validation is not valid");
            return false;
        }
    }

    void addException(String key, List<String> exceptions) {
        exceptionMap.put(key, exceptions);
    }

    public Map<String, List<String>> getExceptionMap() {
        return exceptionMap;
    }
}
