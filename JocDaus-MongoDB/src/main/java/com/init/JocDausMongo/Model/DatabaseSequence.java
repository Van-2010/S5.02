package com.init.JocDausMongo.Model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "database_sequences")
@Component
public class DatabaseSequence {
	
	//classe per crear un id que s'autoincrementa
	
	    @Id
	    private String id;

	    private long seq;

	    public DatabaseSequence() {
	    	
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public long getSeq() {
	        return seq;
	    }

	    public void setSeq(long seq) {
	        this.seq = seq;
	    }
	}


