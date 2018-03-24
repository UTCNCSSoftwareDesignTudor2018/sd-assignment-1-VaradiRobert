package persistence.dao;

import java.util.Map;

public class DAOConfiguration {
	private String tableName;
	private Map<String, Column> mapping;
	public DAOConfiguration(String tableName, Map<String, Column> mapping) {
		this.tableName = tableName;
		this.mapping = mapping;
	}
	public Map<String, Column> getMapping() {
		return mapping;
	}
	public void setMapping(Map<String, Column> mapping) {
		this.mapping = mapping;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
}
