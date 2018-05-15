package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import window.frame.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DiagramSubwindowFrameCornerTest.class,
        DiagramSubwindowFrameRectangleTest.class,
        DiagramSubwindowFrameTest.class,
        TitleBarClickTest.class,
        TitleBarTest.class
})

public class WindowElementsTests {
}
