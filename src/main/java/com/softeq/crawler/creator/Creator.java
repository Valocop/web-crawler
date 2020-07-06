package com.softeq.crawler.creator;

import java.io.IOException;
import java.util.List;

public interface Creator<T> {
    T create(List<String> params) throws IOException;
}
