import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.Date;
import java.text.*;
import java.time.*;
import java.math.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import static jdk.nashorn.internal.objects.ArrayBufferView.length;
import static jdk.nashorn.internal.runtime.ScriptObject.toPropertyDescriptor;

public class CreateLogMapper extends Mapper<Object, Text, IntWritable,Text> {
    IntWritable time_offset = new IntWritable(1);
    Text word = new Text();
    int multiple = 10;//由文件行数到最终记录条数的放大倍数。最终记录条数=放大倍数*输入文件行数*每次迭代的次数的加权平均值(约100）
    int num_of_users = 8000000;

    public void create_one_log(Date date,int second_offset,int userID,boolean in_or_out, Context context)
            throws IOException,InterruptedException{
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        int hour = second_offset/3600;
        int minute = (second_offset%3600)/60;
        int second = second_offset % 60;
        String log = ft.format(date) + " " + String.valueOf(hour) + ":"+String.valueOf(minute) + ":"+
                String.valueOf(second) + " " + userID + (in_or_out?"in":"out");
        word.set(log);
        time_offset.set(second_offset/3600);
        context.write(time_offset,word);
    }

    public void map(Object key, Text value, Context context) throws IOException,InterruptedException {
        StringTokenizer itr = new StringTokenizer(value.toString());

        for(int i=0;i<multiple;i++){
            //用于决定是否有异常行为的随机数，如跨天登录,同时用于决定一天的登录登出次数
            double random_number = (int)(Math.random()*100)+50;
            int userID = (int)(Math.random()*num_of_users);

            //生成登入登出时间队列
            ArrayList<Integer> times=new ArrayList();
            for(int j=0;j<random_number;j++){
                times.add((int)(Math.random()*86400));
            }
            Collections.sort(times);

            if(random_number%56==0){
                //以登出记录开始，登录结尾的用户log
                Boolean in_out_flag = false;
                while(times.size()!=0){
                    // TODO:pop from the end to make it more efficient
                    int second_offset = times.get(times.size()-1);
                    Date date = new Date();
                    create_one_log(date,second_offset,userID,in_out_flag,context);
                    in_out_flag = in_out_flag?false:true;
                    times.remove(times.size()-1);
                }

            }
            else if(random_number%7==0){
                //生成以登出记录开始的用户log
                if(random_number%2==0){
                    //若登录登出的次数是偶数，则丢掉一个，使它的数量变为奇数
                    times.remove(times.size()-1);
                }
                Boolean in_out_flag = false;
                while(times.size()!=0){
                    int second_offset = times.get(times.size()-1);
                    Date date = new Date();
                    create_one_log(date,second_offset,userID,in_out_flag,context);
                    in_out_flag = in_out_flag?false:true;
                    times.remove(times.size()-1);
                }

            }
            else if(random_number%8==0){
                //生成以登录记录开始的用户log
                //登录登出的次数是偶数，则丢掉一个，使它的数量变为奇数
                times.remove(times.size()-1);
                Boolean in_out_flag = true;
                while(times.size()!=0){
                    int second_offset = times.get(0);
                    Date date = new Date();
                    create_one_log(date,second_offset,userID,in_out_flag,context);
                    in_out_flag = in_out_flag?false:true;
                    times.remove(times.size()-1);
                }
            }
        }
    }
}