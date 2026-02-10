package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.hardware.servos.ServoEx;

@TeleOp(name = "ServoResetPos", group = "Test")
public class ServoReset extends OpMode {
    private ServoEx servo;
    private double pos = 0;
    @Override
    public void init() {
        servo = new ServoEx(hardwareMap, "servo");
        servo.set(0);
    }

    @Override
    public void loop() {
        servo.set(pos);
        if (gamepad1.dpadLeftWasPressed()) {
            pos += 0.02;
        }
        if (gamepad1.dpadRightWasPressed()) {
            pos -= 0.02;
        }
        telemetry.addData("ServoPos", servo.getRawPosition());
        telemetry.addData("Pos", pos);
    }
}
