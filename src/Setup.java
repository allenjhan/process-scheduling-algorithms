/**
 * Created by ahan on 9/23/17.
 */
import java.util.ArrayList;
public class Setup {

    /**
     * Main method for the entire program
     * @param args not used
     */
    public static void main(String[] args){

        int totalProcessesPerTrial = 20;
        try {
            totalProcessesPerTrial = Integer.parseInt(args[0].trim());
        }catch (NumberFormatException e){
            totalProcessesPerTrial = 20;
        }catch (ArrayIndexOutOfBoundsException e){
            totalProcessesPerTrial = 20;
        }

        int trialsRun = 5;
        try {
            trialsRun = Integer.parseInt(args[1].trim());
        }catch (NumberFormatException e){
            trialsRun = 5;
        }catch (ArrayIndexOutOfBoundsException e){
            trialsRun = 5;
        }

        boolean arrivalTimeRandom = true;
        try {
            if(Integer.parseInt(args[2].trim())==0){
                arrivalTimeRandom = false;
            }
        } catch (NumberFormatException e){
            arrivalTimeRandom = true;
        } catch (ArrayIndexOutOfBoundsException e){
            arrivalTimeRandom = true;
        }

        ArrayList<FCFSTrial> fcfsTrials = new ArrayList<FCFSTrial>();
        ArrayList<SJFTrial> sjftTrials = new ArrayList<SJFTrial>();
        ArrayList<SRTTrial> srtTrials = new ArrayList<SRTTrial>();
        ArrayList<RRTrial> rrTrials = new ArrayList<RRTrial>();
        ArrayList<HPFPTrial> hpfpTrials = new ArrayList<HPFPTrial>();
        ArrayList<HPFNTrial> hpfnTrials = new ArrayList<HPFNTrial>();
        ArrayList<HPFPWATrial> hpfpwaTrials = new ArrayList<HPFPWATrial>();
        ArrayList<HPFNWATrial> hpfnwaTrials = new ArrayList<HPFNWATrial>();

        for(int i=1; i<trialsRun+1; i++){
            FCFSTrial fcfsTrial = new FCFSTrial(i, totalProcessesPerTrial, arrivalTimeRandom);
            fcfsTrials.add(fcfsTrial);
            fcfsTrial.generateTrialData(i);
        }


        for(int i=1; i<trialsRun+1; i++){
            SJFTrial sjfTrial = new SJFTrial(i, totalProcessesPerTrial, arrivalTimeRandom);
            sjftTrials.add(sjfTrial);
            sjfTrial.generateTrialData(i);
        }

        for(int i=1; i<trialsRun+1; i++){
            SRTTrial srtTrial = new SRTTrial(i, totalProcessesPerTrial, arrivalTimeRandom);
            srtTrials.add(srtTrial);
            srtTrial.generateTrialData(i);
        }

        for(int i=1; i<trialsRun+1; i++){
            RRTrial rrTrial = new RRTrial(i, totalProcessesPerTrial, arrivalTimeRandom);
            rrTrials.add(rrTrial);
            rrTrial.generateTrialData(i);
        }

        for(int i=1; i<trialsRun+1; i++){
            HPFPTrial hpfpTrial = new HPFPTrial(i, totalProcessesPerTrial, arrivalTimeRandom);
            hpfpTrials.add(hpfpTrial);
            hpfpTrial.generateTrialData(i);
        }

        for(int i=1; i<trialsRun+1; i++){
            HPFNTrial hpfnTrial = new HPFNTrial(i, totalProcessesPerTrial, arrivalTimeRandom);
            hpfnTrials.add(hpfnTrial);
            hpfnTrial.generateTrialData(i);
        }

        for(int i=1; i<trialsRun+1; i++){
            HPFPWATrial hpfpwaTrial = new HPFPWATrial(i, totalProcessesPerTrial, arrivalTimeRandom);
            hpfpwaTrials.add(hpfpwaTrial);
            hpfpwaTrial.generateTrialData(i);
        }

        for(int i=1; i<trialsRun+1; i++){
            HPFNWATrial hpfnwaTrial = new HPFNWATrial(i, totalProcessesPerTrial, arrivalTimeRandom);
            hpfnwaTrials.add(hpfnwaTrial);
            hpfnwaTrial.generateTrialData(i);
        }

        for(FCFSTrial trial : fcfsTrials){
            trial.print3BaseParametersOfAll();
            System.out.println("");
        }

        for(SJFTrial trial : sjftTrials){
            trial.print3BaseParametersOfAll();
            System.out.println("");
        }

        for(SRTTrial trial : srtTrials){
            trial.print3BaseParametersOfAll();
            System.out.println("");
        }

        for(RRTrial trial : rrTrials){
            trial.print3BaseParametersOfAll();
            System.out.println("");
        }

        for(HPFPTrial trial : hpfpTrials){
            trial.print3BaseParametersOfAll();
            System.out.println("");
        }

        for(HPFNTrial trial : hpfnTrials){
            trial.print3BaseParametersOfAll();
            System.out.println("");
        }
        for(HPFPWATrial trial : hpfpwaTrials){
            trial.print3BaseParametersOfAll();
            System.out.println("");
        }

        for(HPFNWATrial trial : hpfnwaTrials){
            trial.print3BaseParametersOfAll();
            System.out.println("");
        }

        for(FCFSTrial trial : fcfsTrials){
            trial.printProcessStateDiagrams();
            System.out.println("");
        }

        for(SJFTrial trial : sjftTrials){
            trial.printProcessStateDiagrams();
            System.out.println("");
        }

        for(SRTTrial trial : srtTrials){
            trial.printProcessStateDiagrams();
            System.out.println("");
        }

        for(RRTrial trial : rrTrials){
            trial.printProcessStateDiagrams();
            System.out.println("");
        }

        for(HPFPTrial trial : hpfpTrials){
            trial.printProcessStateDiagrams();
            System.out.println("");
        }

        for(HPFNTrial trial : hpfnTrials){
            trial.printProcessStateDiagrams();
            System.out.println("");
        }

        for(HPFPWATrial trial : hpfpwaTrials){
            trial.printProcessStateDiagrams();
            System.out.println("");
        }

        for(HPFNWATrial trial : hpfnwaTrials){
            trial.printProcessStateDiagrams();
            System.out.println("");
        }

        System.out.println("Times given in units of quanta, throughput given in units of processes/quanta");
        System.out.println("Average Turnaround Time, Average Wait Time, Average Response Time, Total Throughput");
        System.out.println("                   |       | Taround  | Waiting  | Respond  | Tput  | Process Count");
        for(FCFSTrial trial : fcfsTrials){
            trial.printStatistics();
        }

        for(SJFTrial trial : sjftTrials){
            trial.printStatistics();
        }

        for(SRTTrial trial : srtTrials){
            trial.printStatistics();
        }

        for(RRTrial trial : rrTrials){
            trial.printStatistics();
        }

        for(HPFPTrial trial : hpfpTrials){
            trial.printStatistics();
        }

        for(HPFNTrial trial : hpfnTrials){
            trial.printStatistics();
        }

        for(HPFPWATrial trial : hpfpwaTrials){
            trial.printStatistics();
        }

        for(HPFNWATrial trial : hpfnwaTrials){
            trial.printStatistics();
        }

        System.out.println("");

        System.out.println("NaN indicates zero divide by zero error, and therefore no average wait time");
        System.out.println("Waiting times given in units of quanta");
        System.out.println("                   |       |    Q1    |    Q2    |    Q3    |    Q4");
        for(HPFPTrial trial : hpfpTrials){
            trial.printIndividualWaitingTimeStatistics();
        }

        for(HPFNTrial trial : hpfnTrials){
            trial.printIndividualWaitingTimeStatistics();
        }
        for(HPFPWATrial trial : hpfpwaTrials){
            trial.printIndividualWaitingTimeStatistics();
        }

        for(HPFNWATrial trial : hpfnwaTrials){
            trial.printIndividualWaitingTimeStatistics();
        }

        System.out.println("");

        System.out.println("Throughput given in units of processes/quanta");
        System.out.println("                   |       |  Q1   |  Q2   |  Q3   |  Q4");
        for(HPFPTrial trial : hpfpTrials){
            trial.printIndividualThroughputStatistics();
        }

        for(HPFNTrial trial : hpfnTrials){
            trial.printIndividualThroughputStatistics();
        }

        for(HPFPWATrial trial : hpfpwaTrials){
            trial.printIndividualThroughputStatistics();
        }

        for(HPFNWATrial trial : hpfnwaTrials){
            trial.printIndividualThroughputStatistics();
        }
    }
}
