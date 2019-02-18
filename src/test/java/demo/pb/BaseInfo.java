package demo.pb;

public abstract class BaseInfo {

	public static boolean isEmpty(Object obj) {
		return (obj == null || "".equals(obj));
	}

	public static boolean isNullOrEmpty(Object object) {

		if (object == null) {
			return true;
		}
		return isNullOrEmpty(object.toString());
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().equals("");
	}

}
