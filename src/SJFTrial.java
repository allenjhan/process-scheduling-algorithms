/**
 * Created by ahan on 9/23/17.
 */
public class SJFTrial extends Trial{

    /**
     * Constructor for Shortest-Job-First
     * @param trialNumber ordinal number of trial to be run
     * @param processPoolTotal total number of processes to be created for run
     */
    public SJFTrial(int trialNumber, int processPoolTotal, boolean arrivalTimeRandom){
        super(trialNumber, processPoolTotal, arrivalTimeRandom);
        scheduleName = "       SJF        ";
    }

    /**
     * schedule the next process
     * @param time used to generate timestamp for first time process is scheduled
     * @return process to be run
     */
    public OSProcess scheduleOneQuanta(int time){
        OSProcess scheduleThis = null;

        if (currentProcess != null && currentProcess.getRemainingTime() > 0) {
            scheduleThis = currentProcess;
        } else if(!readyQueue.isEmpty()) {
            scheduleThis = readyQueue.get(0);
            int scheduleThisIndex = 0;
            for (int i = 1; i < readyQueue.size(); i++) {
                if (readyQueue.get(i).getRemainingTime() < scheduleThis.getRemainingTime()) {
                    scheduleThis = readyQueue.get(i);
                    scheduleThisIndex = i;
                }
            }
            readyQueue.remove(scheduleThisIndex);
        }

        if(scheduleThis!=null && !scheduleThis.isStarted()) {
            scheduleThis.setStarted(true);
            scheduleThis.setFirstScheduledTime(time);
        }

        return scheduleThis;
    }

}
