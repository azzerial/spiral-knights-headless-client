package com.threerings.projectx.data;

import com.threerings.presents.net.AuthResponseData;
import com.threerings.util.Name;

public class ProjectXAuthResponseData extends AuthResponseData {

    public String ident;
    public String sessionId;
    public Name authName;

    /* Constructors */

    public ProjectXAuthResponseData() {}
}