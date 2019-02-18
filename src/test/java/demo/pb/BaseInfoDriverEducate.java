package demo.pb;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csht.pb.OTIpcDef;
import com.csht.pb.OTIpcDef.IpcType;
import com.csht.pb.OTIpcDef.OTIpc;

public class BaseInfoDriverEducate extends BaseInfo {
	public static byte[] build(List list, int flag) {
		OTIpcDef.OTIpc.Builder builder_OTIpc = OTIpcDef.OTIpc.newBuilder();
		builder_OTIpc.setCompanyId("test");

		// 消息来源标识
		builder_OTIpc.setSource("11");
		builder_OTIpc.setIPCType(IpcType.baseInfoDriverEducate);

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			OTIpcDef.BaseInfoDriverEducate.Builder educate = OTIpcDef.BaseInfoDriverEducate.newBuilder();
			
			educate.setCompanyId("test");
			educate.setAddress((Integer) m.get("address"));
			educate.setLicenseId((String) m.get("license_id"));
			educate.setCourseName((String) m.get("course_name"));
			educate.setCourseDate(((Date) m.get("course_date")).getTime());
			educate.setStartTime(((Date) m.get("start_time")).getTime());
			educate.setStopTime(((Date) m.get("stop_time")).getTime());
			educate.setDuration(((BigDecimal) m.get("duration")).intValue());
			
			educate.setFlag(flag);
			educate.setUpdateTime(new Date().getTime());
			builder_OTIpc.addBaseInfoDriverEducate(educate);
		}

		OTIpc otipc = builder_OTIpc.build();
		return otipc.toByteArray();
	}
}
