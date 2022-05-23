package com.example.demo.model.view;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseView implements Serializable {
    private String id;
    private String code;
    private String name;
    private String description;
    private Instant from;
    private Instant to;
    private Instant createdAt;
    private String createdBy;
    private Instant modifiedAt;
    private String modifiedBy;
    private Long version;
}
