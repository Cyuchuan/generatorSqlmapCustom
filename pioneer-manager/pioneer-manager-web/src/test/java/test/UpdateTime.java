package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.junit.Test;

public class UpdateTime {
	private String DRIVER = "com.mysql.jdbc.Driver";
	private String URL = "jdbc:mysql://localhost:3306/pioneer?characterEncoding=utf-8";
	private String USER = "root";
	private String PASSWORD = "root";

	@Test
	public void updateCreatedDateInItem() throws Exception {
		Class.forName(DRIVER);
		String sql = "update tb_item set created = ?";
		Date date = new Date();
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setObject(1, date);
		long result = prepareStatement.executeUpdate();
		System.out.println(result);
		prepareStatement.close();
		connection.close();

	}
	
	
	@Test
	public void updateCurrentDateInItem() throws Exception {
		Class.forName(DRIVER);
		String sql = "update tb_item set updated = ?";
		Date date = new Date();
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setObject(1, date);
		long result = prepareStatement.executeUpdate();
		System.out.println(result);
		prepareStatement.close();
		connection.close();
	}
	
	
	@Test
	public void updateCurrentDateInItemDesc() throws Exception {
		Class.forName(DRIVER);
		String sql = "update tb_item_desc set updated = ?";
		Date date = new Date();
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setObject(1, date);
		long result = prepareStatement.executeUpdate();
		System.out.println(result);
		prepareStatement.close();
		connection.close();
	}
	
	@Test
	public void updateCreatedDateInItemDesc() throws Exception {
		Class.forName(DRIVER);
		String sql = "update tb_item_desc set created = ?";
		Date date = new Date();
		Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
		PreparedStatement prepareStatement = connection.prepareStatement(sql);
		prepareStatement.setObject(1, date);
		long result = prepareStatement.executeUpdate();
		System.out.println(result);
		prepareStatement.close();
		connection.close();
	}
}
