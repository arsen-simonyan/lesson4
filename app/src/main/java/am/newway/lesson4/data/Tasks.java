package am.newway.lesson4.data;

import android.net.Uri;

public class Tasks
{
    private String strName;
    private String strDescription;
    private Uri uriImage;
    private String strType;

    public Tasks( String strName , String strDescription , Uri uriImage, String strType )
    {
        this.strName = strName;
        this.strDescription = strDescription;
        this.uriImage = uriImage;
        this.strType = strType;
    }

    public String getStrName()
    {
        return strName;
    }

    public String getStrDescription()
    {
        return strDescription;
    }

    public Uri getUriImage()
    {
        return uriImage;
    }

    public String getStrType()
    {
        return strType;
    }
}
