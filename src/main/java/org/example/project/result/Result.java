package org.example.project.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {

    private final String DEFAULT_ERROR_MESSAGE = "Unknown Error!";

    protected boolean success = false;
    protected String message = DEFAULT_ERROR_MESSAGE;
    protected T object = null;

    public Result<T> entityNotFound(String errorMessage) {
        if (errorMessage != null) {
            if (this.message != null && !this.message.equals(DEFAULT_ERROR_MESSAGE)) {
                this.message += "\n";
            }

            this.message = (this.message.equals(DEFAULT_ERROR_MESSAGE) ? "" : this.message);
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
