/**
 * Created by ahan on 9/23/17.
 */
public class RRTrial extends Trial {

    /**
     * Constructor for round robin trial
     * @param trialNumber ordinal number of trial
     * @param processPoolTotal number of processes to create for run
     */
    public RRTrial(int trialNumber, int processPoolTotal, boolean arrivalTimeRandom){
        super(trialNumber, processPoolTotal, arrivalTimeRandom);
        scheduleName = "       RR         ";
    }

    /**
     * schedules the next process to be run
     * @param time is used to create first scheduled timestamp if process has not been
     *             scheduled before
     * @return the process that is scheduled
     */
    public OSProcess scheduleOneQuanta(int time){
        OSProcess scheduleThis = null;

        if(!readyQueue.isEmpty()) {
            //scheduleThis = readyQueue.get(0);
            if(currentProcess!=null)
                readyQueue.add(currentProcess);
            scheduleThis = readyQueue.remove(0);
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
