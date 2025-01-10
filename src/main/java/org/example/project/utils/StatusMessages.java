package org.example.project.utils;


public class StatusMessages {
    public static final String UNKNOWN_ERROR = "Unknown error!";
    public static final String ENTITY_NOT_FOUND = "Entity not found!";
    public static final String MANAGER_NOT_FOUND = "Manager not found!";
    public static final String COMPANY_NOT_FOUND = "Company not found!";
    public static final String COST_CENTER_NOT_FOUND = "Cost Center not found!";
    public static final String COST_CENTER_INITIAL_NOT_FOUND = "Initial Cost Center not found!";
    public static final String COST_CENTER_FINAL_NOT_FOUND = "Final Cost Center not found!";
    public static final String DIVISION_NOT_FOUND = "Division not found!";
    public static final String DEPARTMENT_NOT_FOUND = "Department not found!";
    public static final String INVENTORY_NOT_FOUND = "Inventory not found!";
    public static final String ASSET_NOT_FOUND = "Asset not found!";
    public static final String MANAGER_EXISTS = "Managers cannot have more than 1 company!";

    public static String entityNotFound(Long id) {
        return String.format("Entity with id: %s not found!", id);
    }

    public static String entityDeleted(String entityName) {
        return String.format("%s deleted successfully!", entityName);
    }

    public static String entitiesDeleted(Integer count, String entityName) {
        if (count == null || count == 0) {
            return String.format("No %s deleted.", entityName);
        }
        return String.format("%d %s(s) deleted successfully!", count, entityName);
    }

}
