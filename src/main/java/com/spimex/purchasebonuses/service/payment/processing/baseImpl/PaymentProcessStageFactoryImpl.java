package com.spimex.purchasebonuses.service.payment.processing.baseImpl;

import com.spimex.purchasebonuses.exception.StageNameDuplicateException;
import com.spimex.purchasebonuses.service.payment.processing.core.CalculationProcessStage;
import com.spimex.purchasebonuses.service.payment.processing.core.PaymentProcessStageFactory;
import com.spimex.purchasebonuses.service.payment.processing.core.ProcessStageName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@Qualifier(PaymentProcessStageFactory.BASE_IMPL)
public class PaymentProcessStageFactoryImpl implements PaymentProcessStageFactory {

    private Map<ProcessStageName, CalculationProcessStage> stageByName;

    public CalculationProcessStage getStage(ProcessStageName stageName) {
        return this.stageByName.get(stageName);
    }

    @Autowired
    private void createStrategy(Set<CalculationProcessStage> stages) {
        this.stageByName = new HashMap<>();
        stages.forEach(stage -> {
            ProcessStageName stageName = stage.getStageName();
            if (this.stageByName.containsKey(stageName)) {
                throw new StageNameDuplicateException("Duplicate stage name - %s".formatted(stageName));
            }
            this.stageByName.put(stageName, stage);
            log.info("Register stage - %s".formatted(stageName));
        });
    }
}