package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

public class Intake extends SubsystemBase {
    private MotorEx intake;
    public Intake(HardwareMap hardwareMap){
        intake = new MotorEx(hardwareMap, "intake");
        intake.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        intake.setRunMode(Motor.RunMode.RawPower);
        intake.setInverted(false);
    }
    public void on(){
        intake.set(1);
    }
    public void reverseIntake(){
        intake.set(-1);
    }
    public void off(){
        intake.set(0);
    }
}
