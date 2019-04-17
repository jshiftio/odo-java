package io.jshift.odo.detectors.service;

public class JdbcUrlParser {

    public static String getDatabase(String jdbcUrl) {

        if (jdbcUrl.contains("postgresql")) {
            return parsePostgreSql(jdbcUrl);
        } else {
            if (jdbcUrl.contains("mysql")) {
                return parseMySql(jdbcUrl);
            } else {
                return "";
            }
        }
    }

    private static String parseMySql(String jdbcUrl) {

        final String databasePart = jdbcUrl.substring(jdbcUrl.lastIndexOf('/') + 1);
        return removeParameters(databasePart, '?');
    }

    private static String parsePostgreSql(String jdbcUrl) {

        final String databasePart = jdbcUrl.substring(jdbcUrl.lastIndexOf('/') + 1);
        return removeParameters(databasePart, '?');
    }

    private static String removeParameters(String databasePart, char separator) {
        int paramsIndex = databasePart.indexOf(separator);

        if (paramsIndex > -1) {
            return databasePart.substring(0, paramsIndex);
        }

        return databasePart;
    }

}
