package edu.ucr.qlyu001.Lucene;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.lucene.document.*;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

/**
 * Hello world!
 *
 */
public class App 
{	
	static class Page {
    String title;
    String latitude;
    String longitude;
    String source;
    String Date;
    String tweet_urls;
    String Hashtags;
    String text;
	    Page(String title, String latitude, String longitude, String source, String Date, String Hashtags, String text, String tweet_urls) {
	        this.title = title;
	        this.latitude = latitude;
	        this.longitude = longitude;
	        this.source = source;
	        this.Date = Date;
	        this.tweet_urls = tweet_urls;
	        this.Hashtags = Hashtags;
	        this.text = text;
	    }
	    
	    
	}
	public static void indexing(Page tweet) throws IOException{
		Directory directory = new RAMDirectory();
		InputStream stopWords = new FileInputStream("/Users/nellylyu/Development/eclipse/stopword.txt");
		Reader readerStopWords = new InputStreamReader(stopWords);
		StandardAnalyzer analyzer = new StandardAnalyzer(readerStopWords);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory, config); 
        Document doc = new Document(); 
        doc.add(new Field("title", tweet.title, TextField.TYPE_STORED));
        doc.add(new Field("latitude", tweet.latitude, TextField.TYPE_STORED));
        doc.add(new Field("longitude", tweet.longitude, TextField.TYPE_STORED));
        doc.add(new Field("source", tweet.source, TextField.TYPE_STORED));
        doc.add(new Field("Date", tweet.Date, TextField.TYPE_STORED));
        doc.add(new Field("tweet_urls", tweet.tweet_urls, TextField.TYPE_STORED));
        doc.add(new Field("Hashtags", tweet.Hashtags, TextField.TYPE_STORED));
        doc.add(new Field("text", tweet.text, TextField.TYPE_STORED));
        indexWriter.addDocument(doc);
        indexWriter.close();
	}
    public static void main( String[] args ) throws IOException
    {
    	 //Analyzer analyzer = new StandardAnalyzer();

    	
    	 String jsonFilePath = "/Users/nellylyu/Development/eclipse/Datafiles/0.json"; 
    	 int twittercount = 0;
         int filenumber = 0;
         // Store the index in memory:
         //Directory directory = new RAMDirectory();
         // To store an index on disk, use this instead:
         //Directory directory = FSDirectory.open("/tmp/test");
         
         //FileReader fileReader = new FileReader("/Users/nellylyu/Development/eclipse/Datafiles/0.json");
         String line = null;
         // Always wrap FileReader in BufferedReader.
         //BufferedReader bufferedReader = new BufferedReader(fileReader);
         JSONObject obj;
         ArrayList<JSONObject> json=new ArrayList<JSONObject>();
         
         File file = new File("/Users/nellylyu/Development/eclipse/Datafiles/"+filenumber+".json");
         String fileName = "/Users/nellylyu/Development/eclipse/Datafiles/"+filenumber+".json";
         try {
        	 
        	while(file.exists()){
                System.out.println("Reading from file '" + file + "'...");
                FileReader fileReader = new FileReader(fileName);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                line = bufferedReader.readLine();
                while((line = bufferedReader.readLine()) != null) {
                    obj = (JSONObject) new JSONParser().parse(line);
                     json.add(obj);
                     twittercount++;
                     String title = (String) obj.get("url_title").toString();
                     /*
                     System.out.println((String)obj.get("title")+":"+
                                        (String)obj.get("Date"));*/
                     String latitude = obj.get("latitude").toString();
                     /*
                     Object latitudeO = obj.get("latitude");
                     if (latitudeO instanceof Double)
                     {
                         String latitude = obj.get("latitude").toString();
                     }else{
                         String latitude = (String) obj.get("latitude");
                     }*/
                     String longitude = obj.get("longitude").toString();
                     /*
                     Object longitudeO = obj.get("longitude");
                     if (longitudeO instanceof Double)
                     {
                         String longitude = obj.get("longitude").toString();
                     }else{
                         String longitude = (String) obj.get("longitude");
                     }*/
                     //String longitude = (String) obj.get("longitude");
                     String source = (String) obj.get("source");
                     String Date = (String) obj.get("Date");
                     Object tweet_urlsO =  obj.get("tweet_urls");
                    
                     String tweet_urls = "";
                     if (tweet_urlsO instanceof JSONArray) {
                            // It's an array
                         JSONArray tweet_urlsA = (JSONArray) obj.get("tweet_urls");
                         Iterator<String> iterator1 = tweet_urlsA.iterator();
                         while (iterator1.hasNext()) {
                             tweet_urls += iterator1.next();
                           //System.out.println(iterator1.next());
                         }
                    }else{
                            // It's an object
                        tweet_urls =  (String)obj.get("tweet_urls");
                    }
                    
                     JSONArray HashtagsO = (JSONArray) obj.get("Hashtags");
                     String Hashtags = "";
                     Iterator<String> iterator2 = HashtagsO.iterator();
                     while (iterator2.hasNext()) {
                      //System.out.println(iterator2.next());
                      Hashtags += iterator2.next();
                      
                     }
                     //String Hashtags = (String) obj.get("Hashtags");
                     String text = (String) obj.get("text");
                     //System.out.println(text);
                     Page tweet1 = new Page(title, latitude, longitude, source, Date,  Hashtags, text, tweet_urls); 
                     indexing(tweet1);
                 }
                 // Always close files.
                 bufferedReader.close(); 
                 System.out.println("Current number of tweets = " + twittercount);
	             filenumber++;
	             file = new File("/Users/nellylyu/Development/eclipse/Datafiles/"+filenumber+".json");
                 fileName = "/Users/nellylyu/Development/eclipse/Datafiles/"+filenumber+".json";
        	}
          
             
            
             
        }
        catch( Throwable e ) {
        	e.printStackTrace();
        }

        
         System.out.println( "Hello World!" );
         
         
    }
}
