package edu.escuelaing.arep;

import edu.escuelaing.arep.httpserver.Request;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;


/**
 * Unit test for simple App.
 */
public class RequestTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldGetHeaderKey() {
        HashMap<String, String[]> a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.png", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        Request req = new Request(a);
        assertEquals(req.getResponse(), "png");

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/html;*/*;ff;"});

        req = new Request(a);
        assertEquals(req.getResponse(), "text/html");


        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.jpg", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertEquals(req.getResponse(), "jpg");


        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.css", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/css;ff;"});

        req = new Request(a);
        assertEquals(req.getResponse(), "text/css");

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/style.js", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertEquals(req.getResponse(), "js");
    }

    @Test
    public void resourceTest(){
        HashMap<String, String[]> a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.png", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        Request req = new Request(a);
        assertEquals(req.getResource(), "/prueba.png");

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/ome", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/html;*/*;ff;"});

        req = new Request(a);
        assertEquals(req.getResource(), "/ome");


        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/XXX", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertEquals(req.getResource(), "/XXX");


        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/BB.cita", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/css;ff;"});

        req = new Request(a);
        assertEquals(req.getResource(), "/BB.cita");

    }

    @Test
    public void shouldNotBeImage(){
        HashMap<String, String[]> a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/html;**;ff;"});

        Request req = new Request(a);
        assertFalse(req.isImage());

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/html;*/*;ff;"});

        req = new Request(a);
        assertFalse(req.isImage());

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.css", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/css;ff;"});

        req = new Request(a);
        assertFalse(req.isImage());

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/style.js", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertFalse(req.isImage());
    }

    @Test
    public void shouldBeImage(){
        HashMap<String, String[]> a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.png", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        Request req = new Request(a);
        assertTrue(req.isImage());

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.jpg", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertTrue(req.isImage());
    }
}
