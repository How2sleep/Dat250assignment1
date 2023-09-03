package dat250exp1;

import io.javalin.Javalin;

public class App {

    private static final String WEBPAGE = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>Convert units</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<h1>Unit converter</h1>\n" +
            "<form action=\"/convert\" method=\"post\">\n" +
            "    <fieldset>\n" +
            "    <label for=\"val\">Value:</label>" +
            "    <input id=\"val\" type=\"text\" name=\"value\"><br />\n" +
            "    <label for=\"source-unit\">From unit:</label>\n" +
            "    <select name=\"sunit\" id=\"source-unit\">\n" +
            "        <option value=\"in\">Inches</option>\n" +
            "        <option value=\"ft\">Feet</option>\n" +
            "        <option value=\"mi\">Miles</option>\n" +
            "        <option value=\"m\">Metres</option>\n" +
            "    </select><br />\n" +
            "    <label for=\"target-unit\">To unit:</label>\n" +
            "    <select name=\"tunit\" id=\"target-unit\">\n" +
            "        <option value=\"in\">Inches</option>\n" +
            "        <option value=\"ft\">Feet</option>\n" +
            "        <option value=\"mi\">Miles</option>\n" +
            "        <option value=\"m\">Metres</option>\n" +
            "    </select><br />\n" +
            "    <input type=\"submit\" value=\"Calculate\" />\n" +
            "    </fieldset>\n" +
            "</form>\n" +
            "</body>\n" +
            "</html>";

    private static final double IN_TO_METER = 0.0254;
    private static final double FT_TO_METER = 0.3048;
    private static final double MI_TO_METER = 1609.344;


    public static void main(String[] args) {
        Javalin.create()
                .get("/", ctx -> {
                    ctx.html(WEBPAGE);
                })
                .post("/convert", ctx -> {
                    double value = Double.parseDouble(ctx.formParam("value"));
                    String fromUnit = ctx.formParam("sunit");
                    String toUnit = ctx.formParam("tunit");
                    double result = convert(value, fromUnit, toUnit);
                    ctx.result(Double.toString(result));
                })
                .start(9000);
    }
/** Converts a value between units.
     * @param value The value to convert.
     * @param fromUnit The unit to convert from.
     * @param toUnit The unit to convert to.
     * @return The converted value.
     */
    public static double convert (double value, String fromUnit, String toUnit) {
        double inMeters;
        if (fromUnit.equals("in")) {
            inMeters = fromInches(value);
        } else if (fromUnit.equals("ft")) {
            inMeters = fromFeet(value);
        } else if (fromUnit.equals("mi")) {
            inMeters = fromMiles(value);
        } else if (fromUnit.equals("m")) {
            inMeters = value;
        } else {
            inMeters = Double.NaN;
        }
        double result;
        if (toUnit.equals("in")) {
            result = toInches(inMeters);
        } else if (toUnit.equals("ft")) {
            result = toFeet(inMeters);
        } else if (toUnit.equals("mi")) {
            result = toMiles(inMeters);
        } else if (toUnit.equals("m")) {
            result = inMeters;
        } else {
            result = Double.NaN;
        }
        return result;
    }
    /** Conversion methods*/
    public static double fromFeet (double value) {
        return value * FT_TO_METER;
    }

    public static double fromInches (double value) {
        return value * IN_TO_METER;
    }

    public static double fromMiles (double value) {
        return value * MI_TO_METER;
    }
    public static double toInches (double value) {
        return value / IN_TO_METER;
    }
    public static double toFeet (double value) {
        return value / FT_TO_METER;
    }
    public static double toMiles (double value) {
        return value / MI_TO_METER;
    }
}