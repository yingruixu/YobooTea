package com.yoboo.aspect;

import com.yoboo.constant.AutoFillConstant;
import com.yoboo.context.BaseContext;
import com.yoboo.enumeration.OperationType;
import com.yoboo.annotation.AutoFill;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * Aspect class, auto fill public field
 */

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * Aspect point
     */
    @Pointcut("execution(* com.yoboo.mapper.*.*(..)) && @annotation(com.yoboo.annotation.AutoFill)")
    public  void autoFillPoint(){

    }

    /**
     * BeforeAdvice
     */
    @Before("autoFillPoint()")
    public void autoFill(JoinPoint joinPoint){
        log.info("AutoFill Service started");

        //Get the operation type
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(com.yoboo.annotation.AutoFill.class);
        OperationType operationType = autoFill.value();

        //Get the object,get the first object
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0) {
            return;
        }
        Object object = args[0];

        LocalDateTime now = LocalDateTime.now();
        long currentId = BaseContext.getCurrentId();

        //Set the public field value
        if(operationType == OperationType.INSERT) {
            try {
                Method setCreateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,LocalDateTime.class);
                Method setCreateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,Long.class);
                Method setUpdateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method SetUpdateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                //Set value
                setCreateTime.invoke(object,now);
                setCreateUser.invoke(object,currentId);
                setUpdateTime.invoke(object,now);
                SetUpdateUser.invoke(object,currentId);


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (operationType == OperationType.UPDATE) {
            try {
                Method setUpdateTime = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,LocalDateTime.class);
                Method SetUpdateUser = object.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,Long.class);

                //Set value
                setUpdateTime.invoke(object,now);
                SetUpdateUser.invoke(object,currentId);


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}
