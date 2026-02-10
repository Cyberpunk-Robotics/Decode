package org.firstinspires.ftc.teamcode.Commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Drive;

import java.util.function.DoubleSupplier;

public class DriveNormalizedCommand extends CommandBase {
    private Drive drive;
    private final DoubleSupplier xSupplier, ySupplier, rxSupplier;

    public DriveNormalizedCommand(Drive drive,  DoubleSupplier xSupplier, DoubleSupplier ySupplier, DoubleSupplier rxSupplier){
        this.drive = drive;
        this.xSupplier = xSupplier;
        this.ySupplier = ySupplier;
        this.rxSupplier = rxSupplier;
        addRequirements(drive);
    }
    public void execute(){
        drive.normalizedPeriodic(xSupplier.getAsDouble(), ySupplier.getAsDouble(), rxSupplier.getAsDouble());
    }
    public boolean isFinished(){
        return false;
    }

}
