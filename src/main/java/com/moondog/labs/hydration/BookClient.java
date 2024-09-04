package com.moondog.labs.hydration;

import java.util.Collection;

public interface BookClient {
    Collection<String> getBooksById(Collection<String> ids);
}
