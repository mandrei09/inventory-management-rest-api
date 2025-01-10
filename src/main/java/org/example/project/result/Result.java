package org.example.project.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {
    @Schema(description = "Indicates whether the operation was successful.", example = "true")
    protected boolean success = false;

    @Schema(description = "Message providing additional information on the result.", example = "Entity found successfully")
    protected List<String> messages = new ArrayList<>();

    @Schema(description = "The object returned by the operation, if successful.")
    protected T object = null;

    public Result<T> addToMessages(String message) {
        messages.add(message);
        return this;
    }

    @Schema(description = "Sets the result as entity not found with an optional error message.")
    public Result<T> entityNotFound(List<String> errorMessages) {
        this.messages.addAll(errorMessages);
        this.success = false;
        this.object = null;

        return this;
    }

    @Schema(description = "Sets the result as entity not found with an optional error message.")
    public Result<T> entityNotFound(String errorMessage) {
        this.messages.add(errorMessage);
        this.success = false;
        this.object = null;

        return this;
    }

    @Schema(description = "Sets the result as entity not found with an optional error message.")
    public Result<T> entityNotFound(Long id, String errorMessage) {
        this.messages.add(String.format("Id: %d, Message: %s", id, errorMessage));
        this.success = false;
        this.object = null;

        return this;
    }

    @Schema(description = "Sets the result as entity found with the given object.")
    public Result<T> entityFound(T object) {
        this.messages = new ArrayList<>();
        this.success = true;
        this.object = object;

        return this;
    }
}
