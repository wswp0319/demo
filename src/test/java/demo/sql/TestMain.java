package demo.sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;


import demo.ExcelUtils;
import demo.pb.BaseInfoDriver;
import demo.pb.BaseInfoVehicle;
import demo.pb.BaseInfoVehicleInsurance;
import demo.pb.BaseInfoVehicleTotalMile;

public class TestMain {
	static JdbcTemplate jdbcTemplate = null;

	public static void createTxt(JdbcTemplate jdbcTemplate) {
		String sql = "select TABLE_NAME,TABLE_COMMENT from information_schema.tables where table_schema='hqcxzc' and table_type='base table';";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

		Iterator<Map<String, Object>> it = list.iterator();
		StringBuffer sb = new StringBuffer(10240);
		for (; it.hasNext();) {
			Map<String, Object> map = it.next();
			sb.append("表名\t" + map.get("TABLE_NAME") + "\t说明\t" + map.get("TABLE_COMMENT"));
			sb.append("\n");
			String tableSql = "SELECT COLUMN_NAME,COLUMN_COMMENT,COLUMN_TYPE,IS_NULLABLE FROM INFORMATION_SCHEMA.Columns WHERE table_name='"
					+ map.get("TABLE_NAME") + "' AND table_schema='hqcxzc'";
			List<Map<String, Object>> list1 = jdbcTemplate.queryForList(tableSql);
			sb.append("字段名\t说明\t类型\n");
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> tb = list1.get(i);
				sb.append(tb.get("COLUMN_NAME") + "\t" + tb.get("COLUMN_COMMENT") + "\t" + tb.get("COLUMN_TYPE"));// +
																													// "\t"+
																													// tb.get("IS_NULLABLE")
				sb.append("\n");
			}
			sb.append("\n");
		}

