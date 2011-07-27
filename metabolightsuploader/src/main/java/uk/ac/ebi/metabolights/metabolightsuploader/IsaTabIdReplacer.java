package uk.ac.ebi.metabolights.metabolightsuploader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.naming.ConfigurationException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.spi.LoggerFactoryBinder;

import uk.ac.ebi.metabolights.checklists.CheckList;
import uk.ac.ebi.metabolights.checklists.SubmissionProcessCheckListSeed;
import uk.ac.ebi.metabolights.repository.accessionmanager.AccessionManager;
import uk.ac.ebi.metabolights.utils.FileUtil;
import uk.ac.ebi.metabolights.utils.StringUtils;
import uk.ac.ebi.metabolights.utils.Zipper;


/**
 * IsaTabReplacer
 * It replace StudyIds in ISATabFile by an accession number generated by accesionManager.
 * 
 *@author conesa
 */
public class IsaTabIdReplacer 
{
	static private Properties props = new Properties();
	static final String PROP_IDS = "isatab.ids";
	static String[] idList;
	static final String PROP_FILE_WITH_IDS = "isatab.filewithids";
    static String fileWithIds;

    private static final Logger logger = LoggerFactory.getLogger(IsaTabIdReplacer.class);
    
    static private AccessionManager am = AccessionManager.getInstance();
       
	private String isaTabFolder;
    
    private HashMap<String,String> ids = new HashMap<String,String>();
	
    private CheckList cl;
    /**
	 * 
	 * @param args
	 * First param must be the file name to work with. It should be a ISATab folder.
	 * @throws IOException 
	 * @throws IsaTabIdReplacerException 
	 * @throws ConfigurationException 
	 * 
	 */
	public static void main( String[] args ) throws ConfigurationException, IsaTabIdReplacerException, IOException{
		
		//Check the arguments. 1 is needed.
		if (args.length != 1){
			System.out.println("1 argument is required: 1st IsaFolder");
			return;
		}
		
		//There is 1 arguments
		IsaTabIdReplacer itr = new IsaTabIdReplacer();
		
		//Set the IsaTabArchive
		itr.setIsaTabFolder(args[0]);
		
		//Run it
		itr.Execute();
		
	}
	
	public IsaTabIdReplacer(String isaTabArchive){
		this.isaTabFolder = isaTabArchive;
	}
	public IsaTabIdReplacer(){}
	
	//IsaTabArchive properties
	public String getIsaTabFolder(){return isaTabFolder;}
	public void setIsaTabFolder(String folder){isaTabFolder = folder;}
	
	//Ids property
	public HashMap<String,String> getIds(){return ids;}
	public String getIdsNotes(){
		String notes;
		
		notes = "File " + fileWithIds + " found.";
		//GO through the ids hash
		for (Map.Entry<String,String> entry :ids.entrySet()){
			notes = notes + " Initial Id (" + entry.getKey() + ") has been replaced with metabolights Id (" + entry.getValue() +").";
		}
		return notes;
	}
	//CheckList porperty
	public void setCheckList(CheckList newCl){cl= newCl;}
	
	
	private static void loadProperties() throws FileNotFoundException, IOException, ConfigurationException{
		
		final String PROPS_FILE = "isatabidreplacer.properties";
		
		//If properties are loaded
		if (!props.isEmpty()) {return;}
		

		logger.info("Loading properties using getClassLoader().getResourceAsStream(" + PROPS_FILE + ")");
		
		//Load the properties from the property file
		props.load(IsaTabIdReplacer.class.getClassLoader().getResourceAsStream(PROPS_FILE));
		
		//If property file is empty
		if (props.size() ==0){
			
			//Dereference
			props = null;
			
			//Throw an exception
			throw new ConfigurationException("The isatabidreplacer.properties file has been found, but it is empty.");
		}
		
		//Initilize idList
		String ids = props.getProperty(PROP_IDS);
		
		logger.info(PROP_IDS + " property retrieved :" + ids);
		
		//Split it by ; to go through the array
	    idList = ids.split(";");

	    //Initialize fileWithIds
	    fileWithIds = props.getProperty(PROP_FILE_WITH_IDS);
	}
	private void updateCheckList (SubmissionProcessCheckListSeed spcls, String newNotes){
		
		//If we have a check list
		if (cl != null){
			cl.CheckItem(spcls.getKey(), newNotes);
		}
	}
	public void validateIsaTabArchive () throws IsaTabIdReplacerException{
		String[] msgs = new String[2];
		String msg;
		
		
		//Create a File object
		File isatab = new File(isaTabFolder);
		
		//If file does not exists
		if (!isatab.exists()) {
			//Add the error to msg
			msgs[1]="File " + isaTabFolder + " does not exists.";
		}
		
		//File must be a folder, if not
		if (!isatab.isDirectory()){
			//Add the error to msg
			msgs[0]= isatab.getName() + " is not a directory.\n";
		}

		
		//If there are messages (errors)
		msg = org.apache.commons.lang.StringUtils.join(msgs);
		
		//If there is any message...
		if ( !msg.equals("") ){
			
			//Throw customize exception...
			IsaTabIdReplacerException e = new IsaTabIdReplacerException("Invalid ISA Tab File:\n", msgs);
			throw e;
		}
		
		//Check CheckList Item
		updateCheckList(SubmissionProcessCheckListSeed.FILEVALIDATION, "File passed basic validation: correct extension and file found.");
		
	}

