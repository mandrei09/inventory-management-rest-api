package org.example.project.utils.interfaces;

import java.util.Date;

public interface IAppUtils {

    public default String trimStringOrNull(String value) {
        return value != null ? value.trim() : null;
    }

    public default boolean stringIsNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public default boolean dateBefore(Date date) {
        return dateBefore(date, new Date());
    }

    public default boolean dateBefore(Date date1, Date date2) {
        return date1 != null && date2 != null && date2.before(date1);
    }

    public default boolean dateAfter(Date date) {
        return dateAfter(date, new Date());
    }

    public default boolean dateAfter(Date date1, Date date2) {
        return date1 != null && date2 != null && date2.after(date1);
    }

}
