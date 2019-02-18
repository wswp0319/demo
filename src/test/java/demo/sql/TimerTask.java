package demo.sql;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import demo.pb.BaseInfoDriver;
import demo.pb.BaseInfoDriverApp;
import demo.pb.BaseInfoDriverEducate;
import demo.pb.BaseInfoDriverStat;
import demo.pb.BaseInfoVehicle;
import demo.pb.BaseInfoVehicleInsurance;
import demo.pb.BaseInfoVehicleTotalMile;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:demo/sql/jdbc.xml")
@Service("timerTask")
public class TimerTask {
	@Autowired
	JdbcTemplate jdbcTemplate = null;

	public TimerTask() {
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

		// ApplicationContext context = new
		// ClassPathXmlApplicationContext("demo/sql/jdbc.xml");
	}

	public void test() {
		String sqlUpdate = "update yy_passenger set mobile = 16666666666 where uuid='01459847ec524b518562d5c9b72c4914'";

		jdbcTemplate.update(sqlUpdate);

		if (true) {
			// throw new RuntimeException();
		}

	}

	@Transactional
	public void baseInfoVehicle() {
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

		String sql = "SELECT * FROM chuxing.yy_car t WHERE t.created_on >= ? " + "	and t.created_on < NOW() "
				+ "	and t.vehicle_type IS NOT NULL and t.plate_num is not null and t.seats is not null"
				+ " and t.brand is not null and t.model is not null and t.vehicle_type is not null"
				+ " and t.owner_name is not null and t.car_color is not null"
				+ " and t.engine_id is not null and t.vin is not null"
				+ " and t.certify_date_a is not null and t.fare_type is not null"
				+ " and t.engine_displace is not null  and t.trans_agency is not null"
				+ " and t.trans_area is not null and t.trans_date_start is not null"
				+ " and t.trans_date_stop is not null and t.fix_state is not null"
				+ " and t.check_state is not null and t.fee_print_id is not null"
				+ " and t.gps_brand is not null and t.gps_model is not null"
				+ " and t.gps_imei is not null and t.gps_install_date is not null"
				+ " and t.register_date is not null and t.fare_type is not null";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoVehicle.build(list, 1).length;
			System.out.println(i);
		}

		// jdbcTemplate.update("insert exchange_timer_task values(?,?,?,?)",
		// new Object[] { "baseInfoVehicle", "insert", new Date(), new Date() });

		// throw new RuntimeException();

	}

	@Transactional
	public void baseInfoVehicleInsurance() {
		String sql = "SELECT c.plate_num,c.plate_color,i.insur_com,i.insur_num,i.insur_type,"
				+ " i.insur_count,i.insur_eff,i.insur_exp FROM yy_car c,yy_car_insurance i"
				+ " WHERE i.created_on >= ? and c.uuid = i.car_uuid";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoVehicleInsurance.build(list, 1).length;
			System.out.println(i);
		}
	}

	@Transactional
	public void baseInfoVehicleTotalMile() {
		String sql = "SELECT t.plate_num, t.vehicle_kilometers, t.plate_color FROM yy_car t"
				+ " WHERE t.created_on >= ? AND t.plate_num IS NOT NULL AND t.plate_color IS NOT NULL"
				+ " AND t.vehicle_kilometers IS NOT NULL";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoVehicleTotalMile.build(list, 1).length;
			System.out.println(i);
		}
	}

	@Transactional
	public void baseInfoDriver() {
		String sql = "SELECT * FROM yy_driver t WHERE t.created_on >= ? AND t.mobile IS NOT NULL"
				+ " AND t.id_card IS NOT NULL AND t.sex IS NOT NULL"
				+ " AND t.address IS NOT NULL AND t.driver_birthday IS NOT NULL"
				+ " AND t.driver_nation AND t.driver_contact_address IS NOT NULL"
				+ " AND t.license_id IS NOT NULL AND t.get_driver_license_date IS NOT NULL"
				+ " AND t.driver_license_on AND t.driver_license_off"
				+ " AND t.taxi_driver IS NOT NULL AND t.certificate_no IS NOT NULL"
				+ " AND t.network_car_issue_organization IS NOT NULL" + " AND t.network_car_issue_date IS NOT NULL"
				+ " AND t.get_network_car_proof_date IS NOT NULL"
				+ " AND t.network_car_proof_on IS NOT NULL AND t.network_car_proof_off IS NOT NULL"
				+ " AND t.register_date IS NOT NULL AND t.commercial_type IS NOT NULL"
				+ " AND t.contract_company IS NOT NULL AND t.contract_on IS NOT NULL"
				+ " AND t.contract_off IS NOT NULL";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoDriver.build(list, 1).length;
			System.out.println(i);
		}
	}

	public void baseInfoDriverEducate() {
		String sql = "SELECT d.address,d.license_id,t.course_name,t.course_date,t.start_time,t.stop_time,t.duration FROM yy_driver d,yy_driver_train t where t.created_on >= ? AND d.uuid = t.driver_uuid";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoDriverEducate.build(list, 1).length;
			System.err.println(i);
		}
	}

	public void baseInfoDriverApp() {
		String sql = "SELECT t.address,t.license_id,t.mobile,t.app_version FROM yy_driver t where t.created_on >= ? and t.address is not null and t.license_id is not null and t.mobile is not null and t.app_version  is not null";
		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoDriverApp.build(list, 1).length;
			System.err.println(i);
		}
	}

	@Test
	public void baseInfoDriverStat() {
		String sql = "SELECT d.address,d.license_id, IFNULL(e.received_order_count,0) received_order_count, IFNULL(d.traffic_violations_count,0) traffic_violations_count, IFNULL(e.complained_count,0) complained_count,IFNULL(d.violate_record,0) violate_record,IFNULL(d.traffic_accident_count,0) traffic_accident_count FROM "
				+ "yy_driver d,yy_driver_examine e where e.created_on >= ? and d.uuid = e.driver_uuid and d.address is not null and d.license_id is not null";

		List list = jdbcTemplate.queryForList(sql, new Object[] { "2018-05-01 10:10:10" });
		if (list != null && list.size() > 0) {
			int i = BaseInfoDriverStat.build(list, 1,new Date()).length;
			System.err.println(i);
		}
	}
}
