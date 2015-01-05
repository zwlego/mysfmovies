package com.mysfmovies;

import java.util.ArrayList;
import java.util.List;

public class Query {

	String title;
	String year;
	String location;
	String production;
	String distributor;
	String director;
	String writer;
	String actor;
		public List<SearchPair> toSearchPairs(){
			List<SearchPair> pairs=new ArrayList<SearchPair>();
			if(!this.title.equals("")){
				pairs.add(new SearchPair(Type.TITLE,this.title));
			}
			if(!this.year.equals("")){
				pairs.add(new SearchPair(Type.RELEASE_YEAR,this.year));
			}
			if(!this.location.equals("")){
				pairs.add(new SearchPair(Type.LOCATION,this.location));
			}
			if(!this.production.equals("")){
				pairs.add(new SearchPair(Type.PRODUCATION_COMPANY,this.production));
			}
			if(!this.distributor.equals("")){
				pairs.add(new SearchPair(Type.DISTRIBUTOR,this.distributor));
			}
			if(!this.writer.equals("")){
				pairs.add(new SearchPair(Type.WRITER,this.writer));
			}
			if(!this.actor.equals("")){
				pairs.add(new SearchPair(Type.ACTOR,this.actor));
			}
			return pairs;
		}
}
