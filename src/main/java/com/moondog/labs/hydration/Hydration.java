package com.moondog.labs.hydration;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Hydration {
    public static Hydration create() {
        return new Hydration();
    }

    public Hydration withDelegate(HydrationDelegate hydrationDelegate) {
        this.hydrationDelegate = hydrationDelegate;
        return this;
    }

    public Hydration withFields(Hydratable hydratable) throws NoSuchFieldException, IllegalAccessException {
        this.hydrationFields = List.of();
        for (Field field : getHydrateFromFields(hydratable)) {
                hydrationFields.add(HydrationField.getHydratedFieldFrom(hydratable, field));
        }
        return this;
    }

    public void hydrate() throws IllegalAccessException {
        Collection<HydratedObject> hydratedObjects = hydrationDelegate.hydrate(allIds());
        for (HydrationField hydrationField : hydrationFields) {
            hydrationField.hydrate(hydratedObjects);
        }
    }

    private HydrationDelegate hydrationDelegate;
    private List<HydrationField> hydrationFields;

    private Set<String> allIds() {
        return hydrationFields.stream().flatMap(
                hydrationField -> hydrationField.ids.stream()
        ).collect(Collectors.toSet());
    }

    private static Collection<Field> getHydrateFromFields(Hydratable hydratable) {
        return Stream.of(hydratable.getClass().getDeclaredFields())
                     .filter(field -> field.isAnnotationPresent(HydrateFrom.class))
                     .toList();
    }
}
