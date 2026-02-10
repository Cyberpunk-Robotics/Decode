package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class Limelight extends SubsystemBase {
    private final Limelight3A limelight;
    private double tx, ty, ta;
    // This will store the last valid list of fiducials seen
    private List<LLResultTypes.FiducialResult> resultList;


    public Limelight(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(200);
        limelight.pipelineSwitch(0);
        limelight.start();
        // You don't create results, you get them from the limelight.
        // The incorrect line has been removed.
    }

    @Override
    public void periodic() {
        LLResult llResult = limelight.getLatestResult();
        List<LLResultTypes.FiducialResult> currentFiducials = llResult.getFiducialResults();

        // Only update our stored list if the limelight actually found new tags.
        if (!currentFiducials.isEmpty()) {
            this.resultList = currentFiducials;
        }
            tx = llResult.getTx();
            ty = llResult.getTy();
            ta = llResult.getTa();
    }

    public int getPrimaryTagId() {
        if (resultList != null && !resultList.isEmpty()) {
            // The list is not empty, so we can safely get the first element
            // and then get its ID.
            return resultList.get(0).getFiducialId();
        }
        // Return a default value indicating no tag is visible.
        return -1;
    }


    public void LLPause(){
        limelight.pause();
    }
    public double getTa(){
        return ta;
    }
    public double getTx(){return tx;}
    public double getTy(){return ty;}
    public double getShooterRPM(){
//        return (int) (600 + Math.pow(Math.max(0, 3 - ta), 1.3) / Math.pow(2.9, 1.3) * 1400);
            if(ta >= 1.8){
                return 1050 + ((3 - ta)/2.9)*1400;
            }else if(ta ==0){
                return 100 + ((3 - ta)/2.9)*1000;
            }
            else {
                return 450 + ((3 - ta)/2.9) * 1200;
            }
    }
}
