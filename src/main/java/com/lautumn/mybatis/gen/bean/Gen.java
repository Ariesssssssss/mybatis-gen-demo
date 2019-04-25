package com.lautumn.mybatis.gen.bean;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Lautumn
 * @Describe:
 * @Date: Create in 16:09 2019/4/25
 */
public interface Gen {

    int createTable(@Param("tableInfo") TableInfo tableInfo,
                    @Param("columnList") List<Column> columnList);
}
