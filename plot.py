
import pandas as pd
import math  
import binascii
import matplotlib.pyplot as plt

def plot_graph(file_name, axis=None, transpose=True, last_data=False):
  offset=1;
  data = pd.read_csv(file_name, header=None,index_col=0)
  largest_len = data.index.tolist()[-1]
  
  if transpose:
    data = data.T
  
  #take the last data for comparision, the graph may settle to a steady value at the end of the graph
  if last_data:
    max_index= data.columns[(data.iloc[-1] == data.iloc[-1].max())].tolist()[0] -offset
    print (hex(int(max_index/2)))
  
  else:
    max_index= data.columns[(data == data.max())].tolist()[0] -offset
    print (hex(int(max_index/2)))
    
  if axis != None:
    data.plot(color='grey',legend=None, ax=axis)
    axis.get_lines()[max_index].set(color='red', linewidth=1.5,zorder=1000)
    #axis.get_lines()[max_index].set_color('red')
    axis.set_xlabel("Number of Traces")
    axis.set_ylabel("Correlation")
    
    axis.set_title("Key : " + str(hex(int(max_index/2))))
    
    plt.tight_layout()
    
    fig = axis.get_figure()
    fig.set_size_inches(18.5, 10.5)
    fig.savefig("Graphs.png")
  else:
    plot = data.plot(color='grey',legend=None)
    plot.get_lines()[max_index].set(color='red', linewidth=1.5,zorder=1000)
    #plot.get_lines()[max_index].set_color('red')
    plot.set_xlabel("Number of Traces")
    plot.set_ylabel("Correlation")
    
    plt.text(largest_len-20, data.iloc[-1,max_index]+0.1, "Key:"+str(hex(int(max_index/2))), fontdict=None, withdash=False)
    
    fig = plot.get_figure()
    fig.savefig("plot_"+file_name[:-4]+".png")
    #plt.show()

def plot_scatter(file_name, axis=None, transpose=False):
  data = pd.read_csv(file_name, header=None,index_col=0)
  data.loc[len(data)] = data.columns-1
  largest_len = data.index.tolist()[-1]
  
  if transpose:
    data = data.T
 
  if axis != None:
    #axis.get_lines()[max_index].set_color('red')
    plt.sca(axis)
    
    axis.set_xlabel("Hypothesis")
    axis.set_ylabel("Correlation")

    for x in data.iloc[1]:
      if data.iloc[0,int(x)] == data.max(axis=1).iloc[0]:
        axis.scatter(x, data.iloc[0,int(x)], color='red')
        axis.set_title("Key : " + hex(int(x)))
        print(str(hex(int(x))))
      else:
        axis.scatter(x, data.iloc[0,int(x)], color='grey')
    
    plt.tight_layout()
    
    fig = axis.get_figure()
    fig.set_size_inches(18.5, 10.5)
    fig.savefig("Scatters.png")
  else:
    #plot.get_lines()[max_index].set_color('red')
    plt.xlabel("Hypothesis")
    plt.ylabel("Correlation")
    
    for x in data.iloc[1]:
      if data.iloc[0,int(x)] == data.max(axis=1).iloc[0]:
        plt.scatter(x, data.iloc[0,int(x)], color='red')
        plt.text(x, data.iloc[0,int(x)]-0.05, "Key:"+str(hex(int(x))), fontdict=None, withdash=False)
       # print(str(hex(int(x))))
      else:
        plt.scatter(x, data.iloc[0,int(x)], color='grey')
    fig = plt.gcf()
    fig.savefig("scatter_"+file_name[:-4]+".png")

print("Plotting graph 1 out of 3")


f, ax = plt.subplots(4,4)
plot_graph("MinMaxCorMatrix1.csv", transpose=False, last_data=True,axis=ax[0,0])
plot_graph("MinMaxCorMatrix2.csv", transpose=False, last_data=True,axis=ax[0,1])
plot_graph("MinMaxCorMatrix3.csv", transpose=False, last_data=True,axis=ax[0,2])
plot_graph("MinMaxCorMatrix4.csv", transpose=False, last_data=True,axis=ax[0,3])

plot_graph("MinMaxCorMatrix5.csv", transpose=False, last_data=True,axis=ax[1,0])
plot_graph("MinMaxCorMatrix6.csv", transpose=False, last_data=True,axis=ax[1,1])
plot_graph("MinMaxCorMatrix7.csv", transpose=False, last_data=True,axis=ax[1,2])
plot_graph("MinMaxCorMatrix8.csv", transpose=False, last_data=True,axis=ax[1,3])

plot_graph("MinMaxCorMatrix9.csv", transpose=False, last_data=True,axis=ax[2,0])
plot_graph("MinMaxCorMatrix10.csv", transpose=False, last_data=True,axis=ax[2,1])
plot_graph("MinMaxCorMatrix11.csv", transpose=False, last_data=True,axis=ax[2,2])
plot_graph("MinMaxCorMatrix12.csv", transpose=False, last_data=True,axis=ax[2,3])

plot_graph("MinMaxCorMatrix13.csv", transpose=False, last_data=True,axis=ax[3,0])
plot_graph("MinMaxCorMatrix14.csv", transpose=False, last_data=True,axis=ax[3,1])
plot_graph("MinMaxCorMatrix15.csv", transpose=False, last_data=True,axis=ax[3,2])
plot_graph("MinMaxCorMatrix16.csv", transpose=False, last_data=True,axis=ax[3,3])
plt.show()
offset=2;
data = pd.read_csv("Graph.csv", header=None,index_col=1,usecols=[0,1])
plot = data.plot(legend=None)
print("Plotting graph 2 out of 3")
plot.set_title("Graph of number of traces againts number of bits recovered")

plot.set_ylabel("Number of Traces")
plot.set_xlabel("Number of bits recovered")

fig = plot.get_figure()
fig.savefig("traces vs bits recovered.png")
plt.show()

print("Plotting graph 3 out of 3")

f, ax = plt.subplots(4,4)
plot_scatter("100TracesCorMatrix1.csv",axis=ax[0,0])
plot_scatter("100TracesCorMatrix2.csv",axis=ax[0,1])
plot_scatter("100TracesCorMatrix3.csv",axis=ax[0,2])
plot_scatter("100TracesCorMatrix4.csv", axis=ax[0,3])

plot_scatter("100TracesCorMatrix5.csv", axis=ax[1,0])
plot_scatter("100TracesCorMatrix6.csv", axis=ax[1,1])
plot_scatter("100TracesCorMatrix7.csv", axis=ax[1,2])
plot_scatter("100TracesCorMatrix8.csv", axis=ax[1,3])

plot_scatter("100TracesCorMatrix9.csv", axis=ax[2,0])
plot_scatter("100TracesCorMatrix10.csv", axis=ax[2,1])
plot_scatter("100TracesCorMatrix11.csv", axis=ax[2,2])
plot_scatter("100TracesCorMatrix12.csv", axis=ax[2,3])

plot_scatter("100TracesCorMatrix13.csv", axis=ax[3,0])
plot_scatter("100TracesCorMatrix14.csv", axis=ax[3,1])
plot_scatter("100TracesCorMatrix15.csv", axis=ax[3,2])
plot_scatter("100TracesCorMatrix16.csv", axis=ax[3,3])

plt.show()
