package demo.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据表生成Entity文件
 *
 * 修改test_Table()方法中的参数值,即可执行main方法获得想要的结果
 *
 * 生成文件后导入需要的包
 * 依赖包:mysql数据库驱动包
 * 生成其他数据库类型的表需要更换驱动类并略做数据类型转换的修改即可
 *
 * 注意:数据库数据类型只包括了常用的几种,其他数据类型还需要在完善,请添加‘ftype’该数组变量的值
 *
 */
public class TableToEntity {

    public static void main(String args[]) {
        test_Table();
    }

    /**
     * 设置参数--需要修改
     */
    public static void test_Table() {
        String tableName = "tb_daily";//需要转换的表
        String host = "127.0.0.1";//数据库host
        String port = "3306";//数据库端口
        String database = "work";//数据库名
        String username = "root";//数据库用户名
        String password = "admin";//数据库用密码
        String path = "F:/file/";//生成文件存放目录,不包括文件名,文件名自动命名为 '表名.java,表名Mapper.xml,表名Dao.java' 首字母大写
        String dao_package = "com.test.dao.";//dao的包路径
        String entity_package = "com.test.entity.";//entity的包路径
        //生成entity文件
        getTableToEntityFile(tableName, host, port, database, username, password, entity_package,
                path);
        //生成ResultMap文件
        getTableToResultMap(tableName, host, port, database, username, password, dao_package,
                entity_package, path);
        //生成Dao文件
        getResultMapToDao(tableName, dao_package, entity_package, path);
    }

    /**
     * 根据表生成Entity文件
     */
    public static void getTableToEntityFile(String tableName, String host, String port,
                                            String database, String username, String password,
                                            String entity_package, String path) {
        String strforname = "com.mysql.jdbc.Driver";
        String strurl = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";
        //需要替换的数据类型,待完善
        String ftype[][] = new String[][] { { "tinyint unsigned", "Integer" },
                { "tinyint", "Integer" }, { "int unsigned", "Integer" }, { "int", "Integer" },
                { "smallint unsigned", "Integer" }, { "smallint", "Integer" },
                { "varchar", "String" }, { "char", "String" }, { "longtext", "String" },
                { "text", "String" }, { "decimal unsigned", "BigDecimal" },
                { "decimal", "BigDecimal" }, { "datetime", "String" }, { "date", "String" },
                { "timestamp", "String" }, { "unsigned", "" } };

        //如果包路径是以“.”结尾的要去掉
        entity_package = entity_package == null ? "" : entity_package;
        entity_package = entity_package.length() > 0 && entity_package.endsWith(".") ? entity_package
                .substring(0, entity_package.length() - 1) : entity_package;

        System.out.println("==============================");
        System.out.println("正在生成。。。");
        String sql = "SELECT * FROM " + tableName + " limit 0";
        List<Map<String, String>> list = queryFieldList(sql, strforname, strurl, username, password);

        //模板字符串
        String propertystr = "\tprivate #fieldtype# #fieldname#;\n";

        String methodget1 = "\tpublic #fieldtype# get#fieldname1#() {\n";
        String methodget2 = "\t\treturn #fieldname#;\n";
        String methodget3 = "\t}\n";

        String methodset1 = "\tpublic void set#fieldname1#(#fieldtype# #fieldname#) {\n";
        String methodset2 = "\t\tthis.#fieldname# = #fieldname#;\n";
        String methodset3 = "\t}\n";

        String classstrstart = "package "
                + entity_package
                + ";\n\nimport java.io.Serializable;\nimport java.math.BigDecimal;\n\npublic class #tablename# implements Serializable {\n";
        String classstrend = "}";

        String class_pro = "";//类拼接
        if (list != null && list.size() > 0) {
            for (Map<String, String> map : list) {
                for (Object key : map.keySet()) {
                    String fieldtype = map.get(key).toString().toLowerCase();
                    String fieldname = key.toString();
                    //替换数据类型
                    for (int i = 0; i < ftype.length; i++) {
                        fieldtype = fieldtype.replace(ftype[i][0], ftype[i][1]);
                    }
                    //属性
                    class_pro = class_pro + "\n";
                    class_pro = class_pro
                            + propertystr.replace("#fieldtype#", fieldtype).replace(
                            "#fieldname#",
                            setStringFirstLetterLower(setStringHump(fieldname, "_")));
                    String fieldnameU = setStringFirstLetterUpper(setStringHump(fieldname, "_"));//首字母大写字符串

                    //get方法
                    class_pro = class_pro + "\n";
                    class_pro = class_pro
                            + methodget1.replace("#fieldtype#", fieldtype).replace(
                            "#fieldname1#", fieldnameU)
                            + methodget2.replace("#fieldname#",
                            setStringFirstLetterLower(setStringHump(fieldname, "_")))
                            + methodget3;

                    //set方法
                    class_pro = class_pro + "\n";
                    class_pro = class_pro
                            + methodset1
                            .replace("#fieldtype#", fieldtype)
                            .replace("#fieldname1#", fieldnameU)
                            .replace("#fieldname#",
                                    setStringFirstLetterLower(setStringHump(fieldname, "_")))
                            + methodset2.replace("#fieldname#",
                            setStringFirstLetterLower(setStringHump(fieldname, "_")))
                            + methodset3;
                }
            }
        }
        //添加方法头
        class_pro = classstrstart.replace("#tablename#",
                setStringFirstLetterUpper(setStringHump(tableName, "_")))
                + class_pro;
        //添加方法尾
        class_pro = class_pro + classstrend;

        System.out.println("==============================");
        //生成文件名及保存路径
        path = path + setStringFirstLetterUpper(setStringHump(tableName, "_")) + ".java";
        //写文件
        write(class_pro, path);

        System.out.println("javabean文件已生成,请查看:" + path);
    }

