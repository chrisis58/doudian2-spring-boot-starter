package cn.teacy.doudian.register;


import cn.teacy.common.annotation.SpiEndpoint;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class SpiServiceRegistry implements ApplicationListener<ApplicationReadyEvent> {

    private static final String BASE_PACKAGE = "cn.teacy.doudian";
    private static final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

    private final Set<String> basePackages = new HashSet<>();

    @Getter
    private final Set<String> spiRoutes = new HashSet<>();

    @Getter
    private final Set<Class<?>> responseClasses = new HashSet<>();

    public SpiServiceRegistry() {
        scanner.addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        scanner.addIncludeFilter(new AnnotationTypeFilter(SpiEndpoint.class));

        basePackages.add(BASE_PACKAGE);
    }

    public SpiServiceRegistry(String[] additionalPackages) {
        this();
        basePackages.addAll(Arrays.asList(additionalPackages));
    }

    public SpiServiceRegistry(Collection<String> additionalPackages) {
        this();
        basePackages.addAll(additionalPackages);
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
                            responseClasses.add(method.getReturnType());
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage());
            }
        });
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Object source = event.getSpringApplication().getAllSources().iterator().next();
        if (source instanceof Class<?> mainConfigClass) {
            String[] packages = Optional.ofNullable(AnnotatedElementUtils.findMergedAnnotation(mainConfigClass, ComponentScan.class))
                    .map(ComponentScan::basePackages)
                    .orElseGet(() -> new String[]{mainConfigClass.getPackageName()});
            basePackages.addAll(Arrays.asList(packages));
        }

        log.debug("Scanning SPI endpoints in base packages: {}", basePackages);
        basePackages.forEach(this::scanSpiEndpoints);

        log.info("{} SPI endpoints registered: {}", spiRoutes.size(), spiRoutes);
        log.info("{} response classes registered: {}", responseClasses.size(), responseClasses);
    }
}
