package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Pinpoint Test", group="Test")
public class PinpointTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        GoBildaPinpointDriver pinpoint =
                hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        telemetry.addData("Pinpoint is null?", pinpoint == null);
        telemetry.update();

        waitForStart();

        if (pinpoint != null) {
            pinpoint.resetPosAndIMU();
        }
    }
}
