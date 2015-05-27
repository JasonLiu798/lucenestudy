package com.jason.controller;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import com.jason.database.DBCPPoolManager;
import com.jason.database.DBUtil;
import com.jason.dto.Chapter;
import com.jason.dto.Keyword;
import com.jason.dto.LawEntry;

public class KeywordController {

	private BasicDataSource basicDataSource;
	
	public BasicDataSource getBasicDataSource() {
		return basicDataSource;
	}

	public void setBasicDataSource(BasicDataSource basicDataSource) {
		this.basicDataSource = basicDataSource;
	}

	public KeywordController() {
	}
	
//	
//	public KeywordController( BasicDataSource dm) {
//			this.basicDataSource = dm;
//	}
	
	
	public synchronized int getNewId(){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		int res = -1;
		try {
			conn = basicDataSource.getConnection();
			stmt = conn.prepareStatement(" select  max(kid) kid from keyword ");
			rs = stmt.executeQuery();
			while( rs.next()){
				res = rs.getInt("kid") ;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally{
			if( rs!=null){
				try{
					rs.close();
				}catch (SQLException e) {
					e.printStackTrace();
				}
			}
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
		return res+1;
	}
	
	public boolean saveOne(Keyword kw){
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean res = false;
		try {
			conn = basicDataSource.getConnection();
			stmt = conn.prepareStatement(" INSERT INTO keyword ( kid,keyword)  VALUES(?,?) ");
			stmt.setInt(1, kw.getKid() );
			stmt.setString(2, kw.getKeyword());
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
		KeywordController kc = new KeywordController();
		
//		System.out.println(kc.getNewId());
		Keyword kw = new Keyword();
		kw.setKid(kc.getNewId());
		kw.setKeyword("SD卡雷锋精神考虑到");
		kc.saveOne(kw);
	}

}
