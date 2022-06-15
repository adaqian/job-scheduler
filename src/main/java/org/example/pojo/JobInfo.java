package org.example.pojo;

import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * @author: ada
 * @date: 2022/06/13
 **/
@Data
public class JobInfo {

    /**
     * job name
     */
    private String jobName;

    /**
     * job class
     */
    private String jobClass;

    private String description;

    /**
     * repetitive job interval(Seconds)
     */
    private Integer interval;

}
