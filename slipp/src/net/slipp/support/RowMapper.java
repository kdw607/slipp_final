package net.slipp.support;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper {
	Object maoRow(ResultSet rs) throws SQLException;
}
