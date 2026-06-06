
package com.test.controller;

import com.sys.designer.framework.api.permission.Permission;
import java.util.List;
import com.sys.designer.framework.annotation.Query;
import com.sys.designer.framework.function.QueryFunction;
import com.test.function.test.TestFunction;
import jakarta.annotation.Resource;
import com.sys.designer.framework.common.util.ComponentUtil;
import org.springframework.stereotype.Component;
import com.sys.designer.framework.api.permission.PermissionConst;

@Component
public class QueryFunctionImpl implements QueryFunction {
    @Resource
    private TestFunction testFunction;

}
