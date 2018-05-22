package command;

import command.changeType.ChangeToActorCommand;
import controller.CanvasControllerTest;
import controller.InteractionControllerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ChangeToActorCommandTest.class,
        ChangeToCommunicationCommandTest.class,
        ChangeToObjectCommandTest.class,
        ChangeToSequenceCommandTest.class,
        CloseSubwindowCommandTest.class
})
public class CommandTestSuite {
}
