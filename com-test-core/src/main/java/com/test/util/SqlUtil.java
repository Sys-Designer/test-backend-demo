
package com.test.util;

import org.slf4j.Logger;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.ExecutorType;
import com.sys.designer.framework.common.errorcode.CommonErrorCode;
import org.slf4j.LoggerFactory;
import com.sys.designer.framework.common.util.ValueUtil;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;
import java.util.function.BiFunction;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.sys.designer.framework.common.exception.BusinessRuntimeException;
import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;
import java.util.Objects;
import static com.sys.designer.framework.common.util.Assert.notNull;

public final class SqlUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(SqlUtil.class);

    private SqlUtil() {
    }

    public static <T, U, R> int batchUpdateOrInsert(SqlSessionFactory sqlSessionFactory, Collection<T> data, Class<U> mapperClass, BiFunction<T, U, R> function, int batchSize) {
        notNull(data, CommonErrorCode.SERVER_ERROR, "the data of the parameter which at insertList batch must not be null");
        int index = 1;
        SqlSession batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            U mapper = batchSqlSession.getMapper(mapperClass);
            int size = data.size();
            for (T element : data) {
                function.apply(element, mapper);
                if ((index % batchSize == 0) || index == size) {
                    batchSqlSession.flushStatements();
                }
                index++;
            }
            batchSqlSession.commit(!TransactionSynchronizationManager.isSynchronizationActive());
        } catch (Exception e) {
            batchSqlSession.rollback();
            throw new BusinessRuntimeException(CommonErrorCode.SERVER_ERROR, e);
        } finally {
            batchSqlSession.close();
        }
        return index - 1;
    }

    public static boolean transaction(PlatformTransactionManager transactionManager, Supplier<Boolean> supplier, int timeout, boolean throwEx) {
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(timeout);
        try {
            return Boolean.TRUE.equals(new TransactionTemplate(transactionManager, transactionDefinition).execute(status -> supplier.get()));
        } catch (Throwable throwable) {
            if (throwEx) {
                throw throwable;
            } else {
                LOGGER.error("error.", throwable);
            }
        }
        return false;
    }

    public static boolean transaction(PlatformTransactionManager transactionManager, Runnable runnable, boolean throwEx) {
        return transaction(transactionManager, runnable, 5, throwEx);
    }

    public static boolean transaction(PlatformTransactionManager transactionManager, Runnable runnable, int timeout, boolean throwEx) {
        return transaction(transactionManager, () -> {
            runnable.run();
            return true;
        }, timeout, throwEx);
    }

    public static boolean transaction(PlatformTransactionManager transactionManager, Supplier<Boolean> supplier, boolean throwEx) {
        return transaction(transactionManager, supplier, 5, throwEx);
    }
}
