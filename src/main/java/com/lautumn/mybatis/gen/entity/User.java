package com.lautumn.mybatis.gen.entity;

import com.lautumn.mybatis.gen.anno.GenColumn;
import com.lautumn.mybatis.gen.anno.GenTable;
import lombok.Data;

/**
 * @Author: Lautumn
 * @Describe:
 * @Date: Create in 16:07 2019/4/25
 */
@Data
@GenTable(name = "user")
public class User {

    @GenColumn(columnDefinition = "int(11) auto_increment primary key comment '主键'")
    private Integer id;

    @GenColumn(columnDefinition = "varchar(63) default '' not null comment '用户名称'")
    private String userName;

    @GenColumn(columnDefinition = "varchar(63) default '' not null comment '密码'")
    private String password;
}
