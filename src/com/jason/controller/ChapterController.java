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

	public ChapterController() {
		if (dm == null) {
			dm = new DBCPPoolManager();
			dm.startService();
		}
	}

	public void saveChapters(List<Chapter> cps) {
		Iterator it = (Iterator) cps.iterator();
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = dm.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		while (it.hasNext()) {
			Chapter tmpCp = (Chapter) it.next();
			try {
				stmt = conn
						.prepareStatement("INSERT INTO chapter ( cid,  cname)  VALUES(?, ?)");
				stmt.setInt(1, tmpCp.getCid());
				stmt.setString(2, tmpCp.getCname());
				stmt.execute();
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
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
