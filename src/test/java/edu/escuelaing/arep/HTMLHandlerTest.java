package edu.escuelaing.arep;
import edu.escuelaing.arep.handlers.HTMLHandler;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HTMLHandlerTest  {
    private static Double TOLERANCE=0.1d;

    /**
     * Rigourous Test :-)
     */
    @Test
    public void openTest() throws IOException {
        HTMLHandler h = new HTMLHandler("src/test/resources/HTMLHandler/Test1.txt");
        assertEquals(h.getData(), "Este es un mensaje de prueba.");

        h = new HTMLHandler("src/test/resources/HTMLHandler/Test2.txt");
        assertEquals(h.getData(), "Hello, testing.");

        h = new HTMLHandler("src/test/resources/HTMLHandler/Test3.txt");
        assertEquals(h.getData(), "Palabra abc abc eee abc");
    }

    @Test
    public void replaceTest() throws IOException {
        HTMLHandler h = new HTMLHandler("src/test/resources/HTMLHandler/Test1.txt");
        h.replace("Este", "PESTE");
        assertEquals(h.getData(), "PESTE es un mensaje de prueba.");

        h = new HTMLHandler("src/test/resources/HTMLHandler/Test2.txt");
        h.replace("testing", "success");
        assertEquals(h.getData(), "Hello, success.");

        h = new HTMLHandler("src/test/resources/HTMLHandler/Test3.txt");
        h.replace("abc", "dfg");
        assertEquals(h.getData(), "Palabra dfg dfg eee dfg");
    }




}
