package com.jason.lucene;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import net.paoding.analysis.analyzer.PaodingAnalyzer;
import net.teamhot.lucene.ThesaurusAnalyzer;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.jason.dao.LawEntryDAO;
import com.jason.dto.LawEntry;
import com.jason.tool.Constant;
import com.zb.mmseg.analysis.MMSegAnalyzer;

public class GenerateLawIndex {
	
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式  
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式  
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式  
    //private static final String regEx_space = "\\s+\\s+|\t|\r|\n";//多个空格，回车，换行符，转为单个空格
    private static final String regEx_space = "\t|\r|\n";//回车，换行符，转为单个空格
    private static final String regEx_multispace = "\\s+\\s+";//多个空格，转为单个
//    private BasicDataSource basicDataSource;
//    private DataSource basicDataSource;
    
    //索引目录
//    public GenerateLawIndex(DataSource ds){
//    	this.basicDataSource = ds;
//    }
//    
    
	
	
//	public List<LawEntry> getLawContentFromDatabase(){
////		HashMap<Integer,String> res = new HashMap<Integer,String>();
//		List<LawEntry> res = new LinkedList<LawEntry>();
//		Connection conn =null;
//		PreparedStatement st = null;
//		try {
//			conn = this.basicDataSource.getConnection();
//			String sql = "select ID,post_title,post_content from posts";
////			PreparedStatement prest = conn.prepareStatement(sql);
////			prest.setInt(1, id);
//			st = conn.prepareStatement(sql);
//			ResultSet rs = st.executeQuery();
////			rs = prest.executeQuery();
//			while (rs.next()) {
//				PostVO tmp = new PostVO();
//				tmp.setId(rs.getInt("ID")+"");
//				tmp.setName(rs.getString("post_title"));
//				tmp.setContent( this.removeHtmlTag(rs.getString("post_content") ) );
//				res.add(tmp);
//				
////                res.put( rs.getInt("ID"),tmp );
//            }
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//		return res;
//	}
	
//	/**
//	 * 获取对象
//	 * @param id
//	 * @return
//	 */
//	public PostVO getPostVO(int id){
////		HashMap<Integer,String> hm = new HashMap<Integer,String>();
////		String res = "";
//		Connection conn = null;
//		PostVO res = null;
//		try {
//			conn = this.basicDataSource.getConnection();
//			String sql = "select ID,post_title,post_content from posts where ID=? limit 1";
//			PreparedStatement prest = conn.prepareStatement(sql);
//			prest.setInt(1, id);
//			ResultSet rs = prest.executeQuery();
//			while (rs.next()) {
//				res = new PostVO();
//				res.setId(rs.getInt("ID")+"");
//				res.setName(rs.getString("post_title"));
//				res.setContent( this.removeHtmlTag(rs.getString("post_content") ) );
////                res = rs.getString("post_content");
//            }
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}finally {
//            if (conn != null) {
//                try {
//                    conn.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//		return res;
//	}
	
	/**
	 * 获取index writer
	 * @param indexPath
	 * @param create
	 * @return
	 * @throws IOException
	 */
	private IndexWriter getIndexWriter(String indexPath,boolean create) throws IOException {
		IndexWriter writer = null;
		try {
			System.out.println("Indexing to directory '" + indexPath + "'...");
//			new File(indexPath)
			Directory dir = FSDirectory.open( Paths.get( indexPath )  );
//			MMSegAnalyzer analyzer = new MMSegAnalyzer();
			SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
			
//			Analyzer analyzer = new PaodingAnalyzer();
//			Analyzer analyzer = new  ThesaurusAnalyzer();
			//new StandardAnalyzer(Version.LUCENE_4_10_0);
			
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
//			boolean create = true;
			if (create) {
				// Create a new index in the directory, removing any
				// previously indexed documents:
				iwc.setOpenMode(OpenMode.CREATE);
			} else {
				// Add new documents to an existing index:
				iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			}
			writer = new IndexWriter(dir, iwc);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
	
	
	private void addDocs(IndexWriter indexWriter, List<LawEntry> resultList) throws IOException {  
        for (LawEntry le : resultList) {
            Document doc = le.createDoc();  
            indexWriter.addDocument(doc);
        }
    }
	
	/**
	 * 添加一条索引
	 * @param post_id
	 *
	public boolean addIndex(String path,int post_id){
		boolean res = false;
		try {
			Date start = new Date();
			IndexWriter iw = getIndexWriter( path ,false);
			PostVO p = null;
			p = getPostVO(post_id);
			if(p!=null){
				Document doc = createDoc(p); 
				iw.addDocument(doc);
				res = true;
			}
			iw.close();
			Date end = new Date();
			System.out.println("Generata one index:"+ (end.getTime() - start.getTime())
					+ " total milliseconds");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	*/
	
	/**
	 * 生成所有索引
	 *
	public boolean GenerateAllIndex(String path, LawEntryDAO lc){
		boolean res = false;
		try {
			Date start = new Date();
			IndexWriter iw = getIndexWriter(path,true);

			List<LawEntry> lws = lc.getLawEntrys();
			if(lws!=null){
				addDocs(iw,lws);
				res = true;
			}else{
				res = false;
			}
			iw.close();
			Date end = new Date();
			System.out.println("Generata Indexs: "+ (end.getTime() - start.getTime()) + " total milliseconds");
		} catch (IOException e) {
			e.printStackTrace();
			res = false;
		}
		return res;
	}
	*/
	
	/**
	 * Test
	 * @param args
	 */
	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		String path="applicationContext.xml";
		ApplicationContext factory = new ClassPathXmlApplicationContext(path);
		
		GenerateLawIndex gi = new GenerateLawIndex( );
//		gi.GenerateAllIndex(Constant.IDX_DIR , (LawEntryDAO)factory.getBean("lawentryCtrl"));

		
		
//		List l = gi.getAllContentFromDatabase();
//		Printer.printList(l);
		
//		
		
//		PostVO p = gi.getPostVO(32);
//		 
//		List<String> lists = gi.getWords(p.getContent() , new PaodingAnalyzer() );  
//		for (String s : lists) {
//		    System.out.println(s);  
//		}
//		
		
//		
		
		//String str = gi.getContentFromDatabase(33);
//		HashMap hm;
//		try {
//			hm = gi.getAllContentFromDatabase();
//			TestPrint.printHashMap(hm);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println("STR:"+str);
	}
}
