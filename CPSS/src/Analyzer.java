/**
* A Correlation Power Analysis written for
* Cyber Physical System Security - CZ4055 module
* from Nanyang Technological University
*
* This file is written in a multi-threaded way
* where each key byte can be analyzed concurrently.
* 
* The accuracy of the recovered key can also be
* calculated in this application
* 
*
* @author  Lim Jing Qiang
* @version 1.0
* @since   2019-03-30 
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Analyzer {
	public static int keyHyp[] = new int[256];
	static String[] key;

    public boolean validKey = false;
	public static String[] correlationDatas;
	String actualKey = "";

	private int keySize;
	
	public static int[] Sbox = {
            0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76,
            0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0,
            0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15,
            0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75,
            0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84,
            0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF,
            0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8,
            0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73,
            0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB,
            0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79,
            0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08,
            0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
            0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E,
            0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF,
            0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
	};

	  public Analyzer(String actualKey) {
		  this.actualKey = actualKey;
		  int inputLen = actualKey.replaceAll(" ","").length();
		  this.keySize = inputLen/2;
		  
		 if(inputLen%2==0) {
			
			 validKey=true;
		 }
		 else {
			 System.err.println("[ERR] Key length must be even");
		 }
			
	  }
	  
	  
	  public void writeToCSV(String fileName,ArrayList<String> data)
		{
			File file =null;
			FileWriter fr = null;
			try {
				file = new File(fileName);
				fr = new FileWriter(file, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedWriter br = new BufferedWriter(fr);
			PrintWriter pr = new PrintWriter(br,true);
			for(int i = 0; i<data.size();i++)
			pr.println(data.get(i));
			pr.close();
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	  
	public long analyze(ArrayList<PowerTrace> traces,int maxTraces) {
		
		if(!validKey) {
			System.out.println("Invalid key length!");
			System.exit((0));
		}

		CountDownLatch cdl = new CountDownLatch(keySize);
		
		String dataToWrite="";
		 key = new String[keySize];
		 
		 correlationDatas = new String[keySize];
		
		 Thread[] threads = new Thread[keySize];
		initKeyHyp();
	
		
	

	
		long startTime = System.currentTimeMillis();
		for(int i = 1; i<=keySize;i++) {
			
			 threads[i-1] = new AnalyzeOneByte(cdl,i,traces,maxTraces); 
            threads[i-1].start(); 

    		}
		
		try {
			cdl.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
long endTime = System.currentTimeMillis();
        //long firstTiming = (endTime - startTime);
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
		
//		cdl = new CountDownLatch(keySize/2);
//		 startTime = System.currentTimeMillis();
//		for(int i = (keySize/2)+1; i<=keySize;i++) {
//			
//			 threads[i-1] = new AnalyzeOneByte(cdl,i,traces,maxTraces); 
//           threads[i-1].start(); 
//
//   		}
//		
//		try {
//			cdl.await();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		endTime = System.currentTimeMillis();
//		System.out.println("That took " + (endTime - startTime) + " milliseconds");
//System.out.println("Total ="+(firstTiming +(endTime - startTime)));
	
	
		

		

	
	        String recoveredKey = getRecoveredKey();
	        int recoveredBits = bitsRecovered(actualKey,recoveredKey);
			System.out.println("   Actual: "+actualKey);
			System.out.println("Recovered: "+recoveredKey);
			System.out.println("Recovered number bits: "+recoveredBits+"  ("+((double)recoveredBits/(keySize*8.0))*100.0+"%)");
			
			ArrayList<String> temp = new ArrayList<String>();
			dataToWrite = traces.size()+","+recoveredBits;
			temp.add(dataToWrite);
			writeToCSV("Graph.csv",temp);
			dataToWrite="";
			
			return (endTime - startTime);
	}
	
	public int bitsRecovered(String actualKey,String recoveredKey) {
		int correctBits = 0;
		String bin_actual = hexToBin2(actualKey.replaceAll(" ",""));
		String bin_recovered = hexToBin2(recoveredKey.replaceAll(" ",""));
		for(int i = 0; i<bin_actual.length();i++) {
				if(bin_actual.charAt(i)==bin_recovered.charAt(i)) 
					correctBits++;
		}
		return correctBits;
	}
	
	public String getRecoveredKey() {
		String keyFound = "";
		
		for(int i =0; i<key.length;i++) {
		  keyFound += (key[i]+" ");
		}
		return keyFound;
	}
	
	void initKeyHyp() {
    	for(int i = 0; i<keyHyp.length;i++) {
    		keyHyp[i] = i;
    	}
    }
	
	
	
	
	
	
	
	
	public String hexToBin2(String s) {
		//Doing it byte by byte because we want to pad zeros infront
		String actual = "";
		for(int i = 0 ; i<s.length()/2;i++) {
			actual += hexToBin(s.substring(i*2,(i*2)+2));
		}
		return actual;
	}
	public String hexToBin(String s) {
		
        String preBin = new BigInteger(s, 16).toString(2);
        Integer length = preBin.length();
        if (length < 8) {
            for (int i = 0; i < 8 - length; i++) {
                preBin = "0" + preBin;
            }
        }
        return preBin;
    }
	
	
}
class AnalyzeOneByte extends Thread 

{  
	
	
	private ArrayList<PowerTrace> powerTrace = new ArrayList<PowerTrace>();
	private double[][] corMatrix;
	private double[][] modelMatrix;
	private ArrayList<String> dataToWrite = new ArrayList<String>();
	private ArrayList<String> dataToWrite_100Traces = new ArrayList<String>();
	private int byteNumber;
	private int maxTraces;
    private final CountDownLatch stopLatch;
	public AnalyzeOneByte(CountDownLatch stopLatch,int byteNumber,ArrayList<PowerTrace> powerTrace, int maxTraces) {
	this.maxTraces = maxTraces;
	this.stopLatch = stopLatch;
	this.byteNumber = byteNumber;
	this.powerTrace = powerTrace;
}
    public void run() 
    {  
        try
        { 
            // Displaying the thread that is running 
            System.out.println ("Thread is analyzing key byte number " +byteNumber); 
            
    		initModelMatrix(byteNumber);
    		 
    		// startTime = System.currentTimeMillis();
    		createCorrelationMatrix(); 
    	//	endTime = System.currentTimeMillis();
    		//System.out.println("Byte "+byteNumber +"  That took " + (endTime - startTime) + " milliseconds");
    		Analyzer.key[(byteNumber - 1)] = findKeyIndex();
    		writeToCSV("MinMaxCorMatrix"+byteNumber+".csv",dataToWrite);
    		writeToCSV("100TracesCorMatrix"+byteNumber+".csv",dataToWrite_100Traces);
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.err.println ("Exception is caught in Thread "+byteNumber); 
        } 
        
        finally {
        	//System.out.println("Thread analyzing key byte number " + byteNumber + " is done! Left with "+stopLatch.getCount() +" key bytes!");
          stopLatch.countDown();
        }
        return;
    }
    
    public void writeToCSV(String fileName,ArrayList<String> data)
	{
		File file =null;
		FileWriter fr = null;
		try {
			file = new File(fileName);
			fr = new FileWriter(file, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter br = new BufferedWriter(fr);
		PrintWriter pr = new PrintWriter(br,true);
		for(int i = 0; i<data.size();i++)
		pr.println(data.get(i));
		pr.close();
		try {
			br.close();
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
    
    public void initModelMatrix(int byteNo) {
		modelMatrix = new double[powerTrace.size()][Analyzer.keyHyp.length];
		
		for(int i = 0 ; i<powerTrace.size();i++) {
			String frontByte = "0x"+powerTrace.get(i).getPlainText().substring(2*(byteNo-1),2*byteNo);
			for(int j = 0; j<Analyzer.keyHyp.length;j+=8) {
			//Loop unrolling....
				modelMatrix[i][j] = Integer.bitCount(Analyzer.Sbox[Integer.decode(frontByte) ^ Analyzer.keyHyp[j]]);
				modelMatrix[i][j+1] = Integer.bitCount(Analyzer.Sbox[Integer.decode(frontByte) ^ Analyzer.keyHyp[j+1]]);
				modelMatrix[i][j+2] = Integer.bitCount(Analyzer.Sbox[Integer.decode(frontByte) ^ Analyzer.keyHyp[j+2]]);
				modelMatrix[i][j+3] = Integer.bitCount(Analyzer.Sbox[Integer.decode(frontByte) ^ Analyzer.keyHyp[j+3]]);
				modelMatrix[i][j+4] = Integer.bitCount(Analyzer.Sbox[Integer.decode(frontByte) ^ Analyzer.keyHyp[j+4]]);
				modelMatrix[i][j+5] = Integer.bitCount(Analyzer.Sbox[Integer.decode(frontByte) ^ Analyzer.keyHyp[j+5]]);
				modelMatrix[i][j+6] = Integer.bitCount(Analyzer.Sbox[Integer.decode(frontByte) ^ Analyzer.keyHyp[j+6]]);
				modelMatrix[i][j+7] = Integer.bitCount(Analyzer.Sbox[Integer.decode(frontByte) ^ Analyzer.keyHyp[j+7]]);
			}
		}
	}
    public String findKeyIndex() {
		int index = -1;
		String dataPlot= "";
		String dataPlot100Traces = "";
		double largest = -1.0;
		
	    for (int i = 0; i < Analyzer.keyHyp.length; i++) {
	    	double rowLargest = -1.0;
	    	double rowSmallest = 1.0;
	      for (int j = 0; j < powerTrace.get(0).getTotalNoOfSamplePoints(); j++) {
	        if (corMatrix[i][j] > largest)
	        {   //For analysis 
	        	largest = corMatrix[i][j];
	            index = i;
	        }
	        if(corMatrix[i][j]>rowLargest)//For logging down purpose
	        	rowLargest = corMatrix[i][j];
	        if(corMatrix[i][j]<rowSmallest)//For logging down purpose	
	        	rowSmallest = corMatrix[i][j];
	      }
	     dataPlot +=rowLargest+","+rowSmallest+",";
	     dataPlot100Traces +=rowLargest+",";
	    }
	    
	    if(dataPlot.length()>0) {//Store in ArrayList first before writing to csv
			dataPlot = dataPlot.substring(0,dataPlot.length()-1);
			dataToWrite.add(powerTrace.size()+","+dataPlot);
			dataPlot="";
	    }
	    if(dataPlot100Traces.length()>0 && powerTrace.size()== maxTraces) {//Store in ArrayList first before writing to csv
	    	dataPlot100Traces = dataPlot100Traces.substring(0,dataPlot100Traces.length()-1);
	    	dataToWrite_100Traces.add(powerTrace.size()+","+dataPlot100Traces);
			dataPlot100Traces="";
	    }  
	    return  String.format("%02X", (0xFF & index));
	}
	
    
    public void createCorrelationMatrix() {
		corMatrix = new double[Analyzer.keyHyp.length][powerTrace.get(0).getTotalNoOfSamplePoints()];
		double x[] = new double[powerTrace.size()];
		double y[] = new double[powerTrace.size()];
     
        
        
		for(int count = 0; count<corMatrix.length;count++) {
			
			for(int j = 0; j<powerTrace.size();j++) 
				y[j] = (double) modelMatrix[j][count]/256.0;
			//long    		 startTime = System.currentTimeMillis();
			for(int i = 0 ;i<powerTrace.get(0).getTotalNoOfSamplePoints();i++) {
				for(int j = 0; j<powerTrace.size();j++) {
					x[j] = powerTrace.get(j).getTraceWithIndex(i);
				}
				
				corMatrix[count][i] = correlation(x,y,x.length);
			
			}
		//	long endTime = System.currentTimeMillis();
    		//System.out.println("Byte "+byteNumber +" Loop "+count+" That took " + (endTime - startTime) + " milliseconds");	
		}
	}
    
    public double correlation(double[] arrayX, double[] arrayY, int size) {
		double sum_x = 0.0;
	    double sum_y = 0.0;
	    double sum_xy = 0.0;
	    double sum_xx = 0.0;
	    double sum_yy = 0.0;
	    double x = 0.0;
	    double y = 0.0;

	    for (int i = 0; i < size; i++)
	    {
	       x = arrayX[i];
	       y = arrayY[i];
	   // sum of elements of array X.
	      sum_x += x;
	   // sum of elements of array Y.
	      sum_y += y;
	   // sum of X[i] * Y[i]. 
	      sum_xy += x * y;
	      
	   // sum of square of array elements. 
	      sum_xx += x * x;
	      sum_yy += y * y;	     
	    }

	    return  (double)(size * sum_xy - sum_x * sum_y)  
                / Math.sqrt((double)(size * sum_xx - sum_x * sum_x)  
                        * (size * sum_yy - sum_y * sum_y));
	}
    
 
    
    
    
} 
