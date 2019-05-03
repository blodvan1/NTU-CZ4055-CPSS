# Seret Key Recovery



This project is a deliverable of Cyber Physical System Security - (CZ4005) module from Nanyang Technological University. 

The project consists of two tools: 
  - Correlation Power Analysis Tool (Java)
  - Graphing Tool (Python)
 
### Correlation Power Analysis Tool
This tool is written in Java which analyzes power trace samples against a hypothesis model using Pearson's Correlated Coefficient. The tool is also written in a multithreaded manner to speed things up. Once the analysis is done, a list of .csv files will be created.

### Graphing Tool
This tool is written in Python which utilizes the pandas library to read the .csv files created by the Correlation Power Analysis Tool. 
In addition, it will also utilize the matplotlib.pyplot library to plot out the following graphs.
 - Correlation Coefficient vs No. of Traces (for each byte of the keys)
 - 4x4 Correlation Coefficient vs No. of Traces Graphs
 - No. of Traces vs No. of Bits Recovered
 - Correlation Coefficient of all possible key bytes for 100 traces


### Requirements
* [Python 3](https://realpython.com/installing-python/)
* [Python pandas Library](https://pandas.pydata.org/pandas-docs/stable/install.html)
* [Python matplotlib Library](https://pypi.org/project/matplotlib/)
* [Java](https://www.java.com/en/download/)
* [Eclipse IDE](https://www.eclipse.org/downloads/) or other Java IDE

### Getting Started



Extract the contents in CPSS.rar to Desktop
If you have other sample waveform.csv files, place your waveform.csv into the extracted folder.
If you are using another key, do update Key.txt with your key

**CPSS.jar takes in no arguments OR 5 arguments**
| Argument | Description |
| ------ | ------ |
| arg1 | File name of the trace samples i.e waveform.csv|
| arg2 | start from arg2 number of traces to do analysis (must be >=10)|
| arg3  | total number of traces you want to do analysis minus arg2  |
| arg4 | incremental step size from arg2 to arg3 |
| arg5 | Optional : File name of Key (default value = Key.txt) |

**Running CPSS.jar without arguments**
By default, the application will look for the file (waveform.csv) and analyze ALL the traces. i.e if you have 1500 trace samples, it will analyze all 1500.
Open up command prompt and type in : 

```sh
cd Desktop\CPSS
java -jar CPSS.jar
```

**Running CPSS.jar for analyzing x trace**
To analyze x trace i.e 300 traces
Open up command prompt and type in : 

```sh
cd Desktop\CPSS
java -jar CPSS.jar waveform.csv 300 300 300 Key.txt
```
**Retrieve the key bytes and plot the correlation coefficient graphs**
Open up command prompt and type in : 

```sh
cd Desktop\CPSS
java -jar CPSS.jar waveform.csv 10 200 10 Key.txt
```

Once the program finish analyzing the traces, these .csv files are created

| File name | Description |
| --- | --- |
| Graph.csv | Data for no. of traces vs no. of bits recovered|
| MinMaxCorMatrix1.csv | Data for Correlation Coefficient vs No. of Traces for 1st byte of Key|
| MinMaxCorMatrix2.csv | Data for Correlation Coefficient vs No. of Traces for 2nd byte of Key|
| MinMaxCorMatrix3.csv | Data for Correlation Coefficient vs No. of Traces for 3rd byte of Key|
| MinMaxCorMatrix4.csv | Data for Correlation Coefficient vs No. of Traces for 4th byte of Key|
| MinMaxCorMatrix5.csv | Data for Correlation Coefficient vs No. of Traces for 5th byte of Key|
| MinMaxCorMatrix6.csv | Data for Correlation Coefficient vs No. of Traces for 6th byte of Key|
| MinMaxCorMatrix7.csv | Data for Correlation Coefficient vs No. of Traces for 7th byte of Key|
| MinMaxCorMatrix8.csv | Data for Correlation Coefficient vs No. of Traces for 8th byte of Key|
| MinMaxCorMatrix9.csv | Data for Correlation Coefficient vs No. of Traces for 9th byte of Key|
| MinMaxCorMatrix10.csv | Data for Correlation Coefficient vs No. of Traces for 10th byte of Key|
| MinMaxCorMatrix11.csv | Data for Correlation Coefficient vs No. of Traces for 11th byte of Key|
| MinMaxCorMatrix12.csv | Data for Correlation Coefficient vs No. of Traces for 12th byte of Key|
| MinMaxCorMatrix13.csv | Data for Correlation Coefficient vs No. of Traces for 13th byte of Key|
| MinMaxCorMatrix14.csv | Data for Correlation Coefficient vs No. of Traces for 14th byte of Key|
| MinMaxCorMatrix15.csv | Data for Correlation Coefficient vs No. of Traces for 15th byte of Key|
| MinMaxCorMatrix16.csv | Data for Correlation Coefficient vs No. of Traces for 16th byte of Key|
| 100TracesCorMatrix1.csv | Data for Correlation Coefficient vs No. of Traces for 1st byte of Key for 100 traces|
| 100TracesCorMatrix2.csv | Data for Correlation Coefficient vs No. of Traces for 2nd byte of Key for 100 traces|
| 100TracesCorMatrix3.csv | Data for Correlation Coefficient vs No. of Traces for 3rd byte of Key for 100 traces|
| 100TracesCorMatrix4.csv | Data for Correlation Coefficient vs No. of Traces for 4th byte of Key for 100 traces|
| 100TracesCorMatrix5.csv | Data for Correlation Coefficient vs No. of Traces for 5th byte of Key for 100 traces|
| 100TracesCorMatrix6.csv | Data for Correlation Coefficient vs No. of Traces for 6th byte of Key for 100 traces|
| 100TracesCorMatrix7.csv | Data for Correlation Coefficient vs No. of Traces for 7th byte of Key for 100 traces|
| 100TracesCorMatrix8.csv | Data for Correlation Coefficient vs No. of Traces for 8th byte of Key for 100 traces|
| 100TracesCorMatrix9.csv | Data for Correlation Coefficient vs No. of Traces for 9th byte of Key for 100 traces|
| 100TracesCorMatrix10.csv | Data for Correlation Coefficient vs No. of Traces for 10th byte of Key for 100 traces|
| 100TracesCorMatrix11.csv | Data for Correlation Coefficient vs No. of Traces for 11th byte of Key for 100 traces|
| 100TracesCorMatrix12.csv | Data for Correlation Coefficient vs No. of Traces for 12th byte of Key for 100 traces|
| 100TracesCorMatrix13.csv | Data for Correlation Coefficient vs No. of Traces for 13th byte of Key for 100 traces|
| 100TracesCorMatrix14.csv | Data for Correlation Coefficient vs No. of Traces for 14th byte of Key for 100 traces|
| 100TracesCorMatrix15.csv | Data for Correlation Coefficient vs No. of Traces for 15th byte of Key for 100 traces|
| 100TracesCorMatrix16.csv | Data for Correlation Coefficient vs No. of Traces for 16th byte of Key for 100 traces|

Run the python script to plot the graphs
Type in the following in command prompt:
```sh
python plot.py
```
It will take few seconds to plot the graphs.
Alternatively, you can use the python notebook version : **cyber_physical_security.ipynb**
### Notes
This README will not provide instructions on how to modify the source code. Should there be any questions regarding the source code, you can contact me at [Lim Jing Qiang](mailto:LIMJ0202@e.ntu.edu.sg) or <jing_qiang_5@hotmail.com>
## Group Members

* **Lim Jing Qiang** 
* **Koh Chin Woon** 
* **Oon Zi Hui** 


## Acknowledgments
- Thank you Mr Prasanna Ravi (Lab Instructor), Naina Gupta, as well as other lab instructors for always answering any queries we have.


