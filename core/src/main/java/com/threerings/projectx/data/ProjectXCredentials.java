package com.threerings.projectx.data;

import com.samskivert.util.StringUtil;
import com.threerings.presents.net.UsernamePasswordCreds;
import com.threerings.util.Name;

public class ProjectXCredentials extends UsernamePasswordCreds {

    public String ident;
    public String language;
    public String sessionId;
    public byte[] ticket;
    public boolean segaPass;
    public boolean steamClient;
    public int siteId;
    public String invite;
    public String region;

    /* Constructors */

    public ProjectXCredentials() {}

    public ProjectXCredentials(String username, String password) {
        super(new Name(username), StringUtil.md5hex(password));
        this.ident = "CZHNmYWtsd2VxZmprbGpvZGxrc2pmb2lq";
        this.language = "en";
        this.sessionId = null;
        this.ticket = null;
        this.segaPass = false;
        this.steamClient = false;
        this.siteId = 0;
        this.invite = null;
        this.region = "us-east";
    }

    /* Methods */

    @Override
    protected final void toString(StringBuilder sb) {
        super.toString(sb);
        sb.append(", ident=").append(this.ident);
    }
}