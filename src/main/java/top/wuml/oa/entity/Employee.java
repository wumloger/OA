package top.wuml.oa.entity;

import lombok.Data;

@Data
public class Employee {
    private Long employeeId;
    private String name;
    private Long departmentId;
    private String title;
    private Integer level;
    private String img;
}
