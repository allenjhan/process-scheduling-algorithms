/**
 * Created by ahan on 9/23/17.
 */
import java.util.ArrayList;
public class HPFPTrial extends Trial{

    //Queues for four different priorities
    private ArrayList<OSProcess> readyQueue1;
    private ArrayList<OSProcess> readyQueue2;
    private ArrayList<OSProcess> readyQueue3;
    private ArrayList<OSProcess> readyQueue4;

    //Waiting times for four different queues
    private double queue1AverageWaitingTime;
    private double queue2AverageWaitingTime;
    private double queue3AverageWaitingTime;
    private double queue4AverageWaitingTime;

    //Throughput for four different queues
    private double queue1Throughput;
    private double queue2Throughput;
    private double queue3Throughput;
    private double queue4Throughput;

    /**
     * Constructor for Highest-Priority-First, preemptive
     * @param trialNumber ordinal number for trial to be run
     * @param processPoolTotal total number of processes to create for run
     */
    public HPFPTrial(int trialNumber, int processPoolTotal, boolean arrivalTimeRandom){
        super(trialNumber, processPoolTotal, arrivalTimeRandom);
        readyQueue1 = new ArrayList<OSProcess>();
        readyQueue2 = new ArrayList<OSProcess>();
        readyQueue3 = new ArrayList<OSProcess>();
        readyQueue4 = new ArrayList<OSProcess>();
        scheduleName = "     HPF pre      ";
    }

    public ArrayList<OSProcess> getReadyQueue1() {
        return readyQueue1;
    }

    public void setReadyQueue1(ArrayList<OSProcess> readyQueue1) {
        readyQueue1 = readyQueue1;
    }

    public ArrayList<OSProcess> getReadyQueue2() {
        return readyQueue2;
    }

    public void setReadyQueue2(ArrayList<OSProcess> readyQueue2) {
        readyQueue2 = readyQueue2;
    }

    public ArrayList<OSProcess> getReadyQueue3() {
        return readyQueue3;
    }

    public void setReadyQueue3(ArrayList<OSProcess> readyQueue3) {
        readyQueue3 = readyQueue3;
    }

    public ArrayList<OSProcess> getReadyQueue4() {
        return readyQueue4;
    }

    public void setReadyQueue4(ArrayList<OSProcess> readyQueue4) {
        readyQueue4 = readyQueue4;
    }

    /**
     * Add all processes whose arrival times are less than current time to ready queue.
     * @param time used to determine if process has arrived yet
     */
    public void addToReadyQueue(double time){
        for(int i = 0; i<processes.size(); i++){
            if(processes.get(i).getArrivalTime()<=time && !processes.get(i).isArrived()) {
                switch(processes.get(i).getPriority()){
                    case 1:
                        readyQueue1.add(processes.get(i));
                        break;
                    case 2:
                        readyQueue2.add(processes.get(i));
                        break;
                    case 3:
                        readyQueue3.add(processes.get(i));
                        break;
                    case 4:
                        readyQueue4.add(processes.get(i));
                        break;
                    default:
                }
                processes.get(i).setArrived(true);
            }
        }
    }

    /**
     * Adds one quanta to the waiting time of all processes in a ready queue.
     */
    public void addOneQuantaWaiting(){
        for(OSProcess process : readyQueue1){
            process.setWaitingTime(process.getWaitingTime()+1.);
        }
        for(OSProcess process : readyQueue2){
            process.setWaitingTime(process.getWaitingTime()+1.);
        }
        for(OSProcess process : readyQueue3){
            process.setWaitingTime(process.getWaitingTime()+1.);
        }
        for(OSProcess process : readyQueue4){
            process.setWaitingTime(process.getWaitingTime()+1.);
        }
    }

