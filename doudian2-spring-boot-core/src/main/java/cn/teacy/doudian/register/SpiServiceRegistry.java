package cn.teacy.doudian.register;


import cn.teacy.common.annotation.SpiEndpoint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

@Getter
@Slf4j
public class SpiServiceRegistry {

    private static final String BASE_PACKAGE = "cn.teacy.doudian";
    private static final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

    private final Set<String> spiRoutes = new HashSet<>();

    public SpiServiceRegistry(String[] additionalPackages) {
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(SpiEndpoint.class));

        scanSpiEndpoints(BASE_PACKAGE);

        for (String additionalPackage : additionalPackages) {
            scanSpiEndpoints(additionalPackage);
        }

        log.info("SPI endpoints registered: {}", spiRoutes);

    }

    private void scanSpiEndpoints(String basePackage) {
        scanner.findCandidateComponents(basePackage).forEach(beanDefinition -> {
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                // 获取类级别的 @RequestMapping 注解（合并注解信息）
                boolean registerAll = clazz.isAnnotationPresent(SpiEndpoint.class);
                RequestMapping classMapping = AnnotatedElementUtils.findMergedAnnotation(clazz, RequestMapping.class);
                String classPath = (classMapping != null && classMapping.value().length > 0)
                        ? classMapping.value()[0] : "";

                for (Method method : clazz.getDeclaredMethods()) {
                    // 先判断方法是否标注了 @SpiEndpoint
                    if (registerAll || method.isAnnotationPresent(SpiEndpoint.class)) {
                        // 利用 AnnotatedElementUtils 获取合并后的 @RequestMapping 信息
                        RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
                        if (mapping != null && mapping.value().length > 0) {
                            // 组合类级别和方法级别的路径，得到完整的路由
                            String route = classPath + mapping.value()[0];
                            spiRoutes.add(route);
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage());
            }
        });
    }

}
