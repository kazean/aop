package hello.aop.exam;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {}, retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;
        /*for (int retryCount = 0; retryCount < maxRetry; retryCount++) {
            try{
                log.info("[retry] try count={}/{}", retryCount, maxRetry);
                Object result = joinPoint.proceed();
                return result;
            }catch (Exception e){
                exceptionHolder = e;
            }
        }*/
        int retryCount = 1;
        do {
            try {
                log.info("[retry] tryCount={}/{}", retryCount, maxRetry);
                retryCount++;
                Object result = joinPoint.proceed();
                return result;
            } catch (Exception e) {
                exceptionHolder = e;
            }
        } while (retryCount <= maxRetry);
        throw exceptionHolder;
    }

}
