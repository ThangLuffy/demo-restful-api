package com.example.demo.domain;

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@Table("course")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Course implements Serializable {
    @Id
    private UUID id;
    private String code;
    private String name;
    private String description;
    private Instant from;
    private Instant to;
    @CreatedDate
    private Instant createdAt;
    @CreatedBy
    private String createdBy;
    @LastModifiedDate
    private Instant modifiedAt;
    @LastModifiedBy
    private String modifiedBy;
    @Version
    private Long version;
}
