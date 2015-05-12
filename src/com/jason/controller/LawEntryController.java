package com.jason.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import com.jason.database.DBCPPoolManager;
import com.jason.dto.Chapter;
import com.jason.dto.LawEntry;

public class LawEntryController {
	private DBCPPoolManager dm;
	
	public LawEntryController(DBCPPoolManager dm){
		this.dm = dm;
	}
	
	public boolean saveEntry( Chapter cp,List<LawEntry> lws){
		boolean res = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		Iterator<LawEntry> it = lws.iterator();
		
		while (it.hasNext()) {
			LawEntry tmpLw = it.next();
			try {
				conn = dm.getConnection();
				stmt = conn.prepareStatement("INSERT INTO lawentry ( eid, cid,ename,content) "
								+ " VALUES(?, ?, ?, ?)");
				stmt.setInt(1, cp.getCid() );
				stmt.setInt(2, tmpLw.getCid() );
				stmt.setString(3, tmpLw.getEname() );
				stmt.setString(4, tmpLw.getContent() );
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
			
		}
		return res;
	}
	
	public static void main(String[] args) {
		
	}

}