		ExcelUtils.writTxt("E:\\工作相关\\日报\\tab.txt", sb.toString());
	}

	public static void baseInfoVehicle() {
		// new Object[] {"2018-06-01 10:10:10"}
		// new BeanPropertyRowMapper<Map>(Map.class)
		//String sql = "SELECT * FROM yy_car t where t.created_on >= ? and t.created_on < NOW() and t.vehicle_type is not null and t.fuel_type is not null and t.engine_power is not null and wheelbase is not null and t.trans_area is not null and t.check_date is not null and t.gps_imei is not null and t.fuel_type is not null and t.brand_name is not null";
		String sql = "SELECT \r\n" + 
				"    *\r\n" + 
				"FROM\r\n" + 
				"    chuxing.yy_car t\r\n" + 
				"WHERE t.created_on >= ? \r\n" + 
				"	and t.created_on < NOW() \r\n" + 
				"	and t.vehicle_type IS NOT NULL\r\n" + 
				"    and t.plate_num is not null\r\n" + 
				"    and t.seats is not null\r\n" + 
				"    and t.brand is not null\r\n" + 
				"    and t.model is not null\r\n" + 
				"    and t.vehicle_type is not null\r\n" + 
				"    and t.owner_name is not null\r\n" + 
				"    and t.car_color is not null\r\n" + 
				"    and t.engine_id is not null\r\n" + 
				"    and t.vin is not null\r\n" + 
				"    and t.certify_date_a is not null\r\n" + 
				"    and t.fare_type is not null\r\n" + 
				"    and t.engine_displace is not null\r\n" + 
				"    and t.trans_agency is not null\r\n" + 
				"    and t.trans_area is not null\r\n" + 
				"    and t.trans_date_start is not null\r\n" + 
				"    and t.trans_date_stop is not null\r\n" + 
				"    and t.fix_state is not null\r\n" + 
				"    and t.check_state is not null\r\n" + 
				"    and t.fee_print_id is not null\r\n" + 
				"    and t.gps_brand is not null\r\n" + 
				"    and t.gps_model is not null\r\n" + 
				"    and t.gps_imei is not null\r\n" +
				"    and t.gps_install_date is not null\r\n" + 
				"    and t.register_date is not null\r\n" + 
				"    and t.fare_type is not null";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoVehicle.build(list,1).length;
			System.out.println(i);
		}

	}
	
	public static void baseInfoVehicleInsurance() {
		String sql = "SELECT \r\n" + 
				"    c.plate_num,\r\n" + 
				"    c.plate_color,\r\n" + 
				"    i.insur_com,\r\n" + 
				"    i.insur_num,\r\n" + 
				"    i.insur_type,\r\n" + 
				"    i.insur_count,\r\n" + 
				"    i.insur_eff,\r\n" + 
				"    i.insur_exp\r\n" + 
				"FROM\r\n" + 
				"    yy_car c,\r\n" + 
				"    yy_car_insurance i\r\n" + 
				"WHERE i.created_on >= ? \r\n" + 
				"    and c.uuid = i.car_uuid";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoVehicleInsurance.build(list,1).length;
			System.out.println(i);
		}
	}
	
	public static void baseInfoVehicleTotalMile() {
		String sql = "SELECT \r\n" + 
				"    t.plate_num, t.vehicle_kilometers, t.plate_color\r\n" + 
				"FROM\r\n" + 
				"    yy_car t\r\n" + 
				"WHERE t.created_on >= ? \r\n" + 
				"    AND t.plate_num IS NOT NULL\r\n" + 
				"    AND t.plate_color IS NOT NULL\r\n" + 
				"    AND t.vehicle_kilometers IS NOT NULL";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoVehicleTotalMile.build(list,1).length;
			System.out.println(i);
		}
	}
	
	public static void baseInfoDriver() {
		String sql = "SELECT \r\n" + 
				"    *\r\n" + 
				"FROM\r\n" + 
				"    yy_driver t\r\n" + 
				"WHERE t.created_on >= ? \r\n" + 
				"    AND t.mobile IS NOT NULL\r\n" + 
				"        AND t.id_card IS NOT NULL\r\n" + 
				"        AND t.sex IS NOT NULL\r\n" + 
				"        AND t.address IS NOT NULL\r\n" + 
				"        AND t.driver_birthday IS NOT NULL\r\n" + 
				"        AND t.driver_nation\r\n" + 
				"        AND t.driver_contact_address IS NOT NULL\r\n" + 
				"        AND t.license_id IS NOT NULL\r\n" + 
				"        AND t.get_driver_license_date IS NOT NULL\r\n" + 
				"        AND t.driver_license_on\r\n" + 
				"        AND t.driver_license_off\r\n" + 
				"        AND t.taxi_driver IS NOT NULL\r\n" + 
				"        AND t.certificate_no IS NOT NULL\r\n" + 
				"        AND t.network_car_issue_organization IS NOT NULL\r\n" + 
				"        AND t.network_car_issue_date IS NOT NULL\r\n" + 
				"        AND t.get_network_car_proof_date IS NOT NULL\r\n" + 
				"        AND t.network_car_proof_on IS NOT NULL\r\n" + 
				"        AND t.network_car_proof_off IS NOT NULL\r\n" + 
				"        AND t.register_date IS NOT NULL\r\n" + 
				"        AND t.commercial_type IS NOT NULL\r\n" + 
				"        AND t.contract_company IS NOT NULL\r\n" + 
				"        AND t.contract_on IS NOT NULL\r\n" + 
				"        AND t.contract_off IS NOT NULL";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoDriver.build(list,1).length;
			System.out.println(i);
		}
	}

	public static void main(String[] args) {
		String log4j = "D:\\mcworkspace\\demo\\src\\main\\webapp\\WEB-INF\\conf\\log4j.properties";

		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(new FileInputStream(log4j)));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		PropertyConfigurator.configure(properties);
		Logger logger = Logger.getLogger(TestMain.class.getName());

		logger.info("Load log4j config from " + log4j);

		ApplicationContext context = new ClassPathXmlApplicationContext("demo/sql/jdbc.xml");
		jdbcTemplate = (JdbcTemplate) context.getBean("jdbcTemplate");

		// createTxt(jdbcTemplate);

		baseInfoVehicle();
		baseInfoVehicleInsurance();
		baseInfoVehicleTotalMile();
		baseInfoDriver();
	}

}