    /**
     * Calculate turnaround time, waiting time, response time, and throughput. Also calculate
     * waiting time and throughput for each individual queue.
     * @param timeRan total time ran is used to calculate throughput
     */
    public void calculateStatistics(double timeRan){
        double turnaroundTimeSum = 0;
        double waitingTimeSum = 0;
        double queue1WaitingTimeSum = 0;
        double queue2WaitingTimeSum = 0;
        double queue3WaitingTimeSum = 0;
        double queue4WaitingTimeSum = 0;
        double responseTimeSum = 0;
        ranProcessCount=0;
        double queue1Count=0;
        double queue2Count=0;
        double queue3Count=0;
        double queue4Count=0;
        for (OSProcess process : processes){
            if (process.isStarted()) {
                ranProcessCount++;
                process.calculateTurnaroundTime();
                process.calculateResponseTime();
                turnaroundTimeSum += process.getTurnaroundTime();
                waitingTimeSum += process.getWaitingTime();
                responseTimeSum += process.getResponseTime();
                if(process.getPriority()==1){
                    queue1Count++;
                    queue1WaitingTimeSum += process.getWaitingTime();
                } else if(process.getPriority()==2){
                    queue2Count++;
                    queue2WaitingTimeSum += process.getWaitingTime();
                } else if(process.getPriority()==3){
                    queue3Count++;
                    queue3WaitingTimeSum += process.getWaitingTime();
                } else if(process.getPriority()==4){
                    queue4Count++;
                    queue4WaitingTimeSum += process.getWaitingTime();
                }
            }
        }

        averageTurnaroundTime = turnaroundTimeSum/ranProcessCount;
        averageWaitingTime = waitingTimeSum/ranProcessCount;
        queue1AverageWaitingTime = queue1WaitingTimeSum/queue1Count;
        queue2AverageWaitingTime = queue2WaitingTimeSum/queue2Count;
        queue3AverageWaitingTime = queue3WaitingTimeSum/queue3Count;
        queue4AverageWaitingTime = queue4WaitingTimeSum/queue4Count;
        averageResponseTime = responseTimeSum/ranProcessCount;
        throughput = ranProcessCount/timeRan;
        queue1Throughput = queue1Count/timeRan;
        queue2Throughput = queue2Count/timeRan;
        queue3Throughput = queue3Count/timeRan;
        queue4Throughput = queue4Count/timeRan;

    }

    /**
     * Print the average waiting times of each individual queue
     */
    public void printIndividualWaitingTimeStatistics(){
        System.out.print(scheduleName + " |Trial "+trialNumber+"| ");
        System.out.format("%8.2f | ", queue1AverageWaitingTime);
        System.out.format("%8.2f | ", queue2AverageWaitingTime);
        System.out.format("%8.2f | ", queue3AverageWaitingTime);
        System.out.format("%8.2f%n", queue4AverageWaitingTime);
    }

    /**
     * Print the average throughputs of each individual queue.
     */
    public void printIndividualThroughputStatistics() {
        System.out.print(scheduleName + " |Trial " + trialNumber + "| ");
        System.out.format("%5.3f | ", queue1Throughput);
        System.out.format("%5.3f | ", queue2Throughput);
        System.out.format("%5.3f | ", queue3Throughput);
        System.out.format("%5.3f%n", queue4Throughput);
    }

    /**
     * Scheduled next quanta with a process
     * @param time is used to timestamp the first scheduled time if process has never been scheduled before
     * @return the next process to be scheduled
     */
    public OSProcess scheduleOneQuanta(int time){
        OSProcess scheduleThis = null;

        if(!readyQueue1.isEmpty()) {
            scheduleThis = readyQueue1.get(0);
            if(currentProcess!=null && currentProcess.getRemainingTime()>0)
                readyQueue1.add(currentProcess);
            readyQueue1.remove(0);
        }else if(!readyQueue2.isEmpty()) {
            scheduleThis = readyQueue2.get(0);
            if(currentProcess!=null && currentProcess.getRemainingTime()>0)
                readyQueue2.add(currentProcess);
            readyQueue2.remove(0);
        }else if(!readyQueue3.isEmpty()) {
            scheduleThis = readyQueue3.get(0);
            if(currentProcess!=null && currentProcess.getRemainingTime()>0)
                readyQueue3.add(currentProcess);
            readyQueue3.remove(0);
        }else if(!readyQueue4.isEmpty()) {
            scheduleThis = readyQueue4.get(0);
            if(currentProcess!=null && currentProcess.getRemainingTime()>0)
                readyQueue4.add(currentProcess);
            readyQueue4.remove(0);
        }else if (currentProcess != null && currentProcess.getRemainingTime() > 0) {
            scheduleThis = currentProcess;
        }

        if(scheduleThis!=null && !scheduleThis.isStarted()) {
            scheduleThis.setStarted(true);
            scheduleThis.setFirstScheduledTime(time);
        }

        return scheduleThis;
    }
}