    /**
     * 根据表生成resultMap配置,及select,insert,update全配置
     */
    public static void getTableToResultMap(String tableName, String host, String port,
                                           String database, String username, String password,
                                           String dao_package, String entity_package, String path) {
        String strforname = "com.mysql.jdbc.Driver";
        String strurl = "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull";

        System.out.println("==============================");
        System.out.println("正在生成。。。");
        String sql = "SELECT * FROM " + tableName + " limit 0";
        List<Map<String, String>> list = queryFieldList(sql, strforname, strurl, username, password);

        //如果包路径不是以“.”结尾的要补齐
        dao_package = dao_package == null ? "" : dao_package;
        dao_package = dao_package.length() > 0 && !dao_package.endsWith(".") ? dao_package + "."
                : dao_package;
        entity_package = entity_package == null ? "" : entity_package;
        entity_package = entity_package.length() > 0 && !entity_package.endsWith(".") ? entity_package
                + "."
                : entity_package;
        //模板字符串
        String propertystr = "\t\t<result property=\"#fieldname2#\" column=\"#fieldname#\" />\n";

        String mapstart = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n<mapper namespace=\""
                + dao_package + "#tablefilename1#Dao\">\n\n";
        String mapend = "</mapper>";
        String resultstart = "\t<resultMap id=\"#tablefilename2#Result\" type=\"" + entity_package
                + "#tablefilename1#\">\n";
        String resultend = "\t</resultMap>\n\n";
        String selectstart = "\t<select id=\"get\" resultMap=\"#tablefilename2#Result\">\n";
        String selectend = "\t</select>\n\n";
        String insertstart = "\t<insert id=\"insert\" parameterType=\"" + entity_package
                + "#tablefilename1#\">\n";
        String insertend = "\t</insert>\n\n";
        String updatestart = "\t<update id=\"update\" parameterType=\"" + entity_package
                + "#tablefilename1#\">\n";
        String updateend = "\t</update>\n\n";

        String resultMap = "";//字段拼接
        String select_field = "";//select拼接
        String insert_field = "";//insert拼接
        String insert_value = "";//insert value拼接
        String update_set = "";//update set拼接
        if (list != null && list.size() > 0) {
            for (Map<String, String> map : list) {
                for (Object key : map.keySet()) {
                    String fieldname = key.toString();
                    //resultMap
                    resultMap = resultMap
                            + propertystr.replace("#fieldname2#",
                            setStringFirstLetterLower(setStringHump(fieldname, "_")))
                            .replace("#fieldname#", fieldname);
                    //select
                    select_field = select_field + "`" + fieldname + "`,";
                    insert_field = insert_field + "`" + fieldname + "`,";
                    insert_value = insert_value + "#{"
                            + setStringFirstLetterLower(setStringHump(fieldname, "_"))
                            + "},";
                    update_set = update_set + "`" + fieldname + "`=#{"
                            + setStringFirstLetterLower(setStringHump(fieldname, "_")) + "},";
                }
            }
        }

        resultMap = resultstart.replace("#tablename#",
                setStringFirstLetterUpper(setStringHump(tableName, "_")))
                + resultMap;
        resultMap = resultMap + resultend;
        if (select_field.endsWith(",")) {
            select_field = select_field.substring(0, select_field.length() - ",".length());
        }
        if (insert_field.endsWith(",")) {
            insert_field = insert_field.substring(0, insert_field.length() - ",".length());
        }
        if (insert_value.endsWith(",")) {
            insert_value = insert_value.substring(0, insert_value.length() - ",".length());
        }
        if (update_set.endsWith(",")) {
            update_set = update_set.substring(0, update_set.length() - ",".length());
        }

        //拼接sql语句
        String select = "\t\tselect " + select_field + " from " + tableName + "\n";
        String insert = "\t\tinsert into " + tableName + "(" + insert_field + ")values("
                + insert_value + ")" + "\n";
        String update = "\t\tupdate " + tableName + " set " + update_set + "\n";

        //        System.out.println("select:\n" + select);
        //        System.out.println("insert:\n" + insert);
        //        System.out.println("update:\n" + update);
        //生成select语句的Map配置
        String selectMap = selectstart + select + selectend;
        //生成insert语句的Map配置
        String insertMap = insertstart + insert + insertend;
        //生成update语句的Map配置
        String updateMap = updatestart + update + updateend;
        //各个Map配置组合
        resultMap = resultMap + selectMap + insertMap + updateMap;
        //添加Map文件的头和尾
        resultMap = mapstart + resultMap + mapend;
        String tablefilename1 = setStringFirstLetterUpper(setStringHump(tableName, "_"));
        String tablefilename2 = setStringFirstLetterLower(setStringHump(tableName, "_"));
        resultMap = resultMap.replace("#tablefilename1#", tablefilename1);
        resultMap = resultMap.replace("#tablefilename2#", tablefilename2);
        //打印Map文件
        //        System.out.println("==============================");
        //        System.out.println("resultMap:\n" + resultMap);
        //        System.out.println("==============================");
        //生成文件名及保存路径

        System.out.println("==============================");
        path = path + setStringFirstLetterUpper(setStringHump(tableName, "_")) + "Mapper.xml";
        //写文件
        write(resultMap, path);
        System.out.println("Map文件已生成,请查看:" + path);
    }

