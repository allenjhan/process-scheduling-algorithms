/**
 * Created by ahan on 9/23/17.
 */
public class FCFSTrial extends Trial {

    /**
     * Constructor for first-come-first-served trial
     * @param trialNumber ordinal numbering for the trial, i.e., what is its order in trials run
     * @param processPoolTotal Total processes to create in the process pool
     */
    public FCFSTrial(int trialNumber, int processPoolTotal, boolean arrivalTimeRandom){
        super(trialNumber, processPoolTotal, arrivalTimeRandom);
        scheduleName = "       FCFS       ";
    }

    /**
     * This method schedules the next process that should be run for the next quanta.
     * @param time Uses this parameter to set the time at which a process is first scheduled
     *             if it is the first time it is being scheduled
     * @return returns the process to be scheduled
     */
    public OSProcess scheduleOneQuanta(int time){
        OSProcess scheduleThis = null;

        if (currentProcess==null || currentProcess.getRemainingTime()<=0) {
            if(!readyQueue.isEmpty()) {
                for (OSProcess process : readyQueue) {
                    if (process.getRemainingTime() > 0) {
                        scheduleThis = process;
                        readyQueue.remove(process);
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
