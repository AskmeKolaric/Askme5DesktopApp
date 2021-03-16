package TachografReaderUI.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;


public class JSONUtils {

    /**
     * @return
     */
    public static Gson getGson() {
        return new GsonBuilder().setLenient().create();
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        Gson gson = getGson();
        return gson.fromJson(unescapeJavaString(json.trim()), clazz);
    }

    /**
     * @param json
     * @param clazz
     * @param unescape
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz, boolean unescape) {
        Gson gson = getGson();
        if (unescape) {
            return gson.fromJson(unescapeJavaString(json), clazz);
        } else {
            return gson.fromJson(json, clazz);
        }

    }

    /**
     * @param json
     * @param type
     * @return
     */
    public static <T> T fromJson(String json, Type type) {
        Gson gson = getGson();
        return gson.fromJson(unescapeJavaString(json), type);
    }

    /**
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        Gson gson = getGson();
        return gson.toJson(object);
    }

    /**
     * @param st
     * @return
     */
    public static String unescapeJavaString(String st) {
        if (st.charAt(0) == '"') st = st.substring(1, st.length() - 1);

        StringBuilder sb = new StringBuilder(st.length());

        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (ch == '\\') {
                char nextChar = (i == st.length() - 1) ? '\\' : st.charAt(i + 1);
                // Octal escape?
                if (nextChar >= '0' && nextChar <= '7') {
                    String code = "" + nextChar;
                    i++;
                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
                        code += st.charAt(i + 1);
                        i++;
                        if ((i < st.length() - 1) && st.charAt(i + 1) >= '0' && st.charAt(i + 1) <= '7') {
                            code += st.charAt(i + 1);
                            i++;
                        }
                    }
                    sb.append((char) Integer.parseInt(code, 8));
                    continue;
                }
                switch (nextChar) {
                    case '\\':
                        ch = '\\';
                        break;
                    case 'b':
                        ch = '\b';
                        break;
                    case 'f':
                        ch = '\f';
                        break;
                    case 'n':
                        ch = '\n';
                        break;
                    case 'r':
                        ch = '\r';
                        break;
                    case 't':
                        ch = '\t';
                        break;
                    case '\"':
                        ch = '\"';
                        break;
                    case '\'':
                        ch = '\'';
                        break;
                    // Hex Unicode: u????
                    case 'u':
                        if (i >= st.length() - 5) {
                            ch = 'u';
                            break;
                        }
                        int code = Integer.parseInt("" + st.charAt(i + 2) + st.charAt(i + 3) + st.charAt(i + 4) + st.charAt(i + 5),
                                16);
                        sb.append(Character.toChars(code));
                        i += 5;
                        continue;
                }
                i++;
            }
            sb.append(ch);
        }
        return sb.toString();
    }
}

