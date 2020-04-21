package com.gentle.cache.config;

import com.gentle.cache.open.EnableCacheManager;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

/**
 * @author Gentle
 * @date 2020/04/09 : 00:02
 */
public abstract class AbstractCacheConfiguration implements ImportAware {
    @Nullable
    protected AnnotationAttributes enableCaching;

    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        this.enableCaching = AnnotationAttributes.fromMap(
                annotationMetadata.getAnnotationAttributes(EnableCacheManager.class.getName(), false));
    }
}

