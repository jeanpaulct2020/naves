package com.jeancot.naves.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Define el pointcut para métodos en un paquete específico
    @Pointcut("execution(* com.jeancot.naves.service.*.*(..))")
    public void serviceMethods() {}

    // Define un pointcut que apunta al método findById con un parámetro Integer
    @Pointcut("execution(* com.jeancot.naves.service.INaveService.findNaveById(Integer)) && args(id)")
    public void findByIdMethod(Integer id) {}

    // Advice que se ejecuta antes de cada método en el pointcut
    @Before("serviceMethods()")
    public void logBeforeMethod() {
        System.out.println("Método ejecutado: Antes de la ejecución del método");
    }

    //se ejecuta antes de findById si el ID es negativo
    @Before("findByIdMethod(id)")
    public void logIfIdNegative(JoinPoint joinPoint, Integer id) {
        if (id != null && id < 0) {
            System.out.println("El ID a buscar (" + id + ") es negativo en el método: " + joinPoint.getSignature());
        }
    }

    // Advice que se ejecuta después de cada método en el pointcut
    @After("serviceMethods()")
    public void logAfterMethod() {
        System.out.println("Método ejecutado: Después de la ejecución del método");
    }
}
