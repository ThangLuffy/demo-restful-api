package com.example.demo.model.criteria;

import com.example.demo.model.criteria.common.DefaultCriteria;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
public class CourseCriteria extends DefaultCriteria {
    private String query;
    private Instant from;
    private Instant to;
}
