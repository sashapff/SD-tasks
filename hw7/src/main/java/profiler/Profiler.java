package profiler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class Profiler {

    @Around("execution(* app.Calculator.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        if (!signature.getDeclaringType().getPackage().toString().equals("package " + System.getProperty("profiledPackage"))) {
            return joinPoint.proceed();
        }

        Statistics statistics = Statistics.getInstance();
        statistics.add(signature);
        Object result = joinPoint.proceed(joinPoint.getArgs());
        statistics.remove(signature);
        return result;
    }
}
