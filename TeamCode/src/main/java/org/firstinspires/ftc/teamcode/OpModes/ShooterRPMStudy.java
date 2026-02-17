package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.Subsystems.Limelight;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@TeleOp(name = "ShooterRPMStudy", group = "Test")
public class ShooterRPMStudy extends OpMode {
    private MotorEx shooter,shooter2;
    double P = 0.006;
    double F = 0.00044;
    PIDFController pidf;
    public double setpoint;
    private Limelight limelight;
    private Follower follower;

    @Override
    public void init() {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        shooter = new MotorEx(hardwareMap, "shooter");
        shooter2 = new MotorEx(hardwareMap, "shooter2");
        shooter.setRunMode(MotorEx.RunMode.RawPower);
        shooter2.setRunMode(MotorEx.RunMode.RawPower);
        shooter.setInverted(false);
        shooter2.setInverted(true);
        pidf = new PIDFController(P, 0, 0, F);
        setpoint = 600;
        follower = Constants.createFollower(hardwareMap);
        limelight = new Limelight(hardwareMap, follower);
    }

    @Override
    public void loop() {

        if(gamepad1.rightBumperWasPressed()){
            setpoint += 20;
        }
        if(gamepad1.leftBumperWasPressed()){
            setpoint -= 20;
        }
        double velocity = (shooter.getVelocity() + shooter2.getVelocity()) / 2;
        double power = -pidf.calculate(velocity, setpoint);
        shooter.set(power);
        shooter2.set(-power);

        telemetry.addData("BOTPOSE", limelight.getRobotPose());
        telemetry.addData("Setpoint RPM: ", setpoint);
        telemetry.addData("Current RPM ", shooter.getVelocity());
        telemetry.addData("Power", power);
        updateTelemetry(telemetry);

    }
}
