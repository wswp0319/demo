package demo.pb;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csht.pb.OTIpcDef;
import com.csht.pb.OTIpcDef.IpcType;
import com.csht.pb.OTIpcDef.OTIpc;

public class BaseInfoVehicleInsurance extends BaseInfo {
	public static byte[] build(List list, int flag) {
		OTIpcDef.OTIpc.Builder builder_OTIpc = OTIpcDef.OTIpc.newBuilder();
		builder_OTIpc.setCompanyId("test");

		// 消息来源标识
		builder_OTIpc.setSource("8");
		builder_OTIpc.setIPCType(IpcType.baseInfoVehicleInsurance);

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			OTIpcDef.BaseInfoVehicleInsurance.Builder baseInfoVechicle = OTIpcDef.BaseInfoVehicleInsurance.newBuilder();
			baseInfoVechicle.setCompanyId("test");
			baseInfoVechicle.setVehicleNo((String) m.get("plate_num"));
			baseInfoVechicle.setPlateColor((String) m.get("plate_color"));
			baseInfoVechicle.setInsurCom((String) m.get("insur_com"));
			baseInfoVechicle.setInsurNum((String) m.get("insur_num"));
			baseInfoVechicle.setInsurType((String) m.get("insur_type"));
			baseInfoVechicle.setInsurCount(((java.math.BigDecimal) m.get("insur_count")).intValue());
			baseInfoVechicle.setInsurEff(((Date) m.get("insur_eff")).getTime());
			baseInfoVechicle.setInsurExp(((Date) m.get("insur_exp")).getTime());

			baseInfoVechicle.setFlag(flag);
			baseInfoVechicle.setUpdateTime(new Date().getTime());
			builder_OTIpc.addBaseInfoVehicleInsurance(baseInfoVechicle);
		}
		OTIpc otipc = builder_OTIpc.build();
		return otipc.toByteArray();
	}
}
