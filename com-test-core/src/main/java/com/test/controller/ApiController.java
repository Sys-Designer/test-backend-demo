
package com.test.controller;

import com.sys.designer.framework.api.permission.Permission;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.test.function.test.TestFunction;
import jakarta.annotation.Resource;
import com.sys.designer.framework.common.constant.CommonConst;
import com.sys.designer.framework.common.entity.ResultData;
import com.sys.designer.framework.api.permission.PermissionConst;

@RestController
@RequestMapping(CommonConst.API_PREFIX)
public class ApiController {
    @Resource
    private TestFunction testFunction;

}
