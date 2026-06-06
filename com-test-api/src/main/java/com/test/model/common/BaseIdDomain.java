
package com.test.model.common;

/**
 * BaseIdDomain
 */
public class BaseIdDomain {
    /**
     * 自增主键
     */
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseIdDomain{" +
                "id='" + id + '\'' +
                "};";
    }
}
