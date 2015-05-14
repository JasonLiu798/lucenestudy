package com.jason.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.jason.database.DBCPPoolManager;
import com.jason.dto.Chapter;
import com.jason.dto.LawEntry;

public class ChapterController {
	private DBCPPoolManager dm;
	private LawEntryController lc;
	
	public ChapterController() {
		if (dm == null) {
			dm = new DBCPPoolManager();
			dm.startService();
		}
		if(lc == null){
			lc = new LawEntryController(dm);
		}
	}
	
	public Chapter getChapterById(int cid){
		Chapter ch = new Chapter();
		
		return ch;
	}
	
	
	public ChapterController( DBCPPoolManager dm ,LawEntryController lc) {
		if (dm == null) {
			dm = new DBCPPoolManager();
			dm.startService();
		}
		if(lc == null){
			lc = new LawEntryController(dm);
		}
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
		// TODO Auto-generated method stub

	}

}
