package com.example.demo.repository;

import com.example.demo.domain.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository  extends CrudRepository<Course, String> {
    Optional<Course> findByCode(String code);
    Boolean existsByCode(String code);
}
