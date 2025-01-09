package org.example.project.utils;

import java.util.Arrays;

public enum InventoryStatus {
    NOT_STARTED,
    NOT_FINISHED,
    FINISHED;

    public static InventoryStatus getValueByString(String paymentStatus) {
        return Arrays.stream(InventoryStatus.values())
                .filter(p -> p.name().equals(paymentStatus))
                .findAny()
                .orElse(null);
    }
}
