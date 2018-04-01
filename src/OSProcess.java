/**
 * Created by ahan on 9/22/17.
 */
import java.util.Random;

public class OSProcess {
    private double arrivalTime;
    private double totalRunTime;
    private int priority;
    private int originalPriority; //only used for HPF with aging
    private double isCompletedTime; // time completed
    private double firstScheduledTime; // time first scheduled
    private boolean arrived; // true if process arrival time is less than or equal to current time
    private double remainingTime; // remaining time for process
    private String processName;
    private boolean isStarted; // true if process has been scheduled at least once
    private double turnaroundTime;
    private double waitingTime;
    private double responseTime;
    private double timeInQueue; // used to determine when priority promotion should occur in HPF with aging
    private boolean queue1Present; // true if queue has been in queue 1; only used by HPF with aging
    private boolean queue2Present; // true if queue has been in queue 2; only used by HPF with aging
    private boolean queue3Present; // true if queue has been in queue 3; only used by HPF with aging
    private boolean queue4Present; // true if queue has been in queue 4; only used by HPF with aging
    private double queue1WaitingTime; // total waiting time spent in queue 1; only used by HPF with aging
    private double queue2WaitingTime; // total waiting time spent in queue 2; only used by HPF with aging
    private double queue3WaitingTime; // total waiting time spent in queue 3; only used by HPF with aging
    private double queue4WaitingTime; // total waiting time spent in queue 4; only used by HPF with aging

    /**
     * Constructor for process object
     * @param processName is the process name
     * @param arrivalTime is the arrival time of process
     * @param totalRunTime is the total execution time of process
     * @param priority is the priority of the process
     */
    public OSProcess(String processName, double arrivalTime, double totalRunTime, int priority){
        this.arrivalTime = arrivalTime;
        this.totalRunTime = totalRunTime;
        remainingTime = totalRunTime;
        this.priority = priority;
        this.originalPriority = priority;
        this.processName = processName;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getTotalRunTime() {
        return totalRunTime;
    }

    public void setTotalRunTime(double totalRunTime) {
        this.totalRunTime = totalRunTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getOriginalPriority() { return originalPriority; }

    public double getIsCompletedTime() {
        return isCompletedTime;
    }

    public void setIsCompletedTime(double isCompletedTime) {
        this.isCompletedTime = isCompletedTime;
    }

    public double getFirstScheduledTime() {
        return firstScheduledTime;
    }

    public void setFirstScheduledTime(int firstScheduledTime) {
        this.firstScheduledTime = firstScheduledTime;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public double getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }

    public double getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(double turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public double getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }

    public double getTimeInQueue() {
        return timeInQueue;
    }

    public void setTimeInQueue(double timeInQueue) {
        this.timeInQueue = timeInQueue;
    }

    public boolean isQueue1Present() {
        return queue1Present;
    }

    public void setQueue1Present(boolean queue1Present) {
        this.queue1Present = queue1Present;
    }

    public boolean isQueue2Present() {
        return queue2Present;
    }

    public void setQueue2Present(boolean queue2Present) {
        this.queue2Present = queue2Present;
    }

    public boolean isQueue3Present() {
        return queue3Present;
    }

    public void setQueue3Present(boolean queue3Present) {
        this.queue3Present = queue3Present;
    }

    public boolean isQueue4Present() {
        return queue4Present;
    }

    public void setQueue4Present(boolean queue4Present) {
        this.queue4Present = queue4Present;
    }

    public double getQueue1WaitingTime() {
        return queue1WaitingTime;
    }

    public void setQueue1WaitingTime(double queue1WaitingTime) {
        this.queue1WaitingTime = queue1WaitingTime;
    }

    public void incQueue1WaitingTime(){
        this.queue1WaitingTime += 1;
    }

    public double getQueue2WaitingTime() {
        return queue2WaitingTime;
    }

    public void setQueue2WaitingTime(double queue2WaitingTime) {
        this.queue2WaitingTime = queue2WaitingTime;
    }

    public void incQueue2WaitingTime(){
        this.queue2WaitingTime += 1;
    }

    public double getQueue3WaitingTime() {
        return queue3WaitingTime;
    }

    public void setQueue3WaitingTime(double queue3WaitingTime) {
        this.queue3WaitingTime = queue3WaitingTime;
    }

    public void incQueue3WaitingTime(){
        this.queue3WaitingTime += 1;
    }

    public double getQueue4WaitingTime() {
        return queue4WaitingTime;
    }

    public void setQueue4WaitingTime(double queue4WaitingTime) {
        this.queue4WaitingTime = queue4WaitingTime;
    }

    public void incQueue4WaitingTime(){
        this.queue4WaitingTime += 1;
    }

    /**
     * Deduct one quanta of running time from the process.
     * @param currentTime used to calculate the completed time, if process finishes running
     */
    public void deductOneQuantaRunning(double currentTime){
        remainingTime -= 1;
        if(remainingTime == 0.){
            isCompletedTime = currentTime+1;
        } else if(remainingTime < 0.){
            isCompletedTime = currentTime + 1 + remainingTime;
        }
    }

    /**
     * print the process name
     */
    public void printProcessName(){
        System.out.print(processName);
    }

    /**
     * Calculate turnaround time
     * @return turnaround time
     */
    public double calculateTurnaroundTime(){
        turnaroundTime = isCompletedTime - arrivalTime;
        return turnaroundTime;
    }

    /**
     * Calculate response time
     * @return response time
     */
    public double calculateResponseTime(){
        responseTime = firstScheduledTime - arrivalTime;
        return responseTime;
    }
}
