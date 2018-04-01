/**
 * Created by ahan on 9/22/17.
 */
import java.util.ArrayList;
import java.util.Random;

public abstract class Trial {
    protected int trialNumber; // ordinal number of trial
    protected int processPoolTotal; // total process created for run
    protected boolean arrivalTimeRandom;
    protected String scheduleName; // name of the scheduler algorithm
    protected ArrayList<String> trialData; // arraylist for storage of process state diagram
    protected ArrayList<OSProcess> processes; // arraylist for storage of all created processes
    protected ArrayList<OSProcess> readyQueue; // arraylist for storage of all process that have started and are waiting
    protected OSProcess currentProcess; // the current process selected by scheduler
    protected double averageTurnaroundTime;
    protected double averageWaitingTime;
    protected double averageResponseTime;
    protected double throughput;
    protected int ranProcessCount;

    /**
     * Constructor for abstract Trial class
     * @param trialNumber ordinal number of trials
     * @param processPoolTotal total number of processes to create for trial
     */
    public Trial(int trialNumber, int processPoolTotal, boolean arrivalTimeRandom){
        this.trialNumber = trialNumber;
        this.processPoolTotal = processPoolTotal;
        this.arrivalTimeRandom = arrivalTimeRandom;
        trialData = new ArrayList<String>();
        processes = new ArrayList<OSProcess>();
        readyQueue = new ArrayList<OSProcess>();
    }

    public int getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(int trialNumber) {
        this.trialNumber = trialNumber;
    }

    public int getProcessPoolTotal() {
        return processPoolTotal;
    }

