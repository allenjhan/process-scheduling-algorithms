/**
 * Created by ahan on 9/23/17.
 */
public class SRTTrial extends Trial{

    /**
     * Constructor for Shortest-Remaining-Time trial
     * @param trialNumber ordinal number of trial to be run
     * @param processPoolTotal total number of processes to run
     */
    public SRTTrial(int trialNumber, int processPoolTotal, boolean arrivalTimeRandom){
        super(trialNumber, processPoolTotal, arrivalTimeRandom);
        scheduleName = "       SRT        ";
    }

    /**
     * schedule process for next quanta
     * @param time time used to generate timestamp for first time process scheduled
     * @return process scheduled
     */
    public OSProcess scheduleOneQuanta(int time){
        OSProcess scheduleThis = null;

        if(!readyQueue.isEmpty()) {
            scheduleThis = readyQueue.get(0);
            int scheduleThisIndex = 0;
            for (int i = 1; i < readyQueue.size(); i++) {
                if (readyQueue.get(i).getRemainingTime() < scheduleThis.getRemainingTime()) {
                    scheduleThis = readyQueue.get(i);
                    scheduleThisIndex = i;
                }
            }
            if (currentProcess != null && currentProcess.getRemainingTime() > 0) {
                if (scheduleThis.getRemainingTime() < currentProcess.getRemainingTime()) {
                    readyQueue.remove(scheduleThisIndex);
                    readyQueue.add(currentProcess);
                } else
                    scheduleThis = currentProcess;
            } else
                readyQueue.remove(scheduleThisIndex);
        } else if (currentProcess != null && currentProcess.getRemainingTime() > 0) {
            scheduleThis = currentProcess;
        }

        if(scheduleThis!=null && !scheduleThis.isStarted()) {
            scheduleThis.setStarted(true);
            scheduleThis.setFirstScheduledTime(time);
        }

        return scheduleThis;
    }
}
