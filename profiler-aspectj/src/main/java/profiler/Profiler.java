package ru.sashapff.sd.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class Profiler {

    @Around("execution(* ru.sashapff.sd.aop.app.Calculator.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        System.out.println(signature.getDeclaringType().getPackage());
        if (!signature.getDeclaringType().getPackage().toString().startsWith("package " + System.getProperty("profiledPackage"))) {
            return joinPoint.proceed();
        }


        long startNs = System.nanoTime();
        System.out.println("Start method " + joinPoint.getSignature().getName());

        Object result = joinPoint.proceed(joinPoint.getArgs());

        System.out.println("Finish method " + joinPoint.getSignature().getName()
                + ", execution time in ns: " + (System.nanoTime() - startNs));

        return result;
    }

}
