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

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OILExcelUtils {

    public static void main(String[] args) {
        // 操作示例
        String fileUrl = "E:\\文档汇总\\sql需求文档\\新增油卡订单20181015.xls";
        String filePath = "D:\\idea\\demo\\doc";
        String fileName = "oilOrder1015.txt";
        //count代表当天订单从count的值开始算
        String oilOrder = oilOrder(fileUrl, 1);
        if (oilOrder == null || oilOrder == "") {
            System.out.println("error,oilOrder is null or empry");
            return;
        }
        // 换行
        String oilOrderText = oilOrder.replaceAll(";", ";\r\n");
        File pathFile = new File(filePath);

        for(File file:pathFile.listFiles()) {
            if(file.isFile() && fileName.equals(file.getName())) {
                file.delete();
            }
        }
        //写入到文件
        boolean b = writeContent(oilOrderText, filePath,fileName, true);
        System.out.println(b);
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

    //读取Excel自动生成多条油卡sql语句，暂不支持打折商品

    /**
     * @param fileUrl
     * @param count   订单号起始编号
     * @return
     */
    public static String oilOrder(String fileUrl, int count) {
        StringBuffer sb = new StringBuffer(256);
        InputStream is = null;
        List<List<Object>> excelData = null;
        try {
            //rootPath是excel文件的路径
            is = new FileInputStream(new File(fileUrl));
            Workbook workBook = createWorkBook(is);

            for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
                Sheet hssfSheet = workBook.getSheetAt(numSheet);
                if (hssfSheet == null) {
                    continue;
                }
                //总行数
                int lastRowNum = hssfSheet.getLastRowNum();
//                System.out.println("行数:" + lastRowNum);
                int coloumNum = hssfSheet.getRow(0).getPhysicalNumberOfCells();
//                System.out.println("列数:" + coloumNum);
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
                    //油卡号编号后缀
                    StringBuilder oilLastId = new StringBuilder(8);
                    //兼容油卡交易号
                    if (count <= 0) {
                        System.out.println("error,count Not less than or equal to 0 ");
                        return null;

                    }
                    if (count < 10) {
                        oilLastId = oilLastId.append("00").append(count);
                    } else if (count >= 10 && count <= 99) {
                        oilLastId = oilLastId.append("0").append(count);
                    } else {
                        oilLastId = oilLastId.append(count);
                    }
                    count++;

                    Cell mobileCell = row.getCell(7);
                    DecimalFormat df = new DecimalFormat("#");
                    String mobile = df.format(mobileCell.getNumericCellValue());
                    Cell oilCardId = row.getCell(11);
                    Cell jifenCell = row.getCell(14);
                    String jifen = jifenCell.toString().replaceAll("\\.0", "");
                    Cell money = row.getCell(17);

                    StringBuffer sb1 = new StringBuffer(256);
                    StringBuffer sb2 = new StringBuffer(256);
                    StringBuffer sb3 = new StringBuffer(256);
                    StringBuffer sb4 = new StringBuffer(256);

                    sb1.append(
                            "INSERT INTO `tb_oil_order`(`oil_order_id`, `cust_id`, `order_time`, `order_amt`, `mark`, `order_status`, `order_result`, `card_type`, `card_number`, `snap_opt_type`, `snap_opt_name`, `snap_im_arrival_amt`, `snap_im_pay_amt`, `snap_st_rate`, `snap_st_month`, `snap_st_is_vip`, `order_original_price`, `last_recharge_time`, `last_recharge_amt`, `remain_count`, `ready_time`, `ready_amt`, `created_at`, `updated_at`, `delete`, `opt_name`, `month_amt`, `cust_deleted`) (SELECT 'OIL");

                    sb1.append(yymmdd + "X00000").append(oilLastId).append("', cust_id, now(), ").append(money + "000").append(", '手动生成', 2, 1, '中国石化'").append(", '" + oilCardId).
                            append("', 0, '油卡', ").append(money + "000, " + money + "000").append(", 1.0000, 1, NULL").append(", " + money + "000").append(", NULL, NULL, 1, now()").append(", " + money + "000").append(", now(), now(), 0, NULL, NULL, 0 from tb_cust WHERE mobile = '").append(mobile + "');");

                    sb2.append("INSERT INTO `tb_journal_account`(`cust_id`, `order_id`, `order_type`, `order_time`, `order_day`, `order_hour`, `pay_finished`, `pay_msg`, `pay_time`, `pay_day`, `pay_hour`, `order_amt`, `pay_amt`, `point_cost`, `coupon_id`, `coupon_cost`, `ab_code_id`, `ab_deduction_price`, `pay_opt`, `mark`, `created_at`, `updated_at`, `order_name`, `delete`, `platform`, `callback_state`, `renbao_cost`) (SELECT cust_id, '").
                            append("OIL" + yymmdd + "X00000").append(oilLastId).append("', 'oil', now(),").append(" 20" + yymmdd).append(", 0, 1, '0元支付', now(), NULL, NULL, ").
                            append(money + "000" + ", 0.0000, " + jifen).append(", NULL, NULL, NULL, NULL, '0元支付', NULL, now(), now(), '油卡订单', 0, NULL, 0, NULL from tb_cust WHERE mobile = '").append(mobile + "');");

                    sb3.append("INSERT INTO `tb_cust_point`( `cust_id`, `point_amt`, `is_add`, `order_id`, `order_name`, `created_at`, `updated_at`) (SELECT cust_id, ").append(jifen).append(", 0, '").append("OIL" + yymmdd + "X00000").append(oilLastId).append("', '油卡', now(), now() from tb_cust WHERE mobile = '").append(mobile + "');");

                    sb4.append("UPDATE tb_cust SET point = point - ").append(jifen).append(" WHERE mobile = '").append(mobile).append("';");
                    System.out.println(sb1);
                    System.out.println(sb2);
                    System.out.println(sb3);
                    System.out.println(sb4);


                    sb.append(sb1).append(sb2).append(sb3).append(sb4);
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

    /**
     * 写入内如到文件
     *
     * @param c        写入内容
     * @param dirname  文件夹名称
     * @param filename 文件名，推荐txt
     * @param isAppend
     * @return
     */
    public static boolean writeContent(String c, String dirname, String filename, boolean isAppend) {
        File f = new File(dirname);
        if (!f.exists()) {
            f.mkdirs();
        }
        try {
            FileOutputStream fos = new FileOutputStream(dirname + File.separator + filename, isAppend);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(c);
            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
