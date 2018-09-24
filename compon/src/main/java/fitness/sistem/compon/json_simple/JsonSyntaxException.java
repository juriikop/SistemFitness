package fitness.sistem.compon.json_simple;

import java.net.ProtocolException;

public class JsonSyntaxException extends ProtocolException {
    static String syntaxError = "syntax error: ";
    public JsonSyntaxException() {}
    public JsonSyntaxException(String message) {
        super(syntaxError + message);
    }
}
