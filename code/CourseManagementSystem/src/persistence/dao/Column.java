package persistence.dao;

public class Column {
	private String columnName;
	private Boolean primaryKey;
	public Column(String columnName, Boolean primaryKey) {
		this.columnName = columnName;
		this.primaryKey = primaryKey;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Boolean isPrimaryKey() {
		return primaryKey;
	}
}
