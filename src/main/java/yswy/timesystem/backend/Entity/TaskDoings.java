package yswy.timesystem.backend.Entity;

import lombok.Data;

@Data
public class TaskDoings {
    private Integer taskID;
    private String taskDoingText;
    private String taskDoingPhoto;

    @Override
    public String toString() {
        return "TaskDoings{" +
                "taskID=" + taskID +
                ", taskDoingText='" + taskDoingText + '\'' +
                ", taskDoingPhoto='" + taskDoingPhoto + '\'' +
                '}';
    }
}
