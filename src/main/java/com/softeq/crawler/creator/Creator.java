package com.softeq.crawler.creator;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Creator<T> {
    Optional<T> create(List<String> params) throws IOException;
}
