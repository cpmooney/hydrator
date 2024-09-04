package com.moondog.labs;

import com.moondog.labs.hydration.Hydratable;
import com.moondog.labs.hydration.HydrateFrom;

import java.util.List;
import java.util.UUID;

public class Order implements Hydratable {
    public UUID id;

    public List<UUID> bookIds;

    @HydrateFrom("bookIds")
    public List<Book> bookList;
}
