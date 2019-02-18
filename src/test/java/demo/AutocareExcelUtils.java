package demo;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AutocareExcelUtils {

	public static final String HEADERINFO = "headInfo";
	public static final String DATAINFON = "dataInfo";

	/**
	 * 
	 * @Title: getWeebWork
	 * @Description: TODO(根据传入的文件名获取工作簿对象(Workbook))
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWeebWork(String filename) throws IOException {
		Workbook workbook = null;
		if (null != filename) {
			String fileType = filename.substring(filename.lastIndexOf("."), filename.length());
			FileInputStream fileStream = new FileInputStream(new File(filename));
			if (".xls".equals(fileType.trim().toLowerCase())) {
				workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
			} else if (".xlsx".equals(fileType.trim().toLowerCase())) {
				workbook = new XSSFWorkbook(fileStream);// 创建 Excel 2007 工作簿对象
			}
		}
		return workbook;
	}

	public static Workbook getWeebWork(FileInputStream fileStream, String fileType) throws IOException {
		Workbook workbook = null;
		if (fileStream != null) {
			if (".xls".equals(fileType.trim().toLowerCase())) {
				workbook = new HSSFWorkbook(fileStream);// 创建 Excel 2003 工作簿对象
			} else if (".xlsx".equals(fileType.trim().toLowerCase())) {
				workbook = new XSSFWorkbook(fileStream);// 创建 Excel 2007 工作簿对象
			}
		}
		return workbook;
	}

	/**
	 * 
	 * @Title: writeExcel
	 * @Description: TODO(导出Excel表)
	 * @param pathname
	 *            :导出Excel表的文件路径
	 * @param map
	 *            ：封装需要导出的数据(HEADERINFO封装表头信息，DATAINFON：封装要导出的数据信息,此处需要使用TreeMap )
	 *            例如： map.put(ExcelUtil.HEADERINFO,List<String> headList);
	 *            map.put(ExcelUtil.DATAINFON,List<TreeMap<String,Object>>
	 *            dataList);
	 * @param wb
	 * @throws IOException
	 */
	public static void writeExcel(String pathname, Map<String, Object> map, Workbook wb) throws IOException {
		if (null != map && null != pathname) {
			List<Object> headList = (List<Object>) map.get(AutocareExcelUtils.HEADERINFO);
			List<TreeMap<String, Object>> dataList = (List<TreeMap<String, Object>>) map.get(AutocareExcelUtils.DATAINFON);
			CellStyle style = getCellStyle(wb);
			Sheet sheet = wb.createSheet();
			/**
			 * 设置Excel表的第一行即表头
			 */
			Row row = sheet.createRow(0);
			for (int i = 0; i < headList.size(); i++) {
				Cell headCell = row.createCell(i);
				headCell.setCellType(Cell.CELL_TYPE_STRING);
				headCell.setCellStyle(style);// 设置表头样式
				headCell.setCellValue(String.valueOf(headList.get(i)));
			}

			for (int i = 0; i < dataList.size(); i++) {
				Row rowdata = sheet.createRow(i + 1);// 创建数据行
				TreeMap<String, Object> mapdata = dataList.get(i);
				Iterator it = mapdata.keySet().iterator();
				int j = 0;
				while (it.hasNext()) {
					String strdata = String.valueOf(mapdata.get(it.next()));
					Cell celldata = rowdata.createCell(j);
					celldata.setCellType(Cell.CELL_TYPE_STRING);
					celldata.setCellValue(strdata);
					j++;
				}
			}
			File file = new File(pathname);
			OutputStream os = new FileOutputStream(file);
			os.flush();
			wb.write(os);
			os.close();
		}
	}

	/**
	 * 
	 * @Title: getCellStyle
	 * @Description: TODO（设置表头样式）
	 * @param wb
	 * @return
	 */
	public static CellStyle getCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		style.setFillForegroundColor(HSSFColor.LIME.index);// 设置背景色
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);// 让单元格居中
		// style.setWrapText(true);//设置自动换行
		style.setFont(font);
		return style;
	}

	/**
	 * 
	 * @Title: readerExcelDemo
	 * @Description: TODO(读取Excel表中的数据)
	 * @throws IOException
	 */
	public static List<Cell[]> readerExcelDemo(String path) throws IOException {
		/**
		 * 读取Excel表中的所有数据
		 */
		Workbook workbook = getWeebWork(path);
		List<Cell[]> list = new ArrayList<Cell[]>();
		// System.out.println("总表页数为：" + workbook.getNumberOfSheets());// 获取表页数
		Sheet sheet = workbook.getSheetAt(0);
		int rownum = sheet.getLastRowNum();// 获取总行数
		for (int i = 1; i < rownum; i++) {
			Row row = sheet.getRow(i);
			// Cell orderno = row.getCell(2);// 获取指定单元格中的数据
			// System.out.println(orderno.getCellType());
			short cellnum = row.getLastCellNum(); // 获取单元格的总列数
			Cell[] cel = new Cell[cellnum];
			for (int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++) {
				Cell celldata = row.getCell(j);
				cel[j] = celldata;
			}
			list.add(cel);
		}

		/**
		 * 读取指定位置的单元格
		 */
		// Row row1 = sheet.getRow(1);
		// Cell cell1 = row1.getCell(2);
		// System.out.print("(1,2)位置单元格的值为：" + cell1);
		// BigDecimal big = new BigDecimal(cell1.getNumericCellValue());//
		// 将科学计数法表示的数据转化为String类型
		// System.out.print("\t" + String.valueOf(big));

		return list;
	}
	
	public static void writTxt(String fileName,String content) {
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;

        int count = 1000;//写文件行数

        try {

            //经过测试：ufferedOutputStream执行耗时:1,1，1 毫秒
            outSTr = new FileOutputStream(new File(fileName));
            Buff = new BufferedOutputStream(outSTr);
            long begin0 = System.currentTimeMillis();
//            for (int i = 0; i < count; i++) {
//                Buff.write("测试java 文件操作\r\n".getBytes());
//            }
            Buff.write(content.getBytes());
            Buff.flush();
            Buff.close();
            long end0 = System.currentTimeMillis();
            System.out.println("BufferedOutputStream执行耗时:" + (end0 - begin0) + " 毫秒");



        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Buff.close();
                outSTr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

	}

	public static void main(String[] args) throws IOException {
		// 生成平安订单
//		Workbook workbook = getWeebWork("E:\\文档汇总\\sql需求文档\\环球名车上传信息6.7（第九批）.xlsx");
		Workbook workbook = getWeebWork("E:\\文档汇总\\sql需求文档\\autoCare.xlsx");

		Sheet sheet = workbook.getSheetAt(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		int rownum = sheet.getLastRowNum();// 获取总行数
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <= rownum; i++) {
			Row row = sheet.getRow(i);
			// short cellnum = row.getLastCellNum(); // 获取单元格的总列数
			Cell one = row.getCell(0);
			Cell two = row.getCell(1);
			Cell three = row.getCell(2);
			Cell fore = row.getCell(3);



			if (StringUtils.isBlank(one.toString())) {
				break;
			}

			sb.append(
					"INSERT INTO `tb_car_autocare` (`car_auto_id`,`brand_name`, `car_type`, `displacement`, `vip_price`, `created_at`, `updated_at`) VALUES (");
			sb.append((i+1)+",'").append(one).append("','").append(two).append("',").append("'"+three+"'").append(",")
					.append(Integer.valueOf(fore.toString().trim().replace(".0",""))).append(",NOW()").append(",NOW()").append(");\r\n");
			//System.out.println(sb.toString());
			//writTxt("E:\\工作相关\\日报\\pa.txt",sb.toString());
		}

		System.out.println(sb.toString());
		writTxt("E:\\文档汇总\\sql需求文档\\autocare1107.txt",sb.toString());

	}
}