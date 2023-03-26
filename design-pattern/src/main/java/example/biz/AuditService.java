package example.biz;

import example.dao.AuditFlow;
import example.enums.AuditStateEnum;
import example.enums.AuditStatusChangedEvent;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kana-cr
 * @date 2023/3/17
 */
@Component
public class AuditService {

    @Resource
    private StateMachine<AuditStateEnum, AuditStatusChangedEvent> auditFlowStateMachine;

    @Resource
    private StateMachinePersister<AuditStateEnum, AuditStatusChangedEvent, AuditFlow> stateMachinePersist;

    private Map<Long, AuditFlow> auditFlowMap = new HashMap<>();

    private AtomicLong auditIdCreated = new AtomicLong(1);

    public Long createAuditFlow() {
        long auditId = auditIdCreated.getAndIncrement();
        AuditFlow auditFlow = new AuditFlow(auditId, AuditStateEnum.PREPARED);
        auditFlowMap.put(auditId, auditFlow);
        return auditId;
    }

    public void startAudit(Long auditId) throws Exception {
        AuditFlow auditFlow = auditFlowMap.get(auditId);
        sendEvent(auditFlow, AuditStatusChangedEvent.START_AUDITING);
    }

    public void auditFlow(Long auditId) throws Exception {
        AuditFlow auditFlow = auditFlowMap.get(auditId);
        sendEvent(auditFlow, AuditStatusChangedEvent.AUDITING);
    }

    public void finishAudit(Long auditId) throws Exception {
        AuditFlow auditFlow = auditFlowMap.get(auditId);
        sendEvent(auditFlow, AuditStatusChangedEvent.FINISH_AUDITING);
    }

    private void sendEvent(AuditFlow auditFlow, AuditStatusChangedEvent changedEvent) throws Exception {
        Message<AuditStatusChangedEvent> auditMessage = MessageBuilder.withPayload(changedEvent)
                .setHeader("audit", auditFlow).build();
        auditFlowStateMachine.start();
        stateMachinePersist.restore(auditFlowStateMachine, auditFlow);
        Thread.sleep(1000);
        auditFlowStateMachine.sendEvent(auditMessage);
        stateMachinePersist.persist(auditFlowStateMachine, auditFlow);
        auditFlowStateMachine.stop();
    }

}
