
package com.test.function.lib.impl.test;

import java.util.List;
import java.util.Map;
import com.sys.designer.framework.web.util.ContextUtil;
import com.sys.designer.framework.common.util.ValueUtil;
import com.test.function.test.TestFunction;
import java.util.Objects;
import java.util.Comparator;
import java.util.Collections;
import jakarta.annotation.Resource;
import com.sys.designer.framework.common.exception.BusinessRuntimeException;
import org.springframework.stereotype.Component;
import java.util.HashMap;

/**
 * TestFunction
 * <pre>
 * 测试函数模块
 * </pre>
 */
@Component
public class BaseTestFunction implements TestFunction {
    @Override
    public void getTest() {
        System.out.println("这里实现代码");
    }

}
