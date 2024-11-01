package cz.muni.fi.pv168.project.business.model;

/**
 * @author Vladimir Borek
 */
public class LogTimeInfo extends Entity{
    private Integer loggedTime;

    private final Employee user;

    public LogTimeInfo(Integer loggedTime, Employee user){
        super(null);
        this.loggedTime = loggedTime;
        this.user = user;
    }

    public Integer getLoggedTime(){
        return this.loggedTime;
    }
    public void setLoggedTime(Integer newLoggedTime){
        this.loggedTime = newLoggedTime;
    }

    public Long getUserId(){
        return user.getId();
    }

    public String getUsername(){
        return user.getName();
    }
}
