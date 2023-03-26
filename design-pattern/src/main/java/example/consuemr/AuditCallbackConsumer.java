package example.consuemr;

import example.biz.AuditService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * mq consumer
 *
 * @author kana-cr
 * @date 2023/3/17
 */
@Component
public class AuditCallbackConsumer implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    private AuditService auditService;

    public Boolean consumer() {
        try {
            Long auditFlowId = auditService.createAuditFlow();
            //发起审批
            auditService.startAudit(auditFlowId);
            //审批人审批
            auditService.auditFlow(auditFlowId);
            //最后一人或签完成 审批结束
            auditService.finishAudit(auditFlowId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        consumer();
    }
}
