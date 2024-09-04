package com.moondog.labs.hydration;

import java.lang.reflect.Field;
import java.util.Collection;

public class HydrationField {
    public static HydrationField of(Field field, Collection<String> ids) {
        HydrationField hydrationField = new HydrationField();
        hydrationField.ids = ids;
        hydrationField.field = field;
        return hydrationField;
    }

    public void hydrate(Collection<HydratedObject> objects) throws IllegalAccessException {
        Collection<HydratedObject> myObjects = objects.stream()
                                                      .filter(object -> ids.contains(object.id))
                                                      .toList();
        field.set(hydratable, objects);
    }

    static HydrationField getHydratedFieldFrom(Hydratable hydratable, Field field) throws NoSuchFieldException, IllegalAccessException {
        HydrateFrom hydrateFrom = field.getAnnotation(HydrateFrom.class);
        Field idField = hydratable.getClass()
                  .getField(hydrateFrom.value());
        Object fieldValue = field.get(hydratable);
        if (fieldValue instanceof Collection) {
            Collection<String> ids = (Collection<String>) fieldValue;
            return HydrationField.of(field, ids);
        }
        throw new IllegalArgumentException("Field must be a Collection");
    }

    Collection<String> ids;
    private Field field;
    private Hydratable hydratable;
}
