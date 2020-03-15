package co.alexbruvv.tempmute.common;

public enum TimeUnit
{

    SECOND("seconds", 's', 1000),
    MINUTE("minutes", 'm', 60000),
    HOUR("hours", 'h', 3600000),
    DAY("days", 'd', 86400000);

    private String name;
    private char code;
    private long millis;

    TimeUnit(String name, char code, long millis)
    {
        this.name = name;
        this.code = code;
        this.millis = millis;
    }

    public static TimeUnit fromCode(char code)
    {
        for (TimeUnit unit : TimeUnit.values())
        {
            if (unit.getCode() == code)
                return unit;
        }

        return null;
    }

    public String getName()
    {
        return name;
    }

    public char getCode()
    {
        return code;
    }

    public long getMillis()
    {
        return millis;
    }
}
