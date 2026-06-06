
package com.test.function.lib.impl;

import java.util.List;
import java.util.Map;
import com.sys.designer.framework.common.errorcode.CommonErrorCode;
import com.sys.designer.framework.api.tool.ToolParam;
import com.sys.designer.framework.common.util.ComponentUtil;
import com.sys.designer.framework.api.tool.ToolManager;
import com.sys.designer.framework.common.exception.BusinessRuntimeException;
import com.sys.designer.framework.common.util.JsonUtil;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ToolManagerImpl extends ToolManager {
    @Override
    protected Object doExecute(String name, ToolParam param) {
        Object result = switch (name) {
            default -> throw new BusinessRuntimeException(CommonErrorCode.PARAMETER_INVALID, name + " not found");
        };
        return result;
    }
}
