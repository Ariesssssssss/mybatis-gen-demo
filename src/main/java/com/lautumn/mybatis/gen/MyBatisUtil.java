package com.lautumn.mybatis.gen;

import com.alibaba.fastjson.JSON;
import com.lautumn.mybatis.gen.anno.GenColumn;
import com.lautumn.mybatis.gen.anno.GenTable;
import com.lautumn.mybatis.gen.bean.Column;
import com.lautumn.mybatis.gen.bean.Gen;
import com.lautumn.mybatis.gen.bean.TableInfo;
import com.lautumn.mybatis.gen.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Lautumn
 * @Describe:
 * @Date: Create in 16:03 2019/4/25
 */
public class MyBatisUtil {
    private static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();
    private static SqlSessionFactory sqlSessionFactory;

    /**
     * 加载位于src/mybatis.xml配置文件
     */
    static {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取SqlSession
     */
    public static SqlSession getSqlSession() {
        //从当前线程中获取SqlSession对象
        SqlSession sqlSession = threadLocal.get();
        //如果SqlSession对象为空
        if (sqlSession == null) {
            //在SqlSessionFactory非空的情况下，获取SqlSession对象
            sqlSession = sqlSessionFactory.openSession();
            //将SqlSession对象与当前线程绑定在一起
            threadLocal.set(sqlSession);
        }
        //返回SqlSession对象
        return sqlSession;
    }


    /**
     * 测试
     */
    public static void main(String[] args) {
        Class clazz = User.class;
        TableInfo tableInfo = extractingTableInfo(clazz);
        doCreateTable(tableInfo);
    }

    private static void doCreateTable(TableInfo tableInfo) {
        SqlSession sqlSession = MyBatisUtil.getSqlSession();
        Gen mapper = sqlSession.getMapper(Gen.class);
        if (tableInfo.getIsCreate()){
            mapper.createTable(tableInfo,tableInfo.getColumnList());
            System.out.println(JSON.toJSONString(tableInfo));

        }

    }

    private static TableInfo extractingTableInfo(Class clazz) {
        GenTable genTable = (GenTable) clazz.getAnnotation(GenTable.class);
        TableInfo tableInfo = new TableInfo();
        if (genTable == null) {
            throw new RuntimeException("实体类中没有添加GenTable注解");
        }
        // 校验数据库和表名

        checkGenTable(genTable);

        String tableName = genTable.name();
        String engine = genTable.engine();
        String comment = genTable.comment();
        boolean isCreate = genTable.create();
        tableInfo.setTableName(tableName);
        tableInfo.setEngine(engine);
        tableInfo.setComment(comment);
        tableInfo.setIsCreate(isCreate);

        Field[] fields = clazz.getDeclaredFields();
        if (fields == null ||fields.length == 0){
            throw new RuntimeException("实体类没有任何有效字段");
        }
        List<Column> columnList = extractingColumn(fields);
        tableInfo.setColumnList(columnList);

        return tableInfo;
    }

    private static List<Column> extractingColumn(Field[] fields) {
        List<Column> columnList = new ArrayList<>();
        for (Field f : fields) {
            GenColumn genColumn = f.getAnnotation(GenColumn.class);
            if (genColumn == null){
                continue;
            }
           String columnDefinition = genColumn.columnDefinition();
            String name = genColumn.name();

            if (StringUtils.isBlank(name)){
                // 驼峰命名
                String fieldName = f.getName();
                name = humpToLine2(fieldName);
            }
            Column column = new Column();
            column.setColumnDefinition(columnDefinition);
            column.setName(name);
            columnList.add(column);
        }
        return columnList;
    }

    private static void checkGenTable(GenTable genTable) {
        String tableName = genTable.name();
        if ( StringUtils.isBlank(tableName)) {
            throw new RuntimeException("表名称不能为空");
        }
    }

    private static Pattern humpPattern = Pattern.compile("[A-Z]");

    private static String humpToLine2(String str){
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
