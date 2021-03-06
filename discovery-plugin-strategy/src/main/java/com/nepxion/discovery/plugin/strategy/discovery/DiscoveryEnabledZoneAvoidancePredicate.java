package com.nepxion.discovery.plugin.strategy.discovery;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import java.util.Map;

import com.nepxion.discovery.plugin.framework.adapter.PluginAdapter;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.LoadBalancerStats;
import com.netflix.loadbalancer.PredicateKey;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidancePredicate;

public class DiscoveryEnabledZoneAvoidancePredicate extends ZoneAvoidancePredicate {
    protected PluginAdapter pluginAdapter;
    protected DiscoveryEnabledAdapter discoveryEnabledAdapter;

    public DiscoveryEnabledZoneAvoidancePredicate(IRule rule, IClientConfig clientConfig) {
        super(rule, clientConfig);
    }

    public DiscoveryEnabledZoneAvoidancePredicate(LoadBalancerStats lbStats, IClientConfig clientConfig) {
        super(lbStats, clientConfig);
    }

    @Override
    public boolean apply(PredicateKey input) {
        boolean enabled = super.apply(input);
        if (!enabled) {
            return false;
        }

        return apply(input.getServer());
    }

    protected boolean apply(Server server) {
        Map<String, String> metadata = pluginAdapter.getServerMetadata(server);

        return discoveryEnabledAdapter.apply(server, metadata);
    }

    public void setPluginAdapter(PluginAdapter pluginAdapter) {
        this.pluginAdapter = pluginAdapter;
    }

    public void setDiscoveryEnabledAdapter(DiscoveryEnabledAdapter discoveryEnabledAdapter) {
        this.discoveryEnabledAdapter = discoveryEnabledAdapter;
    }
}