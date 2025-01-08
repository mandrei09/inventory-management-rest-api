package org.example.project.utils.interfaces;

public interface IAppUtils {

    public default String trimStringOrNull(String value) {
        return value != null ? value.trim() : null;
    }

    public default boolean stringIsNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

}
