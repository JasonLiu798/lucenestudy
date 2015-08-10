package com.jason.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jason.dao.ChapterDAO;
import com.jason.dto.Chapter;
import com.jason.dto.Law;
import com.jason.dto.LawEntry;

public class SepAndInsert {

	public static void main(String[] args) {

//		String hbf = "D:\\up\\file\\hbf.txt";
		String hbf = "D:\\up\\file\\scf.txt";
		
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(
					hbf), "UTF-8");
			reader = new BufferedReader(isr);

			String line=null;
			int i = 0;

			String regChap = "第.{1,2}章.*";
			String regEntry = "第.{1,6}条.*";

			StringBuffer entrySb = new StringBuffer();
			Chapter cp = null;
			Chapter preCp = null;
			LawEntry le = null;

			
			int lid = 2;
			int cid = 11;
			int eid = 101;

			List<Chapter> cps = new LinkedList();
			
			while ((line = reader.readLine()) != null) {
				i++;
				sb.append(line);
				Pattern ptC = Pattern.compile(regChap);
				Matcher mcC = ptC.matcher(line);
				boolean isChapter = mcC.matches();

				Pattern ptE = Pattern.compile(regEntry);
				Matcher mcE = ptE.matcher(line);
				boolean isEntry = mcE.matches();

				if (isChapter) {
					// System.out.println("Chapter"+i+":"+ line );
					String[] seps = line.split("章");
					String cname = seps[1];

					System.out.println("Chapter:" + cname);
					cp = new Chapter();
					cp.setCid(cid);
					cp.setCname(line );
					Law law = new Law();
					law.setLid(lid);
					cp.setLaw(law);

					Set<LawEntry> les = new HashSet<LawEntry>();
					cp.setLawEntrys(les);
//					cp.setLawEntrys(les);
					cps.add(cp);

					if (cid > 1) {
						preCp = cp;
					}
					cid++;

					isChapter = false;

				} else if (isEntry) {
					le = new LawEntry();
					String[] seps = line.split("条");
					String entryContent = "";
					String ename = seps[0]+"条";
					if (seps.length == 2) {
						entryContent = seps[1];
					} else if (seps.length > 2) {
						for (int k = 0; k < seps.length; k++) {
							entryContent += seps[k];
						}
					}
					Chapter ch = new Chapter();
					ch.setCid( cp.getCid() );
//					ch.set
					le.setEid(eid);
					le.setEname(ename );
					le.setContent(entryContent);
					le.setChapter(ch );
					cp.getLawEntrys().add(le);
					
					System.out.println("cid "+ le.getChapter().getCid() +",eid "+le.getEid() +":"+ entryContent);
					eid++;
					// System.out.println("Entry"+i+":"+ line );
					isEntry = false;
				} else {
					le.setContent( le.getContent()+ line );
					System.out.println("OT" + i + ",cid "+le.getChapter().getCid()+",eid "+le.getEid() +":" + le.getContent() );

//					System.out.println("Entry:" + entryContent);
					eid++;
					// System.out.println("Entry"+i+":"+ line );
				}
			}
			
//			ChapterController cc = new ChapterController( );
//			cc.saveAll(cps);
			
			
//			Iterator it = (Iterator) cps.iterator();
//			while (it.hasNext()) {
//				Chapter tmpCp = (Chapter) it.next();
//				System.out.println(tmpCp);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
