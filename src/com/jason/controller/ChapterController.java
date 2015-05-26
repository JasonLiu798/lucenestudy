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
import com.jason.dto.LawEntry;

public class ChapterController {
	private BasicDataSource dm;
//	private DBUtil dm;
//	private DBCPPoolManager dm;
	private LawEntryController lc;
	
	public ChapterController() {
		if (dm == null) {
			dm = new BasicDataSource();
//			dm.startService();
		}
		
		if(lc == null){
			lc = new LawEntryController(dm);
		}
	}
	
	public ChapterController( BasicDataSource dm ) {
		if (dm == null) {
			dm = new BasicDataSource();
//			dm.startService();
		}else{
			this.dm = dm;
		}
		
		if(lc == null){
			lc = new LawEntryController(dm);
		}
	}
	
	public ChapterController( BasicDataSource dm ,LawEntryController lc) {
//		if (dm == null) {
//			dm = new DBUtil();
////			dm.startService();
//		}else{
		this.dm = dm;
//		}
		
		if(lc == null){
			lc = new LawEntryController(dm);
		}else{
			this.lc=lc;
		}
	}
	
	public Chapter getChapterById(int cid){
		Chapter ch = new Chapter();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		
		try {
			conn = dm.getConnection();
			stmt = conn.prepareStatement(" select  c.law,c.cid,c.cname from chapter c where cid = ? limit 1 ");
			stmt.setInt(1, cid);
			rs = stmt.executeQuery();
			rs.next();
			ch.setCid( rs.getInt("cid") );
			ch.setCname( rs.getString("cname") );
			ch.setLid(rs.getInt("law"));
//			System.out.println( ch );
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
		
		List<LawEntry> lws = lc.getLawEntrysByCid( cid );
//		System.out.println(lws.size() );
		ch.setLawEntrys(lws);
		return ch;
	}
	
	
	public List<Chapter> getChaptersByLid(int lid){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs =null;
		List<Chapter> lcs = new LinkedList<Chapter>();
		
		
		try {
			conn = dm.getConnection();
			stmt = conn.prepareStatement("select  c.cid from chapter c where c.law = ? order by c.cid");
			stmt.setInt(1, lid);
			rs = stmt.executeQuery();
			while( rs.next()){
				int cid = rs.getInt("cid");
//				System.out.println( cid );
				Chapter ch = getChapterById( cid );
//				System.out.println( cid+","+ch);
				lcs.add(ch);
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
		return lcs;
//		Chapter ch = new Chapter();
		
	}
		
		
	
	
	
	
	public boolean saveOne(Chapter cp){
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean res = false;
		try {
			conn = dm.getConnection();
			stmt = conn
					.prepareStatement("INSERT INTO chapter ( law, cid,  cname)  VALUES(?, ?,?)");
			stmt.setInt(1, cp.getLid());
			stmt.setInt(2, cp.getCid());
			stmt.setString(3, cp.getCname());
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
		
		List<LawEntry> lws = cp.getLawEntrys();
		lc.saveAll( lws);
		return res;
	}
	
	

	public boolean saveAll(List<Chapter> cps) {
		Iterator it = (Iterator) cps.iterator();
		
		boolean res = true;
		while (it.hasNext()) {
			Chapter tmpCp = (Chapter) it.next();
			if(!this.saveOne(tmpCp)){
				res = false;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		ChapterController cc = new ChapterController();
//		Chapter c = cc.getChapterById(2);
//		System.out.println(c);
		List<Chapter> lc = cc.getChaptersByLid(1);
		for(int i=0;i<lc.size();i++){
			Chapter c = lc.get(i);
			System.out.println(c);
			
		}
	}

}
