package com.xiao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Description: shiro数据源
 * User: xiaojixiang
 * Date: 2017/4/24
 * Version: 1.0
 */

@Configuration
public class ShiroDataSourceConfig {

    @Value("${druid.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "myDataSource")
    @ConfigurationProperties(prefix = "druid.orderDBMaster")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
}
