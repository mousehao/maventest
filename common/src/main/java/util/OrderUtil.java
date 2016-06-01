package util;

/**
 * Created by Huawei on 15/11/20.
 */
public class OrderUtil {

	/*
	0,0%~10%,10%~20%... 90%~100%,100%，一个就有12个描述
	 */
	public static String getDesc(int order) {
		if (0 == order) {
			return "0%";
		} else if (11 == order) {
			return "100%";
		} else if (1 == order) {
			return "1%~10%";
		} else if (10 == order) {
			return "90%~99%";
		} else {
			String desc = (order - 1) * 10 + "%~" + order * 10 + "%";
			return desc;
		}
	}

	/**
	 * 1为 11
	 * 0为 0
	 * 0.0000 - 0.1000 为 1
	 * 0.1000 -> 0.2000为2
	 * 0.9000 -> 1 为10
	 *
	 * @param rate
	 * @return
	 */
	public static int getOrder(double rate) {
		if (rate == 1) {
			return 11;
		} else if (rate == 0) {
			return 0;
		} else {
			return (int) Math.ceil(MathUtils.div(rate, 0.1));
		}
	}
}
