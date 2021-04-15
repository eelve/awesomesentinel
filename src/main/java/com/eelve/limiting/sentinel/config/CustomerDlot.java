package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.node.Node;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.spi.SpiOrder;
import lombok.extern.java.Log;

/**
 * @ClassName CustomDlot
 * @Description 按照SpiOrder顺序扫描
 * @Author zhao.zhilue
 * @Date 2021/4/15 23:30
 * @Version 1.0
 **/
@SpiOrder(-3500)
@Log
public class CustomerDlot extends AbstractLinkedProcessorSlot<DefaultNode> {
    /**
     * Entrance of this slot.
     *
     * @param context         current {@link Context}
     * @param resourceWrapper current resource
     * @param param           generics parameter, usually is a {@link Node}
     * @param count           tokens needed
     * @param prioritized     whether the entry is prioritized
     * @param args            parameters of the original call
     * @throws Throwable blocked exception or unexpected error
     */
    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode param, int count, boolean prioritized, Object... args) throws Throwable {
        log.info("Current context:" + context.getName() + "Current entry resource:" + context.getCurEntry().getResourceWrapper().getName());
        fireEntry(context,resourceWrapper,param,count,prioritized,args);
    }

    /**
     * Exit of this slot.
     *
     * @param context         current {@link Context}
     * @param resourceWrapper current resource
     * @param count           tokens needed
     * @param args            parameters of the original call
     */
    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args) {
        log.info("Exting for entry on CustomerDlot" + context.getCurEntry().getResourceWrapper().getName());
        fireExit(context,resourceWrapper,count,args);
    }
}
