package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.controller.PIDFController;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.LookUpTable.LinearLookupTable;

public class Shooter extends SubsystemBase {
    private final MotorEx shooter, shooter2;
    double P = 0.07;
    double F = 0.00052;
    PIDFController pidf;
    public double setpoint = 1530; // For lowRPM()
    public int highSetpoint = 1210; // For highRPM()
    private final Limelight limelight;

    private LinearLookupTable lookUpTable;

    double[] distance = new double[]{
            130,
            135,
            140,
            145,
            150,
            155,
            160,
            165,
            170,
            175,
            180,
            185,
            190,
            195,
            200,
            205,
            210,
            215,
            220,
            225,
            230,
            235,
            240,
            245

    };
    double[] calibratedRPM = new double[]{
            1400, 1420, 1420, 1440, 1460, 1460, 1480, 1480, 1500, 1520, 1540, 1560, 1620, 1640, 1660, 1680, 1700, 1720, 1720, 1740, 1760, 1800, 1820, 1860
    };



    public Shooter(HardwareMap hardwareMap, Limelight limelight) {
        shooter = new MotorEx(hardwareMap, "shooter");
        shooter2 = new MotorEx(hardwareMap, "shooter2");
        shooter.setRunMode(MotorEx.RunMode.RawPower);
        shooter2.setRunMode(MotorEx.RunMode.RawPower);
        shooter.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        shooter2.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        shooter.setInverted(true);
        shooter2.setInverted(true);
        pidf = new PIDFController(P, 0, 0, F);
        this.limelight = limelight;

        lookUpTable = new LinearLookupTable(distance, calibratedRPM);
    }

    public void on(){
            // Get distance from Limelight and find RPM from the lookup table
            setpoint = limelight.getShooterRPM();
            double output = pidf.calculate(shooter.getVelocity(), limelight.getDistanceToBasket());
            shooter.set(output);
            shooter2.set(output);

    }

    public void setTargetRpm (double rpm) {
        setpoint = rpm;
    }

    public void off() {
        shooter.set(0.2);
    }

    public double getRPM(){
        return shooter.getVelocity();
    }

    public void lowRPM(){
        double output = pidf.calculate(shooter2.getVelocity(), setpoint);
        shooter.set(output);
    }

    public void highRPM(){
        double output = pidf.calculate(shooter.getVelocity(), highSetpoint);
        shooter.set(output);
    }
}
