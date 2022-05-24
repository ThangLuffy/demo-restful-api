package com.example.demo.model.problem;

import com.example.demo.model.problem.common.AbstractProblem;
import org.zalando.problem.Status;

import java.util.Map;

public class BadRequestProblem extends AbstractProblem {
    public BadRequestProblem(String title, String detail) {
        super(title, Status.BAD_REQUEST, detail);
    }

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public BadRequestProblem(String title, String msg, String... data) {
        super(title, Status.BAD_REQUEST, String.format(msg, data));
    }

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public BadRequestProblem(String title, String msg, Map<String, Object> parameters, String... data) {
        super(title, Status.BAD_REQUEST, String.format(msg, data), parameters);
    }
}
