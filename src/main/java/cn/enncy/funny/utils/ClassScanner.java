package cn.enncy.funny.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * //TODO
 * <br/>Created in 23:24 2021/8/19
 *
 * @author: enncy
 */

@Slf4j
public class ClassScanner {

    private static final String RESOURCE_PATTERN = "/**/*.class";

    public static   List<Class<?>> scan(String packageName) {
        List<Class<?>> list = new ArrayList<>();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+
                    ClassUtils.convertClassNameToResourcePath(packageName)+ RESOURCE_PATTERN;
            Resource[] resources =resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory =new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource: resources) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);
                list.add(clazz);
            }
            return list;
        }catch(Exception e) {
            log.error(e.getMessage());
            return list;
        }
    }

}
