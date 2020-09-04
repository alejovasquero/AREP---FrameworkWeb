package edu.escuelaing.arep;

import edu.escuelaing.arep.httpserver.Request;
import edu.escuelaing.arep.webfram.WebFramework;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WebFrameworkTest {

    @Before
    public void shouldReturnGoodPath(){
        WebFramework.setHomeFolder("src/main/java/webapp");
    }

    @Test
    public void testTotalPath(){
        assertEquals(WebFramework.getPath("/Heeloo"), "src/main/java/webapp/Heeloo");
        assertEquals(WebFramework.getPath("/1"), "src/main/java/webapp/1");
        assertEquals(WebFramework.getPath("/2"), "src/main/java/webapp/2");
        assertEquals(WebFramework.getPath("/3"), "src/main/java/webapp/3");
        assertEquals(WebFramework.getPath("/4"), "src/main/java/webapp/4");
    }

    @Test
    public void shouldExistResource(){
        WebFramework.get("ome", (a,m) -> funcion(a,m));
        WebFramework.get("abc", (a,m) -> funcion(a,m));
        WebFramework.get("13344", (a,m) -> funcion(a,m));
        WebFramework.get("ddc", (a,m) -> funcion(a,m));
        WebFramework.get("oceew", (a,m) -> funcion(a,m));

        assertEquals(WebFramework.getResource("ome", null), "ome");
        assertEquals(WebFramework.getResource("abc", null), "abc");
        assertEquals(WebFramework.getResource("13344", null), "13344");
        assertEquals(WebFramework.getResource("ddc", null), "ddc");
        assertEquals(WebFramework.getResource("oceew", null), "oceew");

    }

    public String funcion(String a, Map m){
        return a;
    }


    @Test
    public void shouldNotSupportFormat() {
        HashMap<String, String[]> a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.pnfffbfvg", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        Request req = new Request(a);
        assertFalse(WebFramework.isSupported(req));

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.not.", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "tt/html;*/*;ff;"});

        req = new Request(a);
        assertFalse(WebFramework.isSupported(req));


        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.ttt", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertFalse(WebFramework.isSupported(req));


        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.csfffs", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "tecss;ff;"});

        req = new Request(a);
        assertFalse(WebFramework.isSupported(req));

        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/style.sss", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertFalse(WebFramework.isSupported(req));
    }

    @Test
    public void shouldSupportFormat() {
        WebFramework.get("/prueba.png", (t,m) -> funcion(t,m));
        HashMap<String, String[]> a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.png", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        Request req = new Request(a);
        assertTrue(WebFramework.isSupported(req));
        WebFramework.get("/prueba", (t,m) -> funcion(t,m));
        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/html;*/*;ff;"});

        req = new Request(a);
        assertTrue(WebFramework.isSupported(req));

        WebFramework.get("/prueba.jpg", (t,m) -> funcion(t,m));
        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.jpg", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertTrue(WebFramework.isSupported(req));

        WebFramework.get("/prueba.css", (t,m) -> funcion(t,m));
        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/prueba.css", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "text/css;ff;"});

        req = new Request(a);
        assertTrue(WebFramework.isSupported(req));
        WebFramework.get("/style.js", (t,m) -> funcion(t,m));
        a = new HashMap<String, String[]>();
        a.put("GET", new String[]{"GET", "/style.js", "HTTP"});
        a.put("Accept:", new String[]{"Accept:", "png/**;ff;"});

        req = new Request(a);
        assertTrue(WebFramework.isSupported(req));
    }
}
