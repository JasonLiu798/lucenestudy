package com.jason.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;

import com.jason.database.DBCPPoolManager;
import com.jason.database.DBUtil;
import com.jason.dto.Chapter;
import com.jason.dto.LawEntry;

public class LawEntryController {
//	private DBCPPoolManager dm;
	private BasicDataSource dm;
	
	
//	private LawEntryController(){
//		if (dm == null) {
//			dm = new DBUtil();
////			dm.startService();
//		}
//	}
	
	public LawEntryController(BasicDataSource dm){
		this.dm = dm;
	}
	
	public LawEntry getLawEntryById(int eid){
		LawEntry lw = new LawEntry();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		try {
			conn = dm.getConnection();
			stmt = conn.prepareStatement(" select  c.law,le.cid,c.cname,le.eid,le.ename,le.content "
					+ " from lawentry le left join chapter c on c.cid = le.cid where eid = ? limit 1 ");
			stmt.setInt(1, eid);
			rs = stmt.executeQuery();
			rs.next();
			lw.setLid( rs.getInt("law") );
			lw.setCid( rs.getInt("cid") );
			lw.setCname( rs.getString("cname") );
			lw.setEid( rs.getInt("eid") );
			lw.setEname( rs.getString("ename") );
			lw.setContent( rs.getString("content") );
			
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
		
		return lw;
	}
	
	public List<LawEntry> getLawEntrysByCid(int cid){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		List<LawEntry> lws = new LinkedList<LawEntry>();
		
		try {
			conn = dm.getConnection();
			stmt = conn.prepareStatement(" select  c.law,le.cid,c.cname,le.eid,le.ename,le.content "
					+ " from lawentry le left join chapter c on c.cid = le.cid where le.cid = ? ");
			stmt.setInt(1, cid);
			rs = stmt.executeQuery();
			while( rs.next()){
				LawEntry lw = new LawEntry();
				lw.setLid( rs.getInt("law") );
				lw.setCid( rs.getInt("cid") );
				lw.setCname( rs.getString("cname") );
				lw.setEid( rs.getInt("eid") );
				lw.setEname( rs.getString("ename") );
				lw.setContent( rs.getString("content") );
				lws.add(lw);
//				System.out.println(lw);
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
		return lws;
	}
	
	public List<LawEntry> getLawEntrys(){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		List<LawEntry> lws = new LinkedList<LawEntry>();
		
		try {
			conn = dm.getConnection();
			stmt = conn.prepareStatement(" select  c.law,le.cid,c.cname,le.eid,le.ename,le.content from lawentry le left join chapter c on c.cid = le.cid ");
			rs = stmt.executeQuery();
			while( rs.next()){
				LawEntry lw = new LawEntry();
				lw.setLid( rs.getInt("law") );
				lw.setCid( rs.getInt("cid") );
				lw.setCname( rs.getString("cname") );
				lw.setEid( rs.getInt("eid") );
				lw.setEname( rs.getString("ename") );
				lw.setContent( rs.getString("content") );
				lws.add(lw);
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
		
		return lws;
		
	}
	
	public List<LawEntry> chapters2LawEntrys(List<Chapter> lcs){
		Iterator<Chapter> lc = lcs.iterator();
		List<LawEntry> res  =  new LinkedList<LawEntry>();
		while (lc.hasNext()) {
			Chapter tmpCp = lc.next();
			res.addAll(tmpCp.getLawEntrys());
		}
		return res;
	}
	
	public boolean saveOne(LawEntry lw){
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean res = false;
		try {
			conn = dm.getConnection();
			stmt = conn.prepareStatement("INSERT INTO lawentry ( eid, cid,ename,content) "
							+ " VALUES(?, ?, ?, ?)");
			stmt.setInt(1, lw.getEid() );
			stmt.setInt(2, lw.getCid() );
			stmt.setString(3, lw.getEname() );
			stmt.setString(4, lw.getContent() );
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
	
	
	public boolean saveAll( List<LawEntry> lws){
		boolean res = true;
		Connection conn = null;
		PreparedStatement stmt = null;
		Iterator<LawEntry> it = lws.iterator();
		
		while (it.hasNext()) {
			LawEntry tmpLw = it.next();
			if( !this.saveOne(tmpLw)){
				res = false;
			}
		}
		return res;
	}
	
	
	
	
	public static void main(String[] args) {
//		LawEntryController lc = new LawEntryController();
//		LawEntry lw = lc.getLawEntryById(2);
//		System.out.println(lw);
//		
//		List<LawEntry> lws = lc.getLawEntrysByCid(2);
//		Iterator<LawEntry> it = lws.iterator();
//		while (it.hasNext()) {
//			LawEntry le = it.next();
//			System.out.println(le);
//		}
		
//		List<LawEntry> lws = lc.getLawEntrys();
//		Iterator<LawEntry> it = lws.iterator();
//		while (it.hasNext()) {
//			LawEntry le = it.next();
//			System.out.println(le);
//		}
	}

}