    /**
     * 根据resultMap生成Dao文件
     */
    public static void getResultMapToDao(String tableName, String dao_package,
                                         String entity_package, String path) {

        System.out.println("==============================");
        System.out.println("正在生成。。。");

        //如果包路径是以“.”结尾的要去掉
        dao_package = dao_package == null ? "" : dao_package;
        dao_package = dao_package.length() > 0 && dao_package.endsWith(".") ? dao_package
                .substring(0, dao_package.length() - 1) : dao_package;
        //如果包路径不是以“.”结尾的要补齐
        entity_package = entity_package == null ? "" : entity_package;
        entity_package = entity_package.length() > 0 && !entity_package.endsWith(".") ? entity_package
                + "."
                : entity_package;

        //模板字符串
        String daostart = "package " + dao_package + ";\n\nimport " + entity_package
                + setStringFirstLetterUpper(setStringHump(tableName, "_"))
                + ";\n\npublic interface #tablefilename1#Dao {\n\n";
        String daoend = "}";
        String get = "\t#tablefilename1# get(Integer id);\n\n";
        String insert = "\tint insert(#tablefilename1# #tablefilename2#);\n\n";
        String update = "\tint update(#tablefilename1# #tablefilename2#);\n\n";

        String classstr = "";
        classstr = classstr
                + get.replace("#tablefilename1#",
                setStringFirstLetterUpper(setStringHump(tableName, "_")));
        classstr = classstr
                + insert.replace("#tablefilename1#",
                setStringFirstLetterUpper(setStringHump(tableName, "_")))
                .replace("#tablefilename2#",
                        setStringFirstLetterLower(setStringHump(tableName, "_")));
        classstr = classstr
                + update.replace("#tablefilename1#",
                setStringFirstLetterUpper(setStringHump(tableName, "_")))
                .replace("#tablefilename2#",
                        setStringFirstLetterLower(setStringHump(tableName, "_")));

        classstr = daostart.replace("#tablefilename1#",
                setStringFirstLetterUpper(setStringHump(tableName, "_")))
                + classstr;
        classstr = classstr + daoend;

        //打印Map文件
        //        System.out.println("==============================");
        //        System.out.println("classstr:\n" + classstr);
        //        System.out.println("==============================");
        //生成文件名及保存路径

        System.out.println("==============================");
        path = path + setStringFirstLetterUpper(setStringHump(tableName, "_")) + "Dao.java";
        //写文件
        write(classstr, path);
        System.out.println("Dao文件已生成,请查看:" + path);
    }

