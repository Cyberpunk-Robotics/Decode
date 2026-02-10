package org.firstinspires.ftc.teamcode.Commands.IndexerCommands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.Subsystems.Indexer;

public class IndexerReverseCommand extends CommandBase {
    private Indexer indexer;

    public IndexerReverseCommand(Indexer indexer) {
        this.indexer = indexer;
        addRequirements(indexer);
    }

    @Override
    public void execute() {
        indexer.reverse();
    }

    @Override

    public void end(boolean interrupted) {
        indexer.off();
    }

}



