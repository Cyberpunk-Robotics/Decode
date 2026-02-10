package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class Indexer extends SubsystemBase {
    private final MotorEx indexer;

    public Indexer(HardwareMap hardwareMap) {
        indexer = new MotorEx(hardwareMap, "indexer");
        indexer.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        indexer.setRunMode(Motor.RunMode.RawPower);
        indexer.setInverted(true);
    }
    public void run() {
        indexer.set(1);
    }
    public void reverse(){
        indexer.set(-0.7);
    }
    public void off(){
        indexer.set(0);
    }

    public void lowreverse(){indexer.set(-0.6);}

}
