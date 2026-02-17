package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

public class Turret extends SubsystemBase {
    private ServoEx turret;
    private Limelight limelight;

    // This is the proportional gain for our P-controller.
    // It's calculated to map the tx range (-28 to 28) to the servo range (0.0 to 1.0).
    private final double kP = 0.5 / 28.0; // (servo range / 2) / max_tx
    private final double SERVO_CENTER = 0.5;

    public Turret(HardwareMap hardwareMap, Limelight limelight){
        turret = new ServoEx(hardwareMap, "turret");
        turret.setCachingTolerance(0.00001);
        turret.setInverted(true);
        this.limelight = limelight;
    }

    public void periodic(){
        if (limelight.isValid()) {
            double tx = limelight.getTx();

            // This is a "deadband". If the target is already centered, the servo won't move.
            if (tx < -0.5 || tx > 0.5 ) {
                // This P-controller calculates a target position based on the error (tx).
                double targetPosition = turret.getRawPosition() + tx * 0.0003;

                // Clamp the value to the valid servo range of [0.0, 1.0].
                targetPosition = Math.max(0.08, Math.min(0.8, targetPosition));

                turret.set(targetPosition);
            }
        }

    }
    public void init(){
        turret.set(0.5);
    }
    public double getTurretPose(){
        return turret.getRawPosition();
    }
}
