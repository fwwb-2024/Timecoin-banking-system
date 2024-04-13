package yswy.timesystem.backend.Entity;

public class TaskLableTaskCounts {

    Integer taskLable;
    Integer counts;
    public TaskLableTaskCounts(Integer taskLable,Integer counts){
        this.taskLable=taskLable;
        this.counts=counts;
    }

    public Integer getTaskLable(){
        return taskLable;
    }
    public Integer getCounts(){
        return counts;
    }

    public void setTaskLable(Integer taskLable){
        this.taskLable=taskLable;
    }
    public void setCounts(Integer counts){
        this.counts=counts;
    }
}
