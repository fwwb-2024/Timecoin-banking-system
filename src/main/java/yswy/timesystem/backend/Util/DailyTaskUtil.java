package yswy.timesystem.backend.Util;

import yswy.timesystem.backend.Entity.Tasks;
import yswy.timesystem.backend.Entity.Tasksmulti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DailyTaskUtil {
    // 将任务发布量列表转换为指定长度的数组，并用0填充缺失的日期
    public static List<Tasksmulti> convertToArray(List<Tasksmulti> taskCounts, int expectedLength) {

        // 初始化结果列表和日期集合  
        List<Tasksmulti> tasksmultiList = new ArrayList<>();
        Set<LocalDate> dateSet = new HashSet<>();

        // 遍历taskCounts，为每个任务创建Tasksmulti对象并添加到列表中
        for (Tasksmulti task : taskCounts) {
            LocalDate taskDate = LocalDate.parse(task.getTaskBeginTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int taskCount = task.getCounts();

            // 查找或创建Tasksmulti对象，并更新counts  
            Tasksmulti existingTask = tasksmultiList.stream()
                    .filter(t -> t.getTaskBeginTime().equals(taskDate))
                    .findFirst()
                    .orElse(new Tasksmulti(taskDate, 0)); // 如果没有找到，则创建一个新的Tasksmulti对象，counts初始化为0  

            existingTask.setCounts(existingTask.getCounts() + taskCount); // 更新counts  

            // 将Tasksmulti对象添加到列表中（如果它之前不存在），并将日期添加到集合中  
            if (!tasksmultiList.contains(existingTask)) {
                tasksmultiList.add(existingTask);
                dateSet.add(taskDate);
            }
        }

        // 计算开始日期
        LocalDate startDate = LocalDate.now().minusDays(expectedLength - 1);

        // 自动补全列表到 expectedLength 的长度
        IntStream.range(0, expectedLength)
                .mapToObj(i -> startDate.plusDays(i))
                .filter(date -> !dateSet.contains(date)) // 过滤掉已经存在的日期  
                .forEach(missingDate -> tasksmultiList.add(new Tasksmulti(missingDate, 0))); // 添加缺失的日期，counts为0  

        // 排序列表，以确保日期是连续的（如果需要）
        tasksmultiList.sort((t1, t2) -> t1.getTaskBeginTime().compareTo(t2.getTaskBeginTime()));

        // 打印Tasksmulti列表中的元素进行调试（如果需要）
        //tasksmultiList.forEach(System.out::println);

        // 返回Tasksmulti列表
        return tasksmultiList;

        }
}
