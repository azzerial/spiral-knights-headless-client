package com.threerings.projectx.data;

import com.threerings.config.dist.data.DConfigBootstrapData;

public class ProjectXBootstrapData extends DConfigBootstrapData {

    public String hostname;
    public int[] ports;
    public int[] datagramPorts;
    public int serverOid;
    public int exchangeOid;
    public int pvpOid;
    public int pvpGameInfoOid;
    public int missionOid;
    public int openHallOid;
    public int adminOid;
    public int statusOid;
    public int placeBrowserOid;
    public int nodePulseOid;
    public int clientTimingsOid;

    /* Constructors */

    public ProjectXBootstrapData() {}
}