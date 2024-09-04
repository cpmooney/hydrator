package com.moondog.labs.hydration;

import java.util.Collection;

@FunctionalInterface
public interface HydrationDelegate {
    Collection<HydratedObject> hydrate(Collection<String> ids);
}
