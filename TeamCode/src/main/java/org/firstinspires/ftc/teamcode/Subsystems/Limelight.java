package org.firstinspires.ftc.teamcode.Subsystems;

import android.graphics.Point;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class Limelight extends SubsystemBase {
    private final Limelight3A limelight;
    private Follower follower;
    private double tx, ty, ta;
    private static final double METERS_TO_INCHES = 39.3701;
    // This will store the last valid list of fiducials seen
    private List<LLResultTypes.FiducialResult> resultList;

    private final Pose BLUE_BASKET_POSITION = new Pose(15, 132, 0);
    private final Pose RED_BASKET_POSITION = new Pose(129.1076923076923, 131.25594405594404, 0);


    public Limelight(HardwareMap hardwareMap, Follower follower) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(60);
        limelight.pipelineSwitch(0);
        limelight.start();
        this.follower = follower;


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
            if (getRobotPose()!= null) {
                follower.setPose(getRobotPose());
            }
    }

    public boolean hasTarget() {
        return ta > 0;
    }

    public int getPrimaryTagId() {
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0).getFiducialId();
        }
        // Return a default value indicating no tag is visible.
        return -1;
    }

    public Pose getRobotPose() {
        LLResult llResult = limelight.getLatestResult();
        if (isValid()) {
            // Get the pose from the Limelight (this is in METERS)
            Pose3D botpose_mt2 = llResult.getBotpose_MT2();

            // Convert position from meters to inches for PedroPathing
            double x = botpose_mt2.getPosition().x * METERS_TO_INCHES;
            double y = botpose_mt2.getPosition().y * METERS_TO_INCHES;

            // Get the orientation in radians
            double heading = botpose_mt2.getOrientation().getYaw(AngleUnit.RADIANS);
            return new Pose(x, y, heading);
        }else{
            return null;
        }
    }
    public double getDistance() {
        Pose robotPose = getRobotPose();
        if (robotPose != null) {
            return Math.hypot(robotPose.getX(), robotPose.getY());
        }
        return 0; // Default value if no pose is available
    }

    public double getDistanceToBasket() {
        Pose robotPose = getRobotPose();
        if (robotPose != null) {
            double distanceToBlue = Math.hypot(robotPose.getX() - BLUE_BASKET_POSITION.getX(), robotPose.getY() - BLUE_BASKET_POSITION.getY());
            double distanceToRed = Math.hypot(robotPose.getX() - RED_BASKET_POSITION.getX(), robotPose.getY() - RED_BASKET_POSITION.getY());
            return Math.min(distanceToBlue, distanceToRed);
        }
        return 0; // Default value if no pose is available
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
                return 600 + ((3 - ta)/2.9) * 1200;
            }
    }
    public boolean isValid(){
        return getPrimaryTagId() == 24 || getPrimaryTagId() == 20;
    }
    public void setPose(){
        follower.setPose(getRobotPose());
    }
}
