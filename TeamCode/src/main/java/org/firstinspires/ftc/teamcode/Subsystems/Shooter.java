package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class Shooter extends SubsystemBase {
    private final MotorEx shooter;
    double P = 0.07;
    double F = 0.00052;
    PIDFController pidf;
    public int setpoint = 1530; // For lowRPM()
    public int highSetpoint = 1210; // For highRPM()
    private final Limelight limelight;

    public Shooter(HardwareMap hardwareMap, Limelight limelight) {
        shooter = new MotorEx(hardwareMap, "shooter");
        shooter.setRunMode(MotorEx.RunMode.RawPower);
        shooter.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        shooter.setInverted(true);
        pidf = new PIDFController(P, 0, 0, F);
        this.limelight = limelight;
    }

    public void on(){
            double setpoint = limelight.getShooterRPM();
            pidf.setSetPoint(setpoint);
            double output = pidf.calculate(shooter.getVelocity(), pidf.getSetPoint());
            shooter.set(output);
    }

    public void off() {
        shooter.set(0.3);
    }

    public double getRPM(){
        return shooter.getVelocity();
    }

    public void lowRPM(){
        double output = pidf.calculate(shooter.getVelocity(), setpoint);
        shooter.set(output);
    }

    public void highRPM(){
        double output = pidf.calculate(shooter.getVelocity(), highSetpoint);
        shooter.set(output);
    }
}
