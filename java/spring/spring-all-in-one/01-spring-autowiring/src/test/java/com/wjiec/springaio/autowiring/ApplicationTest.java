package com.wjiec.springaio.autowiring;

import com.wjiec.springaio.autowiring.worker.Programmer;
import com.wjiec.springaio.autowiring.worker.Treasurer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Application.class)
class ApplicationTest {

    @Test
    void contextLoaded() {}

    @Autowired
    private Programmer programmer;

    @Test
    void programmerShouldNotBeNull() {
        assertNotNull(programmer);
    }

    @Autowired
    private Treasurer treasurer;

    @Test
    void treasurerShouldNotBeNull() {
        assertNotNull(treasurer);
    }

    @Value("${keyboard.name}")
    private String keyboardName;

    @Value("${mouse.name}")
    private String mouseName;

    @Test
    void programmerWorking() throws Exception {
        String output = tapSystemOut(programmer::work);

        assertThat(output, containsString(keyboardName));
        assertThat(output, containsString(mouseName));
    }

    @Value("${calculator.name}")
    private String calculatorName;

    @Test
    void treasurerWorking() throws Exception {
        String output = tapSystemOut(treasurer::work);

        assertThat(output, containsString(calculatorName));
    }

}
