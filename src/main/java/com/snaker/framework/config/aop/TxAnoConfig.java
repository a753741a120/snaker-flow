package com.snaker.framework.config.aop;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @基本功能: aop全局事务
 * @program:demo
 * @author:pm
 * @create:2021-06-22 16:37:41
 **/
@Configuration
public class TxAnoConfig {

    @Autowired
    private DataSource dataSource;

    @Bean("txManager")
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    /*事务拦截器*/
    @Bean("txAdvice")
    public TransactionInterceptor txAdvice(DataSourceTransactionManager txManager){
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED );
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED,
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setTimeout(5);
        Map<String, TransactionAttribute> txMap = new HashMap<>(7);
        txMap.put("start*", requiredTx);
        txMap.put("execute*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("remove*", requiredTx);
        txMap.put("assign*", requiredTx);
        txMap.put("create*", requiredTx);
        txMap.put("complete*", requiredTx);
        txMap.put("finish*", requiredTx);
        txMap.put("terminate*", requiredTx);
        txMap.put("take*", requiredTx);
        txMap.put("deploy*", requiredTx);
        txMap.put("undeploy*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("search*", readOnlyTx);
        txMap.put("is*", requiredTx);
        txMap.put("*", requiredTx);
        source.setNameMap( txMap );
        return new TransactionInterceptor(txManager ,source) ;
    }

    /**切面拦截规则 参数会自动从容器中注入*/
    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(TransactionInterceptor txAdvice){
        DefaultPointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor();
        pointcutAdvisor.setAdvice(txAdvice);
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* org.snaker.engine.core..*.*(..))");
        pointcut.setExpression("execution(* com.snaker.system.*.service..*.*(..))");
        pointcut.setExpression("execution(* com.snaker.framework.*.service..*.*(..))");
        pointcutAdvisor.setPointcut(pointcut);
        return pointcutAdvisor;
    }
}