package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
@TeleOp(name = "ShooterRPMStudy", group = "Test")
public class ShooterRPMStudy extends OpMode {
    private MotorEx shooter;
    double P = 0.035;
    double F = 0.00056;
    PIDFController pidf;
    public double setpoint;
    @Override
    public void init() {
        shooter = new MotorEx(hardwareMap, "shooter");
        shooter.setRunMode(MotorEx.RunMode.RawPower);
        shooter.setInverted(true);
        pidf = new PIDFController(P, 0, 0, F);
        setpoint = 0;
    }

    @Override
    public void loop() {
            if(gamepad1.rightBumperWasPressed()){
                setpoint += 0.1;
            }
            if(gamepad1.leftBumperWasPressed()){
                setpoint -= 0.1;
            }
        shooter.set(setpoint);
        telemetry.addData("Set RPM: ", setpoint);
        telemetry.addData("CurrentRPM ", shooter.getVelocity());
        telemetry.update();

    }
}
