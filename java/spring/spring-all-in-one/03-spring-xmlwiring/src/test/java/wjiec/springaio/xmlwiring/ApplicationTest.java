package wjiec.springaio.xmlwiring;

import com.wjiec.springaio.xmlwiring.Application;
import com.wjiec.springaio.xmlwiring.model.Author;
import com.wjiec.springaio.xmlwiring.model.Music;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/beans.xml")
class ApplicationTest {

    @Test
    void contextLoaded() {}

    @Autowired
    private Author author;

    @Test
    void authorShouldNotBeNull() {
        assertNotNull(author);
    }

    @Autowired
    private Music music;

    @Test
    void musicShouldNotBeNull() {
        assertNotNull(music);
    }

    @Test
    void authorShouldEquals() {
        assertEquals(author, music.getAuthor());
    }

}
