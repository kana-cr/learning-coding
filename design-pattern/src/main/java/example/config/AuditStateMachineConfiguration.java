package example.config;

import example.dao.AuditFlow;
import example.enums.AuditStateEnum;
import example.enums.AuditStatusChangedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.util.EnumSet;

/**
 * @author kana-cr
 * @date 2023/3/17
 */
@Configuration
@EnableStateMachine(name = "auditStateMachine")
public class AuditStateMachineConfiguration extends StateMachineConfigurerAdapter<AuditStateEnum, AuditStatusChangedEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<AuditStateEnum, AuditStatusChangedEvent> states) throws Exception {
        states.withStates()
                .initial(AuditStateEnum.PREPARED)
                .states(EnumSet.allOf(AuditStateEnum.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<AuditStateEnum, AuditStatusChangedEvent> transitions) throws Exception {
        transitions.withExternal().source(AuditStateEnum.PREPARED).target(AuditStateEnum.STARTED).event(AuditStatusChangedEvent.START_AUDITING)
                .and()
                .withExternal().source(AuditStateEnum.STARTED).target(AuditStateEnum.UNDER_APPROVAL).event(AuditStatusChangedEvent.AUDITING)
                .and()
                .withExternal().source(AuditStateEnum.UNDER_APPROVAL).target(AuditStateEnum.FINISHED).event(AuditStatusChangedEvent.FINISH_AUDITING);
    }

    @Bean
    public DefaultStateMachinePersister persister() {
        return new DefaultStateMachinePersister(new StateMachinePersist<Object, Object, AuditFlow>() {
            @Override
            public void write(StateMachineContext stateMachineContext, AuditFlow flow) {
                System.out.println("持久化状态机：" + flow);
            }

            @Override
            public StateMachineContext<Object, Object> read(AuditFlow flow) {
                return new DefaultStateMachineContext(flow.getAuditStateEnum(), null, null, null);
            }
        });
    }
}

