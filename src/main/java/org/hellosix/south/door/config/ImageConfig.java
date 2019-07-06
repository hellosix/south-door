package org.hellosix.south.door.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

/**
 * @author Jay.H.Zou
 * @date 7/6/2019
 */
@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Value("${south-door.data.site-image-path}")
    private String siteImagePath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + siteImagePath);
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大KB,MB
        factory.setMaxFileSize(DataSize.ofBytes(10485760));
        //设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofBytes(10485760));
        return factory.createMultipartConfig();
    }

}
