package com.example.demo.model.problem;

import com.example.demo.model.problem.common.AbstractProblem;
import org.zalando.problem.Status;

import java.util.Map;

public class NotFoundProblem extends AbstractProblem {
    public NotFoundProblem(String title, String detail) {
        super(title, Status.NOT_FOUND, detail);
    }

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public NotFoundProblem(String title, String msg, String... data) {
        super(title, Status.NOT_FOUND, String.format(msg, data));
    }

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public NotFoundProblem(String title, String msg, Map<String, Object> parameters, String... data) {
        super(title, Status.NOT_FOUND, String.format(msg, data), parameters);
    }
}