    public void setProcessPoolTotal(int processPoolTotal) {
        this.processPoolTotal = processPoolTotal;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public ArrayList<String> getTrialData() {
        return trialData;
    }

    public void setTrialData(ArrayList<String> trialData) {
        trialData = trialData;
    }

    public ArrayList<OSProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(ArrayList<OSProcess> processes) {
        processes = processes;
    }

    public OSProcess getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(OSProcess currentProcess) {
        currentProcess = currentProcess;
    }

    public double getAverageTurnaroundTime() {
        return averageTurnaroundTime;
    }

    public void setAverageTurnaroundTime(double averageTurnaroundTime) {
        this.averageTurnaroundTime = averageTurnaroundTime;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public void setAverageWaitingTime(double averageWaitingTime) {
        this.averageWaitingTime = averageWaitingTime;
    }

    public double getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(double averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }

    public double getThroughput() {
        return throughput;
    }

    public void setThroughput(double throughput) {
        this.throughput = throughput;
    }

    /**
     * print process state diagram for trial
     */
    public void printProcessStateDiagrams(){
        System.out.format( "Process state diagram for " + scheduleName + " Trial " + trialNumber + "%n");
        int i = 0;
        for(String datum : trialData){
            System.out.print(datum + " ");
            if(i%20==19)
                System.out.print("\n");
            i++;
        }
        System.out.format("%n");
    }

    /**
     * run the trial and generate all the data
     * @param randSeed is seed for random number generator
     */
    public void generateTrialData(int randSeed){
        generateProcesses(randSeed);
        int i = 0;
        while(i<100 || (i>=100 && !areProcessesDone())) {
            addToReadyQueue(i);
            currentProcess = scheduleOneQuanta(i);
            if (currentProcess != null){
                trialData.add(currentProcess.getProcessName());
                currentProcess.deductOneQuantaRunning(i);
            } else
                trialData.add("***");
            addOneQuantaWaiting();
            i++;
        }
        calculateStatistics(i);

    }

    /**
     * adds all available process that have arrived to ready queue
     * @param time used to determine if processes have arrived yet
     */
    public void addToReadyQueue(double time){
        for(int i = 0; i<processes.size(); i++){
            if(processes.get(i).getArrivalTime()<=time && !processes.get(i).isArrived()) {
                readyQueue.add(processes.get(i));
                processes.get(i).setArrived(true);
            }
        }
    }

    /**
     * print arrival time, total runtime, and priority of process
     */
    public void print3BaseParametersOfAll(){
        System.out.println(scheduleName + " Trial " + trialNumber + " process parameters");
        System.out.println("Time measurements given in units of quanta");
        System.out.println(" Process | Arrival Time | Runtime | Priority ");
        for(OSProcess process : processes){
            System.out.format("   "+process.getProcessName()+"   |");
            System.out.format("     %5.1f    |", process.getArrivalTime());
            System.out.format("  %5.1f  |", process.getTotalRunTime());
            System.out.format("   %d%n", process.getOriginalPriority());
        }
    }

    /**
     * calculate turnaround time, waiting time, response time, and throughput
     * @param timeRan is total execution time and is used for total throughput
     */
    public void calculateStatistics(double timeRan){
        double turnaroundTimeSum = 0;
        double waitingTimeSum = 0;
        double responseTimeSum = 0;
        ranProcessCount = 0;
        for (OSProcess process : processes){
            if (process.isStarted()) {
                ranProcessCount++;
                process.calculateTurnaroundTime();
                process.calculateResponseTime();
                turnaroundTimeSum += process.getTurnaroundTime();
                waitingTimeSum += process.getWaitingTime();
                responseTimeSum += process.getResponseTime();
            }
        }

        averageTurnaroundTime = turnaroundTimeSum/ranProcessCount;
        averageWaitingTime = waitingTimeSum/ranProcessCount;
        averageResponseTime = responseTimeSum/ranProcessCount;
        throughput = ranProcessCount/timeRan;

    }

    /**
     * Print turnaround time, waiting time, response time, and throughput.
     */
    public void printStatistics(){
        System.out.print(scheduleName + " |Trial "+trialNumber+"|");
        System.out.format(" %8.2f | %8.2f | %8.2f | %5.3f | %d%n",
                averageTurnaroundTime, averageWaitingTime, averageResponseTime, throughput, ranProcessCount);
    }

    /**
     * For all processes in waiting queue, increment waiting time by one quanta.
     */
    public void addOneQuantaWaiting(){
        for(OSProcess process : readyQueue){
            process.setWaitingTime(process.getWaitingTime()+1.);
        }
    }

    /**
     * generate all processes needed for trial
     * @param randSeed is seed for random number generator
     */
    public void generateProcesses(int randSeed){
        Random random = new Random(randSeed);
        int arrivalTimeMultiplier = 1;
        if(arrivalTimeRandom==false)
            arrivalTimeMultiplier = 0;
        for(int i=0; i<getProcessPoolTotal(); i++){
            processes.add(new OSProcess(String.format("%03d", i),
                    random.nextDouble()*99*arrivalTimeMultiplier,
                    random.nextDouble()*9.9+0.1,
                    random.nextInt(4) + 1
                    ));
        }

        sortProcessList();

    }

    /**
     * use insertion sort to sort the list of processes by arrival time
     */
    public void sortProcessList(){
        for(int i=1; i<processes.size(); i++){
           for(int j=0; j<i; j++){
               if(processes.get(i).getArrivalTime()< processes.get(j).getArrivalTime()){
                   OSProcess toInsert = processes.remove(i);
                   processes.add(j, toInsert);
                   break;
               }
           }
        }
    }


    /**
     * see if all process that have started running are done yet
     * @return true if process done, false if not
     */
    public boolean areProcessesDone(){
        boolean returnValue = true;
        for(OSProcess process : processes){
            if(process.isStarted() && process.getRemainingTime()>0) {
                returnValue = false;
                break;
            }
        }

        return returnValue;
    }

    /**
     * abstract method to be defined by subclass; this is where the
     * scheduling algorithm goes
     * @param time is current time of process; used to generate timestamp
     *             of when process is first scheduled
     * @return process to be scheduled
     */
    public abstract OSProcess scheduleOneQuanta(int time);
}
