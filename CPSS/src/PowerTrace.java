/**
* A Correlation Power Analysis written for
* Cyber Physical System Security - CZ4055 module
* from Nanyang Technological University
*
* This is a simple entity class file for 1 Power Trace
*
* @author  Lim Jing Qiang
* @version 1.0
* @since   2019-03-30 
*/
public class PowerTrace {

	private int number_of_sample_points;
	private double[] traces;
	private String plain_text;

	
	public PowerTrace(String[] data) {
		int size = data.length;
		number_of_sample_points = size-2;
		plain_text = data[0];
		this.traces = new double[size];
		for(int i = 2; i<size;i++) {
		this.traces[i-2] = Double.parseDouble(data[i]);
	   }
		
	}
	public int getTotalNoOfSamplePoints() {
		return number_of_sample_points;
	}
	public String getPlainText() {
		return plain_text;
	}
	
	public double[] getTraces() {
		return traces;
	}
	
	public double getTraceWithIndex(int index) {
		return traces[index];
	}
	
	public double[] getAllTrace(){
		return traces;
	}
	

	
	
}
