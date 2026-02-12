package com.example.mealsplanner.util;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiffUtil {

    public static <E> boolean isDifferentById(
            List<E> oldList,
            List<E> newList,
            Function<E, String> idProvider
    ) {
        if (oldList == null || newList == null) return true;
        if (oldList.size() != newList.size()) return true;

        Set<String> oldIds = oldList.stream()
                .map(idProvider)
                .collect(Collectors.toSet());

        Set<String> newIds = newList.stream()
                .map(idProvider)
                .collect(Collectors.toSet());

        return !oldIds.equals(newIds);
    }
}
