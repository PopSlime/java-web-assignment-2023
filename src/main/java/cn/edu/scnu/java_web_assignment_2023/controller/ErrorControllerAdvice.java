package cn.edu.scnu.java_web_assignment_2023.controller;

import cn.edu.scnu.java_web_assignment_2023.entity.User;
import cn.edu.scnu.java_web_assignment_2023.service.ContentService;
import com.mysql.cj.exceptions.CJCommunicationsException;
import com.mysql.cj.exceptions.CJException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class ErrorControllerAdvice {
    private final ContentService service;

    public ErrorControllerAdvice(ContentService service) {
        this.service = service;
    }

    static boolean sqlCompatibilityFlag = false;

    @ModelAttribute("sqlCompatibility")
    public boolean sqlCompatibility() {
        if (sqlCompatibilityFlag) return true;
        service.checkSqlCompatibility();
        return sqlCompatibilityFlag = true;
    }

    @ExceptionHandler(SQLNonTransientConnectionException.class)
    public ModelAndView sqlConnectError(SQLNonTransientConnectionException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof CJCommunicationsException)
            return createServerErrorView("sql.connect");
        if (cause instanceof CJException) {
            switch (((CJException) cause).getSQLState()) {
                case "28000": return createServerErrorView("sql.auth");
                case "42000": return createServerErrorView("sql.no_data");
            }
        }
        return createServerErrorView("sql");
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ModelAndView sqlSyntaxError(SQLSyntaxErrorException ex) {
        return createServerErrorView("sql.incompatible");
    }

    ModelAndView createServerErrorView(String type) {
        ModelAndView result = new ModelAndView("/error");
        result.addObject("user", User.EMPTY);
        result.addObject("scope", "server");
        result.addObject("type", type);
        return result;
    }
}
