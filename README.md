# Inventory Management API

## Project Overview

**Inventory Management** is a REST API designed for the inventory and management of fixed assets across multiple companies. This API can be easily used to conduct an inventory. Managing company employees, as well as their offices, is also facilitated through **Inventory Management**.

## Features

### Business Rules Implemented:

- **Administrative Structure**: The API orchestrates a complex administrative structure, specific to each company.
- **Employee Structure**: Each employee belongs to a cost center, which in turn belongs to a division -> department -> company. Each of these entities has a manager.
- **Adding Employees**: Only employees with a valid email structure can be added to the database through the API.
- **Inventory Structure**: An inventory is managed by a single company for the purpose of cataloging its fixed assets.
- **Adding an Inventory**: When a company adds an inventory, all fixed assets already existing in the platform will be added, using data from the last inventory conducted.
- **Data from the Last Inventory**: The location of the fixed asset in the new inventory will correspond to the last location where the asset was found. The same applies to the quantity found.
- **Inventory Details**: The API exposes details about the progress of the inventory only if it is currently ongoing.
- **Soft Delete**: Any entity can be masked so that it remains in the database but is ignored by all API operations.
- **Hard Delete**: The API has a mechanism that allows for the deletion of all soft-deleted data for an entity that has a creation date later than an input date.
- **Location Management**: When any higher-level location is deleted, the effect will propagate down to the fixed assets associated with it.
## Minimum Viable Product (MVP)

For the **MVP phase**, the following core features have been implemented based on the business requirements:
### 1. Employee Onboarding and Cost Center Assignment

- **Objective**: Ensuring the employees are correctly associated with cost centers
- **Key Features**:
    - Endpoint for adding new employees to the database, with required details such as name, email.
    - Employees must be linked to a specific cost center (then division, department, and company).
- **Business Logic**:
    - Each employee must be linked to a valid cost center, which belongs to a division and department, all within a company.
    - Email validation is crucial to ensure the correct format.
### 2. Inventory Management and Asset Tracking

- **Objective**: Enable companies to manage inventories of their fixed assets and track the history of their assets across different locations.
- **Key Features**:
    - Endpoint for adding new inventories linked to a specific company.
    - Inventory records include data from the most recent inventory of the fixed assets.
    - Ability to track asset location and quantities across different inventories.
- **Business Logic**:
    - When a company adds a new inventory, all assets from the last inventory are added with their updated location and quantity.
    - The location of the asset in the new inventory will correspond to the last known location.

### 3. Soft Delete and Hard Delete Mechanisms

- **Objective**: Provide a way to mark entities as soft deleted (hidden from the API operations) and support a hard delete process for certain data.
- **Key Features**:
    - Endpoint for soft deleting entities (employees, assets, etc.), keeping them in the database but excluding them from API operations.
    - Endpoint for hard deleting soft-deleted data that has been created before a given date.
- **Business Logic**:
    - Soft delete: Entities remain in the database but are excluded from API operations.
    - Hard delete: Permanent removal of entities based on a creation date provided by the user.

### 4. Location Management and Hierarchical Deletion Propagation

- **Objective**: Enable management of locations and ensure that when a higher-level location is deleted, the effect propagates down to associated fixed assets.
- **Key Features**:
    - Endpoint to manage locations within the company hierarchy (cost center, division, department).
    - Automatically delete assets associated with a deleted location.
- **Business Logic**:
    - When a location at any higher level (e.g., department or division) is deleted, the deletion will cascade to the fixed assets associated with that location.

### 5. Inventory Progress and Active Inventory Status

- **Objective**: Expose the current progress of ongoing inventories and ensure that only active inventories can provide updates.
- **Key Features**:
    - Endpoint for checking the status of an ongoing inventory.
    - Expose details only for inventories that are currently in progress.
- **Business Logic**:
    - Inventory details are available only if the inventory is currently active.
    - Companies can view the progress of their inventories and update information accordingly.

## Prerequisites

### Tools and Libraries Required:
- **Java 17** or later
- **Maven** (for dependency management)
- **Spring Boot Framework**
- **MySQL**
- **Postman** (for API testing)
