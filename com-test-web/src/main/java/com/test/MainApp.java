
package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import com.sys.designer.framework.web.Application;
import com.sys.designer.framework.common.FullModelBeanNameGenerator;
import com.sys.designer.framework.common.PluginLoadingInitializer;

@ComponentScan(
        basePackageClasses = {com.sys.designer.framework.PackageInfo.class, PackageInfo.class},
        basePackages = {"com.sys.designer.plugin"},
        nameGenerator = FullModelBeanNameGenerator.class
)
public class MainApp extends Application {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MainApp.class);
        application.addInitializers(new PluginLoadingInitializer());
        application.run(args);
    }
}
