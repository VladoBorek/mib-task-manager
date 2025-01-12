package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;

/**
 * @author Vladimir Borek
 */
public class LogTimeInfo extends Entity {
    private Double loggedTime;
    private final User user;
    private Long taskID;

    public LogTimeInfo(Double loggedTime, User user, Long taskID){
        super(null);
        this.loggedTime = loggedTime;
        this.user = user;
        this.taskID = taskID;
    }

    public LogTimeInfo(Long id, Double loggedTime, User user, Long taskID){
        super(id);
        this.loggedTime = loggedTime;
        this.user = user;
        this.taskID = taskID;
    }

    public Double getLoggedTime(){
        return this.loggedTime;
    }
    public void setLoggedTime(Double newLoggedTime){
        this.loggedTime = newLoggedTime;
    }
    public void setTaskID(Long newTaskID) {
        this.taskID = newTaskID;
    }

    public Long getTaskID() {
        return taskID;
    }

    public Long getUserId(){
        return user.id();
    }

    public String getUsername(){
        return user.username();
    }

    @Override
    public String toString() {
        var nonNullID = id == null ? 691 : id;
        return "LogTimeInfo{" +
                "id=" + id +
                "taskID=" + taskID +
                "user=" + user +
                "uniqueNumber=" + taskID * getUserId() * getLoggedTime() *  nonNullID + 5351 + this.hashCode()+ this.loggedTime+
                '}';
    }
}
