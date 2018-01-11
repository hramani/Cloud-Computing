import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class hadoopTeraSort {

	public static class TeraMapper extends Mapper<Object, Text, Text, Text> 
{
	private Text nK;
    private Text nV;
	
	public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		String word = value.toString();
		String[] tokens = word.split("\\n");
		 
		for(String tkn: tokens){
			nK = new Text(tkn.substring(0, 10));
			nV = new Text(tkn.substring(10, tkn.length()));
			context.write(nK, nV);
		}
	}
}

public static class TeraReducer extends Reducer<Text, Text, Text, Text> 
{

    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException 
    {
	      Text txt = new Text();
	      for (Text val : values)
	      {
	    	  txt = val;
	      }
	      context.write(key, txt);
    }
}

	public static void main(String[] args) throws Exception 
	{
		
		Configuration conf = new Configuration(); 
	    String[] nArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
	    
	    if (nArgs.length != 2) {
	    	System.err.println("!!!!!!!!!!! Invalid Inputs File !!!!!!!!!!!");
	    	System.err.println("Usage: hadoop jar <jar file> <in> <out>");
	    	System.exit(2);
	    }
	    
	    Job job = new Job(conf, "Terasort");
	    job.setJarByClass(TeraMain.class);
	    job.setMapperClass(TeraMapper.class);
	    job.setCombinerClass(TeraReducer.class);
	    job.setReducerClass(TeraReducer.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    FileInputFormat.addInputPath(job, new Path(nArgs[0]));
	    FileOutputFormat.setOutputPath(job, new Path(nArgs[1]));
	    System.exit(job.waitForCompletion(true)?0:1);
	}
}

