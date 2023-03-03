package top.wuml.oa.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LeaveForm {
    private Long formId;
    private Long employeeId;
    private Integer formType;
    private Date startTime;
    private Date endTime;
    private String reason;
    private Date createTime;
    private String state;
}
