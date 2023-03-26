package example.biz;

import example.dao.AuditFlow;
import example.dao.Department;
import example.dao.SuperVisor;
import org.springframework.stereotype.Component;

/**
 * @author kana-cr
 * @date 2023/3/17
 */
@Component
public class AuditExtraBizService {

    public SuperVisor doSuperVisorQuery(Department department, AuditFlow auditFlow) {
        System.out.println("查询审批用户");
        return new SuperVisor();
    }

    ;

    public Department doAuditDepartmentQuery(AuditFlow auditFlow) {
        System.out.println("查询审批部门");
        return new Department();
    }

    ;

    public AuditFlow doAuditFlowQuery() {
        System.out.println("查询审批流");
        return new AuditFlow();
    }

}
