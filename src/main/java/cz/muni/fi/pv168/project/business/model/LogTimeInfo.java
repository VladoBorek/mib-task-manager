package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;

/**
 * @author Vladimir Borek
 */
public class LogTimeInfo extends Entity {
    private Integer loggedTime;
    private final User user;
    private final Long taskID;

    public LogTimeInfo(Integer loggedTime, User user, Long taskID){
        super(null);
        this.loggedTime = loggedTime;
        this.user = user;
        this.taskID = taskID;
    }

    public LogTimeInfo(Long id, Integer loggedTime, User user, Long taskID){
        super(id);
        this.loggedTime = loggedTime;
        this.user = user;
        this.taskID = taskID;
    }

    public Integer getLoggedTime(){
        return this.loggedTime;
    }
    public void setLoggedTime(Integer newLoggedTime){
        this.loggedTime = newLoggedTime;
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
}
