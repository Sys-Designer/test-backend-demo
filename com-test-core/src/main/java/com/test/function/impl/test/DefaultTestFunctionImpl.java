
package com.test.function.impl.test;

import java.util.List;
import org.slf4j.Logger;
import com.sys.designer.framework.common.util.CheckParamUtil;
import org.slf4j.LoggerFactory;
import com.test.function.test.TestFunction;
import com.sys.designer.framework.common.util.ComponentUtil;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * TestFunction
 * <pre>
 * 测试函数模块
 * </pre>
 */
@Primary
@Component
public class DefaultTestFunctionImpl implements TestFunction {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTestFunctionImpl.class);
    private final TestFunction localFunction_;

    public DefaultTestFunctionImpl(List<TestFunction> functions) {
        localFunction_ = ComponentUtil.getLocalFunction(TestFunction.class, functions);
        if (null == localFunction_) {
            LOGGER.error("{} not found implementation", TestFunction.class.getName());
        }
    }

    @Override
    public void getTest() {
        localFunction_.getTest();
    }

}
