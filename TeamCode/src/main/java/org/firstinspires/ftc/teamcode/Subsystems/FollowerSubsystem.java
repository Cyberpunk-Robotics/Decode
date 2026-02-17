package org.firstinspires.ftc.teamcode.Subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class FollowerSubsystem extends SubsystemBase {
    private Follower follower;
    private Turret turret;
    private Limelight limelight;
    public FollowerSubsystem(HardwareMap hardwareMap, Turret turret, Limelight limelight){
        follower = Constants.createFollower(hardwareMap);
        this.turret = turret;

    }
    public Pose getRobotPose(){
        if(turret.getTurretPose() >= 0.48 && turret.getTurretPose() <= 0.52){
            if (limelight.isValid()){
               return limelight.getRobotPose();
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
}