	public void Execute() throws IsaTabIdReplacerException, IOException, ConfigurationException{
		
		logger.info("Starting the replacements of the ids");
		
		//Reset id List, it will be populated with the new accession numbers generated
		ids.clear();
		
		//Load properties
		loadProperties();
		
		//Validate
		validateIsaTabArchive();
				
		//Replace id
		replaceIdInFiles();
		
		//Update CheckList
		updateCheckList(SubmissionProcessCheckListSeed.IDREPLACEMENTS, getIdsNotes());
		
	}
	private void replaceIdInFiles () throws IOException{
		
		//Search for the investigation file
		File isaFolder = new File(isaTabFolder);
		File[] fileList;
		
		
		//Define a filename filter
		FilenameFilter filter = new FilenameFilter() {
			
			public boolean accept(File arg0, String arg1) {
				
				//Accept only investigation files
				return (arg1.equals(fileWithIds));
			}
		};
		
		//Get the file list filtered
		fileList = isaFolder.listFiles(filter);
		
		//If there is not an investigation file...
		if (fileList.length ==0 || fileList == null) {
			throw new FileNotFoundException ("File with Ids (" + fileWithIds + ") not found");
		}
		
		//There must be only one, so take the first
		replaceIdInFile(fileList[0]);

	}
	/**
	 * Replaces Id in a single file. Goes through each line and replace the id if it's the correct line.
	 * @param fileWithId
	 * @throws IOException
	 */
	private void replaceIdInFile(File fileWithId) throws IOException{
		
		logger.info("Replacing ids in file -->" + fileWithId.getAbsolutePath());
		
		//Use a buffered reader
		BufferedReader reader = new BufferedReader(new FileReader(fileWithId));
        String line = "";
        String text = "";
        
        //Go through the file
        while((line = reader.readLine()) != null)
        {
        	//Replace Id in line (if necessary)
        	line = replaceIdInLine (line);
        	
            //Add the final carriage return and line feed
        	text += line + "\r\n";
        }
        
        //Close the reader
        reader.close();
        
        //Save the file
        FileUtil.String2File(text, fileWithId.getPath());
		
	}
	
	private String replaceIdInLine(String line){
		
	    //For each id...
	    for (int i=0;i<idList.length;i++) {
	      
	      //Get the value (Study Identifier, Investigation Identifier)
	      String id = idList[i];
	      
	      //If the value is present in line, in the first position.
	      if (line.indexOf(id)==0){
	    	  
	    	  logger.info("Id found in line " + line);
	    	
	    	  //Get the accession number
	    	  String accession = am.getAccessionNumber();
	    	  
	    	  //Get the Id Value (i.e.: BII-1-S)
	    	  String idInitialValue = StringUtils.replace(line, id + "\t\"", "");
	    	  idInitialValue = StringUtils.truncate(idInitialValue);
	    	  
	    	  //Compose the line:         Study Identifier   "MTBL1"
	    	  line = id + "\t\"" + accession + "\"";
	    	  
	    	  //If the value is a study identifier
	    	  //This is necessary for the uploading using command line tools.
	    	  //The accession number list will be used to assign permissions.
	    	  //Permissions can only be done to Study Identifier elements.
	    	  //Only Study Identifier can be linked.
	    	  if ("Study Identifier".equals(id)){
	    	  
	    		//Populate the list of new accession numbers (initialized in Execute method)
				//accessionNumberList = accessionNumberList + accession + " ";
				//initialIdValuesList = initialIdValuesList + idInitialValue + " ";
	    		ids.put(idInitialValue, accession);
	    		
	    	  }
	    		  
	    	  return line;
	      }
	      
	    }
	    
	    return line;
		
	}
}