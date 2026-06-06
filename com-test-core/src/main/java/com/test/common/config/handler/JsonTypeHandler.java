
package com.test.common.config.handler;

import java.sql.CallableStatement;
import java.sql.SQLException;
import com.sys.designer.framework.common.util.JsonUtil;
import com.sys.designer.framework.common.list.LongList;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.ibatis.type.MappedTypes;
import com.sys.designer.framework.common.list.StringList;
import java.util.Objects;
import org.apache.ibatis.type.BaseTypeHandler;
import java.sql.PreparedStatement;
import org.apache.ibatis.type.JdbcType;
import com.sys.designer.framework.common.list.IntegerList;

@MappedTypes(value = {
            Map.class,
            HashMap.class,
            LinkedHashMap.class,
            StringList.class,
            LongList.class,
            IntegerList.class,
})
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
    private final Class<T> type;

    public JsonTypeHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JsonUtil.toJson(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return JsonUtil.toBean(rs.getString(columnName), type);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return JsonUtil.toBean(rs.getString(columnIndex), type);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return JsonUtil.toBean(cs.getString(columnIndex), type);
    }
}
