package demo.pb;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csht.pb.OTIpcDef;
import com.csht.pb.OTIpcDef.IpcType;
import com.csht.pb.OTIpcDef.OTIpc;

public class BaseInfoVehicle extends BaseInfo {
	
	public static byte[] build(List list,int flag) {
		OTIpcDef.OTIpc.Builder builder_OTIpc = OTIpcDef.OTIpc.newBuilder();
		builder_OTIpc.setCompanyId("test");

		// 消息来源标识
		builder_OTIpc.setSource("7");
		builder_OTIpc.setIPCType(IpcType.baseInfoVehicle);

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			OTIpcDef.BaseInfoVehicle.Builder baseInfoVechicle = OTIpcDef.BaseInfoVehicle.newBuilder();
			baseInfoVechicle.setCompanyId("test");
			baseInfoVechicle.setAddress(420106);
			if (!isEmpty(m.get("plate_num")))
				baseInfoVechicle.setVehicleNo((String) m.get("plate_num"));

			if (!isEmpty(m.get("plate_color")))
				baseInfoVechicle.setPlateColor((String) m.get("plate_color"));

			if (!isEmpty(m.get("seats")))
				baseInfoVechicle.setSeats((Integer) m.get("seats"));

			if (!isEmpty(m.get("brand")))
				baseInfoVechicle.setBrand((String) m.get("brand"));

			if (!isEmpty(m.get("model")))
				baseInfoVechicle.setModel((String) m.get("model"));

			if (!isEmpty(m.get("vehicle_type")))
				baseInfoVechicle.setVehicleType((String) m.get("vehicle_type"));

			if (!isEmpty(m.get("owner_name")))
				baseInfoVechicle.setOwnerName((String) m.get("owner_name"));

			if (!isEmpty(m.get("car_color")))
				baseInfoVechicle.setVehicleColor((String) m.get("car_color"));

			if (!isEmpty(m.get("engine_id")))
				baseInfoVechicle.setEngineId((String) m.get("engine_id"));

			if (!isEmpty(m.get("vin")))
				baseInfoVechicle.setVIN((String) m.get("vin"));

			if (!isEmpty(m.get("certify_date_a")))
				baseInfoVechicle.setCertifyDateA(((Date) m.get("certify_date_a")).getTime());

			if (!isEmpty(m.get("fuel_type")))
				baseInfoVechicle.setFuelType((String) m.get("fuel_type"));

			if (!isEmpty(m.get("engine_displace")))
				baseInfoVechicle.setEngineDisplace((String) m.get("engine_displace"));
			
			if (!isEmpty(m.get("engine_power")))
				baseInfoVechicle.setEnginePower((String) m.get("engine_power"));

			if (!isEmpty(m.get("photo_id")))
				baseInfoVechicle.setPhotoId((String) m.get("photo_id"));

			if (!isEmpty(m.get("certificate")))
				baseInfoVechicle.setCertificate((String) m.get("certificate"));

			if (!isEmpty(m.get("trans_agency")))
				baseInfoVechicle.setTransAgency((String) m.get("trans_agency"));

			if (!isEmpty(m.get("trans_area")))
				baseInfoVechicle.setTransArea((String) m.get("trans_area"));

			if (!isEmpty(m.get("trans_date_start")))
				baseInfoVechicle.setTransDateStart(((Date) m.get("trans_date_start")).getTime());

			if (!isEmpty(m.get("trans_date_stop")))
				baseInfoVechicle.setTransDateStop(((Date) m.get("trans_date_stop")).getTime());

			if (!isEmpty(m.get("certify_date_a")))
				baseInfoVechicle.setCertifyDateB(((Date) m.get("certify_date_a")).getTime());

			if (!isEmpty(m.get("fix_state")))
				baseInfoVechicle.setFixState(((Integer) m.get("fix_state")).toString());

			if (!isEmpty(m.get("check_state")))
				baseInfoVechicle.setCheckState(((Integer) m.get("check_state")).toString());
			
			if (!isEmpty(m.get("check_date")))
				baseInfoVechicle.setCheckDate(((Date) m.get("check_date")).getTime());

			if (!isEmpty(m.get("gps_install_date")))
				baseInfoVechicle.setGPSInstallDate(((Date) m.get("gps_install_date")).getTime());

			if (!isEmpty(m.get("fee_print_id")))
				baseInfoVechicle.setFeePrintId((String) m.get("fee_print_id"));

			if (!isEmpty(m.get("gps_brand")))
				baseInfoVechicle.setGPSBrand((String) m.get("gps_brand"));

			if (!isEmpty(m.get("gps_model")))
				baseInfoVechicle.setGPSModel((String) m.get("gps_model"));

			if (!isEmpty(m.get("gps_imei")))
				baseInfoVechicle.setGPSIMEI((String) m.get("gps_imei"));

			if (!isEmpty(m.get("gps_install_date")))
				baseInfoVechicle.setGPSInstallDate(((Date) m.get("gps_install_date")).getTime());

			if (!isEmpty(m.get("register_date")))
				baseInfoVechicle.setRegisterDate(((Date) m.get("register_date")).getTime());

			if (!isEmpty(m.get("commercial_type")))
				baseInfoVechicle.setCommercialType((Integer) m.get("commercial_type"));

			if (!isEmpty(m.get("fare_type")))
				baseInfoVechicle.setFareType((String) m.get("fare_type"));
			
			if (!isEmpty(m.get("wheelbase")))
			baseInfoVechicle.setWheelBase((String) m.get("wheelbase"));

			baseInfoVechicle.setState(0);
			baseInfoVechicle.setFlag(flag);
			baseInfoVechicle.setUpdateTime(new Date().getTime());
			builder_OTIpc.addBaseInfoVehicle(baseInfoVechicle);
		}

		OTIpc otipc = builder_OTIpc.build();
		return otipc.toByteArray();
	}
}
