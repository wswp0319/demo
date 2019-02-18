package demo.pb;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csht.pb.OTIpcDef;
import com.csht.pb.OTIpcDef.IpcType;
import com.csht.pb.OTIpcDef.OTIpc;

public class BaseInfoVehicleTotalMile extends BaseInfo{
	public static byte[] build(List list, int flag) {
		OTIpcDef.OTIpc.Builder builder_OTIpc = OTIpcDef.OTIpc.newBuilder();
		builder_OTIpc.setCompanyId("test");

		// 消息来源标识
		builder_OTIpc.setSource("9");
		builder_OTIpc.setIPCType(IpcType.baseInfoVehicleTotalMile);
		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			OTIpcDef.BaseInfoVehicleTotalMile.Builder totalMile = OTIpcDef.BaseInfoVehicleTotalMile.newBuilder();
			
			totalMile.setCompanyId("test");
			totalMile.setAddress(420106);
			totalMile.setVehicleNo((String) m.get("plate_num"));
			totalMile.setTotalMile(((Double) m.get("vehicle_kilometers")).floatValue());
			totalMile.setFlag(flag);
			totalMile.setUpdateTime(new Date().getTime());
			totalMile.setPlateColor((String) m.get("plate_color"));
			
			builder_OTIpc.addBaseInfoVehicleTotalMile(totalMile);
		}
		
		OTIpc otipc = builder_OTIpc.build();
		return otipc.toByteArray();
	}
}
