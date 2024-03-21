package yswy.timesystem.backend.Entity;

import java.time.LocalDate;

public class Tasksmulti {
    String taskBeginTime;
    Integer counts;

    public Tasksmulti(LocalDate taskDate, int taskCount) {
        this.taskBeginTime= String.valueOf(taskDate);
        this.counts=taskCount;
    }

    public String getTaskBeginTime() {
        return taskBeginTime;
    }

    public void setTaskBeginTime(String taskBeginTime) {
        this.taskBeginTime = taskBeginTime;
    }

    public Integer getCounts() {
        return counts;
    }

    public void setCounts(Integer counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        return "Tasksmulti{" +
                "taskBeginTime='" + taskBeginTime + '\'' +
                ", counts=" + counts +
                '}';
    }
}
