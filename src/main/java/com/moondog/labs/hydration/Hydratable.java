package com.moondog.labs.hydration;

public interface Hydratable {
    default void hydrate(HydrationDelegate delegate) throws NoSuchFieldException, IllegalAccessException {
        Hydration.create()
                .withDelegate(delegate)
                .withFields(this)
                .hydrate();
    }
}
