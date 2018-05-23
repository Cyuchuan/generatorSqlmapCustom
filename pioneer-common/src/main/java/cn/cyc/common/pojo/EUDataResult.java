package cn.cyc.common.pojo;

import java.util.List;

public class EUDataResult {
	private long total;
	private List<?> rows;

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public <T> void setRows(List<T> rows) {
		this.rows = rows;
	}
}
