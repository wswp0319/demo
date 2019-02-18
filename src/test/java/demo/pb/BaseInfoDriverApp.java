package demo.pb;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csht.pb.OTIpcDef;
import com.csht.pb.OTIpcDef.IpcType;
import com.csht.pb.OTIpcDef.OTIpc;

public class BaseInfoDriverApp {
	public static byte[] build(List list, int flag) {
		OTIpcDef.OTIpc.Builder builder_OTIpc = OTIpcDef.OTIpc.newBuilder();
		builder_OTIpc.setCompanyId("test");

		// 消息来源标识
		builder_OTIpc.setSource("12");
		builder_OTIpc.setIPCType(IpcType.baseInfoDriverApp);

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			OTIpcDef.BaseInfoDriverApp.Builder driver = OTIpcDef.BaseInfoDriverApp.newBuilder();
			driver.setCompanyId("test");
			driver.setAddress((Integer) m.get("address"));
			driver.setLicenseId((String)m.get("license_id"));
			driver.setDriverPhone((String)m.get("mobile"));
			driver.setNetType(4);
			driver.setAppVersion((String)m.get("app_version"));
			driver.setMapType(2);
			
			driver.setState(0);
			driver.setFlag(flag);
			driver.setUpdateTime(new Date().getTime());
			builder_OTIpc.addBaseInfoDriverApp(driver);
		}

		OTIpc otipc = builder_OTIpc.build();
		return otipc.toByteArray();
	}
}
