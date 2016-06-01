package util;

import java.util.List;

/**
 * com.huawei.fda.common.util.Worksheet
 *
 * @author Huawei
 * @version v1.0
 * @date 2015/11/18 0018 12:39
 * @update
 */
public class Worksheet<E> {
		private String sheet;

		private int columnNum;

		private int rowNum = 60000;

		private List<E> rows;

		public String getSheet() {
			return sheet;
		}

		public void setSheet(String sheet) {
			this.sheet = sheet;
		}

		public List<E> getRows() {
			return rows;
		}

		public void setRows(List<E> rows) {
			this.rows = rows;
		}

		public int getColumnNum() {
			return columnNum;
		}

		public void setColumnNum(int columnNum) {
			this.columnNum = columnNum;
		}

		public int getRowNum() {
			return rowNum;
		}

		public void setRowNum(int rowNum) {
			this.rowNum = rowNum;
		}

}
