package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Drive extends SubsystemBase {
    private double x = 0, y = 0, rx = 0;
    private final DcMotorEx leftFront, leftBack, rightFront, rightBack;

    private double leftFrontPos, leftBackPos, rightFrontPos, rightBackPos;

    public Drive(HardwareMap hardwareMap){
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack=hardwareMap.get(DcMotorEx.class, "leftBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
        rightBack= hardwareMap.get(DcMotorEx.class, "rightBack");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightBack.setDirection(DcMotorSimple.Direction.FORWARD);


        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }



    public void periodic(double x, double y, double rx){
        leftFront.setPower(y + x + rx);
        leftBack.setPower(y - x + rx);
        rightFront.setPower(y - x - rx);
        rightBack.setPower(y + x - rx);
    }
//    public void fieldCentric(double x, double y, double rx){
//        leftFront.set(adjustedY + adjustedX - rx);
//        leftBack.set(adjustedY - adjustedX - rx);
//        rightFront.set(adjustedY - adjustedX + rx);
//        rightBack.set(adjustedY + adjustedX + rx);
//    }
    public void normalizedPeriodic(double x, double y, double rx){
        leftFront.setPower((y + x + rx) * 2.0 / 5.0);
        leftBack.setPower((y - x + rx) * 2.0 / 5.0);
        rightFront.setPower((y - x - rx) * 2.0 / 5.0);
        rightBack.setPower((y + x - rx) * 2.0 / 5.0);
    }
    public double getOdometry(){
        return rightFrontPos;
    }
    public void resetOdometry(){
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
}
