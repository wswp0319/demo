//package demo.utils;
//
//import org.apache.commons.lang.StringUtils;
//
//public class SqlWhereUtils {
//
//    public static void main(String[] args) {
//
//        //使用实例
//        String sqlStr = " id, cust_id, bar_code, type, remark, create_time  ";
//        generateSqlColumns(sqlStr);
//        generateSqlWhere(sqlStr);
//    }
//
//    private static void generateSqlWhere(String sqlStr) {
//        isBlank(sqlStr);
//        String sqlColumnStr = sqlStr.replaceAll(" ", "");
//        String[] sqlColumnsStrs = sqlColumnStr.split(",");
//
//        StringBuilder sb = new StringBuilder();
//        sb.append("<sql id=\"sqlWhere\">" + "\r\n" + "    <where>");
//        int length = sqlColumnsStrs.length;
//
//        sb.append("\r\n" + "    ");
//        for (int y = 0; y < length; y++) {
//            if (!sqlColumnsStrs[y].contains("_")) {
//                sb.append("    <if test=\"" + sqlColumnsStrs[y] + " != null\"> and " + sqlColumnsStrs[y] + " = #{" + sqlColumnsStrs[y] + "}</if>" + "\r\n" + "    ");
//            } else {
//                String[] split = sqlColumnsStrs[y].split("_");
//                StringBuilder temp = new StringBuilder();
//                spliceStr(split, temp);
//                sb.append("    <if test=\"" + split[0] + temp + " != null\">" + " and " + sqlColumnsStrs[y] + " = #{" + split[0] + temp + "}</if>" + " \r\n" + "    ");
//            }
//        }
//        sb.append("</where>");
//        sb.append("\r\n" + "</sql>");
//        System.out.println(sb.toString());
//        System.out.println();
//    }
//
//    private static void generateSqlColumns(String sqlStr) {
//        isBlank(sqlStr);
//        String sqlColumnStr = sqlStr.replaceAll(" ", "");
//        String[] sqlColumnsStrs = sqlColumnStr.split(",");
//
//        StringBuilder sb = new StringBuilder();
//        StringBuilder stringBuilder = new StringBuilder();
//        sb.append("<sql id=\"sqlColumns\">" + "\r\n" + "    ");
//        int length = sqlColumnsStrs.length;
//
//        for (int y = 0; y < length; y++) {
//            if (!sqlColumnsStrs[y].contains("_")) {
//                sb.append(sqlColumnsStrs[y] + " " + sqlColumnsStrs[y] + ", " + "\r\n" + "    ");
//            } else {
//                String[] split = sqlColumnsStrs[y].split("_");
//                StringBuilder temp = new StringBuilder();
//                spliceStr(split, temp);
//                sb.append(sqlColumnsStrs[y] + " " + split[0] + temp + ", \r\n" + "    ");
//            }
//        }
//
//        String trimStr = sb.toString().trim();
//        String sub = trimStr.substring(0, trimStr.length() - 1);
//        stringBuilder.append(sub);
//        stringBuilder.append("\r\n" + "</sql>");
//        System.out.println(stringBuilder.toString());
//        System.out.println();
//    }
//
//    private static void isBlank(String sqlStr) {
//        if (StringUtils.isBlank(sqlStr)) {
//            throw new RuntimeException("sqlColumns is null or empty,please check than try again!");
//        }
//    }
//
//    private static void spliceStr(String[] split, StringBuilder temp) {
//        for (int x = 0; x < split.length; x++) {
//            if (x == 0) {
//                continue;
//            }
//            char[] chars = split[x].toCharArray();
//            chars[0] -= 32;
//            String s = String.valueOf(chars);
//            temp.append(s);
//        }
//    }
//}