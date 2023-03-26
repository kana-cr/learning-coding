package example.factory;

import example.biz.AuditExtraBizService;
import example.context.AuditContext;
import example.dao.AuditFlow;
import example.dao.Department;
import example.function.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kana-cr
 * @date 2023/3/18
 */
@Component
public class AuditContextFactory {

    @Resource
    private AuditExtraBizService auditExtraBizService;

    public AuditContext createAuditContext() {
        AuditContext auditContext = new AuditContext();
        Lazy<AuditFlow> auditFlowLazy = Lazy.create(() -> auditExtraBizService.doAuditFlowQuery());
        auditContext.setAuditFlow(auditFlowLazy);


        Lazy<Department> departmentLazy = auditFlowLazy.map((auditFlow) -> auditExtraBizService.doAuditDepartmentQuery(auditFlow));
        auditContext.setDepartment(departmentLazy);

        auditContext.setSuperVisor(auditFlowLazy.flatMap((auditFlow) ->
                departmentLazy.map(department -> auditExtraBizService.doSuperVisorQuery(department, auditFlow))));
        return auditContext;
    }
}
