package com.abcfitness.glofox.coaching;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ClassDTO {

    @NotNull(message = "Class name is required")
    @Size(min = 3, message = "Class name must be at least 3 characters long")
    private String className;

    @NotNull(message = "Start date is required")
    private Date startDate;

    @NotNull(message = "End date is required")
    private Date endDate;

    @NotNull(message = "Capacity is required")
    private int capacity;
}

