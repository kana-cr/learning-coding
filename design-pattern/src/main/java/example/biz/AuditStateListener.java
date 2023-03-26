package example.biz;

import example.dao.AuditFlow;
import example.enums.AuditStateEnum;
import example.enums.AuditStatusChangedEvent;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;

/**
 * 状态监听器
 *
 * @author kana-cr
 * @date 2023/3/17
 */
@Component
@WithStateMachine(name = "auditStateMachine")
public class AuditStateListener {

    @OnTransition(source = "PREPARED", target = "STARTED")
    public boolean startAudit(Message<AuditStatusChangedEvent> eventMessage) {
        System.out.println("发起审批: " + eventMessage.getHeaders().toString());
        AuditFlow audit = (AuditFlow) eventMessage.getHeaders().get("audit");
        audit.setAuditStateEnum(AuditStateEnum.STARTED);
        return true;
    }

    @OnTransition(source = "STARTED", target = "UNDER_APPROVAL")
    public boolean auditing(Message<AuditStatusChangedEvent> eventMessage) {
        System.out.println("开始审批中: " + eventMessage.getHeaders().toString());
        AuditFlow audit = (AuditFlow) eventMessage.getHeaders().get("audit");
        audit.setAuditStateEnum(AuditStateEnum.UNDER_APPROVAL);
        return true;
    }

    @OnTransition(source = "UNDER_APPROVAL", target = "FINISHED")
    public boolean auditFinish(Message<AuditStatusChangedEvent> eventMessage) {
        System.out.println("完成审批流: " + eventMessage.getHeaders().toString());
        AuditFlow audit = (AuditFlow) eventMessage.getHeaders().get("audit");
        audit.setAuditStateEnum(AuditStateEnum.FINISHED);
        return true;
    }
}
