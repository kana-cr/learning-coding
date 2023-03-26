package example.context;

import example.dao.AuditFlow;
import example.dao.Department;
import example.dao.SuperVisor;
import example.function.Lazy;

/**
 * @author kana-cr
 * @date 2023/3/17
 */

public class AuditContext {

    private Lazy<SuperVisor> superVisor;

    private Lazy<Department> department;

    private Lazy<AuditFlow> auditFlow;

    public Lazy<SuperVisor> getSuperVisor() {
        return superVisor;
    }

    public void setSuperVisor(Lazy<SuperVisor> superVistor) {
        this.superVisor = superVistor;
    }

    public Lazy<Department> getDepartment() {
        return department;
    }

    public void setDepartment(Lazy<Department> department) {
        this.department = department;
    }

    public Lazy<AuditFlow> getAuditFlow() {
        return auditFlow;
    }

    public void setAuditFlow(Lazy<AuditFlow> auditFlow) {
        this.auditFlow = auditFlow;
    }
}
