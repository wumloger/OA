package top.wuml.oa.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ProcessFlow {
    private Long processId;
    private Long formId;
    private Long operatorId;
    private String action;
    private String result;
    private String reason;
    private Date createTime;
    private Date auditTime;
    private Integer orderNo;
    private String state;
    private Integer isLast;


}
