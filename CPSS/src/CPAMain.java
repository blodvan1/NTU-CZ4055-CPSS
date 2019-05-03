/**
* A Correlation Power Analysis written for
* Cyber Physical System Security - CZ4055 module
* from Nanyang Technological University
*
* The files needed :
*    1) Sample Trace file 
*    2) Key file  
* 
* @author  Lim Jing Qiang
* @version 1.0
* @since   2019-03-30 
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CPAMain {
	public ArrayList<PowerTrace> powerTrace;
	public double[][] traceMatrix;
	private String actualKey = "48 9D B4 B3 F3 17 29 61 CC 2B CB 4E D2 E2 8E B7";
	private Analyzer analyzer;

	public CPAMain(String fileName, int start, int end, int increment, String actualKeyFile, boolean isDefault) {   
		findKeyFile(actualKeyFile);
		cleanFile();
		
		int inputLen = actualKey.replaceAll(" ","").length();
		if(inputLen%2!=0) {
			 System.err.println("[ERR] Key length must be even");
			 return;
		}
    	powerTrace = new ArrayList<PowerTrace>();
    	loadPowerTraceFromFile(fileName);
    	if(isDefault) {
    		end = powerTrace.size();
    		start = end;
    		increment = end;
    	}
    	
    	
        if(end-start>powerTrace.size()) {
        	System.err.println("[ERR] Start and end value out of range! Max end value is "+powerTrace.size());
        }
        else if(end<start) {
        	System.err.println("[ERR] End value must be >= start value!");
        }
        else if(start<10) {
        	System.err.println("[ERR] Start value must be >= 10!");
        }
        else {
    	analyzer = new Analyzer(actualKey);
    	long sumTime = 0;
    	
    	for(int i = start; i<=end;i+=increment) {
    		int diff = end-i;
    		
    		System.out.println("Retrieving secret key using "+i+ " number of traces...");

    		long time_taken = analyzer.analyze(new ArrayList<PowerTrace> (powerTrace.subList(0, i)),end);
    		
    		sumTime += time_taken;
    		
    		System.out.println();
    	
    		if(diff<increment) 
    			increment=diff;
		
    		if(increment==0)
    			break;
          }
 
    	System.out.println("Total Time Taken : " + (sumTime) + " milliseconds");

		
        }
    }
    
	
	public void cleanFile() {
		
		File file;
		file = new File("Graph.csv");
		if(file.exists()) {
		System.out.println("Found existing Graph.csv . Deleting...");
			file.delete();
			
		}
		
		for(int i= 1 ; i<=16;i++) {
			file = new File("MinMaxCorMatrix"+i+".csv");
			
			if(file.exists()) {
				System.out.println("Found existing"+file.getName()+" . Deleting...");
				file.delete();
			}
			file = new File("100TracesCorMatrix"+i+".csv");
			if(file.exists()) {
				System.out.println("Found existing"+file.getName()+" . Deleting...");
				file.delete();
			}
			
		}
		
	}
    public void findKeyFile(String actualKeyFile) {
    	 File file = new File(actualKeyFile);
	     if(file.exists()) {
	  try {
          
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) {
                String line = input.nextLine();
                actualKey=line;
                System.out.println("["+actualKeyFile+"] Using "+actualKey+" as actual key");
            }
            input.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	     }
	     else {
	    	 System.err.println("[ERR] Key file not found! Using default key in program...");
	     }
        
	 
    }
   
    
	public static void main(String[] args) {
		if(args.length>0) {
		if(args.length>=4) {
		new CPAMain(args[0],Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3]), args[4], false);
		}
		else {
			System.err.println("[ERR] Please provide 4 or more arguments");
		}
		}
		else {
			
	new CPAMain("waveform.csv",-1,-1,-1,"Key.txt",true);
	
		}
	}
	
	public void loadPowerTraceFromFile(String csvFileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
		    String line;
		    try {
				while ((line = br.readLine()) != null) {
				    String[] values = line.split(",");
				    powerTrace.add(new PowerTrace(values));
				   
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		} catch (FileNotFoundException e1) {
			System.err.println("[ERR] Trace file not found. Make sure it is in the same directory and re-run the program.");
			System.exit(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
	}

}
