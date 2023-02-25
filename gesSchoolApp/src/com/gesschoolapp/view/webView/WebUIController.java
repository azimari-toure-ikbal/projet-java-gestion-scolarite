package com.gesschoolapp.view.webView;

import com.gesschoolapp.runtime.Main;
import com.gesschoolapp.utils.Toolbox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class WebUIController implements Initializable {

    @FXML
    private WebView webView;

    private WebEngine engine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine = webView.getEngine();
        loadPage();
            writeFilePath();
    }

    public void writeFilePath(){
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
// read script file
            try {
                engine.eval(Files.newBufferedReader(Paths.get("src/com/gesschoolapp/resources/pdf_viewer-master/js/main.js"), StandardCharsets.UTF_8));
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            }

            Invocable inv = (Invocable) engine;
// call function from script file
            try {
                inv.invokeFunction("myFunction", "./docs/pdf.pdf");
            } catch (ScriptException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public void loadPage(){
        URL url = this.getClass().getResource("/com/gesschoolapp/resources/pdf_viewer-master/index.html");
        engine.load(url.toString());
    }
}
