package com.jason.tool;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;




import com.jason.dto.Chapter;
import com.jason.dto.LawEntry;

public class sepword {

	
	public static void main(String[] args) {
		String hbf = "D:\\up\\file\\hbf.txt";
		
//		File file = new java.io.File( hbf );
//		FileInputStream fis;
		
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			
			InputStreamReader isr=new InputStreamReader(new FileInputStream(hbf),"UTF-8");
			
			reader = new BufferedReader( isr );
			
			String line;
			int i=0;
			String chapter = "";
			String entry = "";
			String content = "";
			
			String regChap = "第.{1,2}章.*";
			String regEntry  = "第.{1,6}条.*";
			
			
			StringBuffer entrySb = new StringBuffer();
			Chapter cp = null;
			Chapter preCp = null;
			LawEntry le = null;
			
			int cid = 1;
			int eid = 1;
			List<Chapter> cps = new LinkedList();
			
			
			while ((line = reader.readLine()) != null) {
				i++;
				sb.append(line);
				Pattern ptC = Pattern.compile(regChap);
				Matcher mcC = ptC.matcher(line);
				boolean isChapter =  mcC.matches();
				
				Pattern ptE = Pattern.compile(regEntry);
				Matcher mcE = ptE.matcher(line);
				boolean isEntry =  mcE.matches();
				
				if( isChapter){
//					System.out.println("Chapter"+i+":"+ line );
					String[] seps = line.split("章");
					String cname = seps[1];
					
					System.out.println("Chapter:"+ cname);
					cp = new Chapter();
					cp.setCid(cid);
					cp.setCname( "");
					List<LawEntry> les = new LinkedList<LawEntry>();
					cp.setLawEntrys(les);
					cps.add(cp);
					
					if(cid > 1){
						preCp = cp;
					}
					cid++;
				}else if( isEntry){
					le = new LawEntry();
					String[] seps = line.split("条");
					String entryContent = "";
					if(seps.length==2){
						entryContent = seps[1];
					}else if(seps.length>2){
						for(int k = 0;k< seps.length;k++){
							entryContent += seps[k];
						}
					}
					le.setCid(cp.getCid());
					le.setEid(eid);
					le.setContent(entryContent);
					cp.getLawEntrys().add(le);
					System.out.println("Entry:"+ entryContent);
					eid++;
//					System.out.println("Entry"+i+":"+ line );
				}else{
					le.setContent(le.getCid()+line);
					System.out.println("ot"+i+":"+line);
				}
			}
			
			
			Iterator it = (Iterator) cps.iterator();
			while(it.hasNext()){
				Chapter tmpCp = (Chapter) it.next();
				System.out.println(tmpCp);
			}
			
			
			
//			while( ){
//				
//			}
//			
//			fis = new FileInputStream(file );
//			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();    
//			int ch = fis.read();   
//			while (ch != -1) {     
//			    bytestream.write(ch);
//			}
//			byte imgdata[] = bytestream.toByteArray();
//			System.out.println(imgdata);
//			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}
