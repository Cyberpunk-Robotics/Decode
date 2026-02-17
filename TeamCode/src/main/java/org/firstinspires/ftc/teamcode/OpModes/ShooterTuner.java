package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.bylazar.configurables.annotations.Configurable;
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
import org.firstinspires.ftc.teamcode.Subsystems.Limelight;

@Config
@Configurable
@TeleOp(name = "ShooterTuner", group = "Tuning")
public class ShooterTuner extends OpMode {
    private MotorEx shooterLeft, shooterRight;
    private Limelight limelight;

    double lowVelocity = 700, highVelocity = 1500;
    public static double vitezaPeCareOVreauAcumSaOTunez = 0;
//    double P = 0.0026;
//    double F = 0.000053;
        public static double P = 0.006;
        public static double F = 0.00044;

    public static PIDFController controller1 = new PIDFController(new PIDFCoefficients(P, 0, 0, F));

    @Override
    public void init() {
        shooterLeft = new MotorEx(hardwareMap, "shooter");
        shooterRight = new MotorEx(hardwareMap, "shooter2");
        shooterLeft.setRunMode(MotorEx.RunMode.RawPower);
        shooterRight.setRunMode(MotorEx.RunMode.RawPower);
        shooterLeft.setInverted(false);
        shooterRight.setInverted(true);
        shooterLeft.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        shooterRight.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);

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

        double velocity = (shooterLeft.getVelocity() + shooterRight.getVelocity()) / 2;
        double output = controller1.calculate(velocity, vitezaPeCareOVreauAcumSaOTunez);
        shooterRight.set(output);
        shooterLeft.set(-output);


        telemetry.addData("Output", output);
        telemetry.addData("Pose", limelight.getRobotPose());
        telemetry.addData("Error", vitezaPeCareOVreauAcumSaOTunez - shooterLeft.getVelocity());
//        telemetry.addData("RightVelocity" , shooterSecondary.getVelocity());
        telemetry.addData("LeftVelocity", shooterLeft.getVelocity());
        telemetry.addData("P", P);
        telemetry.addData("F", F);
        telemetry.update();
    }
}
