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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PushbackInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DriverExcelUtils {


    public static void main(String[] args) {
        // 操作示例
        String fileUrl = "E:\\文档汇总\\sql需求文档\\yy_driver_check.xls";

        oilOrder(fileUrl, 1);
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

                    /**
                     * 	uuid,
                     * 	agent_uuid,
                     * 	company_uuid,
                     * 	name,
                     * 	face,
                     * 	mobile,
                     * 	license_num,
                     * 	license_pic,
                     * 	driving_licence_home_img,
                     * 	driving_licence_sub_img,
                     * 	driving_num,
                     * 	driving_registered_time,
                     * 	driving_pic,
                     * 	driving_pic_back,
                     * 	driving_time,
                     * 	id_card,
                     * 	id_card_face_img,
                     * 	id_card_back_img,
                     * 	full_driver_id_card_img,
                     * 	certificate_no,
                     * 	get_driver_license_date,
                     * 	network_car_proof_on,
                     * 	network_car_proof_off,
                     * 	certificate_image,
                     * 	license_no,
                     * 	plate_num,
                     * 	type,
                     * 	status,写死=1
                     * 	appid,
                     * 	create_time,
                     * 	update_time,
                     * 	update_on
                     */
                    Cell uuid = row.getCell(0);
                    Cell agent_uuid = row.getCell(1);
                    Cell company_uuid = row.getCell(2);
                    Cell name = row.getCell(3);
                    Cell face = row.getCell(4);
                    Cell mobile = row.getCell(5);
                    Cell license_num = row.getCell(6);
                    Cell license_pic = row.getCell(7);
                    Cell driving_licence_home_img = row.getCell(8);
                    Cell driving_licence_sub_img = row.getCell(9);
                    Cell driving_num = row.getCell(10);
                    Cell driving_registered_time = row.getCell(11);
                    Cell driving_pic = row.getCell(12);
                    Cell driving_pic_back = row.getCell(13);
                    Cell driving_time = row.getCell(14);
                    Cell id_card = row.getCell(15);
                    Cell id_card_face_img = row.getCell(16);
                    Cell id_card_back_img = row.getCell(17);
                    Cell full_driver_id_card_img = row.getCell(18);
                    Cell certificate_no = row.getCell(19);
                    Cell get_driver_license_date = row.getCell(20);
                    Cell network_car_proof_on = row.getCell(21);
                    Cell network_car_proof_off = row.getCell(22);
                    Cell certificate_image = row.getCell(23);
                    Cell license_no = row.getCell(24);
                    Cell plate_num = row.getCell(25);
                    Cell type = row.getCell(26);
                    Cell status = row.getCell(27);
                    Cell appid = row.getCell(28);
                    System.out.println(appid);
                    Cell pic = row.getCell(29);
                    System.out.println(pic);
                    Cell create_time = row.getCell(30);
                    Cell update_time = row.getCell(31);
//                    System.out.println("update_time:"+update_time);
                    Cell update_on = row.getCell(32);
//                    System.out.println("update_on:"+update_on);

                    StringBuffer sb1 = new StringBuffer(256);

                    sb1.append("INSERT INTO yy_driver_check ( uuid, agent_uuid, company_uuid, name, face, mobile, license_num, license_pic, driving_licence_home_img, driving_licence_sub_img, driving_num, driving_registered_time, driving_pic, driving_pic_back, driving_time, id_card, id_card_face_img, id_card_back_img, full_driver_id_card_img, certificate_no, get_driver_license_date, network_car_proof_on, network_car_proof_off, certificate_image, license_no, plate_num, type, status, appid,pic, create_time, update_time, update_on ) VALUES (").
                            append("'"+uuid+"','"+agent_uuid+"','"+company_uuid+"','"+name+"','"+face+"','"+mobile+"','"+license_num+"','"+license_pic+"','"+driving_licence_home_img+"','"+driving_licence_sub_img+"','"+driving_num+"','"+driving_registered_time+"','"+driving_pic+"','"+driving_pic_back+"','"+driving_time+"','"+id_card+"','"+id_card_face_img+"','"+id_card_back_img+"','"+full_driver_id_card_img+"','"+certificate_no+"','"+get_driver_license_date+"','"+network_car_proof_on+"','"+network_car_proof_off+"','"+certificate_image+"','"+license_no+"','"+plate_num+"',"+4+","+1+",'"+appid+"','"+pic+"','"+create_time+"','"+update_time+"','"+update_on+"');");

//                    System.out.println(sb1);
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


    //INSERT INTO yy_driver_check ( uuid, agent_uuid, company_uuid, NAME, face, mobile, license_num, license_pic, driving_licence_home_img, driving_licence_sub_img, driving_num, driving_registered_time, driving_pic, driving_pic_back, driving_time, id_card, id_card_face_img, id_card_back_img, full_driver_id_card_img, certificate_no, get_driver_license_date, network_car_proof_on, network_car_proof_off, certificate_image, license_no, plate_num, type, brand_name, car_color, car_user, car_user_phone, operation_certificate_img, monitoring_card_img, mancar_img, remark, STATUS, appid, create_time, update_time, update_on ) VALUES ()
}
