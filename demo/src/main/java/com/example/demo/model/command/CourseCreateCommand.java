package com.example.demo.model.command;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseCreateCommand implements Serializable {
    @NotBlank
    private String code;
    @NotBlank
    private String name;
    private String description;
    private Instant from;
    private Instant to;
}
