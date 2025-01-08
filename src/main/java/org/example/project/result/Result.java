package org.example.project.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Result<T> {
    protected boolean success = true;
    protected String message = null;
    protected T object = null;

    public Result<T> entityNotFound(String entityName) {
        if (entityName != null) {
            if (this.message != null && !this.message.isEmpty()) {
                this.message += "\n";
            }

            this.message = (this.message == null ? "" : this.message) + String.format("%s not found!", entityName);
        }

        this.success = false;
        this.object = null;

        return this;
    }

    public Result<T> entityFound(T object) {
        this.message = null;
        this.success = true;
        this.object = object;

        return this;
    }
}
