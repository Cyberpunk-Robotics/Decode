package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
import com.seattlesolvers.solverslib.kinematics.Odometry;

public class Turret extends SubsystemBase {
    private ServoEx turret;
    private Limelight limelight;
    private MotorEx odometry;
    public Turret(HardwareMap hardwareMap, Limelight limelight) {
        turret = new ServoEx(hardwareMap, "turret");
//        odometry = new MotorEx(hardwareMap, "odometry");
        turret.setCachingTolerance(0.00001);
//        odometry.setRunMode(Motor.RunMode.RawPower);
//        odometry.stopAndResetEncoder();
        turret.setInverted(false);
        this.limelight = limelight;
    }
    public void periodic(){

    }
}
