package com.mysfmovies;
/**
 *  a class used to contain a pair of query information.
 */
public class SearchPair {
	 String type;
	 String val;
	 Type type_enum;
	 	SearchPair(Type type){
	 		this.type_enum=type;
	 		this.type=types(type);
	 	}
	 	SearchPair(Type type, String s){
	 		this.type_enum=type;
	 		this.type=types(type);
	 		this.val=s;
	 	}
	 	private String types(Type t){
	        switch(t){
	            case TITLE:
	                return "title";
	            case RELEASE_YEAR:
	                return "release_year";   
	            case LOCATION:
	                return "locations";
	            case PRODUCATION_COMPANY:
	                return "producation_company";
	            case DISTRIBUTOR:
	                return "distributor";
	            case DIRECTOR:
	                return "director";
	            case WRITER:
	                return "writer";
	            default:
	                return "actor";
	        }
	    }
}
