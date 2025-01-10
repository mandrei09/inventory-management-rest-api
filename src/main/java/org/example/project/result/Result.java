package org.example.project.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {
    @Schema(description = "Indicates whether the operation was successful.", example = "true")
    protected boolean success = false;

    @Schema(description = "Message providing additional information on the result.", example = "Entity found successfully")
    protected String message = null;

    @Schema(description = "The object returned by the operation, if successful.")
    protected T object = null;

    @Schema(description = "Sets the result as entity not found with an optional error message.")
    public Result<T> entityNotFound(String errorMessage) {
        this.message = (this.message == null ? errorMessage : this.message + "\n" + errorMessage);
        this.success = false;
        this.object = null;

        return this;
    }

    @Schema(description = "Sets the result as entity found with the given object.")
    public Result<T> entityFound(T object) {
        this.message = null;
        this.success = true;
        this.object = object;

        return this;
    }
}
