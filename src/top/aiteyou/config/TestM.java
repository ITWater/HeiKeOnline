package top.aiteyou.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;


/**
	*@author :tb
	*@version 2018年7月16日 下午6:30:19
*/

public class TestM {

    static Map<String,Counter> pool=new HashMap<>();
    /**
     * 实现一个统计URL在一段时间的使用次数，当超过次数回，拒绝访问
     *
     *    - 时间由参数决定
     *    - 时间需要分隔成N个时间片进行统计
     */
    public static void main(String[] args) {
	    // write your code here
    	int count=0;
    	Random random=new Random();
    	String url_prefix="http://url/";
    	while(count<10000){
    		int random_int=random.nextInt(9);
    		String url=url_prefix+random_int;
    		boolean result=Counter.increament(url, 1, 20);
    		
//    		模拟请求等待时间
    		try {
				Thread.currentThread().sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		count++;
    	}
    	
    	
    }
    static class Counter {
         int timePeriod;
         int num;
         
         
         long lastQuestTime=System.currentTimeMillis();//创建对象的系统时间
//         LinkedList<Integer> queue=new LinkedList<>();
         int [] queue; 
         
         public Counter() {
        	this(100, 10); //默认100ms 10片段
 		}
	     /**
	      * param timePeriod时间周期 1000ms
	      * @param num时间片段数  10
	      */
         public Counter(int timePeriod, int num) {
             this.timePeriod=timePeriod;
             this.num=num;
             queue=new int[num];//默认存储0次
         }    

      

         /**
        *   
        * @param url  请求url
        * @param add
        * @param max  允许的最大次数
        * @return
        */
         public static boolean  increament(String url, int add, int max) {
         	
         	//默认阻止
             boolean result=false;
             
             Counter counter=pool.get(url);
             
             if(counter==null){
                 counter=new Counter();
             }
             long currentTime=System.currentTimeMillis();
             long dis=currentTime-counter.lastQuestTime;
             //判断时间是否过了一轮
             if (dis>counter.timePeriod) {
				counter=new Counter();//
				System.out.println(url+"重新创建对象");
			}
             int pageNum=(int)(dis%counter.timePeriod)/(counter.timePeriod/counter.num);  
             counter.queue[pageNum]=add+counter.queue[pageNum];
             
             //判断是否超过max
             if (counter.queue[pageNum]<max) {
				result=true;
				System.out.println("请求成功");
			 }
             else {
            	 result=false;
            	 System.out.println(url+":"+counter.toString());
			}
             //保存本次访问的时间
             counter.lastQuestTime=currentTime;
             
             //更新
             pool.put(url,counter);
             
             return result;
         }
		@Override
		public String toString() {
			return "queue=" + Arrays.toString(queue) ;
		}
         
         
     }
    



	
}
/**
 * 实现一个统计URL在一段时间的使用次数，当超过次数回，拒绝访问
 *
 *    - 时间由参数决定
 *    - 时间需要分隔成N个时间片进行统计
 */

