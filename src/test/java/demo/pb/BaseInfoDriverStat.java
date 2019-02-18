package demo.pb;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csht.pb.OTIpcDef;
import com.csht.pb.OTIpcDef.IpcType;
import com.csht.pb.OTIpcDef.OTIpc;

import demo.utils.DateUtil;

public class BaseInfoDriverStat {
	public static byte[] build(List list, int flag,Date date) {
		OTIpcDef.OTIpc.Builder builder_OTIpc = OTIpcDef.OTIpc.newBuilder();
		builder_OTIpc.setCompanyId("test");

		// 消息来源标识
		builder_OTIpc.setSource("13");
		builder_OTIpc.setIPCType(IpcType.baseInfoDriverStat);

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			OTIpcDef.BaseInfoDriverStat.Builder driver = OTIpcDef.BaseInfoDriverStat.newBuilder();
			driver.setCompanyId("test");
			driver.setAddress((Integer) m.get("address"));
			driver.setLicenseId((String) m.get("license_id"));
			driver.setCycle(DateUtil.getYearMonth(date));
			driver.setOrderCount(((Long) m.get("received_order_count")).intValue());
			driver.setComplainedCount(((Long) m.get("complained_count")).intValue());
			driver.setTrafficAccidentCount(((Long) m.get("traffic_accident_count")).intValue());
			driver.setTrafficViolationCount(((Long) m.get("traffic_violations_count")).intValue());
			driver.setViolateRecord(((Long) m.get("violate_record")).intValue());
			
			driver.setFlag(flag);
			driver.setUpdateTime(new Date().getTime());
			builder_OTIpc.addBaseInfoDriverStat(driver);
		}

		OTIpc otipc = builder_OTIpc.build();
		return otipc.toByteArray();
	}
}
