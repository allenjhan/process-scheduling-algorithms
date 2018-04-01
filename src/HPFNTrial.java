/**
 * Created by ahan on 9/23/17.
 */
import java.util.ArrayList;

public class HPFNTrial extends Trial{

    //Priority queues for different priorities
    private ArrayList<OSProcess> readyQueue1;
    private ArrayList<OSProcess> readyQueue2;
    private ArrayList<OSProcess> readyQueue3;
    private ArrayList<OSProcess> readyQueue4;

    //Average waiting time for each queue
    private double queue1AverageWaitingTime;
    private double queue2AverageWaitingTime;
    private double queue3AverageWaitingTime;
    private double queue4AverageWaitingTime;

    //Average throughput for each priority queue
    private double queue1Throughput;
    private double queue2Throughput;
    private double queue3Throughput;
    private double queue4Throughput;

    /**
     * Constructor for Highest-Priority-First, non-preemptive trial
     * @param trialNumber ordinal numbering for trial, i.e., what is its order in trials run
     * @param processPoolTotal total number of processes to be created for the run
     */
    public HPFNTrial(int trialNumber, int processPoolTotal, boolean arrivalTimeRandom){
        super(trialNumber, processPoolTotal, arrivalTimeRandom);
        readyQueue1 = new ArrayList<OSProcess>();
        readyQueue2 = new ArrayList<OSProcess>();
        readyQueue3 = new ArrayList<OSProcess>();
        readyQueue4 = new ArrayList<OSProcess>();
        scheduleName = "   HPF non-pre    ";
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
     * This method adds all processes available to be added to the correct ready queue.
     * @param time The current time of the simulation. Uses this to check if the arrival time of a process
     *             has arrived, making it ready to be added to the ready queues.
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
     * Causes all processes in ready queues to increment their waiting counter by 1.
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
     * Calculates average turnaround time, average waiting time, average response time. Also
     * calculates average waiting time and average throughput for each individiual priority queue.
     * @param timeRan this is the total execution time of the trial, used to calculate throughput
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
     * This prints the individual waiting times for each of the four queues.
     */
    public void printIndividualWaitingTimeStatistics(){
        System.out.print(scheduleName + " |Trial "+trialNumber+"| ");
        System.out.format("%8.2f | ", queue1AverageWaitingTime);
        System.out.format("%8.2f | ", queue2AverageWaitingTime);
        System.out.format("%8.2f | ", queue3AverageWaitingTime);
        System.out.format("%8.2f%n", queue4AverageWaitingTime);
    }

    /**
     * This prints the individual throughputs for each of the four queues.
     */
    public void printIndividualThroughputStatistics() {
        System.out.print(scheduleName + " |Trial " + trialNumber + "| ");
        System.out.format("%5.3f | ", queue1Throughput);
        System.out.format("%5.3f | ", queue2Throughput);
        System.out.format("%5.3f | ", queue3Throughput);
        System.out.format("%5.3f%n", queue4Throughput);
    }

    /**
     * This method returns the process to be scheduled
     * @param time Uses the current time to set the first scheduled time if it is the first time process
     *             gets scheduled.
     * @return  process to be scheduled
     */
    public OSProcess scheduleOneQuanta(int time){
        OSProcess scheduleThis = null;

        if (currentProcess==null || currentProcess.getRemainingTime()<=0) {
            if(!readyQueue1.isEmpty()) {
                for (OSProcess process : readyQueue1) {
                    if (process.getRemainingTime() > 0) {
                        scheduleThis = process;
                        readyQueue1.remove(process);
                        break;
                    }
                }
            }else if(!readyQueue2.isEmpty()) {
                for (OSProcess process : readyQueue2) {
                    if (process.getRemainingTime() > 0) {
                        scheduleThis = process;
                        readyQueue2.remove(process);
                        break;
                    }
                }
            }else if(!readyQueue3.isEmpty()) {
                for (OSProcess process : readyQueue3) {
                    if (process.getRemainingTime() > 0) {
                        scheduleThis = process;
                        readyQueue3.remove(process);
                        break;
                    }
                }
            }else if(!readyQueue4.isEmpty()) {
                for (OSProcess process : readyQueue4) {
                    if (process.getRemainingTime() > 0) {
                        scheduleThis = process;
                        readyQueue4.remove(process);
                        break;
                    }
                }
            }
        } else
            scheduleThis = currentProcess;

        if(scheduleThis!=null && !scheduleThis.isStarted()) {
            scheduleThis.setStarted(true);
            scheduleThis.setFirstScheduledTime(time);
        }

        return scheduleThis;
    }
}
