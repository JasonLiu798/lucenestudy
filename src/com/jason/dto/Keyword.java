package com.jason.dto;

import java.util.Iterator;
import java.util.Set;

public class Keyword {
	private static String pk ="kid";
	
	private int kid;
	private int count;
	private Set<KeywordHistory> historys;
	
	private String searchWord;
	
	
	public String getSearchWord() {
		return searchWord;
	}




	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}




	public static String getPk() {
		return pk;
	}




	public static void setPk(String pk) {
		Keyword.pk = pk;
	}




	public int getKid() {
		return kid;
	}




	public void setKid(int kid) {
		this.kid = kid;
	}




	public int getCount() {
		return count;
	}




	public void setCount(int count) {
		this.count = count;
	}




	public Set<KeywordHistory> getHistorys() {
		return historys;
	}




	public void setHistorys(Set<KeywordHistory> historys) {
		this.historys = historys;
	}




	@Override
	public String toString() {
		String res = "Keyword [kid=" + kid + ",searchText="+searchWord+", count=" + count + ",\n (Keyhistorys:";
		if(historys!=null){
			Iterator<KeywordHistory> itr = historys.iterator();
			while(itr.hasNext()){
				res = itr.next()+"\n";
			}
		}
		res+=")]";
		return res;
	}
	
	
}
