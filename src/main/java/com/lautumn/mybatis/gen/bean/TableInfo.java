package com.lautumn.mybatis.gen.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author: Lautumn
 * @Describe:
 * @Date: Create in 16:29 2019/4/25
 */
@Data
public class TableInfo {

    private String tableName;

    private String comment;

    private String engine;

    private Boolean isCreate;

    private List<Column> columnList;

}
