package common.model;

import java.util.Date;

/**
 * Created  on 2016/6/1.
 */
public class BaseModel {
	private String id;
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
