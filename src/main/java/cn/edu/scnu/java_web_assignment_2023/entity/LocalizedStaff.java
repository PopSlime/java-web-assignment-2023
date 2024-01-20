package cn.edu.scnu.java_web_assignment_2023.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LocalizedStaff extends Staff {
    private String name;
    private String description;
}
