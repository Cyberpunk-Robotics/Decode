package org.firstinspires.ftc.teamcode.Commands.CombinedCommands;

import com.seattlesolvers.solverslib.command.ParallelCommandGroup;
import com.seattlesolvers.solverslib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.Commands.IndexerCommands.IndexerForwardCommand;
import org.firstinspires.ftc.teamcode.Commands.IntakeCommands.IntakeCommand;
import org.firstinspires.ftc.teamcode.Subsystems.Indexer;
import org.firstinspires.ftc.teamcode.Subsystems.Intake;

public class IntakeIndexerCommand extends ParallelCommandGroup {
        private Intake intake;
        private Indexer indexer;
        public IntakeIndexerCommand(Intake intake, Indexer indexer){
            addCommands(
                    new IntakeCommand(intake),
                    new IndexerForwardCommand(indexer),
                    new WaitCommand(1500)
            );
            addRequirements(intake, indexer);
        }
}
