package org.firstinspires.ftc.teamcode.OpModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.ReverseIntakeCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

@TeleOp(name = "ShooterTuner", group = "Tuning")
public class ShooterTuner extends OpMode {
    private MotorEx shooterLeft;

    double lowVelocity = 700, highVelocity = 1500;
    double vitezaPeCareOVreauAcumSaOTunez = highVelocity;
//    double P = 0.0026;
//    double F = 0.000053;
        double P = 0;
        double F = 0;

    @Override
    public void init() {
        shooterLeft = new MotorEx(hardwareMap, "shooter");
//        shooterSecondary = new MotorEx(hardwareMap, "shooterSecondary");
        shooterLeft.setRunMode(MotorEx.RunMode.RawPower);
//        shooterSecondary.setRunMode(MotorEx.RunMode.RawPower);
        shooterLeft.setInverted(true);
//        shooterSecondary.setInverted(true);
        shooterLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
//        shooterSecondary.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


    }

    @Override
    public void loop() {
        if(gamepad1.dpadLeftWasPressed()){
            P = P-0.0001;
        }
        if(gamepad1.dpadDownWasPressed()){
            P = P-0.001;
        }
        if(gamepad1.dpadRightWasPressed()){
            P = P+0.0001;
        }
        if(gamepad1.dpadUpWasPressed()){
            P = P+0.001;
        }

        if(gamepad1.squareWasPressed()){
            F = F+0.00001;
        }
        if(gamepad1.triangleWasPressed()){
            F = F+0.0001;
        }
        if(gamepad1.circleWasPressed()){
            F = F-0.00001;
        }
        if(gamepad1.crossWasPressed()){
            F = F-0.0001;
        }

        if(gamepad1.leftBumperWasPressed()){
            vitezaPeCareOVreauAcumSaOTunez = lowVelocity;
        }
        if(gamepad1.rightBumperWasPressed()){
            vitezaPeCareOVreauAcumSaOTunez = highVelocity;
        }

        PIDFController controller1 = new PIDFController(new PIDFCoefficients(P, 0, 0, F));
        double output = controller1.calculate(shooterLeft.getVelocity(), vitezaPeCareOVreauAcumSaOTunez);
//        shooterSecondary.set(output);
        shooterLeft.set(output);


        telemetry.addData("Output", output);
        telemetry.addData("Error", vitezaPeCareOVreauAcumSaOTunez - shooterLeft.getVelocity());
//        telemetry.addData("RightVelocity" , shooterSecondary.getVelocity());
        telemetry.addData("LeftVelocity", shooterLeft.getVelocity());
        telemetry.addData("P", P);
        telemetry.addData("F", F);
        telemetry.update();
    }
}
