package am.newway.lesson4.enums;

public enum TaskType
{
    IMP( "important" ),
    ORD( "ordinary" ),
    UNI( "unimportant" );

    private String type;

    TaskType( String type )
    {
        this.type = type;
    }

    public String get()
    {
        return type;
    }

    public static String toString( TaskType tsk )
    {
        return tsk.get();
    }
}
