import java.io.IOException;
import java.util.*;
import java.util.regex.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

public class CreateLogReducer extends Reducer<IntWritable, Text , Text, Text> {
    Text result = new Text();
    Text empty_text = new Text("[end]");


    public void reduce(IntWritable	key, Iterable<Text> logs_raw, Context context) throws IOException,InterruptedException {
        ArrayList<ArrayList<String>> logs = new ArrayList<ArrayList<String>>();
        for(Text log_raw:logs_raw) {
            ArrayList<String> log = new ArrayList<String>();
            String log_text = new String(log_raw.getBytes());
            log.add(log_text);
            Pattern pattern = Pattern.compile("[0-9][0-9]:[0-9][0-9]:[0-9][0-9]");
            Matcher m = pattern.matcher(log_text);
            if(m.find()) {
                int second = 0;
                String time_00_00_00 = m.group(0);
                StringTokenizer itr = new StringTokenizer(time_00_00_00, ":");
                second += Integer.valueOf(itr.nextToken()) * 3600;
                second += Integer.valueOf(itr.nextToken()) * 60;
                second += Integer.valueOf(itr.nextToken());
                log.add(second + "");
                logs.add(log);
            }
        }

        // the raw logs process done;
        //start to sort;
        Collections.sort(logs, new Comparator<ArrayList<String>>() {
            public int compare(ArrayList<String> o1, ArrayList<String> o2) {
                return Integer.valueOf(o1.get(1)) > Integer.valueOf(o2.get(1)) ? 1:-1;
            }
        });

        //TODO:随机交换几对以模拟时间同步导致的log错乱

        for(ArrayList<String> log:logs){
            result.set(log.get(0));
            empty_text.set(String.valueOf(key.get()));
            context.write(result,null);
        }

    }
}