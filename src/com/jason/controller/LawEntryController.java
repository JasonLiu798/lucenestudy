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
	
	
	public boolean save2Db(Connection conn,Chapter cp,List<LawEntry> lws){
		boolean res = false;
		
		PreparedStatement stmt = null;
		Iterator it = lws.iterator();
		
		while (it.hasNext()) {
			LawEntry tmpLw = (LawEntry) it.next();
			try {
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
		// TODO Auto-generated method stub

	}

}
