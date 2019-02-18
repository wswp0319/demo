package demo;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class NetWorkExcelUtils2 {

    public static void main(String[] args) {
        // 操作示例
        String storeFileUrl = "E:\\文档汇总\\sql需求文档\\01.xlsx";
        String netWorkFileUrl = "E:\\文档汇总\\sql需求文档\\store.xlsx";

        oilOrder(storeFileUrl, netWorkFileUrl);
    }


    /**
     */
    public static String oilOrder(String storeFileUrl, String netWorkFileUrl) {
        StringBuffer sb = new StringBuffer(256);
        InputStream is = null;

        List<List<Object>> excelData = null;

        System.out.println(storeFileUrl);
        System.out.println(netWorkFileUrl);
        try {
            //rootPath是excel文件的路径
            is = new FileInputStream(new File(storeFileUrl));


            Workbook workBook = createWorkBook(is);

            for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = workBook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                //总行数
                int lastRowNum = hssfSheet.getLastRowNum();
                System.out.println("行数:" + lastRowNum);
                int coloumNum = hssfSheet.getRow(0).getPhysicalNumberOfCells();
                System.out.println("列数:" + coloumNum);

                // 循环行Row，从第二行开始
                for (int i = 1; i <= lastRowNum; i++) {

                    Row hssfRow = hssfSheet.getRow(i);
                    Row row = hssfSheet.getRow(i);
                    if (null == hssfRow) {
                        break;
                    }
                    Cell tCell = hssfRow.getCell(0);
                    if (null == tCell) {
                        break;
                    }
                    String str = getValue(tCell);
                    if (null == str || str.trim().length() == 0) {
                        break;
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("YYMMdd");
                    String yymmdd = sdf.format(new Date());



                    Cell netWorkCell = row.getCell(0);
//                    System.out.println("--------"+netWorkCell);
                    Cell netNameCell = row.getCell(1);
                    Cell storeIdCell = row.getCell(2);
//                    System.out.println("**********"+netNameCell);


                    String netWork = netWorkCell.toString().trim();
                    String netNameTrim = netNameCell.toString().trim();
                    String storeId = storeIdCell.toString().trim().replace(".0","");

                    StringBuilder sb1 = new StringBuilder(100);
                    sb1.append("insert into tb_store_pingan (store_id,netword_id,network_name) values(" + storeId + ",'" + netWork.trim() + "','" + netNameTrim + "');");
                    System.out.println(sb1.toString());

//                    sql(netWorkCell.toString(), netNameCell.toString(), netWorkFileUrl);

                }
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
        return sb.toString();
    }

    static void sql(String netWork, String netName, String fileName) {
//        System.out.println("调用sql方法,netWork="+netWork+"    netName"+netName);
//        System.out.println(a);
//        System.out.println(b);

        StringBuffer sb = new StringBuffer(256);
        InputStream is = null;
        List<List<Object>> excelData = null;


        try {
            //rootPath是excel文件的路径
            is = new FileInputStream(new File(fileName));
//            System.out.println(111111111);
            Workbook workBook = createWorkBook(is);
//            System.out.println(222222);

            for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = workBook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                //总行数
                int lastRowNum = hssfSheet.getLastRowNum();
//                System.out.println("行数new:" + lastRowNum);
                int coloumNum = hssfSheet.getRow(0).getPhysicalNumberOfCells();
//                System.out.println("列数new:" + coloumNum);

                // 循环行Row，从第二行开始
                for (int i = 1; i <= lastRowNum; i++) {

                    Row hssfRow = hssfSheet.getRow(i);
                    Row row = hssfSheet.getRow(i);
                    if (null == hssfRow) {
                        break;
                    }
                    Cell tCell = hssfRow.getCell(0);
                    if (null == tCell) {
                        break;
                    }
                    String str = getValue(tCell);
                    if (null == str || str.trim().length() == 0) {
                        break;
                    }

                    String storeId = row.getCell(0).toString();
                    String storeName = row.getCell(1).toString();

                    if (netName.startsWith("1")) {
                        netName = netName.replace("1", "");
                    }
//                    System.out.println("调用sql方法,netWork="+netWork+"    netName"+netName);
//                    System.err.println("storeName:  "+storeName);

                    String netNameTrim = netName.trim();
                    String storeNameTrim = storeName.trim();


                    if (netNameTrim.equals(storeNameTrim)) {
//  `store_pingan_id` bigint(32) NOT NULL AUTO_INCREMENT COMMENT '主键id',
//  `netword_id` varchar(32) NOT NULL COMMENT '网点id',
//  `store_id` bigint(16) NOT NULL COMMENT '商户id',
//  `network_name` varchar(255) DEFAULT NULL COMMENT '网点（商户）名称',

                        sb.append("insert into tb_store_pingan (store_id,netword_id,network_name) values(" + storeId + ",'" + netWork.trim() + "','" + netNameTrim + "');" + " -- " + storeNameTrim);
                        System.out.println(sb.toString());
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("e.getMessage()" + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }

    }


    //兼容性方法创建对象
    public static Workbook createWorkBook(InputStream in) throws Exception {
        if (!in.markSupported()) {
            in = new PushbackInputStream(in, 8);
        }
        if (POIFSFileSystem.hasPOIFSHeader(in)) {
            return new HSSFWorkbook(in);
        }
        if (POIXMLDocument.hasOOXMLHeader(in)) {
            return new XSSFWorkbook(OPCPackage.open(in));
        }
        throw new Exception("MSG_此Excel版本目前无法解析");
    }


    @SuppressWarnings("static-access")
    private static String getValue(Cell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }


}
