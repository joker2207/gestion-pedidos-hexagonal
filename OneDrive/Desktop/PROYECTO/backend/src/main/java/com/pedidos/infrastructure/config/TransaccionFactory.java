package com.pedidos.infrastructure.config;

import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Envuelve un caso de uso en una transaccion sin que la capa de aplicacion
 * conozca Spring: toda la operacion se confirma o se revierte junta.
 */
@Component
public class TransaccionFactory {

    private final TransactionTemplate template;

    public TransaccionFactory(PlatformTransactionManager transactionManager) {
        this.template = new TransactionTemplate(transactionManager);
    }

    public <T> T envolver(Class<T> tipo, T objetivo) {
        Object proxy = Proxy.newProxyInstance(
                tipo.getClassLoader(),
                new Class<?>[]{tipo},
                (p, metodo, args) -> template.execute(status -> invocar(objetivo, metodo, args)));
        return tipo.cast(proxy);
    }

    private Object invocar(Object objetivo, Method metodo, Object[] args) {
        try {
            return metodo.invoke(objetivo, args);
        } catch (InvocationTargetException e) {
            Throwable causa = e.getCause();
            if (causa instanceof RuntimeException runtime) {
                throw runtime;
            }
            if (causa instanceof Error error) {
                throw error;
            }
            throw new IllegalStateException(causa);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
