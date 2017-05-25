package net.slipp.user;

import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.slipp.support.ConnectionManager;

import org.junit.Test;

public class ConnectionManagerTest {
	
	@Test
	public void connection() throws SQLException {
		
		Connection con = ConnectionManager.getConnection();
		assertNotNull(con);
	}
}