
package com.test.common.config.handler;

import org.apache.ibatis.type.MappedTypes;
import com.sys.designer.framework.api.TypeEnum;
import java.sql.CallableStatement;
import java.util.Objects;
import org.apache.ibatis.type.BaseTypeHandler;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.sys.designer.framework.common.util.JsonUtil;
import org.apache.ibatis.type.JdbcType;
import java.math.BigDecimal;
import java.sql.ResultSet;

@MappedTypes(value = {
    com.sys.designer.framework.common.enums.FileType.class,
})
public class UniversalEnumHandler<E extends Enum<E> & TypeEnum> extends BaseTypeHandler<E> {
    private final Class<E> type;

    static {
    }

    public UniversalEnumHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        Object value = parameter.getValue();
        if (value instanceof Integer) {
            ps.setInt(i, (Integer) value);
        } else if (value instanceof Long) {
            ps.setLong(i, (Long) value);
        } else if (value instanceof String) {
            ps.setString(i, (String) value);
        } else if (value instanceof Boolean) {
            ps.setBoolean(i, (Boolean) value);
        } else if (value instanceof Float) {
            ps.setFloat(i, (Float) value);
        } else {
            throw new RuntimeException("unsupported type:" + value.getClass().getName());
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object value = null;
        String valueType = TypeEnum.getValueTypeName(type);
        if (String.class.getSimpleName().equals(valueType)) {
            value = rs.getString(columnName);
        } else if (Integer.class.getSimpleName().equals(valueType)) {
            value = rs.getInt(columnName);
        } else if (Long.class.getSimpleName().equals(valueType)) {
            value = rs.getLong(columnName);
        } else if (BigDecimal.class.getSimpleName().equals(valueType)) {
            value = rs.getBigDecimal(columnName);
        }
        if (Objects.isNull(value)) {
            return null;
        }
        return rs.wasNull() ? null : TypeEnum.from(value, this.type);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object value = null;
        String valueType = TypeEnum.getValueTypeName(type);
        if (String.class.getSimpleName().equals(valueType)) {
            value = rs.getString(columnIndex);
        } else if (Integer.class.getSimpleName().equals(valueType)) {
            value = rs.getInt(columnIndex);
        } else if (Long.class.getSimpleName().equals(valueType)) {
            value = rs.getLong(columnIndex);
        } else if (BigDecimal.class.getSimpleName().equals(valueType)) {
            value = rs.getBigDecimal(columnIndex);
        }
        if (Objects.isNull(value)) {
            return null;
        }
        return rs.wasNull() ? null : TypeEnum.from(value, this.type);
    }

    @Override
    public E getNullableResult(CallableStatement rs, int columnIndex) throws SQLException {
        Object value = null;
        String valueType = TypeEnum.getValueTypeName(type);
        if (String.class.getSimpleName().equals(valueType)) {
            value = rs.getString(columnIndex);
        } else if (Integer.class.getSimpleName().equals(valueType)) {
            value = rs.getInt(columnIndex);
        } else if (Long.class.getSimpleName().equals(valueType)) {
            value = rs.getLong(columnIndex);
        } else if (BigDecimal.class.getSimpleName().equals(valueType)) {
            value = rs.getBigDecimal(columnIndex);
        }
        if (Objects.isNull(value)) {
            return null;
        }
        return rs.wasNull() ? null : TypeEnum.from(value, this.type);
    }
}
