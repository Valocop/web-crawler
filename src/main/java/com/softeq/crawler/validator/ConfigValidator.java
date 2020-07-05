package com.softeq.crawler.validator;

import com.softeq.crawler.config.Config;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Log4j
public class ConfigValidator implements Validator<Config> {
    @Override
    public ResultValidator validate(Config param) {
        ResultValidator rv = new ResultValidator();
        if (param == null) {
            rv.addException("Config", Collections.singletonList("Config is null"));
            log.warn("Config is null");
        } else {
            validateUrl(param.getRootUrl(), rv);
            validateHeight(param.getHeight(), rv);
            validatePagesLimit(param.getPagesLimit(), rv);
            validateTags(param.getTags(), rv);
            validatePath(param.getPath(), rv);
            validateFileName(param.getFileName(), rv);
            validateFileName(param.getTopFileName(), rv);
            validateTopLimit(param.getTopLimit(), rv);
        }
        return rv;
    }

    private void validateUrl(String url, ResultValidator rv) {
        if (url == null) {
            rv.addException("Url", Collections.singletonList("Url is null"));
            log.warn("Url is null");
        } else {
            try {
                Jsoup.connect(url).get();
                log.info("url is valid " + url);
            } catch (IOException e) {
                rv.addException("Url", Collections.singletonList("url " + url + " is not valid"));
                log.warn("Url " + url + " is not valid", e);
            }
        }
    }

    private void validateHeight(int height, ResultValidator rv) {
        if (height < 0) {
            rv.addException("Height", Collections.singletonList("Height is below zero"));
            log.warn("Height is below zero " + height);
        } else log.info("Height is valid " + height);
    }

    private void validatePagesLimit(long limit, ResultValidator rv) {
        if (limit <= 0) {
            rv.addException("PagesLimit", Collections.singletonList("Pages limit is incorrect " + limit));
            log.warn("PagesLimit is incorrect " + limit);
        } else log.info("PagesLimit is valid " + limit);
    }

    public void validateTags(List<String> tags, ResultValidator rv) {
        if (tags == null || tags.isEmpty()) {
            rv.addException("Tags", Collections.singletonList("Tags is empty"));
            log.warn("Tags is empty");
        } else log.info("Tags is valid");
    }

    public void validatePath(String path, ResultValidator rv) {
        if (path == null) {
            rv.addException("Path", Collections.singletonList("Path is null"));
            log.warn("Path is null");
        } else {
            try {
                Path.of(path);
                log.info("Path is valid");
            } catch (Exception e) {
                rv.addException("FilePath", Collections.singletonList("Path is not valid " + path));
                log.warn("Path is not valid", e);
            }
        }
    }

    public void validateFileName(String fileName, ResultValidator rv) {
        if (fileName == null || fileName.isEmpty()) {
            rv.addException("FileName", Collections.singletonList("FileName is empty"));
            log.warn("FileName is empty");
        } else log.info("FileName is valid " + fileName);
    }

    public void validateTopLimit(int limit, ResultValidator rv) {
        if (limit <= 0) {
            rv.addException("TopLimit", Collections.singletonList("TopLimit is not valid " + limit));
            log.warn("TopLimit is not valid " + limit);
        } else log.info("TopLimit is valid " + limit);
    }
}
