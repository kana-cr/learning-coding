package example.dao;

import example.enums.AuditStateEnum;

/**
 * 审批流
 *
 * @author kana-cr
 * @date 2023/3/17
 */

public class AuditFlow {

    private Long auditId;

    public AuditFlow() {
    }

    public AuditFlow(Long auditId, AuditStateEnum auditStateEnum) {
        this.auditId = auditId;
        this.auditStateEnum = auditStateEnum;
    }

    private AuditStateEnum auditStateEnum;

    @Override
    public String toString() {
        return "审批流 {" +
                "审批id=" + auditId +
                ", 审批状态=" + auditStateEnum +
                '}';
    }

    public Long getAuditId() {
        return auditId;
    }

    public AuditFlow setAuditId(Long auditId) {
        this.auditId = auditId;
        return this;
    }

    public AuditStateEnum getAuditStateEnum() {
        return auditStateEnum;
    }

    public AuditFlow setAuditStateEnum(AuditStateEnum auditStateEnum) {
        this.auditStateEnum = auditStateEnum;
        return this;
    }
}
