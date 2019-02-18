package demo.pb;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.csht.pb.OTIpcDef;
import com.csht.pb.OTIpcDef.IpcType;
import com.csht.pb.OTIpcDef.OTIpc;

public class BaseInfoDriver extends BaseInfo {
	public static byte[] build(List list, int flag) {
		OTIpcDef.OTIpc.Builder builder_OTIpc = OTIpcDef.OTIpc.newBuilder();
		builder_OTIpc.setCompanyId("test");

		// 消息来源标识
		builder_OTIpc.setSource("10");
		builder_OTIpc.setIPCType(IpcType.baseInfoDriver);

		for (int i = 0; i < list.size(); i++) {
			Map m = (Map) list.get(i);
			OTIpcDef.BaseInfoDriver.Builder driver = OTIpcDef.BaseInfoDriver.newBuilder();
			driver.setCompanyId("test");
			driver.setAddress((Integer) m.get("address"));

			if (!isEmpty(m.get("name")))
				driver.setDriverName((String) m.get("name"));

			driver.setDriverIDCard((String) m.get("id_card"));
			driver.setDriverPhone((String) m.get("mobile"));
			driver.setDriverGender(String.valueOf(m.get("sex")));
			driver.setDriverBirthday(((Date) m.get("driver_birthday")).getTime());

			if (!isEmpty(m.get("driver_nationality")))
				driver.setDriverNationality((String) m.get("driver_nationality"));

			driver.setDriverNation((String) m.get("driver_nation"));

			if (!isEmpty(m.get("driver_marital_status")))
				driver.setDriverMaritalStatus((String) m.get("driver_marital_status"));
			if (!isEmpty(m.get("driver_language_level")))
				driver.setDriverLanguageLevel((String) m.get("driver_language_level"));
			if (!isEmpty(m.get("driver_education")))
				driver.setDriverEducation((String) m.get("driver_education"));
			if (!isEmpty(m.get("driver_census")))
				driver.setDriverCensus((String) m.get("driver_census"));
			if (!isEmpty(m.get("driver_address")))
				driver.setDiverAddress((String) m.get("driver_address"));

			driver.setDriverContactAddress((String) m.get("driver_contact_address"));

			if (!isEmpty(m.get("photo_id")))
				driver.setPhotoId((String) m.get("photo_id"));

			driver.setLicenseId((String) m.get("license_id"));

			if (!isEmpty(m.get("license_photo_id")))
				driver.setLicensePhotoId((String) m.get("license_photo_id"));
			if (!isEmpty(m.get("driver_type")))
				driver.setDirverType((String) m.get("driver_type"));

			driver.setGetDriverLicenseDate(((Date) m.get("get_driver_license_date")).getTime());
			driver.setDriverLicenseOn(((Date) m.get("driver_license_on")).getTime());
			driver.setDriverLicenseOff(((Date) m.get("driver_license_off")).getTime());
			driver.setTaxiDriver((Integer) m.get("taxi_driver"));
			driver.setCertificateA((String) m.get("certificate_no"));
			driver.setNetworkCarIssueOrganization((String) m.get("network_car_issue_organization"));
			driver.setNetworkCarIssueDate(((Date) m.get("network_car_issue_date")).getTime());
			driver.setGetNetworkCarProofDate(((Date) m.get("get_network_car_proof_date")).getTime());
			driver.setNetworkCarProofOn(((Date) m.get("network_car_proof_on")).getTime());
			driver.setNetworkCarProofOff(((Date) m.get("network_car_proof_off")).getTime());
			driver.setRegisterDate(((Date) m.get("register_date")).getTime());

			if (!isEmpty(m.get("full_time_driver")))
				driver.setFullTimeDriver((Integer) m.get("taxi_driver"));
			if (!isEmpty(m.get("in_driver_blacklist")))
				driver.setInDriverBlacklist((Integer) m.get("taxi_driver"));

			driver.setCommercialType((Integer) m.get("commercial_type"));
			driver.setContractCompany((String) m.get("contract_company"));
			driver.setContractOn(((Date) m.get("contract_on")).getTime());
			driver.setContractOff(((Date) m.get("contract_off")).getTime());

			if (!isEmpty(m.get("emergency_contact")))
				driver.setEmergencyContact((String) m.get("emergency_contact"));
			if (!isEmpty(m.get("emergency_contact_phone")))
				driver.setEmergencyContactPhone((String) m.get("emergency_contact_phone"));
			if (!isEmpty(m.get("emergency_contact_address")))
				driver.setEmergencyContactAddress((String) m.get("emergency_contact_address"));

			driver.setState(0);
			driver.setFlag(flag);
			driver.setUpdateTime(new Date().getTime());
			builder_OTIpc.addBaseInfoDriver(driver);
		}

		OTIpc otipc = builder_OTIpc.build();
		return otipc.toByteArray();
	}
}
