package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

public class Turret extends SubsystemBase {
    private DcMotorEx odometry;
    private ServoEx turret;
    private Limelight limelight;
    public Turret(HardwareMap hardwareMap, Limelight limelight) {
        odometry = hardwareMap.get(DcMotorEx.class, "odometry");
        turret = new ServoEx(hardwareMap, "turret");
        turret.setInverted(false);
        this.limelight = limelight;
    }
}
