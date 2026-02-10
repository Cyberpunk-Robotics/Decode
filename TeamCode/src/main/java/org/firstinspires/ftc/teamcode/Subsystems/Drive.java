package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drive extends SubsystemBase {
    private double x = 0, y = 0, rx = 0;
    private final MotorEx leftFront, leftBack, rightFront, rightBack;
    private final GoBildaPinpointDriver pinpoint;
    double heading;
    public Drive(HardwareMap hardwareMap){
        leftFront = new MotorEx(hardwareMap, "leftFront");
        leftBack = new MotorEx(hardwareMap, "leftBack");
        rightFront = new MotorEx(hardwareMap, "rightFront");
        rightBack = new MotorEx(hardwareMap, "rightBack");

        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        leftFront.setInverted(true);
        leftBack.setInverted(true);
        rightFront.setInverted(false);
        rightBack.setInverted(false);


        leftFront.setRunMode(Motor.RunMode.RawPower);
        leftBack.setRunMode(Motor.RunMode.RawPower);
        rightFront.setRunMode(Motor.RunMode.RawPower);
        rightBack.setRunMode(Motor.RunMode.RawPower);
        pinpoint.setHeading(Math.toRadians(-36), AngleUnit.RADIANS);
    }
    public void periodic(double x, double y, double rx){
        leftFront.set(y + x + rx);
        leftBack.set(y - x + rx);
        rightFront.set(y - x - rx);
        rightBack.set(y + x - rx);
    }
    public void fieldCentric(double x, double y, double rx){
    double heading = pinpoint.getHeading(AngleUnit.RADIANS);
    double adjustedX = -y * Math.sin(heading) + x * Math.cos(heading);
    double adjustedY = y * Math.cos(heading) + x * Math.sin(heading);
        leftFront.set(adjustedY + adjustedX - rx);
        leftBack.set(adjustedY - adjustedX - rx);
        rightFront.set(adjustedY - adjustedX + rx);
        rightBack.set(adjustedY + adjustedX + rx);
    }
    public void normalizedPeriodic(double x, double y, double rx){
        leftFront.set((y + x + rx) * 2.0 / 5.0);
        leftBack.set((y - x + rx) * 2.0 / 5.0);
        rightFront.set((y - x - rx) * 2.0 / 5.0);
        rightBack.set((y + x - rx) * 2.0 / 5.0);
    }
}
