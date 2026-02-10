package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import org.firstinspires.ftc.teamcode.Subsystems.Drive;
import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    private final Drive drive;
    private final DoubleSupplier xSupplier, ySupplier, rxSupplier;

    public DriveCommand(Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier, DoubleSupplier rxSupplier) {
        this.drive = drive;
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
        this.rxSupplier = rxSupplier;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        // Get the latest joystick values from the suppliers and pass them to the subsystem
        drive.periodic(xSupplier.getAsDouble(), ySupplier.getAsDouble(), rxSupplier.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
