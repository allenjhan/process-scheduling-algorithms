/**
 * Created by ahan on 9/23/17.
 */
import java.util.ArrayList;
import java.util.Iterator;

public class HPFPWATrial extends Trial{

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

    //Throughputs for four different queues
    private double queue1Throughput;
    private double queue2Throughput;
    private double queue3Throughput;
    private double queue4Throughput;

    /**
     * Constructor for Highest-Priority-First, with Aging
     * @param trialNumber ordinal number of trial to be run
     * @param processPoolTotal total number of processes to create
     */
    public HPFPWATrial(int trialNumber, int processPoolTotal, boolean arrivalTimeRandom){
        super(trialNumber, processPoolTotal, arrivalTimeRandom);
        readyQueue1 = new ArrayList<OSProcess>();
        readyQueue2 = new ArrayList<OSProcess>();
        readyQueue3 = new ArrayList<OSProcess>();
        readyQueue4 = new ArrayList<OSProcess>();
        scheduleName = "  HPF pre w/ age  ";
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
     * Add all processes that have arrived to ready queue
     * @param time time used to determine if processes have arrived
     */
    public void addToReadyQueue(double time){
        for(int i = 0; i<processes.size(); i++){
            if(processes.get(i).getArrivalTime()<=time && !processes.get(i).isArrived()) {
                switch(processes.get(i).getPriority()){
                    case 1:
                        processes.get(i).setQueue1Present(true);
                        readyQueue1.add(processes.get(i));
                        break;
                    case 2:
                        processes.get(i).setQueue2Present(true);
                        readyQueue2.add(processes.get(i));
                        break;
                    case 3:
                        processes.get(i).setQueue3Present(true);
                        readyQueue3.add(processes.get(i));
                        break;
                    case 4:
                        processes.get(i).setQueue4Present(true);
                        readyQueue4.add(processes.get(i));
                        break;
                    default:
                }
                processes.get(i).setArrived(true);
            }
        }
    }

    /**
     * Add one quanta to waiting times of all processes in the waiting queue
     */
    public void addOneQuantaWaiting(){
        Iterator<OSProcess> queue1 = readyQueue1.iterator();
        while(queue1.hasNext()){
            OSProcess process = queue1.next();
            process.setWaitingTime(process.getWaitingTime()+1.);
            process.setTimeInQueue(process.getTimeInQueue()+1.);
            process.incQueue1WaitingTime();
        }
        Iterator<OSProcess> queue2 = readyQueue2.iterator();
        while(queue2.hasNext()){
            OSProcess process = queue2.next();
            process.setWaitingTime(process.getWaitingTime()+1.);
            process.setTimeInQueue(process.getTimeInQueue()+1.);
            process.incQueue2WaitingTime();
            if(process.getTimeInQueue()>=5){
                process.setTimeInQueue(0);
                process.setPriority(process.getPriority()-1);
                queue2.remove();
                readyQueue1.add(process);
                process.setQueue1Present(true);
            }
        }
        Iterator<OSProcess> queue3 = readyQueue3.iterator();
        while(queue3.hasNext()){
            OSProcess process = queue3.next();
            process.setWaitingTime(process.getWaitingTime()+1.);
            process.setTimeInQueue(process.getTimeInQueue()+1.);
            process.incQueue3WaitingTime();
            if(process.getTimeInQueue()>=5){
                process.setTimeInQueue(0);
                process.setPriority(process.getPriority()-1);
                queue3.remove();
                readyQueue2.add(process);
                process.setQueue2Present(true);
            }
        }
        Iterator<OSProcess> queue4 = readyQueue4.iterator();
        while(queue4.hasNext()){
            OSProcess process = queue4.next();
            process.setWaitingTime(process.getWaitingTime()+1.);
            process.setTimeInQueue(process.getTimeInQueue()+1.);
            process.incQueue4WaitingTime();
            if(process.getTimeInQueue()>=5){
                process.setTimeInQueue(0);
                process.setPriority(process.getPriority()-1);
                queue4.remove();
                readyQueue3.add(process);
                process.setQueue3Present(true);
            }
        }
    }

    /**
     * Calculate turnaround time, waiting time, response time, and throughput. Calculate
     * waiting times and throughputs for each queue.
     * @param timeRan used to calculate throughput
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
        int queue1Count = 0;
        int queue2Count = 0;
        int queue3Count = 0;
        int queue4Count = 0;
        for (OSProcess process : processes){
            if (process.isStarted()) {
                ranProcessCount++;
                process.calculateTurnaroundTime();
                process.calculateResponseTime();
                turnaroundTimeSum += process.getTurnaroundTime();
                waitingTimeSum += process.getWaitingTime();
                responseTimeSum += process.getResponseTime();
                if(process.isQueue1Present()==true){
                    queue1Count++;
                    queue1WaitingTimeSum+=process.getQueue1WaitingTime();
                }
                if(process.isQueue2Present()==true){
                    queue2Count++;
                    queue2WaitingTimeSum+=process.getQueue2WaitingTime();
                }
                if(process.isQueue3Present()==true){
                    queue3Count++;
                    queue3WaitingTimeSum+=process.getQueue3WaitingTime();
                }
                if(process.isQueue4Present()==true){
                    queue4Count++;
                    queue4WaitingTimeSum+=process.getQueue4WaitingTime();
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
     * Print the average waiting times of each queue
     */
    public void printIndividualWaitingTimeStatistics(){
        System.out.print(scheduleName + " |Trial "+trialNumber+"| ");
        System.out.format("%8.2f | ", queue1AverageWaitingTime);
        System.out.format("%8.2f | ", queue2AverageWaitingTime);
        System.out.format("%8.2f | ", queue3AverageWaitingTime);
        System.out.format("%8.2f%n", queue4AverageWaitingTime);
    }

    /**
     * Print the average throughput for each queue
     */
    public void printIndividualThroughputStatistics() {
        System.out.print(scheduleName + " |Trial " + trialNumber + "| ");
        System.out.format("%5.3f | ", queue1Throughput);
        System.out.format("%5.3f | ", queue2Throughput);
        System.out.format("%5.3f | ", queue3Throughput);
        System.out.format("%5.3f%n", queue4Throughput);
    }

    /**
     * Schedule a process for this quanta
     * @param time used to create timestamp for first time process is scheduled
     * @return process to be scheduled
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
            scheduleThis.setTimeInQueue(0);
        }

        return scheduleThis;
    }
}
