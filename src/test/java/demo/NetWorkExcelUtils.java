//package demo;
//
//import org.apache.commons.lang.StringUtils;
//import org.apache.poi.POIXMLDocument;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.openxml4j.opc.OPCPackage;
//import org.apache.poi.poifs.filesystem.POIFSFileSystem;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import javax.sound.midi.Soundbank;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.PushbackInputStream;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//
//public class NetWorkExcelUtils {
//
//    public static void main(String[] args) {
//        // 操作示例
//        String storeFileUrl = "E:\\文档汇总\\sql需求文档\\副本环球名车-网点端口对接.xlsx";
//        String netWorkFileUrl = "E:\\文档汇总\\sql需求文档\\store.xlsx";
//
//        oilOrder(storeFileUrl, netWorkFileUrl);
//    }
//
//
//    /**
//     */
//    public static String oilOrder(String storeFileUrl, String netWorkFileUrl) {
//        StringBuffer sb = new StringBuffer(256);
//        InputStream is = null;
//
//        String somes = "100000000175791" +
//                "100000000197549" +
//                "100000000197550" +
//                "100000000197551" +
//                "100000000197552" +
//                "100000000197553" +
//                "100000000197554" +
//                "100000000197596" +
//                "100000000197556" +
//                "100000000197557" +
//                "100000000197558" +
//                "100000000197559" +
//                "100000000197560" +
//                "100000000197561" +
//                "100000000197563" +
//                "100000000197565" +
//                "100000000197566" +
//                "100000000197569" +
//                "100000000197570" +
//                "100000000197571" +
//                "100000000197573" +
//                "100000000197575" +
//                "100000000197577" +
//                "100000000197578" +
//                "100000000197579" +
//                "100000000197580" +
//                "100000000197583" +
//                "100000000197584" +
//                "100000000197585" +
//                "100000000197589" +
//                "100000000197592" +
//                "100000000197593" +
//                "100000000197595" +
//                "100000000197528" +
//                "100000000197529" +
//                "100000000197531" +
//                "100000000197532" +
//                "100000000197534" +
//                "100000000197535" +
//                "100000000197536" +
//                "100000000197538" +
//                "100000000197539" +
//                "100000000197541" +
//                "100000000197542" +
//                "100000000197544" +
//                "100000000197546" +
//                "100000000197547" +
//                "100000000204991" +
//                "100000000204994" +
//                "100000000204995" +
//                "100000000204996" +
//                "100000000204997" +
//                "100000000205134" +
//                "100000000205135" +
//                "100000000204981" +
//                "100000000204983" +
//                "100000000204985" +
//                "100000000204989" +
//                "100000000209239" +
//                "100000000209240" +
//                "100000000209241" +
//                "100000000212878" +
//                "100000000212879" +
//                "100000000212880" +
//                "100000000212881" +
//                "100000000212882" +
//                "100000000212883" +
//                "100000000212884" +
//                "100000000212885" +
//                "100000000212886" +
//                "100000000212887" +
//                "100000000212888" +
//                "100000000212889" +
//                "100000000212890" +
//                "100000000212891" +
//                "100000000212893" +
//                "100000000236245" +
//                "100000000236246" +
//                "100000000236247" +
//                "100000000236248" +
//                "100000000236249" +
//                "100000000236250" +
//                "100000000236251" +
//                "100000000236252" +
//                "100000000236253" +
//                "100000000236254" +
//                "100000000236264" +
//                "100000000236265" +
//                "100000000236266" +
//                "100000000236267" +
//                "100000000236268" +
//                "100000000236269" +
//                "100000000236270" +
//                "100000000236271" +
//                "100000000236272" +
//                "100000000236255" +
//                "100000000236256" +
//                "100000000236257" +
//                "100000000236258" +
//                "100000000236259" +
//                "100000000236260" +
//                "100000000236261" +
//                "100000000236262" +
//                "100000000236263" +
//                "100000000236273" +
//                "100000000236275" +
//                "100000000236276" +
//                "100000000236277" +
//                "100000000236278" +
//                "100000000236281" +
//                "100000000236286" +
//                "100000000236287" +
//                "100000000236288" +
//                "100000000236291" +
//                "100000000236292" +
//                "100000000236293" +
//                "100000000236294" +
//                "100000000236296" +
//                "100000000236297" +
//                "100000000236299" +
//                "100000000236300" +
//                "100000000236302" +
//                "100000000236303" +
//                "100000000236304" +
//                "100000000236305" +
//                "100000000236306" +
//                "100000000236308" +
//                "100000000236309" +
//                "100000000236310" +
//                "100000000236311" +
//                "100000000236312" +
//                "100000000236313" +
//                "100000000236315" +
//                "100000000236316" +
//                "100000000236317" +
//                "100000000236318" +
//                "100000000247442" +
//                "100000000247444" +
//                "100000000247446" +
//                "100000000247447" +
//                "100000000247448" +
//                "100000000247449" +
//                "100000000247450" +
//                "100000000247452" +
//                "100000000247453" +
//                "100000000250261" +
//                "100000000250262" +
//                "100000000250266" +
//                "100000000250271" +
//                "100000000250277" +
//                "100000000250279" +
//                "100000000250280" +
//                "100000000250281" +
//                "100000000250283" +
//                "100000000250284" +
//                "100000000250285" +
//                "100000000250287" +
//                "100000000250290" +
//                "100000000250294" +
//                "100000000250297" +
//                "100000000250299" +
//                "100000000250300" +
//                "100000000250301" +
//                "100000000250302" +
//                "100000000250305" +
//                "100000000250228" +
//                "100000000250230" +
//                "100000000250231" +
//                "100000000250238" +
//                "100000000250239" +
//                "100000000250240" +
//                "100000000250241" +
//                "100000000250243" +
//                "100000000250246" +
//                "100000000250249" +
//                "100000000250251" +
//                "100000000250252" +
//                "100000000250253" +
//                "100000000250254" +
//                "100000000250256" +
//                "100000000250258" +
//                "100000000252580" +
//                "100000000252581" +
//                "100000000252582" +
//                "100000000252583" +
//                "100000000252584" +
//                "100000000252587" +
//                "100000000258078" +
//                "100000000258080" +
//                "100000000258081" +
//                "100000000258082" +
//                "100000000258083" +
//                "100000000258084" +
//                "100000000258085" +
//                "100000000263228" +
//                "100000000263229" +
//                "100000000281486" +
//                "100000000283230";
//
//        List<List<Object>> excelData = null;
//
//        System.out.println(storeFileUrl);
//        System.out.println(netWorkFileUrl);
//        try {
//            //rootPath是excel文件的路径
//            is = new FileInputStream(new File(storeFileUrl));
//
//
//            Workbook workBook = createWorkBook(is);
//
//            for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
//                Sheet hssfSheet = workBook.getSheetAt(numSheet);
//                if (hssfSheet == null) {
//                    continue;
//                }
//                //总行数
//                int lastRowNum = hssfSheet.getLastRowNum();
//                System.out.println("行数:" + lastRowNum);
//                int coloumNum = hssfSheet.getRow(0).getPhysicalNumberOfCells();
//                System.out.println("列数:" + coloumNum);
//
//                // 循环行Row，从第二行开始
//                for (int i = 1; i <= lastRowNum; i++) {
//
//                    Row hssfRow = hssfSheet.getRow(i);
//                    Row row = hssfSheet.getRow(i);
//                    if (null == hssfRow) {
//                        break;
//                    }
//                    Cell tCell = hssfRow.getCell(0);
//                    if (null == tCell) {
//                        break;
//                    }
//                    String str = getValue(tCell);
//                    if (null == str || str.trim().length() == 0) {
//                        break;
//                    }
//                    SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
//                    String yymmdd = sdf.format(new Date());
//
//
//
//                    Cell netWorkCell = row.getCell(2);
////                    System.out.println("--------"+netWorkCell);
//                    Cell netNameCell = row.getCell(3);
////                    System.out.println("**********"+netNameCell);
//
//
//
//
//                    if (netWorkCell == null || netNameCell == null) {
//                        continue;
//                    }
//                    String have = netWorkCell.toString().trim();
//                    if(!somes.contains(have)){
//                        System.out.println(netNameCell.toString().trim());
//                    }
//
//
//
//
//
////                    sql(netWorkCell.toString(), netNameCell.toString(), netWorkFileUrl);
//
//                }
//            }
//        } catch (Exception e) {
//            e.getMessage();
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.getMessage();
//                }
//            }
//        }
//        return sb.toString();
//    }
//
//    static void sql(String netWork, String netName, String fileName) {
////        System.out.println("调用sql方法,netWork="+netWork+"    netName"+netName);
////        System.out.println(a);
////        System.out.println(b);
//
//        StringBuffer sb = new StringBuffer(256);
//        InputStream is = null;
//        List<List<Object>> excelData = null;
//
//
//        try {
//            //rootPath是excel文件的路径
//            is = new FileInputStream(new File(fileName));
////            System.out.println(111111111);
//            Workbook workBook = createWorkBook(is);
////            System.out.println(222222);
//
//            for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
//                Sheet hssfSheet = workBook.getSheetAt(numSheet);
//                if (hssfSheet == null) {
//                    continue;
//                }
//                //总行数
//                int lastRowNum = hssfSheet.getLastRowNum();
////                System.out.println("行数new:" + lastRowNum);
//                int coloumNum = hssfSheet.getRow(0).getPhysicalNumberOfCells();
////                System.out.println("列数new:" + coloumNum);
//
//                // 循环行Row，从第二行开始
//                for (int i = 1; i <= lastRowNum; i++) {
//
//                    Row hssfRow = hssfSheet.getRow(i);
//                    Row row = hssfSheet.getRow(i);
//                    if (null == hssfRow) {
//                        break;
//                    }
//                    Cell tCell = hssfRow.getCell(0);
//                    if (null == tCell) {
//                        break;
//                    }
//                    String str = getValue(tCell);
//                    if (null == str || str.trim().length() == 0) {
//                        break;
//                    }
//
//                    String storeId = row.getCell(0).toString();
//                    String storeName = row.getCell(1).toString();
//
//                    if (netName.startsWith("1")) {
//                        netName = netName.replace("1", "");
//                    }
////                    System.out.println("调用sql方法,netWork="+netWork+"    netName"+netName);
////                    System.err.println("storeName:  "+storeName);
//
//                    String netNameTrim = netName.trim();
//                    String storeNameTrim = storeName.trim();
//
//
//                    if (netNameTrim.equals(storeNameTrim)) {
////  `store_pingan_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键id',
////  `netword_id` varchar(32) NOT NULL COMMENT '网点id',
////  `store_id` bigint(16) NOT NULL COMMENT '商户id',
////  `network_name` varchar(255) DEFAULT NULL COMMENT '网点（商户）名称',
//
//                        sb.append("insert into tb_store_pingan (store_id,netword_id,network_name) values(" + storeId + ",'" + netWork.trim() + "','" + netNameTrim + "');" + " -- " + storeNameTrim);
//                        System.out.println(sb.toString());
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("e.getMessage()" + e.getMessage());
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.getMessage();
//                }
//            }
//        }
//
//    }
//
//
//    //兼容性方法创建对象
//    public static Workbook createWorkBook(InputStream in) throws Exception {
//        if (!in.markSupported()) {
//            in = new PushbackInputStream(in, 8);
//        }
//        if (POIFSFileSystem.hasPOIFSHeader(in)) {
//            return new HSSFWorkbook(in);
//        }
//        if (POIXMLDocument.hasOOXMLHeader(in)) {
//            return new XSSFWorkbook(OPCPackage.open(in));
//        }
//        throw new Exception("MSG_此Excel版本目前无法解析");
//    }
//
//
//    @SuppressWarnings("static-access")
//    private static String getValue(Cell hssfCell) {
//        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
//            // 返回布尔类型的值
//            return String.valueOf(hssfCell.getBooleanCellValue());
//        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
//            return String.valueOf(hssfCell.getNumericCellValue());
//        } else {
//            // 返回字符串类型的值
//            return String.valueOf(hssfCell.getStringCellValue());
//        }
//    }
//
//
//}
