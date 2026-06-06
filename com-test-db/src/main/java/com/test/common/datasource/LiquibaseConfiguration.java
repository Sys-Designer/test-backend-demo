
package com.test.common.datasource;

import org.springframework.core.io.DefaultResourceLoader;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import java.util.Objects;
import javax.sql.DataSource;
import com.sys.designer.framework.common.config.CommonConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import liquibase.integration.spring.SpringLiquibase;
import java.util.HashMap;

@Configuration
public class LiquibaseConfiguration {
    @Autowired
    private CommonConfig commonConfig;

    private String getVersion(String dbName) {
        String version = commonConfig.getValue("oc.datasource." + dbName + ".version", "");
        return Objects.nonNull(version) && !version.isEmpty() ? version + "/" : "";
    }

    private Map<String, String> getParams(String dbName) {
        Map<String, String> params = new HashMap<>();
        params.put("version", getVersion(dbName));
        return params;
    }

    @Bean
    public SpringLiquibase masterLiquibase(@Qualifier("masterDataSource") DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        String version = getVersion("master");
        liquibase.setChangeLog("classpath:/liquibase/changelog-master" + (!version.isEmpty() ? "-" : "") + version + ".xml");
        liquibase.setDataSource(dataSource);
        liquibase.setShouldRun(commonConfig.getValueAsBoolean("oc.datasource.version.sql.run", false));
        liquibase.setChangeLogParameters(getParams(version));
        liquibase.setResourceLoader(new DefaultResourceLoader());
        return liquibase;
    }
}
