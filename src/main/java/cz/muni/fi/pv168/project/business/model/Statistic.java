package cz.muni.fi.pv168.project.business.model;

/**
 * @author Vladimir Borek
 */
public class Statistic {

    private int total = 0;
    private int completed = 0;
    private int overdue = 0;
    private int inProgress = 0;
    private int onHold = 0;


    public Statistic() {
    }

    public int getTotal() {
        return total;
    }

    public int getCompleted() {
        return completed;
    }

    public int getOverdue() {
        return overdue;
    }

    public int getInProgress() {
        return inProgress;
    }

    public int getOnHold() {
        return onHold;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public void setInProgress(int inProgress) {
        this.inProgress = inProgress;
    }

    public void setOnHold(int onHold) {
        this.onHold = onHold;
    }
}
