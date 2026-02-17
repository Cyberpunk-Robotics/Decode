//package org.firstinspires.ftc.teamcode.OpModes;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.seattlesolvers.solverslib.hardware.motors.Motor;
//import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
//import com.seattlesolvers.solverslib.hardware.servos.ServoEx;
//
//import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
//@TeleOp(name = "TurretTuner")
//public class TurretTuner extends OpMode {
//    private ServoEx turret;
//    private double pos = 0.5;
//    private double deviation = 0.0025;
//    public double maxLimit = 2.5, minLimit = -2.5;
//    private Limelight limelight;
//    private MotorEx leftFront, leftBack, rightFront, rightBack;
//    @Override
//    public void init() {
//        turret = new ServoEx(hardwareMap, "turret");
//        leftFront = new MotorEx(hardwareMap, "leftFront");
//        leftBack = new MotorEx(hardwareMap, "leftBack");
//        rightFront = new MotorEx(hardwareMap, "rightFront");
//        rightBack = new MotorEx(hardwareMap, "rightBack");
//
//        leftFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        leftBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        rightFront.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        rightBack.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        leftFront.setInverted(true);
//        leftBack.setInverted(true);
//        limelight = new Limelight(hardwareMap);
//        turret.setInverted(true);
//        turret.setCachingTolerance(0.000001);
//        turret.set(pos);
//    }
//
//    @Override
//    public void loop() {
//        double x = gamepad1.left_stick_x;
//        double y = gamepad1.left_stick_y;
//        double rx = gamepad1.right_stick_x;
//
//        leftFront.set(y + x + rx);
//        leftBack.set(y - x + rx);
//        rightFront.set(y - x - rx);
//        rightBack.set(y + x - rx);
//
//        if (limelight.getTx() >= maxLimit){
//            pos += deviation;
//            turret.set(pos);
//        }
//        if(limelight.getTx() <= minLimit){
//            pos -= deviation;
//            turret.set(pos);
//        }
//        if(gamepad1.dpadUpWasPressed()){
//            maxLimit += 0.04;
//        }
//        if(gamepad1.dpadLeftWasPressed()){
//            minLimit -= 0.4;
//        }
//        if (gamepad1.dpadRightWasPressed()){
//            minLimit += 0.4;
//        }
//        if (gamepad1.dpadDownWasPressed()){
//            maxLimit -= 0.4;
//        }
//        if (gamepad1.rightBumperWasPressed()){
//            deviation += 0.0002;
//        }
//        if(gamepad1.leftBumperWasPressed()){
//            deviation -= 0.0002;
//        }
//        telemetry.addData("deviation: ", deviation);
//        telemetry.addData("maxLimit", maxLimit);
//        telemetry.addData("minLimit", minLimit);
//        telemetry.addData("pos: ", pos);
//        telemetry.update();
//
//        }
//    }
//
