package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.business.model.abstracts.Entity;

/**
 * @author Vladimir Borek
 */
public class LogTimeInfo extends Entity {
    private Double loggedTime;
    private final User user;
    private Long taskID;
    private final TimeUnit timeUnit;

    public LogTimeInfo(Double loggedTime, User user, Long taskID, TimeUnit timeUnit) {
        super(null);
        this.loggedTime = loggedTime;
        this.user = user;
        this.taskID = taskID;
        this.timeUnit = timeUnit;
    }

    public LogTimeInfo(Long id, Double loggedTime, User user, Long taskID, TimeUnit timeUnit) {
        super(id);
        this.loggedTime = loggedTime;
        this.user = user;
        this.taskID = taskID;
        this.timeUnit = timeUnit;
    }

    public Double getLoggedTime() {
        return this.loggedTime;
    }

    public void setLoggedTime(Double newLoggedTime) {
        this.loggedTime = newLoggedTime;
    }

    public void setTaskID(Long taskID) {
        this.taskID = taskID;
    }

    public Long getTaskID() {
        return taskID;
    }

    public Long getUserId() {
        return user.id();
    }

    public String getUsername() {
        return user.username();
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