    /**
     * 查询表的元数据信息
     * @return
     */
    public static List<Map<String, String>> queryFieldList(String querySql, String strforname,
                                                           String strurl, String username,
                                                           String password) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection(strforname, strurl, username, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(querySql);
            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put(rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    /**
     * 设置字符串驼峰格式
     * @param str
     * @param regex_split 驼峰分隔符
     * @return
     */
    private static String setStringHump(String str, String regex_split) {
        if (str == null) {
            return "";
        }
        if (regex_split == null || regex_split.equals("")) {
            regex_split = "_";
        }
        String temp_str = "";
        String sp[] = str.split(regex_split);
        if (sp != null && sp.length > 0) {
            for (int i = 0; i < sp.length; i++) {
                temp_str += setStringFirstLetterUpper(sp[i]);
            }
        }

        return temp_str;
    }

    /**
     * 字符串首字母大写
     * @param str
     * @return
     */
    private static String setStringFirstLetterUpper(String str) {
        if (str == null) {
            return "";
        }
        String str1 = "";
        String str2 = "";
        if (str.length() > 1) {
            str1 = str.substring(0, 1).toUpperCase();
            str2 = str.substring(1);
            str = str1 + str2;
        } else {
            str = str.toUpperCase();
        }
        return str;
    }

    /**
     * 字符串首字母小写
     * @param str
     * @return
     */
    private static String setStringFirstLetterLower(String str) {
        if (str == null) {
            return "";
        }
        String str1 = "";
        String str2 = "";
        if (str.length() > 1) {
            str1 = str.substring(0, 1).toLowerCase();
            str2 = str.substring(1);
            str = str1 + str2;
        } else {
            str = str.toLowerCase();
        }
        return str;
    }

    /**
     * 生成文件
     * @return
     */
    private static void write(String str, String path) {
        FileWriter fw = null;
        PrintWriter out = null;
        try {
            File file = new File(path);
            //            int n = 1;
            //            while (file.exists()) {
            //                if (n > 10) {
            //                    return;
            //                }
            //                n++;
            //                String fpath = path.replace(".", "[" + n + "].");
            //                file = new File(fpath);
            //            }
            fw = new FileWriter(file, false);
            out = new PrintWriter(fw);
            out.print(str);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection(String strforname, String strurl, String username,
                                           String password) {
        String className = strforname;
        Connection conn = null;
        try {
            Class.forName(className);
            String url = strurl;
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}