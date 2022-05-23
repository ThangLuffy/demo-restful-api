package com.example.demo.service;

import com.example.demo.domain.Course;
import com.example.demo.model.command.CourseCreateCommand;
import com.example.demo.model.command.CourseUpdateCommand;
import com.example.demo.model.criteria.CourseCriteria;
import com.example.demo.model.problem.BadRequestProblem;
import com.example.demo.model.problem.NotFoundProblem;
import com.example.demo.model.view.CourseView;
import com.example.demo.repository.CourseRepository;
import com.sun.istack.internal.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final JdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public CourseView findOne(String code) {
        log.info("Get detail course with code '{}'", code);
        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundProblem("course_not_found", String.format("Cannot find course with code '%s'", code)));
        return convertToView(course);
    }

    @Transactional(readOnly = true)
    public Page<CourseView> filter(CourseCriteria courseCriteria) {
        log.info("Getting list courses");
        Criteria criteria = Criteria.where("code").isNotNull();
        if (StringUtils.hasText(courseCriteria.getQuery())) {
            criteria = criteria.and(Criteria.where("code").like("%" + courseCriteria.getQuery() + "%").ignoreCase(true)
                    .or("name").like("%" + courseCriteria.getQuery() + "%").ignoreCase(true));
        }
        if (courseCriteria.getFrom() != null) {
            criteria = criteria.and(Criteria.where("from").greaterThanOrEquals(courseCriteria.getFrom()));
        }
        if (courseCriteria.getFrom() != null) {
            criteria = criteria.and(Criteria.where("to").lessThanOrEquals(courseCriteria.getTo()));
        }
        Query query = Query.query(criteria);
        int count = jdbcTemplate.queryForObject(String.valueOf(query), Integer.class);
        List<CourseView> currencies = jdbcTemplate.queryForList(String.valueOf(query.with(courseCriteria.pageable())), Course.class)
                .stream()
                .map(this::convertToView)
                .collect(Collectors.toList());
        return new PageImpl<>(currencies, courseCriteria.pageable(), count);
    }

    @Transactional
    public CourseView create(CourseCreateCommand createCommand) {
        log.info("Create new course");
        boolean isExist = courseRepository.existsByCode(createCommand.getCode());
        if (isExist) {
            throw new BadRequestProblem("course_exist", String.format("Course with code '%s' is exists", createCommand.getCode()));
        }
        Course course = Course.builder()
                .code(createCommand.getCode())
                .name(createCommand.getName())
                .description(createCommand.getDescription())
                .from(createCommand.getFrom())
                .to(createCommand.getTo())
                .build();
        return convertToView(courseRepository.save(course));
    }

    @Transactional
    public CourseView update(@NonNull String code, CourseUpdateCommand updateCommand) {
        log.info("Update course with code '%'", code);
        Course course = courseRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundProblem("course_not_found", String.format("Cannot find course with code '%s'", code)));
        course.setName(updateCommand.getName());
        course.setDescription(updateCommand.getDescription());
        course.setFrom(updateCommand.getFrom());
        course.setTo(updateCommand.getTo());
        return convertToView(courseRepository.save(course));
    }

    @NotNull
    private CourseView convertToView(final Course course) {
        return CourseView.builder()
                .id(course.getId().toString())
                .code(course.getCode())
                .name(course.getName())
                .description(course.getDescription())
                .from(course.getFrom())
                .to(course.getTo())
                .createdAt(course.getCreatedAt())
                .createdBy(course.getCreatedBy())
                .modifiedAt(course.getModifiedAt())
                .modifiedBy(course.getModifiedBy())
                .build();
    }
}
