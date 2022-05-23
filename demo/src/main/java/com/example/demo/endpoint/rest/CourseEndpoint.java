package com.example.demo.endpoint.rest;

import com.example.demo.endpoint.common.AbstractEndpoint;
import com.example.demo.model.command.CourseCreateCommand;
import com.example.demo.model.command.CourseUpdateCommand;
import com.example.demo.model.criteria.CourseCriteria;
import com.example.demo.model.view.CourseView;
import com.example.demo.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseEndpoint extends AbstractEndpoint {
    private final CourseService courseService;

    @GetMapping("/{code}")
    public CourseView get(@PathVariable String code){
        return courseService.findOne(code);
    }

    @GetMapping
    public ResponseEntity<List<CourseView>> filter(CourseCriteria courseCriteria){
        return buildPagingResponse(courseService.filter(courseCriteria));
    }

    @PostMapping
    public CourseView create(@RequestBody @Valid CourseCreateCommand createCommand) {
        return courseService.create(createCommand);
    }

    @PutMapping("/{code}")
    public CourseView update(@PathVariable String code, @RequestBody @Valid CourseUpdateCommand updateCommand){
        return courseService.update(code, updateCommand);
    }
}
