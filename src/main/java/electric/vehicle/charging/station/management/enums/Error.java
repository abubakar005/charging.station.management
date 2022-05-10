package electric.vehicle.charging.station.management.enums;

public enum Error {

    SUCCESS_CODE(1000, "success"),
    GENERAL_ERROR(1001, "General exception occurred"),
    PARENT_COMPANY_NOT_FOUND(1002, "Parent company not found against the Id %s"),
    COMPANY_NOT_FOUND(1003, "Company not found against the Id %s"),
    STATION_NOT_FOUND(1004, "Station not found against the Id %s"),
    PARENT_NOT_ALLOWED(1005, "Two companies can not be parent to each other"),
    ;

    private final int code;
    private final String msg;

    Error(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}
