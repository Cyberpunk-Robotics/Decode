//package org.firstinspires.ftc.teamcode.Commands;
//
//import com.seattlesolvers.solverslib.command.CommandBase;
//
//import org.firstinspires.ftc.teamcode.Subsystems.Drive;
//
//import java.util.function.DoubleSupplier;
//
//public class FieldCentricCommand extends CommandBase {
//    private Drive drive;
//    private final DoubleSupplier xSupplier, ySupplier, rxSupplier;
//
//    public FieldCentricCommand(Drive drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier, DoubleSupplier rxSupplier){
//        this.drive = drive;
//        this.xSupplier = xSupplier;
//        this.ySupplier = ySupplier;
//        this.rxSupplier = rxSupplier;
//        addRequirements(drive);
//    }
//    @Override
//    public void execute(){
//        drive.fieldCentric(xSupplier.getAsDouble(), ySupplier.getAsDouble(), rxSupplier.getAsDouble());
//    }
//    @Override
//    public boolean isFinished(){
//        return false;
//    }
//}
