package com.jason.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.jason.database.DBCPPoolManager;
import com.jason.dto.Keyword;
import com.jason.dto.LawEntry;
import com.jason.dto.User;

public class UserController {
	
	private DBCPPoolManager dm;
	
	
	public UserController(){
		if (dm == null) {
			dm = new DBCPPoolManager();
			dm.startService();
		}
	}
	
	public UserController(DBCPPoolManager dm){
		if (dm != null) {
			this.dm = dm;
		}else{
			dm = new DBCPPoolManager();
			dm.startService();
		}
	}
	
	public boolean saveOne(User user){
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean res = false;
		try {
			conn = dm.getConnection();
			stmt = conn
					.prepareStatement("INSERT INTO user ( ip,kid)  VALUES(?,?)");
			stmt.setString(1, user.getIp() );
			stmt.setInt(2, user.getKid() );
			res = stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	
	public boolean saveSearchKeyword(User user,Keyword kw){
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean res = false;
		try {
			conn = dm.getConnection();
			stmt = conn
					.prepareStatement("INSERT INTO user_keyword ( uid,kid)  VALUES(?)");
			stmt.setString(1, user.getIp() );
			res = stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return res;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UserController uc = new UserController();
		User user = new User();
		user.setIp("10231432.asdfsajdflk");
		user.setKid(12);
		uc.saveOne(user);
	}

}
