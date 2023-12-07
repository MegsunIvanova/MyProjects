package bg.softuni.auto_moto_manager.service.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("@annotation(WarnIfExecutionExceeds)")
    public void warnIfExecutionExceeds() {
    }
}
