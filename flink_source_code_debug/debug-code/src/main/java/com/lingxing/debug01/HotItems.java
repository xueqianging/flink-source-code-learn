package com.lingxing.debug01;

import com.lingxing.bean.UserBehavior;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AscendingTimestampExtractor;

public class HotItems {
    public static void main(String[] args) throws Exception{
        // 1. 创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        // 2. 读取数据，创建DataStream
        DataStream<String> inputStream = env.readTextFile("F:\\demo\\flink_source_code_debug\\debug-code\\src\\main\\resources\\02_Data\\UserBehavior.csv");

        // 3. 转换为POJO，并分配时间戳和watermark
        DataStream<UserBehavior> dataStream = inputStream
                .map( line -> {
                    String[] fields = line.split(",");
                    return new UserBehavior(new Long(fields[0]), new Long(fields[1]), new Integer(fields[2]), fields[3], new Long(fields[4]));
                } )
                .assignTimestampsAndWatermarks(new AscendingTimestampExtractor<UserBehavior>() {
                    @Override
                    public long extractAscendingTimestamp(UserBehavior element) {
                        return element.getTimestamp() * 1000L;
                    }
                });

        dataStream.print();
        env.execute("hot items");
    }
}

